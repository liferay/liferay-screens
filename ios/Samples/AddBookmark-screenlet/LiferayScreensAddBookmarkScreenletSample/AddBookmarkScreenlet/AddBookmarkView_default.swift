//
//  AddBookmarkView_default.swift
//  AddBookmarkScreenletSample
//
//  Created by jmWork on 18/05/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

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
