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
import LRMobileSDK

class LoginViewController: UIViewController, LoginScreenletDelegate {

	// MARK: Outlets

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
	@IBOutlet weak var basicModeSwitch: UISwitch!
	@IBOutlet weak var cookieModeSwitch: UISwitch!
	@IBOutlet weak var OAuth2redirectModeSwitch: UISwitch!
	@IBOutlet weak var OAuth2UsernameAndPasswordModeSwitch: UISwitch!

	//MARK: IBActions

	@IBAction func signOutAction() {
		SessionContext.currentContext?.removeStoredCredentials()
		SessionContext.logout()

		showLogged(animated: true)
	}

	@IBAction func reloginAction(_ sender: AnyObject) {
		guard let ctx = SessionContext.currentContext else {
			return print("Session doesn't exist")
		}

		_ = ctx.relogin {
			guard let attributes = $0 else {
				print("Relogin failed")
				self.showLogged(animated: true)
				return
			}

			print("Relogin completed: \(attributes)")
		}
	}

	@IBAction func credentialsValueChangedAction(_ sender: UISwitch) {
		loginScreenlet.saveCredentials = sender.isOn
	}

	@IBAction func onLoginModeSelected(_ sender: UISwitch) {

		basicModeSwitch.isOn = false
		cookieModeSwitch.isOn = false
		OAuth2redirectModeSwitch.isOn = false
		OAuth2UsernameAndPasswordModeSwitch.isOn = false

		let authType = AuthType(rawValue: sender.tag)!

		sender.isOn = true

		switch authType {
		case .basic:
			loginScreenlet.authType = .basic
		case .cookie:
			loginScreenlet.authType = .cookie
			loginScreenlet.cookieExpirationTime = 20
			loginScreenlet.shouldHandleCookieExpiration = true
		case .oauth2Redirect:
			loginScreenlet.authType = .oauth2Redirect
			loginScreenlet.oauth2clientId = LiferayServerContext.stringPropertyForKey("clientIdRedirect")
			loginScreenlet.oauth2redirectURL = LiferayServerContext.stringPropertyForKey("redirectUrl")
		case .oauth2UsernameAndPassword:
			loginScreenlet.authType = .oauth2UsernameAndPassword
			loginScreenlet.oauth2clientId = LiferayServerContext.stringPropertyForKey("clientId")
			loginScreenlet.oauth2clientSecret = LiferayServerContext.stringPropertyForKey("clientSecret")
		}
	}

	//MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		SessionContext.loadStoredCredentials()

		loginScreenlet.authType = .basic

		setTranslations()
	}

	override func viewWillAppear(_ animated: Bool) {
		super.viewWillAppear(animated)
		self.navigationItem.title = "LoginScreenlet"

		showLogged(animated: false)
	}

	override func viewWillDisappear(_ animated: Bool) {
		super.viewWillDisappear(animated)
		self.navigationItem.title = nil
	}

	// MARK: LoginScreenletDelegate

	func screenlet(_ screenlet: BaseScreenlet,
			onLoginResponseUserAttributes attributes: [String: AnyObject]) {
		LiferayLogger.logDelegateMessage(args: attributes as AnyObject?)
		showLogged(animated: true)
	}

	func screenlet(_ screenlet: BaseScreenlet, onLoginError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(_ screenlet: BaseScreenlet,
			onCredentialsSavedUserAttributes attributes: [String: AnyObject]) {
		LiferayLogger.logDelegateMessage(args: attributes as AnyObject?)
	}

	func screenlet(_ screenlet: LoginScreenlet,
			onCredentialsLoadedUserAttributes attributes: [String: AnyObject]) {
		LiferayLogger.logDelegateMessage(args: attributes as AnyObject?)
	}

	// MARK: Private methods

	fileprivate func showLogged(animated: Bool) {
		if SessionContext.isLoggedIn {
			loggedUsername?.text = SessionContext.currentContext?.basicAuthUsername
		}

		UIView.animate(withDuration: animated ? 0.5 : 0.0, animations: { () -> Void in
			self.loggedView?.alpha = SessionContext.isLoggedIn ? 1.0 : 0.0
			self.logginView?.alpha = SessionContext.isLoggedIn ? 0.0 : 1.0

			if !SessionContext.isLoggedIn {
				self.loginScreenlet.viewModel.userName = "test@liferay.com"
				self.loginScreenlet.viewModel.password = "test"
			}
		})
	}

	fileprivate func setTranslations() {
		saveCredentialsLabel.text = NSLocalizedString("login-save-credentials", comment: "Save credentials")
		forgetPasswordButton.replaceAttributedTitle(NSLocalizedString("login-forgot-password",
				comment: "Did you forget your password?"), forState: .normal)
		signUpButton.replaceAttributedTitle(NSLocalizedString("login-sign-up", comment: "SIGN UP"),
		                                    forState: .normal)
		signOutButton.replaceAttributedTitle(NSLocalizedString("login-sign-out", comment: "SIGN OUT"),
		                                    forState: .normal)
		loggedLabel.text = NSLocalizedString("login-logged", comment: "You are logged as...")
		reloginButton.replaceAttributedTitle(NSLocalizedString("login-relogin", comment: "RELOGIN"),
		                                    forState: .normal)
	}

}
