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


@objc public protocol SignUpWidgetDelegate {

	optional func onSignUpResponse(attributes: [String:AnyObject])
	optional func onSignUpError(error: NSError)

}


@IBDesignable public class SignUpWidget: BaseWidget, AnonymousAuthData {

	//MARK: Inspectables

	@IBInspectable public var anonymousApiUserName: String? = "test@liferay.com"
	@IBInspectable public var anonymousApiPassword: String? = "test"

	@IBInspectable public var autologin = true

	@IBInspectable public var saveCredentials = false

	@IBOutlet public var delegate: SignUpWidgetDelegate?
	@IBOutlet public var autoLoginDelegate: LoginWidgetDelegate?


	internal var signUpData: SignUpData {
		return widgetView as SignUpData
	}

	internal var signUpOperation: LiferaySignUpOperation {
		return serverOperation as LiferaySignUpOperation
	}


	//MARK: BaseWidget

	override func onCreated() {
		super.onCreated()

		serverOperation = LiferaySignUpOperation(widget: self)
	}

	override internal func onUserAction(actionName: String?, sender: AnyObject?) {
		serverOperation?.validateAndEnqueue() {
			if $0.lastError != nil {
				self.delegate?.onSignUpError?($0.lastError!)
			}
			else {
				self.onSignUpSuccess()
			}
		}
	}


	//MARK: Private methods

	private func onSignUpSuccess() {
		delegate?.onSignUpResponse?(signUpOperation.createdUserAttributes!)

		if autologin {
			SessionContext.removeStoredSession()

			SessionContext.createSession(
					username: signUpData.emailAddress!,
					password: signUpData.password!,
					userAttributes: signUpOperation.createdUserAttributes!)

			autoLoginDelegate?.onLoginResponse?(signUpOperation.createdUserAttributes!)

			if saveCredentials {
				if SessionContext.storeSession() {
					autoLoginDelegate?.onCredentialsSaved?()
				}
			}
		}
	}

}
