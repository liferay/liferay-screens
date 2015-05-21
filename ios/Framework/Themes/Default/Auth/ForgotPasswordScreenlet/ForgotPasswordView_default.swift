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


public class ForgotPasswordView_default: BaseScreenletView, ForgotPasswordViewModel {

	@IBOutlet public var userNameIcon: UIImageView?
	@IBOutlet public var userNameField: UITextField?
	@IBOutlet public var requestPasswordButton: UIButton?


	//MARK: ForgotPasswordViewModel

	public var userName: String? {
		get {
			return nullIfEmpty(userNameField!.text)
		}
		set {
			userNameField!.text = newValue
		}
	}


	//MARK: AuthBasedViewModel

	public var authMethod: AuthMethodType? = AuthMethod.Email.rawValue {
		didSet {
			setAuthMethodStyles(
					view: self,
					authMethod: AuthMethod.create(authMethod),
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


	//MARK: BaseScreenletView

	override public func onCreated() {
		super.onCreated()

		setButtonDefaultStyle(requestPasswordButton)

		BaseScreenlet.setHUDCustomColor(DefaultThemeBasicBlue)
	}

	override public func onSetTranslations() {
		requestPasswordButton?.replaceAttributedTitle(
				LocalizedString("default", "forgot-password-button", self),
				forState: .Normal)

	}

	override public func onStartOperation() {
		requestPasswordButton!.enabled = false
	}

	override public func onFinishOperation() {
		requestPasswordButton!.enabled = true
	}


	//MARK: UITextFieldDelegate

	internal func textFieldDidBeginEditing(textField: UITextField!) {
		userNameField!.highlighted = (textField == userNameField)
	}

}
