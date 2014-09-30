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


public class DDLFieldTextareaTableCell_default: DDLFieldTableCell, UITextViewDelegate {

	@IBOutlet internal var textView: UITextView?
	@IBOutlet internal var placeholder: UILabel?
	@IBOutlet internal var textViewBackground: UIImageView?
	@IBOutlet internal var label: UILabel?
	@IBOutlet internal var separator: UIView?

	private var originalTextViewRect:CGRect = CGRectZero
	private var originalBackgroundRect:CGRect = CGRectZero


	//MARK: DDLFieldTableCell

	override public func canBecomeFirstResponder() -> Bool {
		return textView!.canBecomeFirstResponder()
	}

	override public func becomeFirstResponder() -> Bool {
		textView!.becomeFirstResponder()
		return false
	}

	override internal func onChangedField() {
		if let stringField = field as? DDLFieldString {

			if stringField.currentValue != nil {
				textView?.text = stringField.currentValueAsString
			}

			if stringField.showLabel {
				placeholder?.text = ""
				label?.text = stringField.label
				label?.hidden = false

				moveSubviewsVertically(0.0)
			}
			else {
				placeholder?.text = stringField.label
				placeholder?.alpha = (textView?.text == "") ? 1.0 : 0.0
				label?.hidden = true

				moveSubviewsVertically(
					-(DDLFieldTextFieldHeightWithLabel - DDLFieldTextFieldHeightWithoutLabel))
				field?.currentHeight = DDLFieldTextFieldHeightWithoutLabel
			}

			textView?.returnKeyType = isLastCell ? .Send : .Next

			originalTextViewRect = textView!.frame
			originalBackgroundRect = textViewBackground!.frame

			if stringField.lastValidationResult != nil {
				onValidated(stringField.lastValidationResult!)
			}
		}
	}

	override internal func onValidated(valid: Bool) {
		let imgName = valid ? "default-field" : "default-field-failed"

		textViewBackground?.image = UIImage(named: imgName)
	}


	//MARK: UITextViewDelegate

	public func textViewDidBeginEditing(textView: UITextView!) {
		var heightLabelOffset:CGFloat =
				DDLFieldTextFieldHeightWithLabel - DDLFieldTextFieldHeightWithoutLabel
		changeCellHeight(DDLFieldTextareaExpandedCellHeight +
				(field!.showLabel ? heightLabelOffset : 0.0))

		separator!.frame.origin.y +=
				DDLFieldTextareaExpandedBackgroundHeight - originalBackgroundRect.size.height

		textView.frame = CGRectMake(
			originalTextViewRect.origin.x,
			originalTextViewRect.origin.y,
			originalTextViewRect.size.width,
			DDLFieldTextareaExpandedTextViewHeight)
		textViewBackground!.frame.size.height = DDLFieldTextareaExpandedBackgroundHeight

		textViewBackground?.highlighted = true

		formView!.firstCellResponder = textView
	}

	public func textViewDidEndEditing(textView: UITextView!) {
		separator!.frame.origin.y -=
				DDLFieldTextareaExpandedBackgroundHeight - originalBackgroundRect.size.height
		textView.frame = originalTextViewRect
		textViewBackground!.frame = originalBackgroundRect

		var heightLabelOffset:CGFloat =
				DDLFieldTextFieldHeightWithLabel - DDLFieldTextFieldHeightWithoutLabel

		changeCellHeight(
				field!.editorType.registeredHeight -
				(field!.showLabel ? 0.0 : heightLabelOffset))

		textViewBackground?.highlighted = false
	}

	public func textView(textView: UITextView!,
			shouldChangeTextInRange range: NSRange,
			replacementText text: String!) -> Bool {

		var result = false

		if text == "\n" {
			textViewDidEndEditing(textView)
			nextCellResponder(textView)
			result = false
		} else {
			result = true

			let newText = (textView!.text as NSString).stringByReplacingCharactersInRange(range,
					withString:text)

			placeholder!.changeVisibility(visible: newText != "")

			field?.currentValue = newText

			if field!.lastValidationResult != nil && !field!.lastValidationResult! {
				field!.lastValidationResult = true

				onValidated(true)
			}
		}

		return result
	}

}
