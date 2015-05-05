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


class SignUpScreenletViewController: UIViewController, SignUpScreenletDelegate, LoginScreenletDelegate {

	@IBOutlet var screenlet: SignUpScreenlet?

	@IBAction func credentialsValueChangedAction(sender: UISwitch) {
		self.screenlet?.autoLogin = sender.on
	}

	@IBAction func loginValueChangedAction(sender: UISwitch) {
		self.screenlet?.saveCredentials = sender.on
	}

	override func viewDidLoad() {
		super.viewDidLoad()

		self.screenlet?.delegate = self
		self.screenlet?.autoLoginDelegate = self;
	}

	override func viewWillAppear(animated: Bool) {
		self.screenlet?.loadCurrentUser()
	}

	func onSignUpResponse(attributes: [String : AnyObject]) {
		println("DELEGATE: onSignUpResponse called -> \(attributes)");
	}

	func onSignUpError(error: NSError) {
		println("DELEGATE: onSignUpError called -> \(error)");

	}

	func onLoginResponse(attributes: [String:AnyObject]) {
		println("DELEGATE: onLoginResponse (autologin delegate) called -> \(attributes)");
	}

	func onLoginError(error: NSError) {
		println("DELEGATE: onLoginError (autologin delegate) called -> \(error)");
	}

	func onCredentialsSaved() {
		println("DELEGATE: onCredentialsSaved (autologin delegate) called");
	}

	func onCredentialsLoaded() {
		println("DELEGATE: onCredentialsLoaded (autologin delegate) called");
	}

}

