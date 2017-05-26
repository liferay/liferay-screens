//
//  WKWebView+Screens.swift
//  LiferayScreens
//
//  Created by Victor Galán on 16/03/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import Foundation
import WebKit

extension WKWebView {

	public func injectCookies() {
		guard let authentication =
			SessionContext.currentContext?.session.authentication as? LRCookieAuthentication else {
				return
		}

		let jsessionID = authentication.cookieHeader.characters.split(separator: ";")
				.map(String.init).filter { $0.contains("JSESSIONID") }.first

		if let jsessionID = jsessionID {
			let script = WKUserScript(source: "document.cookie='\(jsessionID)'",
					injectionTime: .atDocumentStart, forMainFrameOnly: false)

			configuration.userContentController.addUserScript(script)
		}
	}

	public func injectViewportMetaTag() {
		let addMetaScriptString = "var meta = document.createElement('meta');" +
			"meta.name = 'viewport';" +
			"meta.content = 'width=device-width';" +
			"var head = document.getElementsByTagName('head')[0];" +
			"head.appendChild(meta);"

		let addMetaScript = WKUserScript(source: addMetaScriptString,
				injectionTime: .atDocumentEnd, forMainFrameOnly: false)

		configuration.userContentController.addUserScript(addMetaScript)
	}
}

extension WKWebViewConfiguration {
	public static var noCacheConfiguration: WKWebViewConfiguration {
		let config = WKWebViewConfiguration()

		if #available(iOS 9.0, *) {
			config.websiteDataStore = WKWebsiteDataStore.nonPersistent()
		}

		return config
	}
}
