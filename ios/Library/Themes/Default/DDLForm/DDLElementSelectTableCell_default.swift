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

public class DDLElementSelectTableCell_default: DDLElementTableCell, UITextFieldDelegate {

	@IBOutlet var textField: UITextField?
	@IBOutlet var textFieldBackground: UIImageView?
	@IBOutlet var chooseButton: UIButton? {
		didSet {
			chooseButton?.layer.masksToBounds = true
	        chooseButton?.layer.cornerRadius = 4.0
		}
	}

	override func onChangedElement() {
		if let stringElement = element as? DDLElementStringWithOptions {
			textField?.placeholder = stringElement.label
			textField?.text = stringElement.currentOptionLabel

			setFieldPresenter(stringElement)

			if stringElement.lastValidationResult != nil {
				self.onValidated(stringElement.lastValidationResult!)
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

	private func setFieldPresenter(element:DDLElementStringWithOptions) {

		func dataSource() -> DTPickerDataSource {
			var rows:[String] = [""]

			for option in element.options {
				rows.append(option.label)
			}

			return DTPickerDataSource.datasourceWithItems([rows])
		}

		func currentValueIndex() -> Int? {
			for (index,option) in enumerate(element.options) {
				if option.label == element.currentOptionLabel {
					return index
				}
			}

			return nil
		}

		let onChangeClosure = {
			(selectedComponents:[AnyObject]!, selectedIndexPath:NSIndexPath!) -> () in

			self.textField?.text = selectedComponents.first?.description
			element.currentValue = selectedComponents.first?.description

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

		let optionsPresenter = DTPickerViewPresenter(
				datasource: dataSource(),
				changeBlock:onChangeClosure)

		optionsPresenter.pickerView.backgroundColor = UIColor.whiteColor()
		optionsPresenter.pickerView.layer.borderColor = UIColor.lightGrayColor().CGColor
		optionsPresenter.pickerView.layer.borderWidth = 1.5

		if let currentIndex = currentValueIndex() {
			optionsPresenter.pickerView.selectRow(currentIndex + 1, inComponent: 0, animated: false)
		}

		textField?.dt_setPresenter(optionsPresenter)
	}

}
