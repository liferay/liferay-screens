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

public enum AuthType: String {
	case Email = "email"
	case Screenname = "screenname"
	case UserId = "userid"
}

class LoginView: BaseWidgetView, UITextFieldDelegate {

	@IBOutlet var usernameField: UITextField?
	@IBOutlet var passwordField: UITextField?
	@IBOutlet var rememberSwitch: UISwitch?
	@IBOutlet var loginButton: UIButton?
	@IBOutlet var usernameBackground: UIImageView?
	@IBOutlet var passwordBackground: UIImageView?

	public var shouldRememberCredentials: Bool {
		if let rememberSwitchValue = rememberSwitch {
			return rememberSwitchValue.on;
		}

		return true
	}

	public func setAuthType(authType: String) {
		switch authType {
		case AuthType.Email.toRaw():
            usernameField!.placeholder = "Email"
            usernameField!.keyboardType = UIKeyboardType.EmailAddress
		case AuthType.Screenname.toRaw():
			usernameField!.placeholder = "Screen name"
            usernameField!.keyboardType = UIKeyboardType.ASCIICapable

			let username = usernameField!.text as NSString
			if username.containsString("@") {
				usernameField!.text = username.componentsSeparatedByString("@")[0] as String
			}
		case AuthType.UserId.toRaw():
			usernameField!.placeholder = "User ID"
			usernameField!.keyboardType = UIKeyboardType.NumberPad
		default:
            usernameField!.placeholder = "Unknown"
		}
	}

	// BaseWidgetView METHODS

    override func becomeFirstResponder() -> Bool {
        return usernameField!.becomeFirstResponder()
    }

    // UITextFieldDelegate METHODS

	func textFieldShouldBeginEditing(textField: UITextField!) -> Bool {
		usernameBackground!.highlighted = (textField == usernameField);
		passwordBackground!.highlighted = (textField == passwordField);

		return true
	}

	func textFieldShouldReturn(textField: UITextField!) -> Bool {
		if textField == usernameField {
			textField.resignFirstResponder()
			passwordField!.becomeFirstResponder()
		}
		else if textField == passwordField {
			textField.resignFirstResponder()
			loginButton!.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
		}

		return true
	}

}
