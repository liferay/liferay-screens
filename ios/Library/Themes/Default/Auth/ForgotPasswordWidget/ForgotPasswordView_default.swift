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


public class ForgotPasswordView_default: BaseWidgetView, ForgotPasswordView {

	@IBOutlet internal var userNameIcon: UIImageView?
	@IBOutlet internal var userNameField: UITextField?
	@IBOutlet internal var requestPasswordButton: UIButton?


	//MARK: ForgotPasswordView

	public var userName: String? {
		get {
			return userNameField!.text == "" ? nil : userNameField!.text
		}
		set {
			userNameField!.text = newValue
		}
	}

	public var authMethod: AuthMethodType = AuthMethod.Email.toRaw() {
		didSet {
			setAuthMethodStyles(
					authMethod: AuthMethod.fromRaw(authMethod)!,
					userNameField: userNameField,
					userNameIcon: userNameIcon)
		}
	}

	public var saveCredentials: Bool {
		get {
			return false
		}
		set {}
	}



	//MARK: UITextFieldDelegate

	internal func textFieldDidBeginEditing(textField: UITextField!) {
		userNameField!.highlighted = (textField == userNameField)
	}

}
