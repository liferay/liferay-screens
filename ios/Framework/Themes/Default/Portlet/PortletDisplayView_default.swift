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

@objc(PortletDisplayView_default)
open class PortletDisplayView_default: BaseScreenletView, PortletDisplayViewModel {

	// MARK: Public properties

    var progressPresenter: ProgressPresenter?

	// MARK: PortletDisplayViewModel

	open var isLoggingEnabled = true

	open var isScrollEnabled: Bool {
		get {
			return screensWebView?.isScrollEnabled ?? false
		}
		set {
			screensWebView?.isScrollEnabled = newValue
		}
	}
	
	open var screensWebView: ScreensWebView?

	open var portletDisplayScreenlet: PortletDisplayScreenlet {
		return self.screenlet as! PortletDisplayScreenlet
	}

	open func configureView(with cordovaEnabled: Bool) {
		if screensWebView != nil {
			return
		}

		let jsCallHandler = weakify(owner: self, f: PortletDisplayView_default.handleJsCall)
		let onPageLoadFinished = weakify(owner: self, f: PortletDisplayView_default.onPageLoadFinished)

		let jsErrorHandler: (String) -> (Any?, Error?) -> Void = { [unowned self] scriptName in
			return { _, error in
				guard self.isLoggingEnabled else { return }
				print("executed \(scriptName)")
				if let error = error as NSError? {

					if error.domain == "WKErrorDomain" && error.code != 5 {
						print("\nError injecting \(scriptName): \(self.parseJavaScriptError(error))")
					}
				}
			}
		}

		if cordovaEnabled {
			screensWebView = ScreensCordovaWebView(
					jsCallHandler: jsCallHandler,
					jsErrorHandler: jsErrorHandler,
					onPageLoadFinished: onPageLoadFinished)
		}
		else {
			screensWebView = ScreensWKWebView(
					jsCallHandler: jsCallHandler,
					jsErrorHandler: jsErrorHandler,
					onPageLoadFinished: onPageLoadFinished)

			(screensWebView as? ScreensWKWebView)?.viewController = screenlet?.presentingViewController
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
		screensWebView?.view.isHidden = true
		screensWebView?.load(request: request)
	}

	open func load(htmlString: String) {
		showHud()
		screensWebView?.view.isHidden = true
		screensWebView?.load(htmlString: htmlString)
	}

    // MARK: BaseScreenletView

    open override func onCreated() {
        super.onCreated()
        backgroundColor = .clear
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

	open func onPageLoadFinished(url: String, with error: Error?) {
		if let error = error {
			self.progressPresenter?
				.hideHUDFromView(self,
				message: LocalizedString("default", key: "portletdisplay-loading-error", obj: self),
				forInteractor: Interactor(), withError: error as NSError?)

			portletDisplayScreenlet
				.portletDisplayDelegate?.screenlet?(portletDisplayScreenlet, onPortletError: error as NSError)
		}
		else {
			progressPresenter?.hideHUDFromView(self, message: nil, forInteractor: Interactor(), withError: nil)

			UIView.transition(
				with: screensWebView!.view,
				duration: 0.4,
				options: .transitionCrossDissolve,
				animations: {
					self.screensWebView?.view.isHidden = false
			},
				completion: nil)
			portletDisplayScreenlet.portletDisplayDelegate?
				.onPortletPageLoaded?(portletDisplayScreenlet, url: "")
//			screensWebView?.inject(injectableScript: JsScript(name: "waitForJs", js: "window.Screens.waitForJsLoaded()"))
		}
	}

	open func handleJsCall(namespace: String, message: String) {
			if namespace == "jsloaded" {
				progressPresenter?.hideHUDFromView(self, message: nil, forInteractor: Interactor(), withError: nil)

				UIView.transition(
					with: screensWebView!.view,
					duration: 0.4,
					options: .transitionCrossDissolve,
					animations: {
						self.screensWebView?.view.isHidden = false
					},
					completion: nil)

				portletDisplayScreenlet.portletDisplayDelegate?
					.onPortletPageLoaded?(portletDisplayScreenlet, url: message)
			}
			else {
				portletDisplayScreenlet.handleJsCall(namespace: namespace, message: message)
			}
		}

	open func showHud() {
		progressPresenter?.showHUDInView(self, message: LocalizedString(
			"default", key: "portletdisplay-loading-message", obj: self), forInteractor: Interactor())
	}

	func parseJavaScriptError(_ error: NSError) -> String {
		let lineNumber = error.userInfo["WKJavaScriptExceptionLineNumber"] ?? ""
		let message = error.userInfo["WKJavaScriptExceptionMessage"] ?? ""

		return "Line \(lineNumber), \(message)"
	}
}
