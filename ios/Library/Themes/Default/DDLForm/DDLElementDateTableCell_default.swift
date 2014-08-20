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

public class DDLElementDateTableCell_default: DDLElementTableCell, UITextFieldDelegate {

	@IBOutlet var textField: UITextField?
	@IBOutlet var textFieldBackground: UIImageView?
	@IBOutlet var chooseButton: UIButton? {
		didSet {
			chooseButton?.layer.masksToBounds = true
	        chooseButton?.layer.cornerRadius = 4.0
		}
	}

	override func onChangedElement() {
		if let dateElement = element as? DDLElementDate {
			textField?.placeholder = dateElement.label
			textField?.text = dateElement.currentStringValue

			setFieldPresenter(dateElement)

			if dateElement.lastValidationResult != nil {
				self.onValidated(dateElement.lastValidationResult!)
			}
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

	@IBAction func chooseButtonAction(sender: AnyObject) {
		textField!.becomeFirstResponder()
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

	private func setFieldPresenter(element:DDLElementDate) {

		func onChange(selectedDate:NSDate!) {
			element.currentValue = selectedDate
			self.textField?.text = element.currentStringValue

			if element.lastValidationResult != nil && !element.lastValidationResult! {
				element.lastValidationResult = true

				self.onValidated(true)

				//FIXME!
				// This hack is the only way I found to repaint the text field while it's in edition mode.
				// It doesn't produce flickering nor nasty effects.
				self.textFieldBackground?.highlighted = false
				self.textFieldBackground?.highlighted = true
			}
		}

		let presenter = DTDatePickerPresenter(changeBlock:onChange)

		presenter.datePicker.datePickerMode = .Date
		presenter.datePicker.backgroundColor = UIColor.whiteColor()
		presenter.datePicker.layer.borderColor = UIColor.lightGrayColor().CGColor
		presenter.datePicker.layer.borderWidth = 1.5

		if let currentDate = element.currentValue as? NSDate {
			presenter.datePicker.setDate(currentDate, animated: false)
		}

		textField?.dt_setPresenter(presenter)
	}

}
