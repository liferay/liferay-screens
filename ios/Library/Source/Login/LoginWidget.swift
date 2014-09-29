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


@objc public protocol LoginWidgetDelegate {

	optional func onLoginResponse(attributes: [String:AnyObject])
	optional func onLoginError(error: NSError)

	optional func onCredentialsSaved()
	optional func onCredentialsLoaded()

}


public class LoginWidget: BaseWidget {

	@IBInspectable public var saveCredentials: Bool = false {
		didSet {
			if widgetView != nil {
				loginView.saveCredentials = self.saveCredentials
			}
		}
	}

	@IBOutlet public var delegate: LoginWidgetDelegate?

	public var authType: LoginAuthType = LoginAuth.Email.toRaw() {
		didSet {
			(widgetView as? LoginView)?.authType = authType

			switch LoginAuth.fromRaw(authType)! {
				case .Email:
					connector = LiferayLoginEmailConnector(widget: self)
				case .ScreenName:
					connector = LiferayLoginScreenNameConnector(widget: self)
				default: ()
			}
		}
	}

	internal var loginView: LoginView {
		return widgetView as LoginView
	}

	internal var loginConnector: LiferayLoginBaseConnector {
		return connector as LiferayLoginBaseConnector
	}

	private var loginSession: LRSession?


	//MARK: BaseWidget

	override internal func onCreated() {
		loginView.saveCredentials = self.saveCredentials

		if SessionContext.loadSessionFromStore() {
			loginView.userName = SessionContext.currentUserName!
			loginView.password = SessionContext.currentPassword!

			delegate?.onCredentialsLoaded?()
		}
	}

	override internal func onCustomAction(actionName: String?, sender: AnyObject?) {
		if actionName == "login-action" {
			sendLoginWithUserName(loginView.userName!, password:loginView.password!)
		}
	}

	internal func onLoginResult(result: [String:AnyObject], session: LRSession) {
		SessionContext.createSession(
				username: session.username,
				password: session.password,
				userAttributes: result)

		delegate?.onLoginResponse?(result)

		if saveCredentials {
			if SessionContext.storeSession() {
				delegate?.onCredentialsSaved?()
			}
		}
	}


	//MARK: Private methods

	private func sendLoginWithUserName(userName:String, password:String) {
		loginConnector.userName = userName
		loginConnector.password = password

		loginConnector.addToQueue() {
			if let error = $0.lastError {
				self.delegate?.onLoginError?(error)
			}
			else {
				self.onLoginResult(self.loginConnector.loggedUserAttributes!, session: self.loginConnector.session!)
			}
		}
	}

}
