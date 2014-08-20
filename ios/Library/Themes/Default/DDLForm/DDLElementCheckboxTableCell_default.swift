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

public class DDLElementCheckboxTableCell_default: DDLElementTableCell {

	@IBOutlet var switchView: UISwitch?
	@IBOutlet var label: UILabel?

	@IBAction func switchValueChanged(sender: AnyObject) {
		element?.currentValue = switchView?.on

		if element!.lastValidationResult != nil && !element!.lastValidationResult! {
			element!.lastValidationResult = true
			onValidated(true)
		}
	}

	override func onChangedElement() {
		if let boolElement = element as? DDLElementBoolean {
			switchView?.on = boolElement.predefinedValue as Bool
			label?.text = boolElement.label

			if boolElement.lastValidationResult != nil {
				onValidated(boolElement.lastValidationResult!)
			}
		}
	}

	override func onValidated(valid: Bool) {
		label?.textColor = valid ? UIColor.blackColor() : UIColor.redColor()
	}

	override public func canBecomeFirstResponder() -> Bool {
		return false
	}

}
