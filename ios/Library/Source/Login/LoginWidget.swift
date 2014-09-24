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

	optional func onCredentialsSaved(session:LRSession)
	optional func onCredentialsLoaded(session:LRSession)

}


public class LoginWidget: BaseWidget {

	@IBOutlet public var delegate: LoginWidgetDelegate?

	internal var loginView: LoginView {
		return widgetView as LoginView
	}

	private let supportedAuthClosures = [
		LoginAuthType.Email: authWithEmail,
		LoginAuthType.ScreenName: authWithScreenName,
		LoginAuthType.UserId: authWithUserId]

	private var authClosure: ((String, String, LRUserService_v62, NSError -> Void) -> Void)?

	private var loginSession: LRSession?


	//MARK: Class methods

	public class func storedSession() -> LRSession? {
		return LRSession.sessionFromStoredCredential()
	}


	//MARK: BaseWidget

	override internal func onCreated() {
        setAuthType(LoginAuthType.Email)

		if let session = LRSession.sessionFromStoredCredential() {
			LiferayContext.instance.currentSession = session

			loginView.setUserName(session.username)
			loginView.setPassword(session.password)

			delegate?.onCredentialsLoaded?(session)
		}
	}

	override internal func onCustomAction(actionName: String?, sender: AnyObject?) {
		if actionName == "login-action" {
			sendLoginWithUserName(loginView.getUserName(), password:loginView.getPassword())
		}
	}

	override internal func onServerError(error: NSError) {
		delegate?.onLoginError?(error)

		LRSession.removeStoredCredential()

		finishOperationWithError(error, message:"Error signing in!")
	}

	override internal func onServerResult(result: [String:AnyObject]) {

		LiferayContext.instance.createSession(
				username: loginSession!.username,
				password: loginSession!.password,
				userAttributes: result)

		delegate?.onLoginResponse?(result)

		if loginView.shouldRememberCredentials {
			if LiferayContext.instance.currentSession!.storeCredential() {
				delegate?.onCredentialsSaved?(LiferayContext.instance.currentSession!)
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

		LiferayContext.instance.clearSession()

		loginSession = LRSession(
				server: LiferayContext.instance.server,
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
			(LiferayContext.instance.companyId as NSNumber).longLongValue,
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
			(LiferayContext.instance.companyId as NSNumber).longLongValue,
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
