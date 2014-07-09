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

class ViewController: UIViewController, LoginWidgetDelegate {

	@IBOutlet var loginWidget: LoginWidget!

    
    // UIViewController METHODS
    
    
	override func viewDidLoad() {
		super.viewDidLoad()

		// WORKAROUND!
		// Delegate assignment in IB doesn't work!!
		loginWidget.delegate = self
        loginWidget.setAuthType(AuthType.Email)

		loginWidget.becomeFirstResponder()
	}

	override func didReceiveMemoryWarning() {
		super.didReceiveMemoryWarning()
	}

    
    // LoginWidgetDelegate METHODS
    
    
	func onLoginError(error: NSError)  {
		println("Error -> " + error.description)

	}

	func onLoginResponse(attributes: Dictionary<String, Any!>)  {
		NSLog("Login %@", attributes)
	}

}

