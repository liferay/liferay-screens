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

import Foundation
import UIKit

@objc open class ScreensCordovaWebView: NSObject, ScreensWebView, UIWebViewDelegate {

	open var view: UIView {
		return cordovaVC.view
	}

	var scriptsToInject = [InjectableScript]()

	lazy var cordovaVC: ScreensCordovaViewController = ScreensCordovaViewController(webViewDelegate: self)

	let jsCallHandler: (String, String) -> Void
	let onPageLoadFinished: () -> Void

	public required init(jsCallHandler: @escaping (String, String) -> Void, onPageLoadFinished: @escaping () -> Void) {
		self.jsCallHandler = jsCallHandler
		self.onPageLoadFinished = onPageLoadFinished

		super.init()

		let plugin = loadCordovaPlugin()
		self.scriptsToInject.append(plugin)
	}

	open func add(injectableScript: InjectableScript) {
		scriptsToInject.append(injectableScript)
	}

	open func inject(injectableScript: InjectableScript) {
		cordovaVC.inject(script: injectableScript)
	}

	open func load(request: URLRequest) {
		cordovaVC.webViewEngine.load(request)
	}

	open func load(htmlString: String) {
		let server = SessionContext.currentContext?.session.server ?? ""
		cordovaVC.webViewEngine.loadHTMLString(htmlString, baseURL: URL(string: server)!)
	}


	open func webView(_ webView: UIWebView, shouldStartLoadWith request: URLRequest,
	                    navigationType: UIWebViewNavigationType) -> Bool {

		if handleJsCalls(uri: request.url?.absoluteString ?? "") {
			return true
		}

		return false
	}

	open func webViewDidFinishLoad(_ webView: UIWebView) {
		self.scriptsToInject.forEach { cordovaVC.inject(script: $0) }
		onPageLoadFinished()
	}

	open func handleJsCalls(uri: String) -> Bool {
		if uri.hasPrefix("screens-") {
			let parts = uri.components(separatedBy: "://")
			let key = parts[0].components(separatedBy: "-")[1]
			let message = parts[1]

			jsCallHandler(key, message)

			return true
		}

		return false
	}

	open func loadCordovaPlugin() -> InjectableScript {
		var jsPaths = [String]()

		jsPaths.append("www/cordova.js")

		let directoryEnumerator = FileManager.default.enumerator(atPath: Bundle.main.path(forResource: "www/plugins", ofType: nil)!)

		while let path = directoryEnumerator?.nextObject() as? String {
			if path.hasSuffix(".js") {
				jsPaths.append("www/plugins/\(path)")
			}
		}

		jsPaths.append("www/cordova_plugins.js")

		let fullScript = jsPaths
			.map { Bundle.main.path(forResource: $0, ofType: nil) }
			.flatMap { $0 }
			.map { try! String(contentsOfFile: $0) }
			.joined(separator: "\n")
		
		
		return JsScript(js: fullScript)
	}
}

