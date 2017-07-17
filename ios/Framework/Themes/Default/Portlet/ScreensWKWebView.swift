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
		return wkWebView
	}

	let wkWebView: WKWebView

	let jsCallHandler: (String, String) -> Void
	let onPageLoadFinished: () -> Void

	public required init(jsCallHandler: @escaping (String, String) -> Void, onPageLoadFinished: @escaping () -> Void) {
		self.jsCallHandler = jsCallHandler
		self.onPageLoadFinished = onPageLoadFinished
		self.wkWebView = WKWebView()

		super.init()

		wkWebView.injectViewportMetaTag()
		wkWebView.navigationDelegate = self
		wkWebView.scrollView.delegate = self
		wkWebView.configuration.userContentController.add(self, name: defaultNamespace)
	}

	open func add(injectableScript: InjectableScript) {
		wkWebView.loadScript(js: injectableScript.content)
	}

	open func inject(injectableScript: InjectableScript) {
		wkWebView.evaluateJavaScript(injectableScript.content, completionHandler: nil)
	}

	open func load(request: URLRequest) {
		wkWebView.load(request)
	}

	open func load(htmlString: String) {
		let server = SessionContext.currentContext?.session.server ?? ""
		wkWebView.loadHTMLString(htmlString, baseURL: URL(string: server)!)
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
		onPageLoadFinished()
	}

}
