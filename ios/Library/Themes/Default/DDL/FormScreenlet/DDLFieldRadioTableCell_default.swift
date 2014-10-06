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


public class DDLFieldRadioTableCell_default: DDLFieldTableCell {

	@IBOutlet internal var label: UILabel?
	@IBOutlet internal var radioReferenceLabel: UILabel?
	@IBOutlet internal var separator: UIView?

	internal var radioGroup:TNRadioButtonGroup?


	//MARK: DDLFieldTableCell

	override public func canBecomeFirstResponder() -> Bool {
		return false
	}

	override internal func onChangedField() {
		if let stringField = field as? DDLFieldStringWithOptions {
			label!.text = stringField.label

			stringField.currentHeight =
				label!.frame.origin.y + label!.frame.size.height +
				DDLFieldRadioGroupMarginTop + DDLFieldRadioGroupMarginBottom +
				(CGFloat(stringField.options.count) *
					(DDLFieldRadioButtonHeight + DDLFieldRadioButtonMargin))

			separator!.frame.origin.y = stringField.currentHeight

			createRadioButtons(stringField)

			if stringField.lastValidationResult != nil {
				onPostValidation(stringField.lastValidationResult!)
			}
		}
	}

	override internal func onPostValidation(valid: Bool) {
		super.onPostValidation(valid)

		label?.textColor = valid ? UIColor.blackColor() : UIColor.redColor()
		let radioColor = valid ? DefaultThemeBasicBlue : UIColor.redColor()

		for radioButton in radioGroup!.radioButtons as [TNRectangularRadioButton] {
			radioButton.data.labelColor = label?.textColor
			radioButton.data.borderColor = radioColor
			radioButton.data.rectangleColor = radioColor

			radioButton.update()
		}
	}


	//MARK: Private methods

	private func createRadioButtons(field:DDLFieldStringWithOptions) {
		var radioButtons:[AnyObject] = []

		for option in field.options {
			let data = TNRectangularRadioButtonData()
			data.labelFont = radioReferenceLabel?.font
			data.labelText = option.label
			data.identifier = option.value
			data.borderColor = DefaultThemeBasicBlue
			data.rectangleColor = DefaultThemeBasicBlue
			data.rectangleHeight = 8
			data.rectangleWidth = 8
			data.selected = filter(field.currentValue as [DDLFieldStringWithOptions.Option]) {
				$0.name == option.name
			}.count > 0

			radioButtons.append(data)
		}

		if radioGroup != nil {
			NSNotificationCenter.defaultCenter().removeObserver(self,
					name: SELECTED_RADIO_BUTTON_CHANGED,
					object: radioGroup!)
			radioGroup!.removeFromSuperview()
		}

		radioGroup = TNRadioButtonGroup(radioButtonData: radioButtons,
				layout: TNRadioButtonGroupLayoutVertical)
		radioGroup!.identifier = field.name
		radioGroup!.marginBetweenItems = Int(DDLFieldRadioButtonMargin)
		radioGroup!.create()
		radioGroup!.position = CGPointMake(25.0,
				DDLFieldRadioGroupMarginTop + label!.frame.origin.y + label!.frame.size.height)

		addSubview(radioGroup!)

		NSNotificationCenter.defaultCenter().addObserver(self,
				selector: "radioButtonSelected:",
				name: SELECTED_RADIO_BUTTON_CHANGED,
				object: radioGroup)
	}

	private dynamic func radioButtonSelected(notification:NSNotification) {
		if let stringField = field as? DDLFieldStringWithOptions {
			stringField.currentValue = radioGroup!.selectedRadioButton.data.labelText

			if stringField.lastValidationResult != nil && !stringField.lastValidationResult! {
				stringField.lastValidationResult = true
				onPostValidation(true)
			}
		}
	}

}
