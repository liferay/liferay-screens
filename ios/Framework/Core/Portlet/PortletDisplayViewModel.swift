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

@objc public protocol PortletDisplayViewModel {

	var initialHtml: String? { get set }

	var automaticMode: Bool { get set }

	// Inject JS to be used by the screenlet.
	func add(injectableScripts: [InjectableScript])

	func add(injectableScript: InjectableScript)

	func inject(injectableScript: InjectableScript)

	/// Add script handler that will take messages from WKWebView.
	func add(scriptHandler: String)

}
