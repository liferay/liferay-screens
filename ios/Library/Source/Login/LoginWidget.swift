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

	@optional func onLoginResponse(attributes: Dictionary<String, Any!>)
	@optional func onLoginError(error: NSError)

	@optional func onCredentialsSaved(session:LRSession)
	@optional func onCredentialsLoaded(session:LRSession)

}

@IBDesignable class LoginWidget: BaseWidget {

    @IBOutlet var delegate: LoginWidgetDelegate?
    
	typealias AuthCall = (String, String, LRUserService_v62, (NSError)->()) -> (Void)

    let authMethods: Dictionary<String, AuthCall> = [
        AuthType.Email.toRaw(): authCallWithEmail,
        AuthType.Screenname.toRaw(): authCallWithScreenname]
    
    var authMethod: AuthCall?

	class func storedCredencials() -> LRSession? {
		return LRSession.storedSession()
	}
    
	/*
	WORKAROUND!
	XCode crashes with "swift_unknownWeakLoadStrong" error
	Storing the enum as a String seems to workaround the problem
	This code is the optimal solution to be used when XCode is fixed

	var authType: AuthType = AuthType.Email {
		didSet {
			loginView().setAuthType(authType)
		}
	}
	*/
    func setAuthType(authType:AuthType) {
        loginView().setAuthType(authType.toRaw())
        
        authMethod = authMethods[authType.toRaw()]
    }

    
    // BaseWidget METHODS
    
    
	override func onCreate() {
        setAuthType(AuthType.Email)

        loginView().usernameField.text = "test@liferay.com"

		if let storedSession = LRSession.storedSession() {
			LiferayContext.instance.currentSession = storedSession
			if delegate {
				println("hay delegate")
			}
			else {
				println("no hay delegate")
			}
			delegate?.onCredentialsLoaded?(storedSession)
		}
	}

	override func onCustomAction(actionName: String?, sender: UIControl) {
		if actionName == "login-action" {
			sendLoginWithUsername(loginView().usernameField.text, password:loginView().passwordField.text)
		}
	}

    override func onServerError(error: NSError) {
		delegate?.onLoginError?(error)

		LiferayContext.instance.clearSession()
		LRSession.emptyStore()

        hideHUDWithMessage("Error signing in!", details: nil)
    }
    
    override func onServerResult(result: AnyObject!) {
        if let dict = result as? Dictionary<String, Any!> {
			delegate?.onLoginResponse?(dict)

			if loginView().shouldRememberCredentials {
				if LiferayContext.instance.currentSession!.store() {
					delegate?.onCredentialsSaved?(LiferayContext.instance.currentSession!)
				}
			}
        }
        
        hideHUDWithMessage("Sign in completed", details: nil)
    }
    

    // PRIVATE METHDOS
    
    
	func loginView() -> LoginView {
		return widgetView as LoginView
	}

	func sendLoginWithUsername(username:String, password:String) {
		showHUDWithMessage("Sending sign in...", details:"Wait few seconds...")

		let session = LiferayContext.instance.createSession(username, password: password)
		session.callback = self

		authMethod!(username, password, LRUserService_v62(session: session)) {error in
			self.onFailure(error)
		}
	}

}

func authCallWithEmail(email:String, password:String, service:LRUserService_v62, onError:(NSError)->()) {
	let companyId: CLongLong = (LiferayContext.instance.companyId as NSNumber).longLongValue

	var outError: NSError?

	service.getUserByEmailAddressWithCompanyId(companyId, emailAddress:email, error:&outError)

	if let error = outError {
		onError(error)
	}
}

func authCallWithScreenname(name:String, password:String, service:LRUserService_v62, onError:(NSError)->()) {
	let companyId: CLongLong = (LiferayContext.instance.companyId as NSNumber).longLongValue

	var outError: NSError?

	service.getUserByScreenNameWithCompanyId(companyId, screenName:name, error: &outError)

	if let error = outError {
		onError(error)
	}
}
