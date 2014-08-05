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

class ForgotPasswordView_default: ForgotPasswordView {

	@IBOutlet var userNameLabel: UILabel?
	@IBOutlet var userNameField: UITextField?
	@IBOutlet var requestPasswordButton: UIButton?

	// MARK: Overriden accessors

	override public func getUserName() -> String {
		return userNameField!.text
	}

	override public func setUserName(userName: String) {
		userNameField!.text = userName
	}

	override public func setAuthType(authType: String) {
		userNameLabel!.text = authType

        switch authType {
		case AuthType.Email.toRaw():
			userNameField!.keyboardType = UIKeyboardType.EmailAddress
		case AuthType.ScreenName.toRaw():
			userNameField!.keyboardType = UIKeyboardType.ASCIICapable
		case AuthType.UserId.toRaw():
			userNameField!.keyboardType = UIKeyboardType.NumberPad
		default:
			break
		}
	}

	// MARK: BaseWidgetView

	override func becomeFirstResponder() -> Bool {
		return userNameField!.becomeFirstResponder()
	}

	override internal func nextResponderForResponder(responder:UIResponder) -> UIResponder {
		return requestPasswordButton!
	}

}