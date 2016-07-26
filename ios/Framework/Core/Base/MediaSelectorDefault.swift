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
import Photos

public enum LiferayMediaType {
	case Camera
	case Video
	case Image
}

@objc public class MediaSelectorDefault: NSObject, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

	public typealias SelectedMediaClosure = ((UIImage?, NSURL?) -> Void)?
	public typealias title = String

	let pickerController = UIImagePickerController()
	let viewController: UIViewController?
	let types: [LiferayMediaType : title]
	let selectedMediaClosure: SelectedMediaClosure

	public init(viewController: UIViewController, types: [LiferayMediaType : title], selectedMediaClosure: SelectedMediaClosure) {
		self.viewController = viewController
		self.types = types
		self.selectedMediaClosure = selectedMediaClosure
	}

	public func show() {
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

		let cancelAction = UIAlertAction(title: "Cancel", style: .Cancel, handler: nil)

		alert.addAction(cancelAction)

		viewController?.presentViewController(alert, animated: true) {}
	}


	public func imagePickerController(
		picker: UIImagePickerController,
		didFinishPickingMediaWithInfo info: [String : AnyObject]) {

		let selectedImage = info[UIImagePickerControllerOriginalImage] as? UIImage
		let selectedURL = info[UIImagePickerControllerMediaURL] as? NSURL

		selectedMediaClosure?(selectedImage, selectedURL)

		pickerController.dismissViewControllerAnimated(true) {}
	}

	public func imagePickerControllerDidCancel(picker: UIImagePickerController) {
		pickerController.dismissViewControllerAnimated(true) {}
	}
}

