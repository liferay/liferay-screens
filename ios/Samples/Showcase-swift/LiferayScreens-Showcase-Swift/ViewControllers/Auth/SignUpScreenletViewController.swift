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

	// MARK: Outlets

	@IBOutlet var screenlet: SignUpScreenlet! {
		didSet {
			screenlet.delegate = self
			screenlet.autoLoginDelegate = self
			screenlet.anonymousApiUserName =
				LiferayServerContext.stringPropertyForKey("anonymousUsername")
			screenlet.anonymousApiPassword =
				LiferayServerContext.stringPropertyForKey("anonymousPassword")
		}
	}

	// MARK: Actions

	@IBAction func credentialsValueChangedAction(_ sender: UISwitch) {
		self.screenlet?.autoLogin = sender.isOn
	}

	@IBAction func loginValueChangedAction(_ sender: UISwitch) {
		self.screenlet?.saveCredentials = sender.isOn
	}

	// MARK: SignUpScreenletDelegate

	func screenlet(_ screenlet: SignUpScreenlet,
			onSignUpResponseUserAttributes attributes: [String: AnyObject]) {
		LiferayLogger.logDelegateMessage(args: attributes as AnyObject?)
		self.navigationController?.popViewController(animated: true)
	}

	func screenlet(_ screenlet: SignUpScreenlet, onSignUpError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	// MARK: LoginScreenletDelegate

	func screenlet(_ screenlet: BaseScreenlet,
			onLoginResponseUserAttributes attributes: [String: AnyObject]) {
		LiferayLogger.logDelegateMessage(args: attributes as AnyObject?)
	}

	func screenlet(_ screenlet: BaseScreenlet, onLoginError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func onScreenletCredentialsSaved(_ screenlet: BaseScreenlet) {
		LiferayLogger.logDelegateMessage()
	}

	func onScreenletCredentialsLoaded(_ screenlet: BaseScreenlet) {
		LiferayLogger.logDelegateMessage()
	}

}
