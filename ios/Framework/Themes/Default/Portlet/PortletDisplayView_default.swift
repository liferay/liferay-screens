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

    var progressPresenter: ProgressPresenter?

	// MARK: PortletDisplayViewModel

	open var isThemeEnabled = false

	open var screensWebView: ScreensWebView?

	open func configureView(with cordovaEnabled: Bool) {
		if screensWebView != nil {
			return;
		}
		
		let jsCallHandler: (String, String) -> Void = { [weak self] namespace, message in
			self?.handleJsCall(namespace: namespace, message: message)
		}

		let onPageLoadFinishedHandler: () -> Void = { [weak self] in
			self?.onPageLoadFinished()
		}

		if cordovaEnabled {
			screensWebView = ScreensCordovaWebView(jsCallHandler: jsCallHandler, onPageLoadFinished: onPageLoadFinishedHandler)
		}
		else {
			screensWebView = ScreensWKWebView(jsCallHandler: jsCallHandler, onPageLoadFinished: onPageLoadFinishedHandler)
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
    
    // MARK: BaseScreenletView
    
    open override func onCreated() {
        super.onCreated()
        
        self.progressPresenter = createProgressPresenter()
    }

	open override func onDestroy() {
		super.onDestroy()
		screensWebView?.onDestroy?()
	}
    
    open override func createProgressPresenter() -> ProgressPresenter {
        return DefaultProgressPresenter()
    }

	open func addWebView() {
		guard let webView = screensWebView?.view else { return }

		webView.translatesAutoresizingMaskIntoConstraints = false

		webView.backgroundColor = .clear
		
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
        self.progressPresenter?.hideHUDFromView(self, message: nil, forInteractor: Interactor(), withError: nil)
		if isThemeEnabled {
			screensWebView?.inject(injectableScript: js)
				let js = JsScript(name: "listPorlets", js: "window.Screens.listPortlets()")
		}
	}

	open func handleJsCall(namespace: String, message: String) {
		(screenlet as? PortletDisplayScreenlet)?.handleJsCall(namespace: namespace, message: message)
	}

	open func showHud() {
		progressPresenter?.showHUDInView(self, message: LocalizedString(
			"default", key: "portletdisplay-loading-message", obj: self), forInteractor: Interactor())
	}
}
