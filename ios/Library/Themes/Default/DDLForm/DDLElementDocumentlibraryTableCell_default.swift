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

	@IBOutlet private var chooseButton: UIButton? {
		didSet {
			chooseButton!.layer.masksToBounds = true
	        chooseButton!.layer.cornerRadius = 4.0
		}
	}

	@IBOutlet private var progress:MDRadialProgressView?

	@IBAction private func chooseButtonAction(sender: AnyObject) {
		textField!.becomeFirstResponder()
	}

	private let presenterViewController =
		DDLElementDocumentlibraryPresenterViewController_default()

	private let completedColor = [
			DDLElementDocument.UploadStatus.Uploading(0,0) :
					UIColor(red:0, green:184/255.0, blue:224/255.0, alpha:1),
			DDLElementDocument.UploadStatus.Uploaded([:]) :
					UIColor(red:90/255.0, green:212/255.0, blue:39/255.0, alpha:1),
			DDLElementDocument.UploadStatus.Failed(nil) :
					UIColor(red:1, green:0, blue:0, alpha:1)
		]

	private let incompletedColor = [
			DDLElementDocument.UploadStatus.Uploading(0,0) :
					UIColor(red:176/255.0, green:238/255.0, blue:1.0, alpha:0.87)
		]

	private let centerColor = [
			DDLElementDocument.UploadStatus.Uploading(0,0) :
					UIColor(red:240/255.0, green:1, blue:1.0, alpha:0.87),
			DDLElementDocument.UploadStatus.Uploaded([:]) :
					UIColor(red:240/255.0, green:1, blue:1, alpha:1),
			DDLElementDocument.UploadStatus.Failed(nil) :
					UIColor(red:1, green:220/255.0, blue:200/255.0, alpha:1)
		]

	private let labelColor = [
			DDLElementDocument.UploadStatus.Uploading(0,0) :
					UIColor.whiteColor(),
			DDLElementDocument.UploadStatus.Uploaded([:]) :
					UIColor(red:240/255.0, green:1, blue:1, alpha:1),
			DDLElementDocument.UploadStatus.Failed(nil) :
					UIColor(red:1, green:220/255.0, blue:200/255.0, alpha:1)
		]

	override internal func onChangedElement() {
		super.onChangedElement()

		if let docElement = element as? DDLElementDocument {
			textField?.text = docElement.currentDocumentLabel

			presenterViewController.selectedDocumentClosure = selectedDocumentClosure

			setFieldPresenter(docElement)

			setProgress(docElement)
		}
	}

	private func setProgress(element:DDLElementDocument) {
		let theme = progress!.theme

		theme.font = UIFont(descriptor: textField!.font.fontDescriptor(), size: 2.0)

		theme.sliceDividerHidden = true
		theme.thickness = 10.0

		progress!.theme = theme

		changeDocumentUploadStatus(element)
	}

	override internal func changeDocumentUploadStatus(element: DDLElementDocument) {
		let theme = progress!.theme

		theme.completedColor = completedColor[element.uploadStatus]
		theme.incompletedColor = incompletedColor[element.uploadStatus]
		theme.centerColor = centerColor[element.uploadStatus]
		theme.labelColor = labelColor[element.uploadStatus]

		switch element.uploadStatus {
			case .Uploading(let current, let max):
				progress!.progressTotal = max
				progress!.progressCounter = current

				if progress!.alpha == 0 {
					changeProgressVisilibity(show:true)
				}
			case .Failed(_): ()
				changeProgressVisilibity(show:false, delay:2.0)

			default: ()
		}

		dispatch_async(dispatch_get_main_queue()) {
			self.progress!.setNeedsDisplay()
	    }
	}

	private func changeProgressVisilibity(#show:Bool, delay:Double = 0.0) {
		UIView.animateWithDuration(0.3, delay: delay, options: nil, animations: {
			self.progress!.alpha = show ? 1.0 : 0.0
			self.chooseButton!.alpha = show ? 0.0 : 1.0
		}, completion: nil)
	}

	private func setFieldPresenter(element:DDLElementDocument) {
		let presenter = DTViewPresenter(view:presenterViewController.view)

		presenter.presenterView.backgroundColor = UIColor.whiteColor()
		presenter.presenterView.layer.borderColor = UIColor.lightGrayColor().CGColor
		presenter.presenterView.layer.borderWidth = 1.5

		textField?.dt_setPresenter(presenter)
	}

	private func selectedDocumentClosure(image:UIImage?, url:NSURL?) {
		textField!.resignFirstResponder()

		if image != nil || url != nil {
			element!.currentValue = image ?? url
			
			textField?.text = (element as DDLElementDocument).currentDocumentLabel

			formView?.customActionHandler(
				actionName: "upload-document",
				sender: element! as DDLElementDocument)
		}
	}

}
