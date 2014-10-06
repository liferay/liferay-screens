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


public class LoginWidget: BaseWidget, AuthBasedData {

	//MARK: Inspectables

	@IBInspectable public var authMethod: String? = AuthMethod.Email.toRaw() {
		didSet {
			copyAuth(source: self, target: widgetView)
			serverOperation = createLoginOperation(authMethod: AuthMethod.create(authMethod))
		}
	}

	@IBInspectable public var saveCredentials: Bool = false {
		didSet {
			(widgetView as? AuthBasedData)?.saveCredentials = self.saveCredentials
		}
	}

	@IBInspectable public var companyId: Int64 = 0 {
		didSet {
			(widgetView as? LoginData)?.companyId = self.companyId
		}
	}


	@IBOutlet public var delegate: LoginWidgetDelegate?


	internal var loginData: LoginData {
		return widgetView as LoginData
	}

	internal var loginOperation: LiferayLoginBaseOperation {
		return serverOperation as LiferayLoginBaseOperation
	}


	//MARK: BaseWidget

	override internal func onCreated() {
		super.onCreated()
		
		copyAuth(source: self, target: widgetView)
		serverOperation = createLoginOperation(authMethod: AuthMethod.create(authMethod))

		loginData.companyId = companyId

		if SessionContext.loadSessionFromStore() {
			loginData.userName = SessionContext.currentUserName!
			loginData.password = SessionContext.currentPassword!

			delegate?.onCredentialsLoaded?()
		}
	}

	override internal func onUserAction(actionName: String?, sender: AnyObject?) {
		serverOperation?.validateAndEnqueue() {
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
		delegate?.onLoginResponse?(loginOperation.loggedUserAttributes!)

		if saveCredentials {
			if SessionContext.storeSession() {
				delegate?.onCredentialsSaved?()
			}
		}
	}

	private func createLoginOperation(#authMethod: AuthMethod) -> LiferayLoginBaseOperation {
		switch authMethod {
			case .ScreenName:
				return LiferayLoginScreenNameOperation(widget: self)
			case .UserId:
				return LiferayLoginUserIdOperation(widget: self)
			default:
				return LiferayLoginEmailOperation(widget: self)
		}
	}

}
