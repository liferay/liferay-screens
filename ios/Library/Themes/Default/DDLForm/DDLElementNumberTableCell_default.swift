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


public class DDLElementNumberTableCell_default: DDLBaseElementTextFieldTableCell_default {

	@IBOutlet internal var stepper:UIStepper?

	@IBAction private func stepperChanged(sender: AnyObject) {
		element!.currentValue = NSDecimalNumber(double: stepper!.value)
		textField?.text = element!.currentStringValue
	}


	//MARK: DDLBaseElementTextFieldTableCell

	override public func awakeFromNib() {
		super.awakeFromNib()

		stepper?.maximumValue = Double(UInt16.max)
	}

	override internal func onChangedElement() {
		super.onChangedElement()

		if let numberElement = element as? DDLElementNumber {
			if let currentValue = numberElement.currentValue as? NSNumber {
				stepper?.value = currentValue
			}
			textField!.keyboardType = (numberElement.isDecimal) ? .DecimalPad : .NumberPad
		}
	}

	override public func textField(textField: UITextField!,
			shouldChangeCharactersInRange range: NSRange,
			replacementString string: String!) -> Bool {

		let newText = (textField.text as NSString).stringByReplacingCharactersInRange(range,
				withString:string)

		if newText != "" {
			element!.currentStringValue = newText
		}
		else {
			element!.currentValue = NSDecimalNumber(double: 0)
		}

		stepper?.value = Double(element!.currentValue as NSNumber)

		return super.textField(textField,
				shouldChangeCharactersInRange: range,
				replacementString: string)
	}


	//MARK: UITextFieldDelegate

	public func textFieldShouldReturn(textField: UITextField!) -> Bool {
		return nextCellResponder(textField)
	}

}
