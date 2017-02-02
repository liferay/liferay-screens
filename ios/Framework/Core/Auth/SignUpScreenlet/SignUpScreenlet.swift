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


@objc public protocol SignUpScreenletDelegate : BaseScreenletDelegate {

	/// Called when sign up successfully completes.
	/// The user attributes are passed as a dictionary of keys (String or NSStrings)
	/// and values (AnyObject or NSObject).
	///
	/// - Parameters:
	///   - screenlet
	///   - attributes: user attributes.
	@objc optional func screenlet(_ screenlet: SignUpScreenlet,
			onSignUpResponseUserAttributes attributes: [String:AnyObject])

	/// Called when an error occurs in the process. The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error in sign up.
	@objc optional func screenlet(_ screenlet: SignUpScreenlet,
			onSignUpError error: NSError)

}


open class SignUpScreenlet: BaseScreenlet, AnonymousBasicAuthType {

	//MARK: Inspectables

	@IBInspectable open var anonymousApiUserName: String? = "test@liferay.com"

	@IBInspectable open var anonymousApiPassword: String? = "test"

	@IBInspectable open var autoLogin: Bool = true

	@IBInspectable open var saveCredentials: Bool = true

	@IBInspectable open var companyId: Int64 = 0

	@IBOutlet open weak var autoLoginDelegate: LoginScreenletDelegate?


	open var signUpDelegate: SignUpScreenletDelegate? {
		return delegate as? SignUpScreenletDelegate
	}

	open var viewModel: SignUpViewModel {
		return screenletView as! SignUpViewModel
	}

	//MARK: BaseScreenlet

	override open func createInteractor(name: String, sender: AnyObject?) -> Interactor? {

		switch name {
		case "signup-action":
			return createSignUpConnectorInteractor()
		case "save-action":
			return createSaveInteractor()
		default:
			return nil
		}
	}


	//MARK: Public methods

	/// Loads the current user throught editCurrentUser property.
	///
	/// - Returns: if there is a session created, returns true, if not, false.
	open func loadCurrentUser() -> Bool {
		if SessionContext.isLoggedIn {
			self.viewModel.editCurrentUser = true
			return true
		}
		return false
	}
	

	//MARK: Private methods

	fileprivate func createSignUpConnectorInteractor() -> SignUpInteractor {
		let interactor = SignUpInteractor(screenlet: self)

		interactor.onSuccess = {
			self.signUpDelegate?.screenlet?(self,
					onSignUpResponseUserAttributes: interactor.resultUserAttributes!)

			if self.autoLogin {
				self.doAutoLogin(interactor.resultUserAttributes!)

				if let ctx = SessionContext.currentContext, self.saveCredentials {
					ctx.removeStoredCredentials()

					if ctx.storeCredentials() {
						self.autoLoginDelegate?.screenlet?(self,
							onCredentialsSavedUserAttributes: interactor.resultUserAttributes!)
					}
				}
			}
		}

		interactor.onFailure = {
			self.signUpDelegate?.screenlet?(self, onSignUpError: $0)
			return
		}

		return interactor
	}

	fileprivate func createSaveInteractor() -> SaveUserInteractor {
		let interactor = SaveUserInteractor(screenlet: self)

		interactor.onSuccess = {
			if SessionContext.isLoggedIn {
				// refresh current session
				self.doAutoLogin(interactor.resultUserAttributes!)
			}

			self.signUpDelegate?.screenlet?(self,
					onSignUpResponseUserAttributes: interactor.resultUserAttributes!)
		}

		interactor.onFailure = {
			self.signUpDelegate?.screenlet?(self, onSignUpError: $0)
		}

		return interactor
	}

	fileprivate func doAutoLogin(_ userAttributes: [String:AnyObject]) {
		let userNameKeys : [BasicAuthMethod:String] = [
			.email : "emailAddress",
			.screenName : "screenName",
			.userId: "userId"
		]

		let currentAuth = BasicAuthMethod.fromUserName(anonymousApiUserName!)

		if let currentKey = userNameKeys[currentAuth],
				let userName = userAttributes[currentKey] as? String {

			SessionContext.loginWithBasic(
				username: userName,
				password: self.viewModel.password!,
				userAttributes: userAttributes)

			self.autoLoginDelegate?.screenlet?(self,
				onLoginResponseUserAttributes: userAttributes)
		}
	}

}
