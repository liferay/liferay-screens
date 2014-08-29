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

public class LoginView_default: LoginView {

	@IBOutlet var userNameIcon: UIImageView?
	@IBOutlet var userNameField: UITextField?
	@IBOutlet var passwordField: UITextField?
	@IBOutlet var rememberSwitch: UISwitch?
	@IBOutlet var loginButton: UIButton?
	@IBOutlet var userNameBackground: UIImageView?
	@IBOutlet var passwordBackground: UIImageView?

	// MARK: Static methods

	public class func setStylesForAuthType(authTypeLabel:String, userNameField:UITextField!, userNameIcon:UIImageView!) {

		userNameField!.placeholder = authTypeLabel

		if let authType = AuthType.fromRaw(authTypeLabel) {
			userNameField!.keyboardType = AuthType.KeyboardTypes[authType]!
			userNameIcon?.image = UIImage(named:"default-\(AuthIconTypes[authType]!)-icon")
		}
		else {
			println("ERROR: Wrong auth type description \(authTypeLabel)")
		}
	}

	override public var shouldRememberCredentials: Bool {
		if let rememberSwitchValue = rememberSwitch {
			return rememberSwitchValue.on;
		}

		return super.shouldRememberCredentials
	}

	override public func setAuthType(authTypeLabel: String) {
		LoginView_default.setStylesForAuthType(authTypeLabel, userNameField: userNameField, userNameIcon: userNameIcon)
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

	// MARK: UITextFieldDelegate

	func textFieldShouldBeginEditing(textField: UITextField!) -> Bool {
		if userNameBackground != nil {
			userNameBackground!.highlighted = (textField == userNameField);
		}

		if passwordBackground != nil {
			passwordBackground!.highlighted = (textField == passwordField);
		}

		return true
	}

}

//FIXME Should be static class constant, not supported yet
private let AuthIconTypes = [
	AuthType.Email: "mail",
	AuthType.ScreenName: "user",
	AuthType.UserId: "user"]
