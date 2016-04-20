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

#if LIFERAY_SCREENS_FRAMEWORK
	import TNRadioButtonGroup
#endif


public class DDLFieldRadioTableCell_default: DDMFieldTableCell {

	@IBOutlet public var label: UILabel?
	@IBOutlet public var radioReferenceLabel: UILabel?
	@IBOutlet public var separator: UIView?

	public var radioGroup: TNRadioButtonGroup?


	public var radioColor : UIColor {
		return DefaultThemeBasicBlue
	}

	public var radioTextColor : UIColor {
		return UIColor.blackColor()
	}

	public var invalidRadioColor : UIColor {
		return UIColor.redColor()
	}

	public var invalidRadioTextColor : UIColor {
		return UIColor.redColor()
	}

	public var radioButtonWidth: Int {
		return 8
	}

	deinit {
		if radioGroup != nil {
			clearObserver()
		}
	}


	//MARK: DDMFieldTableCell

	override public func canBecomeFirstResponder() -> Bool {
		return false
	}

	override public func onChangedField() {
		if let stringField = field as? DDMFieldStringWithOptions {
			label!.text = stringField.label

			let height =
				label!.frame.origin.y + label!.frame.size.height +
				DDLFieldRadioGroupMarginTop + DDLFieldRadioGroupMarginBottom +
				(CGFloat(stringField.options.count) *
					(DDLFieldRadioButtonHeight + DDLFieldRadioButtonMargin))

			formView!.setCellHeight(height, forField:stringField)
			separator?.frame.origin.y = height

			createRadioButtons(stringField)

			if stringField.lastValidationResult != nil {
				onPostValidation(stringField.lastValidationResult!)
			}
		}
	}

	override public func onPostValidation(valid: Bool) {
		super.onPostValidation(valid)

		label?.textColor = valid ? self.radioTextColor : self.invalidRadioTextColor
		let radioColor = valid ? self.radioColor : self.invalidRadioColor

		for radioButton in radioGroup!.radioButtons as! [TNRectangularRadioButton] {
			radioButton.data.labelColor = label?.textColor
			radioButton.data.borderColor = radioColor
			radioButton.data.rectangleColor = radioColor

			radioButton.update()
		}
	}


	public func createRadioButtons(field: DDMFieldStringWithOptions) {
		var radioButtons:[AnyObject] = []

		for option in field.options {
			let data = createRadioButtonData(field, option: option)
			radioButtons.append(data)
		}

		if radioGroup != nil {
			clearObserver()
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
				selector: #selector(DDLFieldRadioTableCell_default.radioButtonSelected(_:)),
				name: SELECTED_RADIO_BUTTON_CHANGED,
				object: radioGroup)
	}

	public func createRadioButtonData(field: DDMFieldStringWithOptions, option: DDMFieldStringWithOptions.Option)
			-> TNRectangularRadioButtonData {

		let data = TNRectangularRadioButtonData()

		data.labelFont = radioReferenceLabel?.font
		data.labelText = option.label
		data.identifier = option.value
		data.borderColor = radioColor
		data.rectangleColor = radioColor
		data.rectangleHeight = radioButtonWidth
		data.rectangleWidth = radioButtonWidth
		data.selected = (field.currentValue as! [DDMFieldStringWithOptions.Option]).filter {
				if $0.name != nil {
					return $0.name == option.name
				}
				else {
					return $0.label == option.label
				}
			}
			.count > 0

		return data
	}

	public dynamic func radioButtonSelected(notification:NSNotification) {
		if let stringField = field as? DDMFieldStringWithOptions {
			stringField.currentValue = radioGroup!.selectedRadioButton!.data.labelText

			if stringField.lastValidationResult != nil && !stringField.lastValidationResult! {
				stringField.lastValidationResult = true
				onPostValidation(true)
			}
		}
	}

	public func clearObserver() {
		if radioGroup != nil {
			NSNotificationCenter.defaultCenter().removeObserver(self,
					name: SELECTED_RADIO_BUTTON_CHANGED,
					object: radioGroup!)
		}
	}

}
