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

public class PortletConfiguration {

	public let portletUrl: String
	public let scripts: [InjectableScript]
	public let isThemeEnabled: Bool

	public init(portletUrl: String, scripts: [InjectableScript], isThemeEnabled: Bool) {
		self.portletUrl = portletUrl
		self.scripts = scripts
		self.isThemeEnabled = isThemeEnabled
	}

	public class Builder {
		let portletUrl: String
		var localJs: [String]
		var localCss: [String]
		var remoteJs: [String]
		var remoteCss: [String]
		var isThemeEnabled: Bool

		public init(portletUrl: String) {
			self.portletUrl = portletUrl
			self.localJs = []
			self.localCss = []
			self.remoteJs = []
			self.remoteCss = []
			self.isThemeEnabled = true
		}

		public func addJs(localFile: String) -> Self {
			self.localJs.append(localFile)
			return self
		}

		public func addCss(localFile: String) -> Self {
			self.localCss.append(localFile)
			return self
		}

		public func addJs(url: String) -> Self {
			self.remoteJs.append(url)
			return self
		}

		public func addCss(url: String) -> Self {
			self.remoteCss.append(url)
			return self
		}

		public func disableTheme() -> Self {
			self.isThemeEnabled = false
			return self
		}

		public func load() -> PortletConfiguration {
			let localJsScripts: [InjectableScript] = localJs.map(loadLocalJsContent).map(JsScript.init)
			let localCssScripts: [InjectableScript] = localCss.map(loadLocalCssContent).map(CssScript.init)
			let remoteJsScripts: [InjectableScript] = remoteJs.map(RemoteJsScript.init)
			let remoteCssScripts: [InjectableScript] = remoteCss.map(RemoteCssScript.init)

			let allScripts: [InjectableScript] = localJsScripts + localCssScripts + remoteJsScripts + remoteCssScripts

			return PortletConfiguration(portletUrl: portletUrl, scripts: allScripts, isThemeEnabled: isThemeEnabled)
		}

		private func loadLocalCssContent(fileName: String) -> String {
			guard let content = Bundle.loadFile(name: fileName,
				ofType: "css", currentClass: type(of: self)) else {
					print("file named \(fileName) not found")
					return ""
			}
			
			return content
		}

		private func loadLocalJsContent(fileName: String) -> String {
		 	guard let content = Bundle.loadFile(name: fileName,
				ofType: "js", currentClass: type(of: self)) else {
				print("file named \(fileName) not found")
				return ""
			}

			return content
		}
	}

}
