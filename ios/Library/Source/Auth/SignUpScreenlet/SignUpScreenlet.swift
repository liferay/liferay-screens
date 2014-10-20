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


@objc public protocol SignUpScreenletDelegate {

	optional func onSignUpResponse(attributes: [String:AnyObject])
	optional func onSignUpError(error: NSError)

}


@IBDesignable public class SignUpScreenlet: BaseScreenlet, AnonymousAuthData {

	//MARK: Inspectables

	@IBInspectable public var anonymousApiUserName: String? = "test@liferay.com"
	@IBInspectable public var anonymousApiPassword: String? = "test"

	@IBInspectable public var autologin: Bool = true

	@IBInspectable public var saveCredentials: Bool = true

	@IBInspectable public var companyId: Int64 = 0 {
		didSet {
			(screenletView as? SignUpData)?.companyId = self.companyId
		}
	}

	@IBOutlet public var delegate: SignUpScreenletDelegate?
	@IBOutlet public var autoLoginDelegate: LoginScreenletDelegate?


	internal var signUpData: SignUpData {
		return screenletView as SignUpData
	}


	//MARK: BaseScreenlet

	override func onCreated() {
		super.onCreated()

		signUpData.companyId = self.companyId
	}

	override internal func onUserAction(actionName: String?, sender: AnyObject?) {
		let signUpOperation = LiferaySignUpOperation(screenlet: self)

		signUpOperation.validateAndEnqueue() {
			if $0.lastError != nil {
				self.delegate?.onSignUpError?($0.lastError!)
			}
			else {
				self.onSignUpSuccess(userAttributes: signUpOperation.resultUserAttributes!)
			}
		}
	}


	//MARK: Private methods

	private func onSignUpSuccess(#userAttributes: [String:AnyObject]) {
		delegate?.onSignUpResponse?(userAttributes)

		if autologin {
			SessionContext.removeStoredSession()

			SessionContext.createSession(
					username: signUpData.emailAddress!,
					password: signUpData.password!,
					userAttributes: userAttributes)

			autoLoginDelegate?.onLoginResponse?(userAttributes)

			if saveCredentials {
				if SessionContext.storeSession() {
					autoLoginDelegate?.onCredentialsSaved?()
				}
			}
		}
	}

}
