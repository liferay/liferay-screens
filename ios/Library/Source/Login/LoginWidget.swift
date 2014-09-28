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

	public var authType: LoginAuthType = .Email {
		didSet {
			(widgetView as? LoginView)?.authType = authType

			switch authType {
				case .Email:
					loginConnector = LiferayLoginEmailConnector(widget: self)
				case .ScreenName:
					loginConnector = LiferayLoginScreenNameConnector(widget: self)
				default: ()
			}
		}
	}

	internal var loginView: LoginView {
		return widgetView as LoginView
	}

	private let supportedAuthClosures = [
		LoginAuthType.Email: authWithEmail,
		LoginAuthType.ScreenName: authWithScreenName,
		LoginAuthType.UserId: authWithUserId]

	private var authClosure: ((String, String, LRUserService_v62, NSError -> Void) -> Void)?

	private var loginSession: LRSession?


	//MARK: BaseWidget

	override internal func onCreated() {
		loginView.saveCredentials = self.saveCredentials

		if SessionContext.loadSessionFromStore() {
			loginView.setUserName(SessionContext.currentUserName!)
			loginView.setPassword(SessionContext.currentPassword!)

			delegate?.onCredentialsLoaded?()
		}
	}

	override internal func onCustomAction(actionName: String?, sender: AnyObject?) {
		if actionName == "login-action" {
			sendLoginWithUserName(loginView.getUserName(), password:loginView.getPassword())
		}
	}

	override internal func onServerError(error: NSError) {
		delegate?.onLoginError?(error)

		SessionContext.removeStoredSession()

		finishOperationWithError(error, message:"Error signing in!")
	}

	override internal func onServerResult(result: [String:AnyObject]) {
		SessionContext.createSession(
				username: loginSession!.username,
				password: loginSession!.password,
				userAttributes: result)

		delegate?.onLoginResponse?(result)

		if saveCredentials {
			if SessionContext.storeSession() {
				delegate?.onCredentialsSaved?()
			}
		}

		finishOperation()
	}


	//MARK: Public methods

	public func setAuthType(authType:LoginAuthType) {
        loginView.setAuthType(authType)
        
        authClosure = supportedAuthClosures[authType]
	}


	//MARK: Private methods

	private func sendLoginWithUserName(userName:String, password:String) {
		startOperationWithMessage("Sending sign in...", details:"Wait few seconds...")

		SessionContext.clearSession()

		loginSession = LRSession(
				server: LiferayServerContext.instance.server,
				username: userName,
				password: password)
		loginSession!.callback = self

		authClosure!(userName, password, LRUserService_v62(session: loginSession)) {
			self.onFailure($0)
		}
	}

}

func authWithEmail(email:String, password:String,
		service:LRUserService_v62,
		onError:(NSError) -> Void) {

	var outError: NSError?

	service.getUserByEmailAddressWithCompanyId(
			(LiferayServerContext.instance.companyId as NSNumber).longLongValue,
			emailAddress:email,
			error:&outError)

	if let error = outError {
		onError(error)
	}
}

func authWithScreenName(name:String,
		password:String,
		service:LRUserService_v62,
		onError:(NSError) -> Void) {

	var outError: NSError?

	service.getUserByScreenNameWithCompanyId(
			(LiferayServerContext.instance.companyId as NSNumber).longLongValue,
			screenName:name,
			error: &outError)

	if let error = outError {
		onError(error)
	}
}

func authWithUserId(userId:String,
		password:String,
		service:LRUserService_v62,
		onError:(NSError) -> Void) {

	var outError: NSError?

	service.getUserByIdWithUserId((userId.toInt()! as NSNumber).longLongValue, error: &outError)

	if let error = outError {
		onError(error)
	}
}
