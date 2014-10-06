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


public class ViewController: UIViewController, LoginScreenletDelegate, ForgotPasswordScreenletDelegate {

	@IBOutlet private var loginScreenlet: LoginScreenlet?
	@IBOutlet private var forgotScreenlet: ForgotPasswordScreenlet?

    
    // UIViewController METHODS
    
    
	override public func viewDidLoad() {
		super.viewDidLoad()

		// WORKAROUND!
		// Delegate assignment in IB doesn't work!!
		loginScreenlet!.delegate = self
		loginScreenlet!.authMethod = AuthMethod.Email.toRaw()

		forgotScreenlet!.delegate = self;
		forgotScreenlet!.authMethod = AuthMethod.ScreenName.toRaw()
	}

    // LoginScreenletDelegate METHODS

    
	public func onCredentialsLoaded(session:LRSession) {
		print("Saved loaded for server " + session.server)
 	}

	public func onCredentialsSaved(session:LRSession) {
		print("Saved credentials for server " + session.server)
 	}
 
 	public func onLoginError(error: NSError)  {
 		println("Error -> " + error.description)
	}

	public func onLoginResponse(attributes: [String:AnyObject])  {
		NSLog("Login %@", attributes)
	}

	public func onForgotPasswordError(error: NSError)  {
		println("Error -> " + error.description)
	}

	public func onForgotPasswordResponse(newPasswordSent:Bool)  {
		let emailContent = newPasswordSent ? "new password" : "reset password link"

		println("Email with \(emailContent) was sent")
	}

}
