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


public class LoginView_default: BaseScreenletView, LoginData {

	@IBOutlet internal var userNameIcon: UIImageView?
	@IBOutlet internal var userNameField: UITextField?
	@IBOutlet internal var passwordField: UITextField?
	@IBOutlet internal var rememberSwitch: UISwitch?
	@IBOutlet internal var loginButton: UIButton?
	@IBOutlet internal var userNameBackground: UIImageView?
	@IBOutlet internal var passwordBackground: UIImageView?


	//MARK: AuthBasedData

	public var saveCredentials: Bool {
		get {
			if let rememberSwitchValue = rememberSwitch {
				return rememberSwitchValue.on;
			}

			return false
		}
		set {
			if let rememberSwitchValue = rememberSwitch {
				rememberSwitchValue.on = newValue
			}
		}
	}

	public var authMethod: AuthMethodType? = AuthMethod.Email.rawValue {
		didSet {
			setAuthMethodStyles(
					view: self,
					authMethod: AuthMethod.create(authMethod),
					userNameField: userNameField,
					userNameIcon: userNameIcon)
		}
	}


	//MARK: LoginData

	public var userName: String? {
		get {
			return nullIfEmpty(userNameField!.text)
		}
		set {
			userNameField!.text = newValue
		}
	}

	public var password: String? {
		get {
			return nullIfEmpty(passwordField!.text)
		}
		set {
			passwordField!.text = newValue
		}
	}

	public var companyId: Int64 = 0


	//MARK: BaseScreenletView

	override internal func onCreated() {
		super.onCreated()

		setButtonDefaultStyle(loginButton)

		BaseScreenlet.setHUDCustomColor(DefaultThemeBasicBlue)
	}

	override internal func onSetTranslations() {
		passwordField?.placeholder = LocalizedString("default", "password-placeholder", self)

		loginButton?.replaceAttributedTitle(LocalizedString("default", "sign-in-button", self),
				forState: .Normal)
	}

	override internal func onStartOperation() {
		loginButton!.enabled = false
	}

	override internal func onFinishOperation() {
		loginButton!.enabled = true
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
