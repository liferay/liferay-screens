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

class SignUpView: BaseWidgetView, UITextFieldDelegate {

	@IBOutlet var emailAddressField: UITextField?
	@IBOutlet var passwordField: UITextField?
	@IBOutlet var firstNameField: UITextField?
	@IBOutlet var lastNameField: UITextField?
	@IBOutlet var signUpButton: UIButton?
	@IBOutlet var emailAddressBackground: UIImageView?
	@IBOutlet var passwordBackground: UIImageView?
	@IBOutlet var firstNameBackground: UIImageView?
	@IBOutlet var lastNameBackground: UIImageView?

	@IBOutlet var screenNameField: UITextField?
	@IBOutlet var middleNameField: UITextField?
	@IBOutlet var jobTitleField: UITextField?


	// BaseWidgetView METHODS

	override func becomeFirstResponder() -> Bool {
		return firstNameField!.becomeFirstResponder()
	}

	// UITextFieldDelegate METHODS

	func textFieldDidBeginEditing(textField: UITextField!) {
		emailAddressBackground!.highlighted = (textField == emailAddressField)
		passwordBackground!.highlighted = (textField == passwordField)
		firstNameBackground!.highlighted = (textField == firstNameField)
		lastNameBackground!.highlighted = (textField == lastNameField)
	}

	func textFieldShouldReturn(textField: UITextField!) -> Bool {
		textField.resignFirstResponder()

		switch textField {
		case firstNameField!:
			lastNameField!.becomeFirstResponder()
		case lastNameField!:
			emailAddressField!.becomeFirstResponder()
		case emailAddressField!:
			passwordField!.becomeFirstResponder()
		case passwordField!:
			signUpButton!.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
		default:
			return false
		}

		return true
	}

}
