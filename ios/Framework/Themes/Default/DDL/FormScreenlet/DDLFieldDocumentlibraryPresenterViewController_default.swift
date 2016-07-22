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
import MobileCoreServices


private let xibName = "DDLFieldDocumentlibraryPresenterViewController_default"


public class DDMFieldDocumentlibraryPresenterViewController_default:
		UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

	@IBOutlet public var takeNewButton: UIButton?
	@IBOutlet public var selectPhotoButton: UIButton?
	@IBOutlet public var selectVideoButton: UIButton?
	@IBOutlet public var cancelButton: UIButton?

	public var selectedDocumentClosure: ((UIImage?, NSURL?) -> Void)?

	private let imagePicker = UIImagePickerController()


	public override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: NSBundle?) {
		super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
	}

	public convenience init() {
		func bundleForXib() -> NSBundle? {
			let bundles = NSBundle.allBundles(
					DDMFieldDocumentlibraryPresenterViewController_default.self);

			return bundles.filter {
					$0.pathForResource(xibName, ofType:"nib") != nil
				}
				.first
		}

		self.init(
			nibName: xibName,
			bundle: bundleForXib())

		imagePicker.delegate = self
		imagePicker.allowsEditing = false
		imagePicker.modalPresentationStyle = .CurrentContext

		takeNewButton?.replaceAttributedTitle(
				LocalizedString("default", key: "ddlform-upload-picker-take-new", obj: self),
				forState: .Normal)
		selectPhotoButton?.replaceAttributedTitle(
				LocalizedString("default", key: "ddlform-upload-picker-select-photo", obj: self),
				forState: .Normal)
		selectVideoButton?.replaceAttributedTitle(
				LocalizedString("default", key: "ddlform-upload-picker-select-video", obj: self),
				forState: .Normal)
		cancelButton?.replaceAttributedTitle(
				LocalizedString("default", key: "ddlform-upload-picker-cancel", obj: self),
				forState: .Normal)
	}

	required public init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}


	//MARK: Actions

	@IBAction private func cancelButtonAction(sender: AnyObject) {
		selectedDocumentClosure?(nil, nil)
	}

	@IBAction private func takePhotoAction(sender: AnyObject) {
		imagePicker.sourceType = .Camera

		presentViewController(imagePicker, animated: true) {}
	}

	@IBAction private func selectPhotosAction(sender: AnyObject) {
		imagePicker.sourceType = .SavedPhotosAlbum

		presentViewController(imagePicker, animated: true) {}
	}

	@IBAction private func selectVideosAction(sender: AnyObject) {

		imagePicker.sourceType = .SavedPhotosAlbum
		imagePicker.mediaTypes = [kUTTypeMovie as NSString as String]

		presentViewController(imagePicker, animated: true) {}
	}


	//MARK: UIImagePickerControllerDelegate

    public func imagePickerController(
			picker: UIImagePickerController,
			didFinishPickingMediaWithInfo info: [String : AnyObject]) {

		let selectedImage = info[UIImagePickerControllerOriginalImage] as? UIImage
		let selectedURL = info[UIImagePickerControllerMediaURL] as? NSURL

		selectedDocumentClosure?(selectedImage, selectedURL)

		imagePicker.dismissViewControllerAnimated(true) {}
	}

    public func imagePickerControllerDidCancel(picker: UIImagePickerController) {
		cancelButtonAction(picker)
		imagePicker.dismissViewControllerAnimated(true) {}
	}

}
