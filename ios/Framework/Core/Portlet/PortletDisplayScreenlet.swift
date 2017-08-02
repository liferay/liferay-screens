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

/// The PortletDisplayScreenletDelegate protocol defines some methods that you use to
/// manage the PortletDisplayScreenlet events. All of them are optional.
@objc public protocol PortletDisplayScreenletDelegate: BaseScreenletDelegate {

	///  Called when the portlet URL is received.
	///
	/// - Parameters:
	///   - screenlet: Portlet display screenlet instance.
	///   - html: Portlet URL.
	@objc optional func screenlet(_ screenlet: PortletDisplayScreenlet,
	                              onPortletUrlResponse url: String)

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet: Portlet display screenlet instance.
	///   - error: Error while retrieving portlet.
	@objc optional func screenlet(_ screenlet: PortletDisplayScreenlet,
	                              onPortletError error: NSError)

	/// Called when we want to notify a message from the WKWebView used in the view.
	///
	/// - Parameters:
	///   - screenlet: Portlet display screenlet instance.
	///   - key: source message key.
	///   - body: source message body.
	@objc optional func screenlet(_ screenlet: PortletDisplayScreenlet,
	                              onScriptMessageHandler key: String,
	                              onScriptMessageBody body: Any)

	/// Called this method when we want to search the css file to inject in the portlet
	/// when the automaticMode in PortletConfiguration is on.
	/// It's not necessary the same filename as the portlet. For example:
	/// Portlet name: "com_liferay_document_library_web_portlet_IGDisplayPortlet"
	/// Filename: gallery.css
	/// - Parameters:
	///	  - portlet: name of the internal portlet.
	/// - Returns: injectable script.
	@objc optional func screenlet(_ screenlet: PortletDisplayScreenlet,
	                              cssFor portlet: String) -> InjectableScript?

	/// Called this method when we want to search the js file to inject in the portlet
	/// when the automaticMode in PortletConfiguration is on.
	/// It's not necessary the same filename as the portlet. For example:
	/// Portlet name: "com_liferay_document_library_web_portlet_IGDisplayPortlet"
	/// Filename: gallery.js
	/// - Parameters:
	///	  - portlet: name of the internal portlet.
	/// - Returns: injectable script.
	@objc optional func screenlet(_ screenlet: PortletDisplayScreenlet,
	                              jsFor portlet: String) -> InjectableScript?

}

/// Portlet Display Screenlet can display a Liferay page (with one or more portlets
/// inside). With this screenlet you can inject custom css and js files to customize
/// your view.
open class PortletDisplayScreenlet: BaseScreenlet {

	let internalNamespace = "screensinternal"

	/// Whether the content should be retrieved from the portal as soon as the Screenlet appears.
	/// The default value is true.
	@IBInspectable open var autoLoad: Bool = true

	/// The portlet URL to be displayed.
	open var configuration: PortletConfiguration? {
		didSet {
			guard let configuration = configuration else { return }
			portletDisplayViewModel.configureView(with: configuration.isCordovaEnabled)
		}
	}

	// MARK: Public properties

	open var portletDisplayDelegate: PortletDisplayScreenletDelegate? {
		return delegate as? PortletDisplayScreenletDelegate
	}

	open var portletDisplayViewModel: PortletDisplayViewModel {
		return screenletView as! PortletDisplayViewModel
	}

	// MARK: BaseScreenlet

	override open func onShow() {
		if autoLoad {
			load()
		}
	}

	// MARK: Public methods

	open func handleJsCall(namespace: String, message: String) {
		if namespace.hasPrefix(internalNamespace) {
			handleInternal(namespace: namespace, message: message)
		}
		else {
			portletDisplayDelegate?.screenlet?(self,
				onScriptMessageHandler: namespace, onScriptMessageBody: message)
		}
	}

	/// Call this method to load the portlet.
	open func load() {
		guard let configuration = configuration else {
			self.portletDisplayDelegate?.screenlet?(self, onPortletError: NSError.errorWithCause(
				.invalidServerResponse, message: "You need to specify a portlet configuration first"))
			return
		}

		let screensScript = Bundle.loadFile(name: "Screens", ofType: "js", currentClass: type(of: self))!

		portletDisplayViewModel.add(injectableScript: JsScript(name: "Screens.js", js: screensScript))
		portletDisplayViewModel.add(injectableScripts: configuration.scripts)

		portletDisplayViewModel.isThemeEnabled = configuration.isThemeEnabled

		switch configuration.webType {
			case .liferayAuthenticated:
				guard SessionContext.isLoggedIn else {
					self.portletDisplayDelegate?.screenlet?(self, onPortletError: NSError.errorWithCause(
						.abortedDueToPreconditions, message: "You have to be logged to use this web type"))
					return
				}

				let html = configureInitialHtml(portletUrl: configuration.portletUrl)
				portletDisplayViewModel.load(htmlString: html)
			case .liferay:
				portletDisplayViewModel.load(request: URLRequest(url: URL(string: configuration.portletUrl)!))
			case .other:
				portletDisplayViewModel.isThemeEnabled = false
				portletDisplayViewModel.load(request: URLRequest(url: URL(string: configuration.portletUrl)!))
		}
	}

	func handleInternal(namespace: String, message: Any) {
		if namespace.hasSuffix("listportlets") {
			guard let portletsString = message as? String else { return }

			let portlets = portletsString.components(separatedBy: ",")

			for portlet in portlets {
				var js = portletDisplayDelegate?.screenlet?(self, jsFor: portlet)
				var css = portletDisplayDelegate?.screenlet?(self, cssFor: portlet)

				let fileName = "\(themeName!)_\(portlet)"

				if js == nil {
					js = Bundle.loadFile(name: fileName, ofType: "js", currentClass: type(of: self))
						.map { js in
							JsScript(name: fileName, js: js)
						}
				}

				if css == nil {
					css = Bundle.loadFile(name: fileName, ofType: "css", currentClass: type(of: self))
						.map { css in
							CssScript(name: fileName, css: css)
						}
				}

				if let js = js {
					portletDisplayViewModel.inject(injectableScript: js)
				}

				if let css = css {
					portletDisplayViewModel.inject(injectableScript: css)
				}
			}
		}
	}

	func configureInitialHtml(portletUrl: String) -> String {
		// With WKWebView we are not able to load a page using a POST request, in order to work around we have to
		// create a form with the parameters for the POST request and submit it to actually perform the request

		// This html is a template to build the form
		let html = Bundle.loadFile(name: "index", ofType: "html", currentClass: type(of: self))!

		let username = SessionContext.currentContext?.basicAuthUsername ?? ""
		let password = SessionContext.currentContext?.basicAuthPassword ?? ""

		let portletUrlEscaped = portletUrl.addingPercentEncoding(withAllowedCharacters: .urlHostAllowed)

		return html.replacingOccurrences(of: "<portletUrl>", with: portletUrlEscaped!)
			.replacingOccurrences(of: "<login>", with: username)
			.replacingOccurrences(of: "<password>", with: password)
	}

}
