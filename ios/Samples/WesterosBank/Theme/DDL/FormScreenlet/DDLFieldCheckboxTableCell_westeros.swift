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
import LiferayScreens


public class DDLFieldCheckboxTableCell_westeros: DDLFieldTableCell {

	@IBOutlet public var segmentedView: UISegmentedControl?
	@IBOutlet public var label: UILabel?


	//MARK: Actions

	@IBAction private func valueChanged(sender: AnyObject) {
		field?.currentValue = (segmentedView?.selectedSegmentIndex == 0)

		if field!.lastValidationResult != nil && !field!.lastValidationResult! {
			field!.lastValidationResult = true
			onPostValidation(true)
		}
	}


	//MARK: DDLFieldTableCell

	override public func canBecomeFirstResponder() -> Bool {
		return false
	}

	override public func onChangedField() {
		if let boolField = field as? DDLFieldBoolean {
			segmentedView?.selectedSegmentIndex = (boolField.currentValue as! Bool) ? 0 : 1
			label?.text = boolField.label

			if boolField.lastValidationResult != nil {
				onPostValidation(boolField.lastValidationResult!)
			}
		}
	}

	override public func onPostValidation(valid: Bool) {
		super.onPostValidation(valid)

		label?.textColor = valid ? UIColor.blackColor() : UIColor.redColor()
	}

}
