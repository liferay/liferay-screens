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

	@IBOutlet internal var userNameIcon: UIImageView?
	@IBOutlet internal var userNameField: UITextField?
	@IBOutlet internal var passwordField: UITextField?
	@IBOutlet internal var rememberSwitch: UISwitch?
	@IBOutlet internal var loginButton: UIButton?
	@IBOutlet internal var userNameBackground: UIImageView?
	@IBOutlet internal var passwordBackground: UIImageView?

	//MARK: Class methods

	public class func setStylesForAuthType(authType:LoginAuth,
			userNameField:UITextField!, userNameIcon:UIImageView!) {

		userNameField!.placeholder = authType.toRaw()

		userNameField!.keyboardType = authType.keyboardType
		userNameIcon?.image = UIImage(named:"default-\(authType.iconType)-icon")
	}


	//MARK: LoginView

	override public var saveCredentials: Bool {
		get {
			if let rememberSwitchValue = rememberSwitch {
				return rememberSwitchValue.on;
			}

			return super.saveCredentials
		}
		set {
			if let rememberSwitchValue = rememberSwitch {
				rememberSwitchValue.on = newValue
			}
		}
	}

	public var authType: LoginAuthType = LoginAuth.Email.toRaw() {
		didSet {
			LoginView_default.setStylesForAuthType(LoginAuth.fromRaw(authType)!,
					userNameField: userNameField,
					userNameIcon: userNameIcon)
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

	override internal func onCreated() {
		BaseWidget.setHUDCustomColor(DefaultThemeBasicBlue)
	}


	//MARK: UITextFieldDelegate

	internal func textFieldShouldBeginEditing(textField: UITextField!) -> Bool {
		if userNameBackground != nil {
			userNameBackground!.highlighted = (textField == userNameField);
		}

		if passwordBackground != nil {
			passwordBackground!.highlighted = (textField == passwordField);
		}

		return true
	}

}
