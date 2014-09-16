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


@objc protocol LoginWidgetDelegate {

	optional func onLoginResponse(attributes: [String:AnyObject])
	optional func onLoginError(error: NSError)

	optional func onCredentialsSaved(session:LRSession)
	optional func onCredentialsLoaded(session:LRSession)

}


public class LoginWidget: BaseWidget {

	@IBOutlet var delegate: LoginWidgetDelegate?

	private let supportedAuthClosures = [
		AuthType.Email.toRaw(): authWithEmail,
		AuthType.ScreenName.toRaw(): authWithScreenName,
		AuthType.UserId.toRaw(): authWithUserId]

	private var authClosure: ((String, String, LRUserService_v62, NSError -> Void) -> Void)?

	public class func storedSession() -> LRSession? {
		return LRSession.sessionFromStoredCredential()
	}

	//FIXME
	// XCode crashes with "swift_unknownWeakLoadStrong" error
	// Storing the enum as a String seems to workaround the problem
	// This code is the optimal solution to be used when XCode is fixed
	//
	// var authType: AuthType = AuthType.Email {
	// 	didSet {
	//		loginView().setAuthType(authType)
	//	}
	// }
	public func setAuthType(authType:String) {
        loginView().setAuthType(authType)
        
        authClosure = supportedAuthClosures[authType]
	}


	//MARK: BaseWidget METHODS

	override internal func onCreated() {
        setAuthType(AuthType.Email.toRaw())

		if let session = LRSession.sessionFromStoredCredential() {
			LiferayContext.instance.currentSession = session

			loginView().setUserName(session.username)
			loginView().setPassword(session.password)

			delegate?.onCredentialsLoaded?(session)
		}
	}

	override internal func onCustomAction(actionName: String?, sender: AnyObject?) {
		if actionName == "login-action" {
			sendLoginWithUserName(loginView().getUserName(), password:loginView().getPassword())
		}
	}

	override internal func onServerError(error: NSError) {
		delegate?.onLoginError?(error)

		LiferayContext.instance.clearSession()
		LRSession.removeStoredCredential()

		finishOperationWithError(error, message:"Error signing in!")
	}

	override internal func onServerResult(result: [String:AnyObject]) {
		delegate?.onLoginResponse?(result)

		if loginView().shouldRememberCredentials {
			if LiferayContext.instance.currentSession!.storeCredential() {
				delegate?.onCredentialsSaved?(LiferayContext.instance.currentSession!)
			}
		}

		finishOperationWithMessage("Sign in completed")
	}


	//MARK: PRIVATE METHDOS

	private func loginView() -> LoginView {
		return widgetView as LoginView
	}

	private func sendLoginWithUserName(userName:String, password:String) {
		startOperationWithMessage("Sending sign in...", details:"Wait few seconds...")

		let session = LiferayContext.instance.createSession(userName, password: password)
		session.callback = self

		authClosure!(userName, password, LRUserService_v62(session: session)) {
			self.onFailure($0)
		}
	}

}

func authWithEmail(email:String, password:String, service:LRUserService_v62,
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

func authWithScreenName(name:String, password:String, service:LRUserService_v62,
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

func authWithUserId(userId:String, password:String, service:LRUserService_v62,
		onError:(NSError) -> Void) {

	var outError: NSError?

	service.getUserByIdWithUserId((userId.toInt()! as NSNumber).longLongValue, error: &outError)

	if let error = outError {
		onError(error)
	}
}
