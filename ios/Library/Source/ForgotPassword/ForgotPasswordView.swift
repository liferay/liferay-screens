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

	@IBOutlet var userNameLabel: UILabel?
	@IBOutlet var userNameField: UITextField?
	@IBOutlet var requestPasswordButton: UIButton?

	
	public func setAuthType(authType: String) {
		userNameLabel!.text = authType

		switch authType {
		case AuthType.Email.toRaw():
			userNameField!.keyboardType = UIKeyboardType.EmailAddress
		case AuthType.ScreenName.toRaw():
			userNameField!.keyboardType = UIKeyboardType.ASCIICapable
		default:
			break
		}
	}

	//MARK: BaseWidgetView METHODS

	override func becomeFirstResponder() -> Bool {
		return userNameField!.becomeFirstResponder()
	}

	//MARK: UITextFieldDelegate METHODS

	func textFieldShouldReturn(textField: UITextField!) -> Bool {
		textField.resignFirstResponder()

		requestPasswordButton!.sendActionsForControlEvents(UIControlEvents.TouchUpInside)

		return true
	}

}