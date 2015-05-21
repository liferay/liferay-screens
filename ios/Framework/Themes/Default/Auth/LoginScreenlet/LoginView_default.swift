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


public class LoginView_default: BaseScreenletView, LoginViewModel {

	@IBOutlet public var userNameIcon: UIImageView?
	@IBOutlet public var userNameField: UITextField?
	@IBOutlet public var passwordField: UITextField?
	@IBOutlet public var rememberSwitch: UISwitch?
	@IBOutlet public var loginButton: UIButton?
	@IBOutlet public var userNameBackground: UIImageView?
	@IBOutlet public var passwordBackground: UIImageView?


	//MARK: AuthBasedViewModel

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


	//MARK: LoginViewModel

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


	//MARK: BaseScreenletView

	override public func onCreated() {
		super.onCreated()

		setButtonDefaultStyle(loginButton)

		BaseScreenlet.setHUDCustomColor(DefaultThemeBasicBlue)
	}

	override public func onSetTranslations() {
		passwordField?.placeholder = LocalizedString("default", "password-placeholder", self)

		loginButton?.replaceAttributedTitle(LocalizedString("default", "sign-in-button", self),
				forState: .Normal)
	}

	override public func onStartOperation() {
		loginButton!.enabled = false
	}

	override public func onFinishOperation() {
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
