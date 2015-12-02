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

	@IBOutlet public weak var userNameIcon: UIImageView!
	@IBOutlet public weak var userNameField: UITextField!
	@IBOutlet public weak var passwordField: UITextField!
	@IBOutlet public weak var loginButton: UIButton!
	@IBOutlet public weak var userNameBackground: UIImageView!
	@IBOutlet public weak var passwordBackground: UIImageView!
	@IBOutlet public weak var authorizeButton: UIButton!

	override public var progressMessages: [String:ProgressMessages] {
		return [
			BaseScreenlet.DefaultAction :
				[.Working : LocalizedString("default", key: "login-loading-message", obj: self),
				.Failure : LocalizedString("default", key: "login-loading-error", obj: self)]]
	}


	//MARK: AuthBasedViewModel

	public var basicAuthMethod: String? = BasicAuthMethod.Email.rawValue {
		didSet {
			setBasicAuthMethodStyles(
					view: self,
					basicAuthMethod: BasicAuthMethod.create(basicAuthMethod),
					userNameField: userNameField,
					userNameIcon: userNameIcon)
		}
	}

	public var authType: String? = AuthType.Basic.rawValue {
		didSet {
			configureAuthType()
		}
	}


	//MARK: LoginViewModel

	public var userName: String? {
		get {
			return nullIfEmpty(userNameField.text)
		}
		set {
			userNameField?.text = newValue
		}
	}

	public var password: String? {
		get {
			return nullIfEmpty(passwordField.text)
		}
		set {
			passwordField?.text = newValue
		}
	}


	//MARK: BaseScreenletView

	override public func onCreated() {
		super.onCreated()

		setButtonDefaultStyle(loginButton)
		setButtonDefaultStyle(authorizeButton)

		configureAuthType()
	}

	override public func onSetTranslations() {
		userNameField?.placeholder = LocalizedString("default",
			key: BasicAuthMethod.create(basicAuthMethod).description, obj: self)

		passwordField?.placeholder = LocalizedString("default", key: "password-placeholder", obj: self)

		loginButton?.replaceAttributedTitle(LocalizedString("default", key: "signin-button", obj: self),
				forState: .Normal)

		authorizeButton?.replaceAttributedTitle(LocalizedString("default", key: "authorize-button", obj: self),
				forState: .Normal)
	}

	override public func onStartInteraction() {
		loginButton?.enabled = false
		authorizeButton?.enabled = false
	}

	override public func onFinishInteraction(result: AnyObject?, error: NSError?) {
		loginButton?.enabled = true
		authorizeButton?.enabled = true
	}

	override public func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}


	//MARK: UITextFieldDelegate

	internal func textFieldShouldBeginEditing(textField: UITextField!) -> Bool {
		userNameBackground?.highlighted = (textField == userNameField);
		passwordBackground?.highlighted = (textField == passwordField);

		return true
	}

	public func configureAuthType() {
		let auth = AuthType(rawValue: authType!) ?? .Basic

		authorizeButton?.hidden = (auth != .OAuth)
		loginButton?.superview?.hidden = (auth != .Basic)
	}

}
