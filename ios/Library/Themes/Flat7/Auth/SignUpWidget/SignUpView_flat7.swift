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


public class SignUpView_flat7: SignUpView_default {

	@IBOutlet private var titleLabel: UILabel?
	@IBOutlet private var subtitleLabel: UILabel?

	@IBOutlet private var firstNamePlaceholder: UILabel?
	@IBOutlet private var lastNamePlaceholder: UILabel?
	@IBOutlet private var emailAddressPlaceholder: UILabel?
	@IBOutlet private var passwordPlaceholder: UILabel?


	//MARK: SignUpView

	override internal func onCreated() {
		super.onCreated()

		BaseWidget.setHUDCustomColor(Flat7ThemeBasicGreen)
	}

	override internal func onSetTranslations() {
		let bundle = NSBundle(forClass: self.dynamicType)

		titleLabel!.text = NSLocalizedString("flat7-signup-title",
				tableName: "flat7",
				bundle: bundle,
				value: "",
				comment: "")
		subtitleLabel!.text = NSLocalizedString("flat7-signup-subtitle",
				tableName: "flat7",
				bundle: bundle,
				value: "",
				comment: "")
		firstNamePlaceholder!.text = NSLocalizedString("flat7-signup-first-name",
				tableName: "flat7",
				bundle: bundle,
				value: "",
				comment: "")
		lastNamePlaceholder!.text = NSLocalizedString("flat7-signup-last-name",
				tableName: "flat7",
				bundle: bundle,
				value: "",
				comment: "")
		emailAddressPlaceholder!.text = NSLocalizedString("flat7-signup-email",
				tableName: "flat7",
				bundle: bundle,
				value: "",
				comment: "")
		passwordPlaceholder!.text = NSLocalizedString("flat7-signup-password",
				tableName: "flat7",
				bundle: bundle,
				value: "",
				comment: "")

		let str = signUpButton!.attributedTitleForState(UIControlState.Normal)
		let translated = NSLocalizedString("flat7-signup-button",
				tableName: "flat7",
				bundle: bundle,
				value: "",
				comment: "")
		let newStr = NSMutableAttributedString(attributedString: str!)
		newStr.replaceCharactersInRange(NSMakeRange(0, str!.length), withString:translated)
		signUpButton!.setAttributedTitle(newStr, forState: UIControlState.Normal)

		firstNameField!.placeholder = "";
		lastNameField!.placeholder = "";
		emailAddressField!.placeholder = "";
		passwordField!.placeholder = "";
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
