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


class LoginViewController: UIViewController, LoginScreenletDelegate {

	
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
	@IBOutlet weak var saveCredentialsLabel: UILabel!
	@IBOutlet weak var forgetPasswordButton: UIButton!
	@IBOutlet weak var signUpButton: UIButton!
	@IBOutlet weak var loggedLabel: UILabel!
	@IBOutlet weak var signOutButton: UIButton!
	@IBOutlet weak var reloginButton: UIButton!
	
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

		setTranslations()
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
		LiferayLogger.logDelegateMessage(args: attributes)
		showLogged(animated: true)
	}

	func screenlet(screenlet: BaseScreenlet, onLoginError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(screenlet: BaseScreenlet,
			onCredentialsSavedUserAttributes attributes: [String:AnyObject]) {
		LiferayLogger.logDelegateMessage(args: attributes)
	}

	func screenlet(screenlet: LoginScreenlet,
			onCredentialsLoadedUserAttributes attributes: [String:AnyObject]) {
		LiferayLogger.logDelegateMessage(args: attributes)
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

	private func setTranslations() {
		saveCredentialsLabel.text = NSLocalizedString("login-save-credentials", comment: "Save credentials")
		forgetPasswordButton.replaceAttributedTitle(NSLocalizedString("login-forgot-password",
				comment: "Did you forget your password?"), forState: .Normal)
		signUpButton.replaceAttributedTitle(NSLocalizedString("login-sign-up", comment: "SIGN UP"),
		                                    forState: .Normal)
		signOutButton.replaceAttributedTitle(NSLocalizedString("login-sign-out", comment: "SIGN OUT"),
		                                    forState: .Normal)
		loggedLabel.text = NSLocalizedString("login-logged", comment: "You are logged as...")
		reloginButton.replaceAttributedTitle(NSLocalizedString("login-relogin", comment: "RELOGIN"),
		                                    forState: .Normal)
	}

}

