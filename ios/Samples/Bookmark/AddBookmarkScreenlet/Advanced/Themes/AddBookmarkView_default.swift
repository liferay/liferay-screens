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

class AddBookmarkView_default: BaseScreenletView, AddBookmarkViewModel {


	//MARK: Outlets

	@IBOutlet weak var URLTextField: UITextField?
	@IBOutlet weak var titleTextField: UITextField?
	@IBOutlet weak var addBookmarkButton: UIButton? {
		didSet {
			addBookmarkButton?.restorationIdentifier = AddBookmarkScreenlet.AddBookmarkAction
		}
	}
	@IBOutlet weak var getTitleButton: UIButton? {
		didSet {
			getTitleButton?.restorationIdentifier = AddBookmarkScreenlet.GetTitleAction
		}
	}


	//MARK: AddBookmarkViewModel

	var URL: String? {
		return URLTextField?.text
	}

	var title: String? {
		get {
			return titleTextField?.text
		}
		set {
			self.titleTextField?.text = newValue
		}
	}


	//MARK: UITextFieldDelegate

	func textFieldDidEndEditing(textField: UITextField) {
		if textField == URLTextField {
			userAction(name: AddBookmarkScreenlet.GetTitleAction)
		}
	}


	//MARK: View actions

	@IBAction func urlTextFieldDidChange(textField: UITextField) {
		getTitleButton?.enabled = !(textField.text?.isEmpty ?? true)
	}

	
}
