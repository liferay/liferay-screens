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

	func screenlet(screenlet: SignUpScreenlet,
			onSignUpResponseUserAttributes attributes: [String:AnyObject]) {
		print("DELEGATE: onSignUpResponse called -> \(attributes)\n");
	}

	func screenlet(screenlet: SignUpScreenlet,
			onSignUpError error: NSError) {
		print("DELEGATE: onSignUpError called -> \(error)\n");
	}

	func screenlet(screenlet: BaseScreenlet,
			onLoginResponseUserAttributes attributes: [String:AnyObject]) {
		print("DELEGATE: onLoginResponse (autologin delegate) called -> \(attributes)\n");
	}

	func screenlet(screenlet: BaseScreenlet,
			onLoginError error: NSError) {
		print("DELEGATE: onLoginError (autologin delegate) called -> \(error)\n");
	}

	func onScreenletCredentialsSaved(screenlet: BaseScreenlet) {
		print("DELEGATE: onCredentialsSaved (autologin delegate) called\n");
	}

	func onScreenletCredentialsLoaded(screenlet: BaseScreenlet) {
		print("DELEGATE: onCredentialsLoaded (autologin delegate) called\n");
	}

}

