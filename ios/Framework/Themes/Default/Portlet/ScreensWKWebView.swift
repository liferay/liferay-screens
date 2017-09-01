/**
* Copyright (c) 2000-present Liferay, Inc. All rights reserved.
*
* This library is free software; you can redistribute it and/or modify it under
* the terms of the GNU Lesser General Public License as published by the Free
* Software Foundation; either version 2.1 of the License, or (at your option)
* any later version.
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*/
import UIKit
import WebKit

// swiftlint:disable weak_delegate
@objc open class ScreensWKWebView: NSObject, ScreensWebView, WKNavigationDelegate, WKScriptMessageHandler,
UIScrollViewDelegate {

	let defaultNamespace = "screensDefault"

	open var view: UIView {
		wkWebView.scrollView.backgroundColor = .clear
		return wkWebView
	}

	open var isScrollEnabled: Bool {
		get {
			return wkWebView.scrollView.isScrollEnabled
		}
		set {
			wkWebView.scrollView.isScrollEnabled = newValue
		}
	}

	let wkWebView: WKWebView
	lazy var uiDelegate: ScreensWKUIDelegate = ScreensWKUIDelegate(viewController: self.viewController)

	var scriptsToInject = [InjectableScript]()
	var initialNavigation: WKNavigation?
	var viewController: UIViewController? {
		didSet {
			wkWebView.uiDelegate = self.uiDelegate
		}
	}

	let jsCallHandler: ScreensWebView.JsCallHandler
	let jsErrorHandler: ScreensWebView.JsErrorHandler
	let onPageLoadFinished: ScreensWebView.OnPageLoadFinished

	public required init(jsCallHandler: @escaping ScreensWebView.JsCallHandler,
		jsErrorHandler: @escaping ScreensWebView.JsErrorHandler,
		onPageLoadFinished: @escaping ScreensWebView.OnPageLoadFinished) {

		self.jsCallHandler = jsCallHandler
		self.jsErrorHandler = jsErrorHandler
		self.onPageLoadFinished = onPageLoadFinished
		self.wkWebView = WKWebView()

		super.init()

        wkWebView.scrollView.backgroundColor = .clear
		wkWebView.injectViewportMetaTag()
		wkWebView.navigationDelegate = self
		wkWebView.scrollView.delegate = self
		wkWebView.configuration.userContentController.add(self, name: defaultNamespace)
	}

	open func add(injectableScript: InjectableScript) {
		scriptsToInject.append(injectableScript)
	}

	open func inject(injectableScript: InjectableScript) {
		wkWebView.evaluateJavaScript(injectableScript.content, completionHandler: jsErrorHandler(injectableScript.name))
	}

	open func load(request: URLRequest) {
		wkWebView.load(request)
	}

	open func load(htmlString: String) {
		let server = SessionContext.currentContext?.session.server ?? ""
		initialNavigation = wkWebView.loadHTMLString(htmlString, baseURL: URL(string: server)!)
	}

	open func onDestroy() {
		wkWebView.configuration.userContentController.removeScriptMessageHandler(forName: defaultNamespace)
	}

	open func clearCache() {
		wkWebView.clearCache()
	}

	// MARK: UIScrollViewDelegate

	open func viewForZoomingInScrollView(scrollView: UIScrollView) -> UIView? {
		return nil
	}

	// MARK: WKScriptMessageHandler

	open func userContentController(_ userContentController: WKUserContentController,
	                                  didReceive message: WKScriptMessage) {

		guard let body = message.body as? [String] else { return }

		if body[0] == "DOMContentLoaded" {
			scriptsToInject.forEach { script in
				wkWebView.evaluateJavaScript(script.content, completionHandler: jsErrorHandler(script.name))
			}
		}
		else {
			jsCallHandler(body[0], body[1])
		}
	}

	// MARK: WKNavigationDelegate

	public func webView(_ webView: WKWebView, didCommit navigation: WKNavigation!) {
		if initialNavigation == nil || initialNavigation != navigation {

			let notifyDOMContentLoaded = "document.addEventListener('DOMContentLoaded', function(event) {"
				+ "window.webkit.messageHandlers.screensDefault.postMessage(['DOMContentLoaded', '']);"
				+ "});"

			webView.evaluateJavaScript(notifyDOMContentLoaded, completionHandler: nil)
		}
	}

	open func webView(_ webView: WKWebView, didFinish navigation: WKNavigation) {
		if initialNavigation == nil || initialNavigation != navigation {
			onPageLoadFinished(webView.url?.absoluteString ?? "", nil)
		}
	}

	public func webView(_ webView: WKWebView,
	                    didFailProvisionalNavigation navigation: WKNavigation!, withError error: Error) {

		onPageLoadFinished(webView.url?.absoluteString ?? "", error)
	}

	public func webView(_ webView: WKWebView, didFail navigation: WKNavigation!, withError error: Error) {
		onPageLoadFinished(webView.url?.absoluteString ?? "", error)
	}

	public func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction,
			decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {

		decisionHandler(.allow)
	}

}
