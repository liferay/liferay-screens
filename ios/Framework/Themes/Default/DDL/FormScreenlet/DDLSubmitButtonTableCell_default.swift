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


public class DDLSubmitButtonTableCell_default: DDLFieldTableCell {

	@IBOutlet public var submitButton: UIButton?

	//MARK: Actions

	@IBAction private func submitButtonAction(sender: AnyObject) {
		formView!.userActionWithSender(sender)
	}


	//MARK: DDLFieldTableCell

	override public func awakeFromNib() {
		super.awakeFromNib()

		setButtonDefaultStyle(submitButton)

		submitButton?.replaceAttributedTitle(
				LocalizedString("default", key: "ddlform-submit-button", obj: self),
				forState: .Normal)
	}

	override public func canBecomeFirstResponder() -> Bool {
		return false
	}

}
