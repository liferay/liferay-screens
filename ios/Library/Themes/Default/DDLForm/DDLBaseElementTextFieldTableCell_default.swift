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

public class DDLBaseElementTextFieldTableCell_default: DDLElementTableCell, UITextFieldDelegate {

	@IBOutlet var textField: UITextField?
	@IBOutlet var textFieldBackground: UIImageView?
	@IBOutlet var label: UILabel?

	override func onChangedElement() {
		textField?.placeholder = element!.label
		label?.text = element!.label

		textField?.returnKeyType = isLastCell ? .Send : .Next

		if element!.lastValidationResult != nil {
			self.onValidated(element!.lastValidationResult!)
		}

		if element!.currentValue != nil {
			textField?.text = element!.currentStringValue
		}
		else {
			textField?.text = element!.predefinedValue?.description
		}
	}

	override func onValidated(valid: Bool) {
		let imgName = valid ? "default-field" : "default-field-failed"
		let imgNameHighlighted = valid ? "default-field-focused" : "default-field-failed"

		textFieldBackground?.image = UIImage(named: imgName)
		textFieldBackground?.highlightedImage = UIImage(named: imgNameHighlighted)
	}

	override public func canBecomeFirstResponder() -> Bool {
		return textField!.canBecomeFirstResponder()
	}

	override public func becomeFirstResponder() -> Bool {
		return textField!.becomeFirstResponder()
	}

	//MARK: UITextFieldDelegate

	public func textFieldDidBeginEditing(textField: UITextField!) {
		tableView?.scrollToRowAtIndexPath(indexPath, atScrollPosition: .Top, animated: true)

		textFieldBackground?.highlighted = true

		formView!.firstCellResponder = textField
	}

	public func textFieldDidEndEditing(textField: UITextField!) {
		textFieldBackground?.highlighted = false
	}

	public func textField(textField: UITextField!, shouldChangeCharactersInRange range: NSRange, replacementString string: String!) -> Bool {

		if element!.lastValidationResult != nil && !element!.lastValidationResult! {
			element!.lastValidationResult = true

			onValidated(true)

			//FIXME!
			// This hack is the only way I found to repaint the text field while it's in edition mode.
			// It doesn't produce flickering nor nasty effects.

			textFieldBackground?.highlighted = false
			textFieldBackground?.highlighted = true
		}

		return true
	}

}
