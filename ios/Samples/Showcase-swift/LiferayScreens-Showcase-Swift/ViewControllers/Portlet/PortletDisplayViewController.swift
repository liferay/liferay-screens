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

		let configuration = PortletConfiguration.Builder(portletUrl: portletUrl)
				.set(webType: .liferayAuthenticated)
				.enableCordova()
				.addCss(localFile: "bigger_pagination")
				.addJs(localFile: "gallery")
				.load()

		screenlet?.configuration = configuration

		screenlet?.load()
	}


	//MARK: PortletDisplayScreenletDelegate

	func onPortletPageLoaded(_ screenlet: PortletDisplayScreenlet, url: String) {
		
	}

	func screenlet(_ screenlet: PortletDisplayScreenlet, onPortletError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(_ screenlet: PortletDisplayScreenlet, onScriptMessageNamespace namespace: String, onScriptMessage message: String) {

		//We can check what is the name of the message handler responsible for the action
		performSegue(withIdentifier: "detail", sender: message)
	}

	override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
		let vc = segue.destination as? PortletDetailViewController
		vc?.url = "http://" + (sender as! String)
	}
}
