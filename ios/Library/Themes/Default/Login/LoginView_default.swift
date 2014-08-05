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

class LoginView_default: LoginView {

	@IBOutlet var userNameField: UITextField?
	@IBOutlet var passwordField: UITextField?
	@IBOutlet var rememberSwitch: UISwitch?
	@IBOutlet var loginButton: UIButton?
	@IBOutlet var userNameBackground: UIImageView?
	@IBOutlet var passwordBackground: UIImageView?

	override public var shouldRememberCredentials: Bool {
		if let rememberSwitchValue = rememberSwitch {
			return rememberSwitchValue.on;
		}

		return super.shouldRememberCredentials
	}

	override public func setAuthType(authType: String) {
		userNameField!.placeholder = authType

		switch authType {
		case AuthType.Email.toRaw():
			userNameField!.keyboardType = UIKeyboardType.EmailAddress
		case AuthType.ScreenName.toRaw():
			userNameField!.keyboardType = UIKeyboardType.ASCIICapable

			let userName = userNameField!.text as NSString
			if userName.containsString("@") {
				userNameField!.text = userName.componentsSeparatedByString("@")[0] as String
			}
		case AuthType.UserId.toRaw():
			userNameField!.keyboardType = UIKeyboardType.NumberPad
		default:
			break
		}
	}

	override public func getUserName() -> String {
		return userNameField!.text
	}

	override public func getPassword() -> String {
		return passwordField!.text
	}

	override public func setUserName(userName: String) {
		userNameField!.text = userName
	}

	override public func setPassword(password: String) {
		passwordField!.text = password
	}

	// MARK: BaseWidgetView

	override func becomeFirstResponder() -> Bool {
		return userNameField!.becomeFirstResponder()
	}

	// MARK: UITextFieldDelegate

	func textFieldShouldBeginEditing(textField: UITextField!) -> Bool {
		if userNameBackground {
			userNameBackground!.highlighted = (textField == userNameField);
		}

		if passwordBackground {
			passwordBackground!.highlighted = (textField == passwordField);
		}

		return true
	}

}
