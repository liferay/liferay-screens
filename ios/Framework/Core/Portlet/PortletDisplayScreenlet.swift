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


/// The PortletDisplayScreenletDelegate protocol defines some methods that you use to manage the
/// PortletDisplayScreenlet events. All of them are optional.
@objc public protocol PortletDisplayScreenletDelegate : BaseScreenletDelegate {

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

}


/// TODO Fill screenlet description
open class PortletDisplayScreenlet: BaseScreenlet {

	/// Whether the content should be retrieved from the portal as soon as the Screenlet appears.
	/// The default value is true.
	@IBInspectable open var autoLoad: Bool = true

	/// The portlet URL to be displayed.
	@IBInspectable open var portletUrl: String?


	//MARK: Public properties

	open var portletDisplayDelegate: PortletDisplayScreenletDelegate? {
		return delegate as? PortletDisplayScreenletDelegate
	}

	open var portletDisplayViewModel: PortletDisplayViewModel? {
		return screenletView as? PortletDisplayViewModel
	}

	//MARK: BaseScreenlet

	override open func onShow() {
		if autoLoad {
			load()
		}
	}

	//MARK: Public methods

	/// Call this method to load the portlet.
	open func load() {
		if let url = portletUrl, url != "" {
			let url = URL(string: url)
			portletDisplayViewModel?.portletUrl = url
		}
	}

	/// Call this method to load the portlet with a custom Javascript file.
	open func load(withJsFile jsFile: String) {
		if let url = portletUrl, url != "" {
			portletDisplayViewModel?.portletUrl = URL(string: url)
			portletDisplayViewModel?.injectedJsFile = jsFile
		}
	}
}
