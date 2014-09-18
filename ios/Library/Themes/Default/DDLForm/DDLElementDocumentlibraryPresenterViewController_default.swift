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


public class DDLFieldDocumentlibraryPresenterViewController_default:
		UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

	public var selectedDocumentClosure: ((UIImage?, NSURL?) -> Void)?

	private let imagePicker = UIImagePickerController()

	override public init() {
		super.init(
			nibName: "DDLFieldDocumentlibraryPresenterViewController-default",
			bundle: nil)

		imagePicker.delegate = self
		imagePicker.allowsEditing = false
		imagePicker.modalPresentationStyle = .CurrentContext
	}

	required public init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}


	//MARK: Actions

	@IBAction private func cancelButtonAction(sender: AnyObject) {
		selectedDocumentClosure?(nil, nil)
	}

	@IBAction private func takePhotoAction(sender: AnyObject) {
		cancelButtonAction(sender)

		imagePicker.sourceType = .Camera

		presentViewController(imagePicker, animated: true) {}
	}

	@IBAction private func selectPhotosAction(sender: AnyObject) {
		cancelButtonAction(sender)

		imagePicker.sourceType = .SavedPhotosAlbum

		presentViewController(imagePicker, animated: true) {}
	}

	@IBAction private func selectVideosAction(sender: AnyObject) {
		cancelButtonAction(sender)

		imagePicker.sourceType = .SavedPhotosAlbum
		imagePicker.mediaTypes = [kUTTypeMovie as NSString]

		presentViewController(imagePicker, animated: true) {}
	}


	//MARK: UIImagePickerControllerDelegate

    public func imagePickerController(
			picker: UIImagePickerController!,
			didFinishPickingMediaWithInfo info: [NSObject : AnyObject]!) {

		let selectedImage = info[UIImagePickerControllerOriginalImage] as? UIImage
		let selectedURL = info[UIImagePickerControllerMediaURL] as? NSURL

		selectedDocumentClosure?(selectedImage, selectedURL)

		imagePicker.dismissViewControllerAnimated(true) {}
	}

    public func imagePickerControllerDidCancel(picker: UIImagePickerController!) {
		imagePicker.dismissViewControllerAnimated(true) {}
	}

}
