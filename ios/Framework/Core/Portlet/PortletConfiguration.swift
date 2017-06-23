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


public protocol PortletConfiguration {
	var portletUrl: String { get }

	func loadScripts() -> [InjectableScript]
}

public class LocalScriptsConfiguration: PortletConfiguration {
	public let portletUrl: String
	let cssFiles: [String]
	let jsFiles: [String]

	public init(portletUrl: String, cssFiles: [String] = [], jsFiles: [String] = []) {
		self.portletUrl = portletUrl
		self.cssFiles = cssFiles
		self.jsFiles = jsFiles
	}

	public func loadScripts() -> [InjectableScript] {
		return loadCssScripts() + loadJsScripts()
	}

	private func loadCssScripts() -> [InjectableScript] {
		let cssScripts = cssFiles.map { name in
				Bundle.loadFile(name: name, ofType: "css", currentClass: type(of: self))
			}
			.flatMap { $0 }
			.map { css -> InjectableScript in
				CssScript(css: css)
			}

		return cssScripts
	}

	private func loadJsScripts() -> [InjectableScript] {
		let jsScripts = jsFiles.map { name in
				Bundle.loadFile(name: name, ofType: "js", currentClass: type(of: self))
			}
			.flatMap { $0 }
			.map { css -> InjectableScript in
				JsScript(js: css)
			}

		return jsScripts
	}
}

public class AutomaticScriptsConfiguration: PortletConfiguration {
	public let portletUrl: String

	public init(portletUrl: String) {
		self.portletUrl = portletUrl
	}

	public func loadScripts() -> [InjectableScript] {
		return []
	}
}

public class RemoteScriptsConfiguration: PortletConfiguration {
	public let portletUrl: String
	let jsUrls: [String]
	let cssUrls: [String]

	public init(portletUrl: String, jsUrls: [String], cssUrls: [String]) {
		self.portletUrl = portletUrl
		self.jsUrls = jsUrls
		self.cssUrls = cssUrls
	}

	public func loadScripts() -> [InjectableScript] {
		let jsScripts: [InjectableScript] = jsUrls.map(RemoteJsScript.init)
		let cssScripts: [InjectableScript] = cssUrls.map(RemoteCssScript.init)

		return jsScripts + cssScripts
	}
}
