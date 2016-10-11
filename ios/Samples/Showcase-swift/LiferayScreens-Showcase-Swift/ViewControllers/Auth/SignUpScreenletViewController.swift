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

	
	//MARK: IBOutlet
	
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
	
	
	//MARK: IBAction

	@IBAction func credentialsValueChangedAction(sender: UISwitch) {
		self.screenlet?.autoLogin = sender.on
	}

	@IBAction func loginValueChangedAction(sender: UISwitch) {
		self.screenlet?.saveCredentials = sender.on
	}


	//MARK: SignUpScreenletDelegate
	
	func screenlet(screenlet: SignUpScreenlet,
			onSignUpResponseUserAttributes attributes: [String: AnyObject]) {
		LiferayLogger.logDelegateMessage(args: attributes)
		self.navigationController?.popViewControllerAnimated(true)
	}

	func screenlet(screenlet: SignUpScreenlet, onSignUpError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}
	
	
	//MARK: LoginScreenletDelegate

	func screenlet(screenlet: BaseScreenlet,
			onLoginResponseUserAttributes attributes: [String:AnyObject]) {
		LiferayLogger.logDelegateMessage(args: attributes)
	}

	func screenlet(screenlet: BaseScreenlet, onLoginError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func onScreenletCredentialsSaved(screenlet: BaseScreenlet) {
		LiferayLogger.logDelegateMessage()
	}

	func onScreenletCredentialsLoaded(screenlet: BaseScreenlet) {
		LiferayLogger.logDelegateMessage()
	}

}

