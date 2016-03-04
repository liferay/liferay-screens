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

	@IBOutlet weak var URLTextField: UITextField!
	@IBOutlet weak var titleTextField: UITextField!

	@IBOutlet weak var URLBackground: UIImageView!
	@IBOutlet weak var titleBackground: UIImageView!

	var URL: String? {
		get {
			return URLTextField.text
		}
		set {
			URLTextField.text = newValue
		}
	}

	var title: String? {
		get {
			return titleTextField.text
		}
		set {
			titleTextField.text = newValue
		}
	}

	override func onCreated() {
		setFieldBackground(URLBackground)
		setFieldBackground(titleBackground)
	}

	private func setFieldBackground(image: UIImageView) {
		image.image = NSBundle.imageInBundles(
				name: "default-field", currentClass: self.dynamicType)
		image.highlightedImage = NSBundle.imageInBundles(
				name: "default-field-focused", currentClass: self.dynamicType)
	}


}
