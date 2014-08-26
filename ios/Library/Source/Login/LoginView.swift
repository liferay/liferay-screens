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
	case Email = "Email Address"
	case ScreenName = "Screen Name"
	case UserId = "User ID"
}

class LoginView: BaseWidgetView, UITextFieldDelegate {

	@IBOutlet var userNameField: UITextField?
	@IBOutlet var userNamePlaceholder: UILabel?
	@IBOutlet var userNameBackground: UIImageView?

	@IBOutlet var passwordField: UITextField?
	@IBOutlet var passwordPlaceholder: UILabel?
	@IBOutlet var passwordBackground: UIImageView?

	@IBOutlet var rememberSwitch: UISwitch?
	@IBOutlet var loginButton: UIButton?

	public var userName:String {
		get {
			return userNameField!.text
		}
		set {
			userNameField!.text = newValue

			if let placeholderValue = userNamePlaceholder {
				placeholderValue.changeVisibility(visible: newValue == "", delay: 0.0)
			}
		}
	}

	public var password:String {
		get {
			return passwordField!.text
		}
		set {
			passwordField!.text = newValue

			if let placeholderValue = passwordPlaceholder {
				placeholderValue.changeVisibility(visible: newValue == "", delay: 0.0)
			}
		}
	}

	public var shouldRememberCredentials: Bool {
		if let rememberSwitchValue = rememberSwitch {
			return rememberSwitchValue.on;
		}

		return true
	}

	public func setAuthType(authType: String) {
		if let placeholderValue = userNamePlaceholder {
			placeholderValue.text = authType
		}

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

	public func setUserName(userName: String) {
		userNameField!.text = userName
	}
    

	//MARK: BaseWidgetView METHODS

	override func becomeFirstResponder() -> Bool {
		return userNameField!.becomeFirstResponder()
	}

	
	//MARK: UITextFieldDelegate METHODS

	func textFieldShouldBeginEditing(textField: UITextField!) -> Bool {
		userNameBackground!.highlighted = (textField == userNameField);
		passwordBackground!.highlighted = (textField == passwordField);

		return true
	}

	func textField(textField: UITextField!, shouldChangeCharactersInRange range: NSRange, replacementString string: String!) -> Bool {

		let targetPlaceholder = (textField == userNameField) ? userNamePlaceholder : passwordPlaceholder

		if let targetPlaceholderValue = targetPlaceholder {
			let newText = textField.text.bridgeToObjectiveC().stringByReplacingCharactersInRange(range, withString:string)

			targetPlaceholderValue.changeVisibility(visible: newText == "")
		}

		return true
	}

	func textFieldShouldReturn(textField: UITextField!) -> Bool {
		if textField == userNameField {
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
