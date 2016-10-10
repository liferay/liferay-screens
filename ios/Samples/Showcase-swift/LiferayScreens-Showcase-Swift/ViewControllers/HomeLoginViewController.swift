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

	
	//MARK: IBOutlets
	
	@IBOutlet var loggedView: UIView!
	@IBOutlet var loggedUsername: UILabel!
	@IBOutlet var logginView: UIView!
	@IBOutlet var loginScreenlet: LoginScreenlet! {
		didSet {
			self.loginScreenlet.presentingViewController = self
			self.loginScreenlet.delegate = self
		}
	}

	
	//MARK: IBActions
	@IBAction func signOutAction() {
		SessionContext.currentContext?.removeStoredCredentials()
		SessionContext.logout()

		showLogged(animated: true)
	}

	@IBAction func reloginAction(sender: AnyObject) {
		guard let ctx = SessionContext.currentContext else {
			return print("Session doesn't exist")
		}

		ctx.relogin {
			guard let attributes = $0 else {
				print("Relogin failed")
				self.showLogged(animated: true)
				return
			}
			
			print("Relogin completed: \(attributes)")
		}
	}

	@IBAction func credentialsValueChangedAction(sender: UISwitch) {
		loginScreenlet.saveCredentials = sender.on
	}

	
	//MARK: UIViewController
	
	override func viewDidLoad() {
		super.viewDidLoad()

		SessionContext.loadStoredCredentials()
	}
	
	override func viewWillAppear(animated: Bool) {
		super.viewWillAppear(animated)
		self.navigationItem.title = "LoginScreenlet"
		
		showLogged(animated: false)
	}
	
	override func viewWillDisappear(animated: Bool) {
		super.viewWillDisappear(animated)
		self.navigationItem.title = nil
	}

	
	//MARK: LoginScreenletDelegate
	
	func screenlet(screenlet: BaseScreenlet,
			onLoginResponseUserAttributes attributes: [String:AnyObject]) {
		LiferayLogger.delegate(args: attributes)
		showLogged(animated: true)
	}

	func screenlet(screenlet: BaseScreenlet, onLoginError error: NSError) {
		LiferayLogger.delegate(args: error)
	}

	func screenlet(screenlet: BaseScreenlet,
			onCredentialsSavedUserAttributes attributes: [String:AnyObject]) {
		LiferayLogger.delegate(args: attributes)
	}

	func screenlet(screenlet: LoginScreenlet,
			onCredentialsLoadedUserAttributes attributes: [String:AnyObject]) {
		LiferayLogger.delegate(args: attributes)
	}

	
	//MARK: Private methods
	
	private func showLogged(animated animated: Bool) {
		if SessionContext.isLoggedIn {
			loggedUsername?.text = SessionContext.currentContext?.basicAuthUsername
		}

		UIView.animateWithDuration(animated ? 0.5 : 0.0) { () -> Void in
			self.loggedView?.alpha = SessionContext.isLoggedIn ? 1.0 : 0.0
			self.logginView?.alpha = SessionContext.isLoggedIn ? 0.0 : 1.0
			
			if !SessionContext.isLoggedIn {
				self.loginScreenlet.viewModel.userName = "test@liferay.com"
				self.loginScreenlet.viewModel.password = "test"
			}
		}
	}

}

