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

	@IBOutlet var currentTextLabel: UILabel?
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
			textPlaceholder?.text = stringElement.label

			if stringElement.currentValue != nil {
				currentTextLabel?.text = stringElement.currentStringValue
			}

			checkPlaceholderVisibility()
		}
	}

	override func onValidated(valid: Bool) {
		let imgName = valid ? "default-field" : "default-field-failed"

		textFieldBackground?.image = UIImage(named: imgName)

		failedValidation = !valid
	}

	override public func canBecomeFirstResponder() -> Bool {
		return true
	}

	override public func becomeFirstResponder() -> Bool {
		chooseButtonAction(self)
		return true
	}

	@IBAction func chooseButtonAction(sender: AnyObject) {
	}


	private func checkPlaceholderVisibility() {
		textPlaceholder?.hidden = (currentTextLabel?.text != "")
	}

}
