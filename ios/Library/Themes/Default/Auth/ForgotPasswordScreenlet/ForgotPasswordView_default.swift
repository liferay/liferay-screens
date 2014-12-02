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


public class ForgotPasswordView_default: BaseScreenletView, ForgotPasswordData {

	@IBOutlet internal var userNameIcon: UIImageView?
	@IBOutlet internal var userNameField: UITextField?
	@IBOutlet internal var requestPasswordButton: UIButton?


	//MARK: ForgotPasswordData

	public var userName: String? {
		get {
			return nullIfEmpty(userNameField!.text)
		}
		set {
			userNameField!.text = newValue
		}
	}

	public var companyId: Int64 = 0


	//MARK: AuthBasedData

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

	override internal func onCreated() {
		super.onCreated()

		setButtonDefaultStyle(requestPasswordButton)

		BaseScreenlet.setHUDCustomColor(DefaultThemeBasicBlue)
	}

	override func onSetTranslations() {
		requestPasswordButton?.replaceAttributedTitle(
				LocalizedString("default", "forgot-password-button", self),
				forState: .Normal)

	}

	override internal func onStartOperation() {
		requestPasswordButton!.enabled = false
	}

	override internal func onFinishOperation() {
		requestPasswordButton!.enabled = true
	}


	//MARK: UITextFieldDelegate

	internal func textFieldDidBeginEditing(textField: UITextField!) {
		userNameField!.highlighted = (textField == userNameField)
	}

}
