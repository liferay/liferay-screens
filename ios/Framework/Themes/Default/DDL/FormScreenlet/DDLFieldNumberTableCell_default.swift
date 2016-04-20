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


public class DDLFieldNumberTableCell_default: DDLBaseFieldTextboxTableCell_default {

	@IBOutlet public var stepper: UIStepper?

	@IBAction private func stepperChanged(sender: AnyObject) {
		field!.currentValue = NSDecimalNumber(double: stepper!.value)
		textField?.text = field!.currentValueAsString
	}


	//MARK: DDLBaseFieldTextboxTableCell

	override public func awakeFromNib() {
		super.awakeFromNib()

		stepper?.maximumValue = Double(UInt16.max)
	}

	override public func onChangedField() {
		super.onChangedField()

		if let numberField = field as? DDMFieldNumber {
			if let currentValue = numberField.currentValue as? NSNumber {
				stepper?.value = currentValue.doubleValue
			}
			textField!.keyboardType = (numberField.isDecimal) ? .DecimalPad : .NumberPad
		}
	}

	override public func textField(textField: UITextField,
			shouldChangeCharactersInRange range: NSRange,
			replacementString string: String) -> Bool {

		let newText = (textField.text! as NSString).stringByReplacingCharactersInRange(range,
				withString:string)

		if newText != "" {
			field!.currentValueAsString = newText
		}
		else {
			field!.currentValue = NSDecimalNumber(double: 0)
		}

		stepper?.value = Double(field!.currentValue as! NSNumber)

		return super.textField(textField,
				shouldChangeCharactersInRange: range,
				replacementString: string)
	}


	//MARK: UITextFieldDelegate

	public func textFieldShouldReturn(textField: UITextField) -> Bool {
		return nextCellResponder(textField)
	}

}
