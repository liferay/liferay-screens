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

public class DDLElementTextareaTableCell_default: DDLElementTableCell, UITextViewDelegate {

	@IBOutlet var textView: UITextView?
	@IBOutlet var placeholder: UILabel?
	@IBOutlet var singleLineBackground: UIImageView?
	@IBOutlet var multipleLineBackground: UIImageView?

	private var failedValidation = false

	override func onChangedElement() {
		if let stringElement = element as? DDLElementString {
			placeholder?.text = stringElement.label
			textView?.text = stringElement.predefinedValue?.description

			textView?.returnKeyType = isLastCell ? .Send : .Next
		}
	}

	override public func canBecomeFirstResponder() -> Bool {
		return textView!.canBecomeFirstResponder()
	}

	override public func becomeFirstResponder() -> Bool {
		return textView!.becomeFirstResponder()
	}

	public func textViewDidBeginEditing(textView: UITextView!) {
		changeCellHeight(100)
	}

	public func textViewDidEndEditing(textView: UITextView!) {
		changeCellHeight(element!.type.registeredHeight)
	}

	public func textView(textView: UITextView!, shouldChangeTextInRange range: NSRange, replacementText text: String!) -> Bool {

		var result = false

		if text == "\n" {
			return nextCellResponder(textView)
		} else {
			result = true

			let newText = (textView!.text as NSString).stringByReplacingCharactersInRange(range, withString:text)

			showPlaceholder(placeholder!, show:newText == "")

			element?.currentValue = newText

			/*
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
			*/
		}

		return result
	}

	private func showPlaceholder(placeholder:UILabel, show:Bool) {
		UIView.animateWithDuration(0.4, animations: {
			placeholder.alpha = show ? 1.0 : 0.0
		})
	}

}
