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

public class DDLElementTextTableCell_default: DDLElementTableCell, UITextFieldDelegate {

	@IBOutlet var textField: UITextField?
	@IBOutlet var textFieldBackground: UIImageView?

	private var failedValidation = false

	override func onChangedElement() {
		if let stringElement = element as? DDLElementString {
			textField?.placeholder = stringElement.label

			if stringElement.currentValue != nil {
				textField?.text = stringElement.currentStringValue
			}
			else {
				textField?.text = stringElement.predefinedValue?.description
			}

			textField?.returnKeyType = isLastCell ? .Send : .Next
		}
	}

	override func onValidated(valid: Bool) {
		let imgName = valid ? "default-field" : "default-field-failed"
		let imgNameHighlighted = valid ? "default-field-focused" : "default-field-failed"

		textFieldBackground?.image = UIImage(named: imgNameHighlighted)
		textFieldBackground?.highlightedImage = UIImage(named: imgNameHighlighted)

		failedValidation = !valid
	}

	override public func canBecomeFirstResponder() -> Bool {
		return textField!.canBecomeFirstResponder()
	}

	override public func becomeFirstResponder() -> Bool {
		return textField!.becomeFirstResponder()
	}

	//MARK: UITextFieldDelegate

	public func textFieldShouldBeginEditing(textField: UITextField!) -> Bool {
		tableView?.scrollToRowAtIndexPath(indexPath, atScrollPosition: .Top, animated: true)
		textFieldBackground?.highlighted = true

		return true
	}

	public func textFieldDidEndEditing(textField: UITextField!) {
		textFieldBackground?.highlighted = false
	}

	public func textField(textField: UITextField!, shouldChangeCharactersInRange range: NSRange, replacementString string: String!) -> Bool {

		let newText = (textField.text as NSString).stringByReplacingCharactersInRange(range, withString:string)

		element?.currentValue = newText

		if failedValidation {
			failedValidation = false

			textFieldBackground?.image = UIImage(named: "default-field")

			textFieldBackground?.highlightedImage = UIImage(named: "default-field-focused")

			//FIXME!
			// This hack is the only way I found to repaint the text field while it's in edition mode.
			// It doesn't produce flickering nor nasty effects.

			textFieldBackground?.highlighted = false
			textFieldBackground?.highlighted = true
		}

		return true
	}

	public func textFieldShouldReturn(textField: UITextField!) -> Bool {
		return nextCellResponder(textField)
	}

}
