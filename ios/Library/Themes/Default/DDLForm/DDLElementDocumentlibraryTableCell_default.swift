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

public class DDLElementDocumentlibraryTableCell_default: DDLBaseElementTextFieldTableCell_default {

	private let presenterViewController =
		DDLElementDocumentlibraryPresenterViewController_default()

	@IBOutlet var chooseButton: UIButton? {
		didSet {
			chooseButton!.layer.masksToBounds = true
	        chooseButton!.layer.cornerRadius = 4.0
		}
	}

	override func onChangedElement() {
		super.onChangedElement()

		if let docElement = element as? DDLElementDocument {
			textField?.text = docElement.currentStringValue

			presenterViewController.selectedDocumentClosure = selectedDocumentClosure

			setFieldPresenter(docElement)
		}
	}

	@IBAction func chooseButtonAction(sender: AnyObject) {
		textField!.becomeFirstResponder()
	}

	private func setFieldPresenter(element:DDLElementDocument) {
		let presenter = DTViewPresenter(view:presenterViewController.view)

		presenter.presenterView.backgroundColor = UIColor.whiteColor()
		presenter.presenterView.layer.borderColor = UIColor.lightGrayColor().CGColor
		presenter.presenterView.layer.borderWidth = 1.5

		presenterViewController.textField = textField

		textField?.dt_setPresenter(presenter)
	}

	private func selectedDocumentClosure(image:UIImage?, url:NSURL?) {
		element!.currentValue = image ?? url
		textField?.text = element!.currentStringValue

		formView?.customActionHandler(actionName: "upload-document", sender: element! as DDLElementDocument)
	}

}
