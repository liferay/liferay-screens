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

class HomeLoginViewController: UIViewController, LoginScreenletDelegate {

	@IBOutlet var loggedView: UIView?
	@IBOutlet var loggedUsername: UILabel?
	@IBOutlet var loginScreenlet: LoginScreenlet?

	@IBAction func signOutAction() {
		SessionContext.removeStoredSession()
		SessionContext.clearSession()

		showLogged(animated: true);
	}

	@IBAction func credentialsValueChangedAction(sender: UISwitch) {
		loginScreenlet?.saveCredentials = sender.on
	}

	override func viewDidLoad() {
		super.viewDidLoad()

		self.loginScreenlet?.delegate = self

		showLogged(animated: false);
	}

	func onLoginResponse(attributes: [String:AnyObject]) {
		println("DELEGATE: onLoginResponse called -> \(attributes)");
		showLogged(animated: true);
	}

	func onLoginError(error: NSError) {
		println("DELEGATE: onLoginError called -> \(error)");
	}

	func onCredentialsSaved() {
		println("DELEGATE: onCredentialsSaved called");
	}

	func onCredentialsLoaded() {
		println("DELEGATE: onCredentialsLoaded called");
	}

	private func showLogged(#animated:Bool) {
		if SessionContext.hasSession {
			loggedUsername?.text = SessionContext.currentUserName;
		}

		UIView.animateWithDuration(animated ? 0.5 : 0.0) { () -> Void in
			self.loggedView?.alpha = SessionContext.hasSession ? 1.0 : 0.0
			self.loginScreenlet?.alpha = SessionContext.hasSession ? 0.0 : 1.0
		}
	}

}

