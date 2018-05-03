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

class SignUpViewController: CardViewController, SignUpScreenletDelegate {

	// MARK: Outlets

	@IBOutlet weak var screenlet: SignUpScreenlet?
	@IBOutlet weak var signUpButton: UIButton?

	// MARK: Initializers

	convenience init() {
		self.init(nibName: "SignUpViewController", bundle: nil)
	}

	// MARK: Actions

	@IBAction func termsButtonClicked() {
		cardView?.moveRight()
	}

	@IBAction func signUpButtonClicked() {
		signUpButton?.isEnabled = false
		screenlet?.performAction(name: "signup-action")
	}

	// MARK: UIViewController

	override func viewDidLoad() {
		self.screenlet?.delegate = self

		self.screenlet?.anonymousApiUserName =
            LiferayServerContext.stringPropertyForKey("anonymousUsername")
		self.screenlet?.anonymousApiPassword =
            LiferayServerContext.stringPropertyForKey("anonymousPassword")
	}

	// MARK: SignUpScreenletDelegate

	func screenlet(_ screenlet: SignUpScreenlet,
			onSignUpResponseUserAttributes attributes: [String: AnyObject]) {
		signUpButton?.isEnabled = true
		onDone?()
	}

	func screenlet(_ screenlet: SignUpScreenlet, onSignUpError error: NSError) {
		signUpButton?.isEnabled = true
	}

}
