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


public class LoginWidget: BaseWidget, AuthBased {

	//MARK: Inspectables

	@IBInspectable public var authMethod: AuthMethodType = AuthMethod.Email.toRaw() {
		didSet {
			copyAuth(source: self, target: widgetView)

			switch AuthMethod.fromRaw(authMethod)! {
				case .Email:
					connector = LiferayLoginEmailConnector(widget: self)
				case .ScreenName:
					connector = LiferayLoginScreenNameConnector(widget: self)
				case .UserId:
					connector = LiferayLoginUserIdConnector(widget: self)
				default: ()
			}
		}
	}

	@IBInspectable public var saveCredentials: Bool = false {
		didSet {
			(widgetView as? AuthBased)?.saveCredentials = self.saveCredentials
		}
	}

	@IBOutlet public var delegate: LoginWidgetDelegate?


	internal var loginView: LoginView {
		return widgetView as LoginView
	}

	internal var loginConnector: LiferayLoginBaseConnector {
		return connector as LiferayLoginBaseConnector
	}


	//MARK: BaseWidget

	override internal func onCreated() {
		super.onCreated()
		
		copyAuth(source: self, target: widgetView)

		if SessionContext.loadSessionFromStore() {
			loginView.userName = SessionContext.currentUserName!
			loginView.password = SessionContext.currentPassword!

			delegate?.onCredentialsLoaded?()
		}
	}

	override internal func onUserAction(actionName: String?, sender: AnyObject?) {
		connector?.validateAndEnqueue() {
			if let error = $0.lastError {
				self.delegate?.onLoginError?(error)
			}
			else {
				self.onLoginSuccess()
			}
		}
	}


	//MARK: Private methods

	private func onLoginSuccess() {
		delegate?.onLoginResponse?(loginConnector.loggedUserAttributes!)

		if saveCredentials {
			if SessionContext.storeSession() {
				delegate?.onCredentialsSaved?()
			}
		}
	}

}
