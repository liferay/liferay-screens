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


public class SignUpView_default: BaseWidgetView, SignUpView {

	@IBOutlet internal var emailAddressField: UITextField?
	@IBOutlet internal var passwordField: UITextField?
	@IBOutlet internal var firstNameField: UITextField?
	@IBOutlet internal var lastNameField: UITextField?
	@IBOutlet internal var signUpButton: UIButton?
	@IBOutlet internal var emailAddressBackground: UIImageView?
	@IBOutlet internal var passwordBackground: UIImageView?
	@IBOutlet internal var firstNameBackground: UIImageView?
	@IBOutlet internal var lastNameBackground: UIImageView?


	//MARK: BaseWidgetView

	override internal func onStartOperation() {
		signUpButton!.enabled = false
	}

	override internal func onFinishOperation() {
		signUpButton!.enabled = true
	}


	//MARK: SignUpView

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
		emailAddressBackground!.highlighted = (textField == emailAddressField)
		passwordBackground!.highlighted = (textField == passwordField)
		firstNameBackground!.highlighted = (textField == firstNameField)
		lastNameBackground!.highlighted = (textField == lastNameField)
	}

}
