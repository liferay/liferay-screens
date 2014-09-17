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


public class DDLElementDateTableCell_default: DDLBaseElementTextFieldTableCell_default {

	@IBOutlet internal var chooseButton: UIButton? {
		didSet {
			chooseButton?.layer.masksToBounds = true
	        chooseButton?.layer.cornerRadius = DDLElementButtonCornerRadius
		}
	}

	override internal func onChangedElement() {
		super.onChangedElement()

		if let dateElement = element as? DDLElementDate {
			setFieldPresenter(dateElement)

			if dateElement.currentValue != nil {
				textField?.text = dateElement.currentDateLabel
			}
		}
	}

	@IBAction private func chooseButtonAction(sender: AnyObject) {
		textField!.becomeFirstResponder()
	}

	private func setFieldPresenter(element:DDLElementDate) {

		func onChange(selectedDate:NSDate!) {
			element.currentValue = selectedDate
			textField?.text = element.currentDateLabel

			let fullRange = NSMakeRange(0, countElements(textField!.text!))

			textField(textField,
				shouldChangeCharactersInRange: fullRange,
				replacementString: textField!.text!)
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
