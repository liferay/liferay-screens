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

public class DDLElementSelectTableCell_default: DDLElementTableCell {

	@IBOutlet var textField: UITextField?
	@IBOutlet var textPlaceholder: UILabel?
	@IBOutlet var textFieldBackground: UIImageView?
	@IBOutlet var chooseButton: UIButton? {
		didSet {
			chooseButton?.layer.masksToBounds = true
	        chooseButton?.layer.cornerRadius = 4.0
		}
	}

	private var failedValidation = false

	override func onChangedElement() {
		if let stringElement = element as? DDLElementString {
			textField?.placeholder = stringElement.label

			if stringElement.currentValue != nil {
				textField?.text = stringElement.currentStringValue
			}

			setFieldPresenter(stringElement)
		}
	}

	override func onValidated(valid: Bool) {
		let imgName = valid ? "default-field" : "default-field-failed"

		textFieldBackground?.image = UIImage(named: imgName)

		failedValidation = !valid
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

	private func setFieldPresenter(element:DDLElementString) {

		func dataSource() -> DTPickerDataSource {
			var rows:[String] = [""]

			for option in element.options {
				rows.append(option.label)
			}

			return DTPickerDataSource.datasourceWithItems([rows])
		}

		func currentValueIndex() -> Int? {
			for (index,option) in enumerate(element.options) {
				if option.label == element.currentStringValue! {
					return index
				}
			}

			return nil
		}

		let onChangeClosure = {
			(selectedComponents:[AnyObject]!, selectedIndexPath:NSIndexPath!) -> () in

			self.textField?.text = selectedComponents.first?.description
			element.currentValue = selectedComponents.first?.description
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
