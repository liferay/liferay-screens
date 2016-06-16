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


@objc public protocol LoginScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: BaseScreenlet,
			onLoginResponseUserAttributes attributes: [String:AnyObject])

	optional func screenlet(screenlet: BaseScreenlet,
			onLoginError error: NSError)

	optional func screenlet(screenlet: BaseScreenlet,
		onCredentialsSavedUserAttributes attributes: [String:AnyObject])

	optional func screenlet(screenlet: LoginScreenlet,
		onCredentialsLoadedUserAttributes attributes: [String:AnyObject])

}


public class LoginScreenlet: BaseScreenlet, BasicAuthBasedType {

	//MARK: Inspectables

	@IBInspectable public var basicAuthMethod: String? = BasicAuthMethod.Email.rawValue {
		didSet {
			(screenletView as? BasicAuthBasedType)?.basicAuthMethod = basicAuthMethod
		}
	}

	@IBInspectable public var saveCredentials: Bool = false

	@IBInspectable public var companyId: Int64 = 0

	@IBInspectable public var OAuthConsumerKey: String = "" {
		didSet {
			copyAuthType()
		}
	}

	@IBInspectable public var OAuthConsumerSecret: String = "" {
		didSet {
			copyAuthType()
		}
	}


	public var loginDelegate: LoginScreenletDelegate? {
		return self.delegate as? LoginScreenletDelegate
	}

	public var viewModel: LoginViewModel {
		return screenletView as! LoginViewModel
	}


	public func loadStoredCredentials() -> Bool {
		if SessionContext.loadStoredCredentials() {
			viewModel.userName = SessionContext.currentContext?.basicAuthUsername
			viewModel.password = SessionContext.currentContext?.basicAuthPassword

			let userAttributes = SessionContext.currentContext!.userAttributes

			// we don't want the session to be automatically created. Clear it.
			// User can recreate it again in the delegate method.
			SessionContext.logout()

			loginDelegate?.screenlet?(self,
				onCredentialsLoadedUserAttributes: userAttributes)

			return true
		}

		return false
	}


	//MARK: BaseScreenlet

	override public func onCreated() {
		super.onCreated()
		
		(screenletView as? BasicAuthBasedType)?.basicAuthMethod = basicAuthMethod

		copyAuthType()

	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {

		switch name {
		case "login-action", BaseScreenlet.DefaultAction:
			return createLoginBasicInteractor()
		case "oauth-action":
			return createLoginOAuthInteractor()
		default:
			return nil
		}
	}

	private func createLoginBasicInteractor() -> LoginBasicInteractor {
		let interactor = LoginBasicInteractor(loginScreenlet: self)

		interactor.onSuccess = {
			self.loginDelegate?.screenlet?(self,
					onLoginResponseUserAttributes: interactor.resultUserAttributes!)

			if let ctx = SessionContext.currentContext where self.saveCredentials {
				if ctx.storeCredentials() {
					self.loginDelegate?.screenlet?(self,
						onCredentialsSavedUserAttributes: interactor.resultUserAttributes!)
				}
			}
		}

		interactor.onFailure = {
			self.loginDelegate?.screenlet?(self, onLoginError: $0)
		}

		return interactor
	}

	private func createLoginOAuthInteractor() -> LoginOAuthInteractor {
		let interactor = LoginOAuthInteractor(
				screenlet: self,
				consumerKey: OAuthConsumerKey,
				consumerSecret: OAuthConsumerSecret)

		interactor.onSuccess = {
			self.loginDelegate?.screenlet?(self,
					onLoginResponseUserAttributes: interactor.resultUserAttributes!)

			if let ctx = SessionContext.currentContext where self.saveCredentials {
				if ctx.storeCredentials() {
					self.loginDelegate?.screenlet?(self,
						onCredentialsSavedUserAttributes: interactor.resultUserAttributes!)
				}
			}
		}

		interactor.onFailure = {
			self.loginDelegate?.screenlet?(self, onLoginError: $0)
			return
		}

		return interactor
	}


	private func copyAuthType() {
		(screenletView as? LoginViewModel)?.authType = StringFromAuthType(
			(OAuthConsumerKey != "" && OAuthConsumerSecret != "")
				? AuthType.OAuth : AuthType.Basic)
	}

}
