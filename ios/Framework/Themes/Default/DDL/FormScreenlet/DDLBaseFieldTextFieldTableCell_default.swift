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


public class DDLBaseFieldTextboxTableCell_default: DDMFieldTableCell, UITextFieldDelegate {

	@IBOutlet public var textField: UITextField?
	@IBOutlet public var textFieldBackground: UIImageView?
	@IBOutlet public var label: UILabel?


	//MARK: DDMFieldTableCell

	override public func onChangedField() {
		if field!.showLabel {
			textField?.placeholder = ""

			if let labelValue = label {
				labelValue.text = field!.label
				labelValue.hidden = false

				moveSubviewsVertically(0.0)
			}
		}
		else {
			textField?.placeholder = field!.label

			if let labelValue = label {
				labelValue.hidden = true

				moveSubviewsVertically(
					-(DDLFieldTextFieldHeightWithLabel - DDLFieldTextFieldHeightWithoutLabel))

				setCellHeight(DDLFieldTextFieldHeightWithoutLabel)
			}
		}

		textField?.returnKeyType = isLastCell ? .Send : .Next

		if field!.lastValidationResult != nil {
			onPostValidation(field!.lastValidationResult!)
		}

		if field!.currentValue != nil {
			textField?.text = field!.currentValueAsString
		}
	}

	override public func onPostValidation(valid: Bool) {
		super.onPostValidation(valid)

		if valid {
			textFieldBackground?.image = NSBundle.imageInBundles(
					name: "default-field",
					currentClass: self.dynamicType)

			textFieldBackground?.highlightedImage = NSBundle.imageInBundles(
					name: "default-field-focused",
					currentClass: self.dynamicType)
		}
		else {
			let image = NSBundle.imageInBundles(
					name: "default-field-failed",
					currentClass: self.dynamicType)

			textFieldBackground?.image = image
			textFieldBackground?.highlightedImage = image
		}
	}

	override public func canBecomeFirstResponder() -> Bool {
		return textField!.canBecomeFirstResponder()
	}

	override public func becomeFirstResponder() -> Bool {
		return textField!.becomeFirstResponder()
	}


	//MARK: UITextFieldDelegate

	public func textFieldShouldBeginEditing(textField: UITextField) -> Bool {
		textFieldBackground?.highlighted = true

		formView!.firstCellResponder = textField

		return true
	}

	public func textFieldDidEndEditing(textField: UITextField) {
		textFieldBackground?.highlighted = false
	}

	public func textField(textField: UITextField,
			shouldChangeCharactersInRange range: NSRange,
			replacementString string: String) -> Bool {

		if field!.lastValidationResult != nil && !field!.lastValidationResult! {
			field!.lastValidationResult = true
			onPostValidation(true)

			//FIXME!
			// This hack is the only way I found to repaint the text field while it's in
			// edition mode. It doesn't produce flickering nor nasty effects.

			textFieldBackground?.highlighted = false
			textFieldBackground?.highlighted = true
		}

		return true
	}

}
