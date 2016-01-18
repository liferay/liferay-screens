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

#if LIFERAY_SCREENS_FRAMEWORK
	import MDRadialProgress
	import DTPickerPresenter
#endif


public class DDLFieldDocumentlibraryTableCell_default: DDLBaseFieldTextboxTableCell_default {

	//MARK: Outlets

	@IBOutlet public var chooseButton: UIButton? {
		didSet {
			setButtonDefaultStyle(chooseButton)
		}
	}

	@IBOutlet public var progress: MDRadialProgressView?


	//MARK: Constants

	private let presenterViewController =
		DDLFieldDocumentlibraryPresenterViewController_default()

	private let completedColor = [
			DDLFieldDocument.UploadStatus.Uploading(0,0) :
					DefaultThemeBasicBlue,
			DDLFieldDocument.UploadStatus.Uploaded([:]) :
					UIColor(red:90/255.0, green:212/255.0, blue:39/255.0, alpha:1),
			DDLFieldDocument.UploadStatus.Failed(nil) :
					UIColor(red:1, green:0, blue:0, alpha:1)
		]

	private let incompletedColor = [
			DDLFieldDocument.UploadStatus.Uploading(0,0) :
					UIColor(red:176/255.0, green:238/255.0, blue:1.0, alpha:0.87)
		]

	private let centerColor = [
			DDLFieldDocument.UploadStatus.Uploading(0,0) :
					UIColor(red:240/255.0, green:1, blue:1.0, alpha:0.87),
			DDLFieldDocument.UploadStatus.Uploaded([:]) :
					UIColor(red:240/255.0, green:1, blue:1, alpha:1),
			DDLFieldDocument.UploadStatus.Failed(nil) :
					UIColor(red:1, green:220/255.0, blue:200/255.0, alpha:1)
		]

	private let labelColor = [
			DDLFieldDocument.UploadStatus.Uploading(0,0) :
					DefaultThemeBasicBlue,
			DDLFieldDocument.UploadStatus.Uploaded([:]) :
					UIColor(red:240/255.0, green:1, blue:1, alpha:1),
			DDLFieldDocument.UploadStatus.Failed(nil) :
					UIColor(red:1, green:220/255.0, blue:200/255.0, alpha:1)
		]


	//MARK: Actions

	@IBAction private func chooseButtonAction(sender: AnyObject) {
		textField!.becomeFirstResponder()
	}


	//MARK: DDLBaseFieldTextboxTableCell

	override public func onChangedField() {
		super.onChangedField()

		if let docField = field as? DDLFieldDocument {
			textField?.text = docField.currentValueAsLabel

			presenterViewController.selectedDocumentClosure = selectedDocumentClosure

			setFieldPresenter(docField)
			setProgress(docField)

			if field!.lastValidationResult != nil {
				onPostValidation(field!.lastValidationResult!)
			}
		}
	}

	override internal func changeDocumentUploadStatus(field: DDLFieldDocument) {
		let theme = progress!.theme

		theme.completedColor = completedColor[field.uploadStatus]
		theme.incompletedColor = incompletedColor[field.uploadStatus]
		theme.centerColor = centerColor[field.uploadStatus]
		theme.labelColor = labelColor[field.uploadStatus]

		switch field.uploadStatus {
			case .Uploading(let current, let max):
				progress!.progressTotal = UInt(max)
				progress!.progressCounter = UInt(current)

				if progress!.alpha == 0 {
					changeProgressVisilibity(show:true)
				}
			case .Failed(_):
				changeProgressVisilibity(show:false, delay:2.0)

			case .Uploaded(_):
				if field.lastValidationResult != nil {
					field.validate()
				}

			default: ()
		}

		dispatch_async(dispatch_get_main_queue()) {
			self.progress!.setNeedsDisplay()
	    }
	}


	//MARK: Private methods

	private func setProgress(field:DDLFieldDocument) {
		let theme = progress!.theme

		theme.font = UIFont(descriptor: textField!.font!.fontDescriptor(), size: 30.0)

		theme.sliceDividerHidden = true
		theme.thickness = 10.0

		progress!.theme = theme

		changeDocumentUploadStatus(field)
	}

	private func changeProgressVisilibity(show show:Bool, delay:Double = 0.0) {
		UIView.animateWithDuration(0.3, delay: delay, options: [], animations: {
			self.progress!.alpha = show ? 1.0 : 0.0
			self.chooseButton!.alpha = show ? 0.0 : 1.0
		}, completion: nil)
	}

	private func setFieldPresenter(field:DDLFieldDocument) {
		let presenter = DTViewPresenter(view:presenterViewController.view)

		presenter.presenterView.backgroundColor = UIColor.whiteColor()
		presenter.presenterView.layer.borderColor = UIColor.lightGrayColor().CGColor
		presenter.presenterView.layer.borderWidth = 1.5

		textField?.dt_setPresenter(presenter)
	}

	private func selectedDocumentClosure(image:UIImage?, url:NSURL?) {
		textField!.resignFirstResponder()

		if image != nil || url != nil {
			field!.currentValue = image ?? url
			
			textField?.text = field?.currentValueAsLabel

			formView?.userAction(
				name: DDLFormScreenlet.UploadDocumentAction,
				sender: field! as! DDLFieldDocument)
		}
	}

}
