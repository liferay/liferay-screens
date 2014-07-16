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

class ForgotPasswordView: BaseWidgetView, UITextFieldDelegate {

	@IBOutlet var usernameLabel: UILabel
	@IBOutlet var usernameField: UITextField
	@IBOutlet var requestPasswordButton: UIButton

	
	// PUBLIC METHODS
	
	
	func setAuthType(authType: String) {
		usernameLabel.text = authType

        switch authType {
		case AuthType.Email.toRaw():
			usernameField.keyboardType = UIKeyboardType.EmailAddress
		case AuthType.ScreenName.toRaw():
			usernameField.keyboardType = UIKeyboardType.ASCIICapable
		default:
			break
		}
	}


	// BaseWidgetView METHODS


	override func becomeFirstResponder() -> Bool {
		return usernameField.becomeFirstResponder()
	}


	// UITextFieldDelegate METHODS


	func textFieldShouldReturn(textField: UITextField!) -> Bool {
		textField.resignFirstResponder()

		requestPasswordButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)

		return true
	}

}