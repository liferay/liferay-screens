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
		sendSignUpWithEmailAddress(signUpView.getEmailAddress(),
				password:signUpView.getPassword(),
				firstName:signUpView.getFirstName(),
				lastName:signUpView.getLastName())
	}

	internal func onSignUpResult(connector: LiferaySignUpConnector) {
		delegate?.onSignUpResponse?(connector.createdUserAttributes!)

		if autologin {
			SessionContext.removeStoredSession()

			SessionContext.createSession(
					username: connector.session!.username!,
					password: connector.session!.password!,
					userAttributes: connector.createdUserAttributes!)

			autoLoginDelegate?.onLoginResponse?(connector.createdUserAttributes!)

			if saveCredentials {
				if SessionContext.storeSession() {
					autoLoginDelegate?.onCredentialsSaved?()
				}
			}
		}

		finishOperation()
	}


	//MARK: Private methods

	private func sendSignUpWithEmailAddress(
			emailAddress:String, password:String, firstName:String, lastName:String) {

		if anonymousApiUserName == nil || anonymousApiPassword == nil {
			println(
				"ERROR: The credentials to use for anonymous API calls must be set in order to use " +
				"SignUpWidget")

			return
		}

		startOperationWithMessage("Sending sign up...", details:"Wait few seconds...")

		let session = LRSession(
				server: LiferayServerContext.instance.server,
				username: anonymousApiUserName!,
				password: anonymousApiPassword!)

		connector = LiferaySignUpConnector(widget: self, session: session)

		signUpConnector.emailAddress = signUpView.getEmailAddress()
		signUpConnector.password = signUpView.getPassword()
		signUpConnector.firstName = signUpView.getFirstName()
		signUpConnector.lastName = signUpView.getLastName()

		signUpConnector.addToQueue() {
			if $0.lastError != nil {
				self.delegate?.onSignUpError?($0.lastError!)

				self.finishOperationWithError($0.lastError!, message:"Error signing up!")
			}
			else {
				self.onSignUpResult(self.signUpConnector)
			}
		}

	}

}
