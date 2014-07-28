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

@objc protocol SignUpWidgetDelegate {

	optional func onSignUpResponse(attributes: [String:AnyObject])
	optional func onSignUpError(error: NSError)

}

class SignUpWidget: BaseWidget {

    @IBOutlet var delegate: SignUpWidgetDelegate?
    

    // BaseWidget METHODS
    
    
	override func onCustomAction(actionName: String?, sender: UIControl) {
		sendSignUpWithEmailAddress(signUpView().emailAddressField!.text, password:signUpView().passwordField!.text, firstName:signUpView().firstNameField!.text, lastName:signUpView().lastNameField!.text)
	}

    override func onServerError(error: NSError) {
		delegate?.onSignUpError?(error)

        hideHUDWithMessage("Error signing in!", details: nil)
    }
    
	override func onServerResult(result: [String:AnyObject]) {
		delegate?.onSignUpResponse?(result)

		LiferayContext.instance.clearSession()
		LRSession.removeStoredCredential()

		hideHUDWithMessage("Sign up completed", details: nil)
    }
    

	private func signUpView() -> SignUpView {
		return widgetView as SignUpView
	}

	private func sendSignUpWithEmailAddress(emailAddress:String, password:String, firstName:String, lastName:String) {
		showHUDWithMessage("Sending sign up...", details:"Wait few seconds...")

		// TODO remove this when MobileSDK supports unauthenticated call
		let session = LiferayContext.instance.createSession("test", password: "test")
		session.callback = self

		// TODO service call
	}

}