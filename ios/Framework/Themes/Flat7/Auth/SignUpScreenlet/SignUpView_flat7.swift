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
import LiferayScreens


public class SignUpView_flat7: SignUpView_default {

	@IBOutlet private var titleLabel: UILabel?
	@IBOutlet private var subtitleLabel: UILabel?

	@IBOutlet private var firstNamePlaceholder: UILabel?
	@IBOutlet private var lastNamePlaceholder: UILabel?
	@IBOutlet private var emailAddressPlaceholder: UILabel?
	@IBOutlet private var passwordPlaceholder: UILabel?

	//MARK: SignUpView

	override public func onCreated() {
		super.onCreated()

		setFlat7ButtonBackground(signUpButton)
	}

	override public func onSetTranslations() {
		let bundle = NSBundle(forClass: self.dynamicType)

		titleLabel!.text = LocalizedString("flat7", "signup-title", self)
		subtitleLabel!.text = LocalizedString("flat7", "signup-subtitle", self)
		firstNamePlaceholder!.text = LocalizedString("flat7", "signup-first-name", self)
		lastNamePlaceholder!.text = LocalizedString("flat7", "signup-last-name", self)
		emailAddressPlaceholder!.text = LocalizedString("flat7", "signup-email", self)
		passwordPlaceholder!.text = LocalizedString("flat7", "signup-password", self)

		signUpButton!.replaceAttributedTitle(LocalizedString("flat7", "signup-button", self),
				forState: .Normal)

		firstNameField!.placeholder = "";
		lastNameField!.placeholder = "";
		emailAddressField!.placeholder = "";
		passwordField!.placeholder = "";
	}

	override public func createProgressPresenter() -> ProgressPresenter {
		return Flat7ProgressPresenter()
	}


	//MARK: UITextFieldDelegate


	internal func textField(textField: UITextField!,
			shouldChangeCharactersInRange range: NSRange,
			replacementString string: String!)
			-> Bool {

		let newText = (textField.text as NSString).stringByReplacingCharactersInRange(range, withString:string)

		var placeholder = firstNamePlaceholder!

		switch textField {
			case firstNameField!:
				placeholder = firstNamePlaceholder!
			case lastNameField!:
				placeholder = lastNamePlaceholder!
			case emailAddressField!:
				placeholder = emailAddressPlaceholder!
			case passwordField!:
				placeholder = passwordPlaceholder!
			default: ()
		}

		placeholder.changeVisibility(visible: newText != "")

		return true
	}

}
