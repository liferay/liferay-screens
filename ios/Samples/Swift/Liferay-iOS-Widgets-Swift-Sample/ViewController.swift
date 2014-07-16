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

class ViewController: UIViewController, LoginWidgetDelegate, ForgotPasswordWidgetDelegate {

	@IBOutlet var loginWidget: LoginWidget!
	@IBOutlet var forgotWidget: ForgotPasswordWidget!

    
    // UIViewController METHODS
    
    
	override func viewDidLoad() {
		super.viewDidLoad()

		// WORKAROUND!
		// Delegate assignment in IB doesn't work!!
		loginWidget.delegate = self
		loginWidget.setAuthType(AuthType.ScreenName)

		if LoginWidget.storedSession() {
			loginWidget.hidden = true
		}
		else {
			loginWidget.becomeFirstResponder()
		}
		
		forgotWidget.delegate = self;
		forgotWidget.setAuthType(AuthType.ScreenName)
	}

	override func didReceiveMemoryWarning() {
		super.didReceiveMemoryWarning()
	}

    
    // LoginWidgetDelegate METHODS

    
	func onCredentialsLoaded(session:LRSession) {
		print("Saved loaded for server " + session.server)
 	}

	func onCredentialsSaved(session:LRSession) {
		print("Saved credentials for server " + session.server)
 	}
 
 	func onLoginError(error: NSError)  {
 		println("Error -> " + error.description)
	}

	func onLoginResponse(attributes: [String:AnyObject!])  {
		NSLog("Login %@", attributes)
	}

	func onForgotPasswordError(error: NSError)  {
		println("Error -> " + error.description)

	}

	func onForgotPasswordResponse(attributes: Dictionary<String, Any!>)  {
		NSLog("Forgot %@", attributes)
	}

}

