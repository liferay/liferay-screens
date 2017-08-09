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

@objc open class ScreensWKWebView: NSObject, ScreensWebView, WKNavigationDelegate, WKScriptMessageHandler,
UIScrollViewDelegate {

	let defaultNamespace = "screensDefault"

	open var view: UIView {
		wkWebView.scrollView.backgroundColor = .clear
		return wkWebView
	}

	let wkWebView: WKWebView

	var scriptsToInject = [InjectableScript]()
	var initialNavigation: WKNavigation?

	let jsCallHandler: (String, String) -> Void
	let onPageLoadFinished: (String, Error?) -> Void
	let jsErrorHandler: (String) -> (Any?, Error?) -> Void

	public required init(jsCallHandler: @escaping (String, String) -> Void,
		jsErrorHandler: @escaping (String) -> (Any?, Error?) -> Void,
		onPageLoadFinished: @escaping (String, Error?) -> Void) {

		self.jsCallHandler = jsCallHandler
		self.jsErrorHandler = jsErrorHandler
		self.onPageLoadFinished = onPageLoadFinished
		self.wkWebView = WKWebView()

		super.init()

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

	public func onDestroy() {
		wkWebView.configuration.userContentController.removeScriptMessageHandler(forName: defaultNamespace)
	}

	// MARK: UIScrollViewDelegate

	open func viewForZoomingInScrollView(scrollView: UIScrollView) -> UIView? {
		return nil
	}

	// MARK: WKScriptMessageHandler

	open func userContentController(_ userContentController: WKUserContentController,
	                                  didReceive message: WKScriptMessage) {

		guard let body = message.body as? [String] else { return }

		jsCallHandler(body[0], body[1])
	}

	// MARK: WKNavigationDelegate

	open func webView(_ webView: WKWebView, didFinish navigation: WKNavigation) {
		if initialNavigation == nil || initialNavigation != navigation {

			scriptsToInject.forEach { script in
				webView.evaluateJavaScript(script.content, completionHandler: jsErrorHandler(script.name))
			}

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
