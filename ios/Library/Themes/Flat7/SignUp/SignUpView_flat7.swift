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

class SignUpView_flat7: SignUpView_default {

	@IBOutlet var titleLabel: UILabel?
	@IBOutlet var subtitleLabel: UILabel?

	@IBOutlet var firstNamePlaceholder: UILabel?
	@IBOutlet var lastNamePlaceholder: UILabel?
	@IBOutlet var emailAddressPlaceholder: UILabel?
	@IBOutlet var passwordPlaceholder: UILabel?

	override public func onSetTranslations() {
		let bundle = NSBundle(forClass: self.dynamicType)

		titleLabel!.text = NSLocalizedString("flat7-signup-title", tableName: "flat7", bundle: bundle, value: "", comment: "")
		subtitleLabel!.text = NSLocalizedString("flat7-signup-subtitle", tableName: "flat7", bundle: bundle, value: "", comment: "")
		firstNamePlaceholder!.text = NSLocalizedString("flat7-signup-first-name", tableName: "flat7", bundle: bundle, value: "", comment: "")
		lastNamePlaceholder!.text = NSLocalizedString("flat7-signup-last-name", tableName: "flat7", bundle: bundle, value: "", comment: "")
		emailAddressPlaceholder!.text = NSLocalizedString("flat7-signup-email", tableName: "flat7", bundle: bundle, value: "", comment: "")
		passwordPlaceholder!.text = NSLocalizedString("flat7-signup-password", tableName: "flat7", bundle: bundle, value: "", comment: "")

		let str = signUpButton!.attributedTitleForState(UIControlState.Normal)
		let translated = NSLocalizedString("flat7-signup-button", tableName: "flat7", bundle: bundle, value: "", comment: "")
		let newStr = NSMutableAttributedString(attributedString: str)
		newStr.replaceCharactersInRange(NSMakeRange(0, str.length), withString:translated)
		signUpButton!.setAttributedTitle(newStr, forState: UIControlState.Normal)

		firstNameField!.placeholder = "";
		lastNameField!.placeholder = "";
		emailAddressField!.placeholder = "";
		passwordField!.placeholder = "";
	}

	func textField(textField: UITextField!, shouldChangeCharactersInRange range: NSRange, replacementString string: String!) -> Bool {

		let newText = textField.text.bridgeToObjectiveC().stringByReplacingCharactersInRange(range, withString:string)

		var placeholder:UILabel = firstNamePlaceholder!

		switch textField {
		case firstNameField!:
			placeholder = firstNamePlaceholder!
		case lastNameField!:
			placeholder = lastNamePlaceholder!
		case emailAddressField!:
			placeholder = emailAddressPlaceholder!
		case passwordField!:
			placeholder = passwordPlaceholder!
		default:
			break
		}

		showPlaceholder(placeholder, show:newText == "")

		return true
	}

	private func showPlaceholder(placeholder:UILabel, show:Bool) {
		UIView.animateWithDuration(0.4, animations: {
			placeholder.alpha = show ? 1.0 : 0.0
		})
	}

}
