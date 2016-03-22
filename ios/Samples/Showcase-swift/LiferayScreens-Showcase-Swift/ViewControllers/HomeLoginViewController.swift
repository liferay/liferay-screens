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


class HomeLoginViewController: UIViewController, LoginScreenletDelegate {

	@IBOutlet var loggedView: UIView?
	@IBOutlet var loggedUsername: UILabel?
	@IBOutlet var loginScreenlet: LoginScreenlet?

	@IBAction func signOutAction() {
		SessionContext.currentContext?.removeStoredCredentials()
		SessionContext.logout()

		showLogged(animated: true);
	}

	@IBAction func credentialsValueChangedAction(sender: UISwitch) {
		loginScreenlet?.saveCredentials = sender.on
	}

	override func viewDidLoad() {
		super.viewDidLoad()

		self.loginScreenlet?.presentingViewController = self
		self.loginScreenlet?.delegate = self

		if !SessionContext.loadStoredCredentials() {
			self.loginScreenlet?.viewModel.userName = "test@liferay.com"
			self.loginScreenlet?.viewModel.password = "test"
		}

		showLogged(animated: false);
	}

	func screenlet(screenlet: BaseScreenlet,
			onLoginResponseUserAttributes attributes: [String:AnyObject]) {
		print("DELEGATE: onLoginResponse called -> \(attributes)\n");
		showLogged(animated: true);
	}

	func screenlet(screenlet: BaseScreenlet,
			onLoginError error: NSError) {
		print("DELEGATE: onLoginError called -> \(error)\n");
	}

	func screenlet(screenlet: BaseScreenlet,
			onCredentialsSavedUserAttributes attributes: [String:AnyObject]) {
		print("DELEGATE: onCredentialsSavedUserAttributes called -> \(attributes)\n");
	}

	func screenlet(screenlet: LoginScreenlet,
			onCredentialsLoadedUserAttributes attributes: [String:AnyObject]) {
		print("DELEGATE: onCredentialsLoadedUserAttributes called -> \(attributes)\n");
	}

	private func showLogged(animated animated:Bool) {
		if SessionContext.isLoggedIn {
			loggedUsername?.text = SessionContext.currentContext?.basicAuthUsername
		}

		UIView.animateWithDuration(animated ? 0.5 : 0.0) { () -> Void in
			self.loggedView?.alpha = SessionContext.isLoggedIn ? 1.0 : 0.0
			self.loginScreenlet?.alpha = SessionContext.isLoggedIn ? 0.0 : 1.0
		}
	}

}

