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


public class SignUpView_default: BaseScreenletView, SignUpData {

	@IBOutlet internal var emailAddressField: UITextField?
	@IBOutlet internal var passwordField: UITextField?
	@IBOutlet internal var firstNameField: UITextField?
	@IBOutlet internal var lastNameField: UITextField?
	@IBOutlet internal var signUpButton: UIButton?
	@IBOutlet internal var emailAddressBackground: UIImageView?
	@IBOutlet internal var passwordBackground: UIImageView?
	@IBOutlet internal var firstNameBackground: UIImageView?
	@IBOutlet internal var lastNameBackground: UIImageView?

	@IBOutlet internal var scrollView: UIScrollView?


	//MARK: BaseScreenletView

	override internal func onStartOperation() {
		signUpButton!.enabled = false
	}

	override internal func onFinishOperation() {
		signUpButton!.enabled = true
	}


	//MARK: BaseScreenletView

	override internal func onCreated() {
		super.onCreated()

		setButtonDefaultStyle(signUpButton)

		scrollView?.contentSize = scrollView!.frame.size

		BaseScreenlet.setHUDCustomColor(DefaultThemeBasicBlue)
	}

	override func onSetTranslations() {
		firstNameField?.placeholder = LocalizedString("default", "first-name-placeholder", self)
		lastNameField?.placeholder = LocalizedString("default", "last-name-placeholder", self)
		emailAddressField?.placeholder = LocalizedString("default", "auth-method-email", self)
		passwordField?.placeholder = LocalizedString("default", "password-placeholder", self)

		signUpButton?.replaceAttributedTitle(
				LocalizedString("default", "sign-up-button", self),
				forState: .Normal)
	}

	//MARK: SignUpData

	public var emailAddress: String? {
		get {
			return nullIfEmpty(emailAddressField!.text)
		}
		set {
			emailAddressField!.text = newValue
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

	public var firstName: String? {
		get {
			return nullIfEmpty(firstNameField!.text)
		}
		set {
			firstNameField!.text = newValue
		}
	}

	public var lastName: String? {
		get {
			return nullIfEmpty(lastNameField!.text)
		}
		set {
			lastNameField!.text = newValue
		}
	}

	public var companyId: Int64 = 0

	// The following properties are not supported in this theme but
	// may be supported in a child theme

	public var screenName: String? {
		get { return nil }
		set {}
	}

	public var middleName: String? {
		get { return nil }
		set {}
	}

	public var jobTitle: String? {
		get { return nil }
		set {}
	}


	//MARK: UITextFieldDelegate

	internal func textFieldDidBeginEditing(textField: UITextField!) {
		emailAddressBackground?.highlighted = (textField == emailAddressField)
		passwordBackground?.highlighted = (textField == passwordField)
		firstNameBackground?.highlighted = (textField == firstNameField)
		lastNameBackground?.highlighted = (textField == lastNameField)
	}

}
