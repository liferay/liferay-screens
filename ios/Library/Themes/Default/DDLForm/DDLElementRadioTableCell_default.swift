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


public class DDLElementRadioTableCell_default: DDLElementTableCell {

	@IBOutlet internal var label: UILabel?
	@IBOutlet internal var radioReferenceLabel: UILabel?
	@IBOutlet internal var separator: UIView?

	private var radioGroup:TNRadioButtonGroup?


	override internal func onChangedElement() {
		if let stringElement = element as? DDLElementStringWithOptions {
			label!.text = stringElement.label

			stringElement.currentHeight = 
				label!.frame.origin.y + label!.frame.size.height +
				DDLElementRadioGroupMarginTop + DDLElementRadioGroupMarginBottom +
				(CGFloat(stringElement.options.count) *
					(DDLElementRadioButtonHeight + DDLElementRadioButtonMargin))

			separator!.frame.origin.y = stringElement.currentHeight

			createRadioButtons(stringElement)

			if stringElement.lastValidationResult != nil {
				onValidated(stringElement.lastValidationResult!)
			}
		}
	}

	override internal func onValidated(valid: Bool) {
		label?.textColor = valid ? UIColor.blackColor() : UIColor.redColor()
		let radioColor = valid ? DDLElementBasicBlue : UIColor.redColor()

		for radioButton in radioGroup!.radioButtons as [TNRectangularRadioButton] {
			radioButton.data.labelColor = label?.textColor
			radioButton.data.borderColor = radioColor
			radioButton.data.rectangleColor = radioColor

			radioButton.update()
		}
	}

	override public func canBecomeFirstResponder() -> Bool {
		return false
	}

	private func createRadioButtons(element:DDLElementStringWithOptions) {
		var radioButtons:[AnyObject] = []

		for option in element.options {
			let data = TNRectangularRadioButtonData()
			data.labelFont = radioReferenceLabel?.font
			data.labelText = option.label
			data.identifier = option.value
			data.borderColor = DDLElementBasicBlue
			data.rectangleColor = DDLElementBasicBlue
			data.rectangleHeight = 8
			data.rectangleWidth = 8
			data.selected =
				filter(element.currentValue as [DDLStringOption]) {$0.name == option.name}.count > 0

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
		radioGroup!.identifier = element.name
		radioGroup!.marginBetweenItems = Int(DDLElementRadioButtonMargin)
		radioGroup!.create()
		radioGroup!.position = CGPointMake(25.0,
				DDLElementRadioGroupMarginTop + label!.frame.origin.y + label!.frame.size.height)

		addSubview(radioGroup!)

		NSNotificationCenter.defaultCenter().addObserver(self,
				selector: "radioButtonSelected:",
				name: SELECTED_RADIO_BUTTON_CHANGED,
				object: radioGroup)
	}

	private dynamic func radioButtonSelected(notification:NSNotification) {
		if let stringElement = element as? DDLElementStringWithOptions {
			stringElement.currentValue = radioGroup!.selectedRadioButton.data.labelText

			if stringElement.lastValidationResult != nil && !stringElement.lastValidationResult! {
				stringElement.lastValidationResult = true
				onValidated(true)
			}
		}
	}

}
