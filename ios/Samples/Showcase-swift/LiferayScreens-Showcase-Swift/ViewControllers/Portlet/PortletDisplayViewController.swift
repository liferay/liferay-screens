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
import LiferayScreens


class PortletDisplayViewController: UIViewController, PortletDisplayScreenletDelegate {


	//MARK: IBOutlet

	@IBOutlet var screenlet: PortletDisplayScreenlet? {
		didSet {
			screenlet?.delegate = self
			screenlet?.presentingViewController = self
		}
	}


	//MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		let portletUrl = LiferayServerContext.stringPropertyForKey("portletDisplayUrl")

//		screenlet?.configuration = LocalScriptsConfiguration(portletUrl: portletUrl, cssFiles: ["bigger_pagination"], jsFiles: ["gallery"])
//		screenlet?.configuration = RemoteScriptsConfiguration(portletUrl: portletUrl, jsUrls: [""], cssUrls: ["http://localhost:8000/css/bigger_pagination.css"])

		let configuration = PortletConfiguration.Builder(portletUrl: portletUrl)
				.addLocalCss(fileName: "bigger_pagination")
				.load()

		screenlet?.configuration = configuration

		screenlet?.load()
	}


	//MARK: PortletDisplayScreenletDelegate

	func screenlet(_ screenlet: PortletDisplayScreenlet, onPortletUrlResponse url: String) {
		LiferayLogger.logDelegateMessage(args: url as AnyObject?)
	}

	func screenlet(_ screenlet: PortletDisplayScreenlet, onPortletError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(_ screenlet: PortletDisplayScreenlet, onScriptMessageHandler key: String,
	               onScriptMessageBody body: Any) {
		//We can check what is the name of the message handler responsible for the action
		performSegue(withIdentifier: "detail", sender: body)
	}

	func screenlet(_ screenlet: PortletDisplayScreenlet, jsFor portlet: String) -> InjectableScript? {
		if portlet == "com_liferay_document_library_web_portlet_IGDisplayPortlet" {
			return JsScript(js: Bundle.loadFile(name: "gallery", ofType: "js", currentClass: type(of: self))!)
		}

		return nil
	}

	override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
		let vc = segue.destination as? PortletDetailViewController
		vc?.url = sender as! String
	}
}
