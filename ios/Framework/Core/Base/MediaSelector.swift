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

@objc public enum LiferayMediaType : Int {
	case Camera
	case Video
	case Image
	case ImageEdited
}

@objc public class MediaSelector: NSObject, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

	public typealias SelectedMediaClosure = ((UIImage?, NSURL?) -> Void)?

	let pickerController = UIImagePickerController()
	let viewController: UIViewController?
	let types: [LiferayMediaType : String]
	let selectedMediaClosure: SelectedMediaClosure
	let cancelMessage: String
	var alertTitle: String?
	var selfRetain: MediaSelector?

	public init(
			viewController: UIViewController,
			types: [LiferayMediaType : String],
			cancelMessage: String,
			alertTitle: String? = nil,
			selectedMediaClosure: SelectedMediaClosure)  {

		self.viewController = viewController
		self.types = types
		self.selectedMediaClosure = selectedMediaClosure
		self.cancelMessage = cancelMessage
		self.alertTitle = alertTitle
	}

	public func show() {
		selfRetain = self
		pickerController.delegate = self

		let alert = UIAlertController(title: nil, message: nil, preferredStyle: .ActionSheet)

		if types.keys.contains(.Camera) {
			let action = UIAlertAction(title: types[.Camera], style: .Default) { (action) in
				self.pickerController.sourceType = .Camera
				self.viewController?.presentViewController(self.pickerController, animated: true) {}
			}

			alert.addAction(action)
		}

		if types.keys.contains(.Video) {
			let action = UIAlertAction(title: types[.Video], style: .Default) { (action) in
				self.pickerController.sourceType = .SavedPhotosAlbum
				self.pickerController.mediaTypes = [kUTTypeMovie as NSString as String]
				self.viewController?.presentViewController(self.pickerController, animated: true) {}
			}

			alert.addAction(action)
		}

		if types.keys.contains(.Image) {
			let action = UIAlertAction(title: types[.Image], style: .Default) { (action) in
				self.pickerController.sourceType = .SavedPhotosAlbum
				self.viewController?.presentViewController(self.pickerController, animated: true) {}
			}

			alert.addAction(action)
		}

		if types.keys.contains(.ImageEdited) {
			let action = UIAlertAction(title: types[.ImageEdited], style: .Default) { (action) in
				self.pickerController.sourceType = .SavedPhotosAlbum
				self.pickerController.allowsEditing = true
				self.viewController?.presentViewController(self.pickerController, animated: true) {}
			}

			alert.addAction(action)
		}

		let cancelAction = UIAlertAction(title: cancelMessage, style: .Cancel, handler: nil)

		alert.addAction(cancelAction)

		if let title = alertTitle {
			alert.title = title
		}

		viewController?.presentViewController(alert, animated: true) {}
	}


	public func imagePickerController(
		picker: UIImagePickerController,
		didFinishPickingMediaWithInfo info: [String : AnyObject]) {

		let selectedImage = info[UIImagePickerControllerOriginalImage] as? UIImage
		let selectedURL = info[UIImagePickerControllerMediaURL] as? NSURL

		pickerController.dismissViewControllerAnimated(true) {
			self.selectedMediaClosure?(selectedImage, selectedURL)
			self.selfRetain = nil
		}
	}

	public func imagePickerControllerDidCancel(picker: UIImagePickerController) {
		pickerController.dismissViewControllerAnimated(true) {
			self.selfRetain = nil
		}
	}
}

