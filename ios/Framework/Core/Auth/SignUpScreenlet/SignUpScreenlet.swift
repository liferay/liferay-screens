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

	optional func screenlet(screenlet: SignUpScreenlet,
			onSignUpResponseUserAttributes attributes: [String:AnyObject])

	optional func screenlet(screenlet: SignUpScreenlet,
			onSignUpError error: NSError)

}


@IBDesignable public class SignUpScreenlet: BaseScreenlet, AnonymousBasicAuthType {

	//MARK: Inspectables

	@IBInspectable public var anonymousApiUserName: String? = "test@liferay.com"
	@IBInspectable public var anonymousApiPassword: String? = "test"

	@IBInspectable public var autoLogin: Bool = true

	@IBInspectable public var saveCredentials: Bool = true

	@IBInspectable public var companyId: Int64 = 0

	@IBOutlet public weak var autoLoginDelegate: LoginScreenletDelegate?


	public var signUpDelegate: SignUpScreenletDelegate? {
		return delegate as? SignUpScreenletDelegate
	}

	public var viewModel: SignUpViewModel {
		return screenletView as! SignUpViewModel
	}

	public func loadCurrentUser() -> Bool {
		if SessionContext.isLoggedIn {
			self.viewModel.editCurrentUser = true
			return true
		}
		return false
	}


	//MARK: BaseScreenlet

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {

		switch name {
		case "signup-action":
			return createSignUpConnectorInteractor()
		case "save-action":
			return createSaveInteractor()
		default:
			return nil
		}
	}

	private func createSignUpConnectorInteractor() -> SignUpInteractor {
		let interactor = SignUpInteractor(screenlet: self)

		interactor.onSuccess = {
			self.signUpDelegate?.screenlet?(self,
					onSignUpResponseUserAttributes: interactor.resultUserAttributes!)

			if self.autoLogin {
				self.doAutoLogin(interactor.resultUserAttributes!)

				if let ctx = SessionContext.currentContext where self.saveCredentials {
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

	private func createSaveInteractor() -> SaveUserInteractor {
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

	private func doAutoLogin(userAttributes: [String:AnyObject]) {
		let userNameKeys : [BasicAuthMethod:String] = [
			.Email : "emailAddress",
			.ScreenName : "screenName",
			.UserId: "userId"
		]

		let currentAuth = BasicAuthMethod.fromUserName(anonymousApiUserName!)

		if let currentKey = userNameKeys[currentAuth],
				userName = userAttributes[currentKey] as? String {

			SessionContext.loginWithBasic(
				username: userName,
				password: self.viewModel.password!,
				userAttributes: userAttributes)

			self.autoLoginDelegate?.screenlet?(self,
				onLoginResponseUserAttributes: userAttributes)
		}
	}

}
