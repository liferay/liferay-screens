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

open class PortletDisplayView_default: BaseScreenletView, PortletDisplayViewModel, WKUIDelegate, WKNavigationDelegate,
	WKScriptMessageHandler, UIScrollViewDelegate {

	// MARK: Public properties

	open var automaticMode = false

	open lazy var wkWebView: WKWebView = WKWebView(frame: self.frame)

	var initialNavigation: WKNavigation?
	var progressPresenter = DefaultProgressPresenter()

	// MARK: BaseScreenletView

	override open var progressMessages: [String: ProgressMessages] {
		return [
			BaseScreenlet.DefaultAction: [
					.working: LocalizedString(
						"default", key: "portletdisplay-loading-message", obj: self),
					.failure: LocalizedString(
						"default", key: "portletdisplay-loading-error", obj: self)
			]
		]
	}

	override open func onCreated() {
		super.onCreated()
		wkWebView.injectViewportMetaTag()

		wkWebView.uiDelegate = self
		wkWebView.navigationDelegate = self
		wkWebView.scrollView.delegate = self

		addWebView()
	}

	// MARK: PortletDisplayViewModel

	public var initialHtml: String? {
		didSet {
			let server = SessionContext.currentContext?.session.server ?? ""
			initialNavigation = wkWebView.loadHTMLString(initialHtml!, baseURL: URL(string: server)!)

			progressPresenter.showHUDInView(self, message: LocalizedString(
				"default", key: "portletdisplay-loading-message", obj: self), forInteractor: Interactor())
		}
	}

	public func add(injectableScripts: [InjectableScript]) {
		injectableScripts.forEach { add(injectableScript: $0) }
	}

	public func add(injectableScript: InjectableScript) {
		wkWebView.loadScript(js: injectableScript.content)
	}

	public func inject(injectableScript: InjectableScript) {
		wkWebView.evaluateJavaScript(injectableScript.content, completionHandler: nil)
	}

	public func add(scriptHandler: String) {
		wkWebView.configuration.userContentController.add(self, name: scriptHandler)
	}

	// MARK: WKUIDelegate

	public func webView(_ webView: WKWebView, runJavaScriptAlertPanelWithMessage message: String,
			initiatedByFrame frame: WKFrameInfo, completionHandler: @escaping () -> Void) {

		completionHandler()
	}

	public func webView(_ webView: WKWebView, didFinish navigation: WKNavigation) {
		guard let initialNavigation = initialNavigation, initialNavigation != navigation
		else { return }

		progressPresenter.hideHud()

		if automaticMode {
			webView.evaluateJavaScript("window.Screens.listPortlets()", completionHandler: nil)
		}
	}

	func viewForZoomingInScrollView(scrollView: UIScrollView) -> UIView? {
		return nil
	}


	// MARK: WKScriptMessageHandler

	public func userContentController(_ userContentController: WKUserContentController,
			didReceive message: WKScriptMessage) {

		(screenlet as? PortletDisplayScreenlet)?.handleScriptHandler(key: message.name, body: message.body)
	}

	// MARK: Public methods

	open func addWebView() {
		wkWebView.translatesAutoresizingMaskIntoConstraints = false

		addSubview(wkWebView)

		let top = NSLayoutConstraint(item: wkWebView, attribute: .top, relatedBy: .equal,
		                             toItem: self, attribute: .top, multiplier: 1, constant: 0)
		let bottom = NSLayoutConstraint(item: wkWebView, attribute: .bottom, relatedBy: .equal,
		                                toItem: self, attribute: .bottom, multiplier: 1, constant: 0)
		let leading = NSLayoutConstraint(item: wkWebView, attribute: .leading, relatedBy: .equal,
		                                 toItem: self, attribute: .leading, multiplier: 1, constant: 0)
		let trailing = NSLayoutConstraint(item: wkWebView, attribute: .trailing, relatedBy: .equal,
		                                  toItem: self, attribute: .trailing, multiplier: 1, constant: 0)

		NSLayoutConstraint.activate([top, bottom, leading, trailing])
	}
}
