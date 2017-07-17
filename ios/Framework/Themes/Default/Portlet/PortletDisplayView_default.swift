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

open class PortletDisplayView_default: BaseScreenletView, PortletDisplayViewModel {

	// MARK: Public properties

	var progressPresenter = DefaultProgressPresenter()

	// MARK: PortletDisplayViewModel

	open var isThemeEnabled = false

	open var screensWebView: ScreensWebView?

	open func configureView(with cordovaEnabled: Bool) {
		if cordovaEnabled {
			screensWebView = ScreensCordovaWebView(jsCallHandler: handleJsCalls, onPageLoadFinished: onPageLoadFinished)
		}
		else {
			screensWebView = ScreensWKWebView(jsCallHandler: handleJsCalls, onPageLoadFinished: onPageLoadFinished)
		}

		addWebView()
	}

	open func add(injectableScripts: [InjectableScript]) {
		injectableScripts.forEach { add(injectableScript: $0) }
	}

	open func add(injectableScript: InjectableScript) {
		screensWebView?.add(injectableScript: injectableScript)
	}

	open func inject(injectableScript: InjectableScript) {
		screensWebView?.inject(injectableScript: injectableScript)
	}

	open func load(request: URLRequest) {
		showHud()
		screensWebView?.load(request: request)
	}

	open func load(htmlString: String) {
		showHud()
		screensWebView?.load(htmlString: htmlString)
	}

	open func addWebView() {
		guard let webView = screensWebView?.view else { return }

		webView.translatesAutoresizingMaskIntoConstraints = false

		addSubview(webView)

		let top = NSLayoutConstraint(item: webView, attribute: .top, relatedBy: .equal,
		                             toItem: self, attribute: .top, multiplier: 1, constant: 0)
		let bottom = NSLayoutConstraint(item: webView, attribute: .bottom, relatedBy: .equal,
		                                toItem: self, attribute: .bottom, multiplier: 1, constant: 0)
		let leading = NSLayoutConstraint(item: webView, attribute: .leading, relatedBy: .equal,
		                                 toItem: self, attribute: .leading, multiplier: 1, constant: 0)
		let trailing = NSLayoutConstraint(item: webView, attribute: .trailing, relatedBy: .equal,
		                                  toItem: self, attribute: .trailing, multiplier: 1, constant: 0)

		NSLayoutConstraint.activate([top, bottom, leading, trailing])
	}

	open func onPageLoadFinished() {
		progressPresenter.hideHud()
		if isThemeEnabled {
			let js = JsScript(js: "window.Screens.listPortlets()")
			screensWebView?.inject(injectableScript: js)
		}
	}

	open func handleJsCalls(namespace: String, message: String) {
		(screenlet as? PortletDisplayScreenlet)?.handleScriptHandler(namespace: namespace, message: message)
	}

	open func showHud() {
		progressPresenter.showHUDInView(self, message: LocalizedString(
			"default", key: "portletdisplay-loading-message", obj: self), forInteractor: Interactor())
	}
}
