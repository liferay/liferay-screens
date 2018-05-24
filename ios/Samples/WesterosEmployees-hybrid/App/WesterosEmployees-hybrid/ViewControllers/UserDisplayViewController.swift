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

class UserDisplayViewController: UIViewController, WebScreenletDelegate {

	// MARK: Outlets

	@IBOutlet weak var webScreenlet: WebScreenlet? {
		didSet {
			let webScreenletConfiguration = WebScreenletConfigurationBuilder(url: "/web/westeros-hybrid/userprofile")
				.enableCordova()
				.addCss(localFile: "user_profile")
				.addJs(localFile: "user_profile")
				.load()

			webScreenlet?.backgroundColor = .clear
			webScreenlet?.presentingViewController = self
			webScreenlet?.configuration = webScreenletConfiguration
			webScreenlet?.delegate = self
		}
	}

	// MARK: Actions

	@IBAction func goBack(_ sender: Any) {
		dismiss(animated: true, completion: nil)
	}

	@IBAction func signOut(_ sender: Any) {
		SessionContext.currentContext?.removeStoredCredentials()
		SessionContext.logout()
		self.dismiss(animated: true, completion: nil)
	}

	// MARK: UIViewController

	open override func viewDidLoad() {
		super.viewDidLoad()

		webScreenlet?.load()
	}
	
	// MARK: WebScreenletDelegate

	func screenlet(_ screenlet: WebScreenlet, onError error: NSError) {
		print("WebScreenlet error (UserDisplayViewController): \(error.debugDescription)")
	}
}
