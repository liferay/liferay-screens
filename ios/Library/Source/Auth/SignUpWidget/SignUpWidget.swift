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


@IBDesignable public class SignUpWidget: BaseWidget {

	@IBInspectable public var anonymousApiUserName: String?
	@IBInspectable public var anonymousApiPassword: String?

	@IBInspectable public var autologin = true
	@IBInspectable public var saveCredentials = false

	@IBOutlet public var delegate: SignUpWidgetDelegate?
	@IBOutlet public var autoLoginDelegate: LoginWidgetDelegate?

	public var authMethod = AuthMethod.Email.toRaw()

	internal var signUpView: SignUpView {
		return widgetView as SignUpView
	}

	internal var signUpConnector: LiferaySignUpConnector {
		return connector as LiferaySignUpConnector
	}

	private var creatingUsername: String?
	private var creatingPassword: String?


	//MARK: BaseWidget

	override internal func onCustomAction(actionName: String?, sender: AnyObject?) {
		if signUpView.emailAddress == nil {
			showHUDAlert(message: "Please, enter your email address at least")
		}
		else {
			sendSignUp()
		}
	}

	internal func onSignUpResult(connector: LiferaySignUpConnector) {
		delegate?.onSignUpResponse?(connector.createdUserAttributes!)

		if autologin {
			SessionContext.removeStoredSession()

			SessionContext.createSession(
					username: connector.emailAddress!,
					password: connector.password!,
					userAttributes: connector.createdUserAttributes!)

			autoLoginDelegate?.onLoginResponse?(connector.createdUserAttributes!)

			if saveCredentials {
				if SessionContext.storeSession() {
					autoLoginDelegate?.onCredentialsSaved?()
				}
			}
		}
	}


	//MARK: Private methods

	private func sendSignUp() {
		connector = LiferaySignUpConnector(widget: self)

		signUpConnector.anonymousApiUserName = anonymousApiUserName
		signUpConnector.anonymousApiPassword = anonymousApiPassword

		signUpConnector.emailAddress = signUpView.emailAddress
		signUpConnector.password = signUpView.password
		signUpConnector.firstName = signUpView.firstName
		signUpConnector.lastName = signUpView.lastName

		signUpConnector.enqueue() {
			if $0.lastError != nil {
				self.delegate?.onSignUpError?($0.lastError!)
			}
			else {
				self.onSignUpResult(self.signUpConnector)
			}
		}

	}

}
