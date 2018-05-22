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

class UploadImageViewController: CardViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

	var imagePicker: UIImagePickerController? {
		didSet {
			imagePicker?.allowsEditing = true
			imagePicker?.delegate = self
		}
	}

	///Called when an user has selected an image from a picker
	var onImageSelected: ((UIImage) -> Void)?

	// MARK: Outlets

	@IBOutlet weak var takePhotoButton: UIButton? {
		didSet {
			takePhotoButton?.layer.borderWidth = 3.0
			takePhotoButton?.layer.borderColor = DefaultResources.EvenColorBackground.cgColor
		}
	}

	// MARK: Actions

	@IBAction func selectImageButtonClicked() {
		openImagePicker(.photoLibrary)
	}

	@IBAction func takePhotoButtonClicked() {
		openImagePicker(.camera)
	}

	// MARK: Initializers

	convenience init() {
		self.init(nibName: "UploadImageViewController", bundle: nil)
	}

	// MARK: UIViewController

	override func viewDidLoad() {
		imagePicker = UIImagePickerController()
	}

	// MARK: UIImagePickerControllerDelegate

	func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String: Any]) {
		if let image = info[UIImagePickerControllerEditedImage] as? UIImage {
			onImageSelected?(image)
		} else {
			self.cardView?.changeToState(.minimized)
		}
		imagePicker?.dismiss(animated: true, completion: nil)
	}

	func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
		self.cardView?.changeToState(.minimized)
		imagePicker?.dismiss(animated: true, completion: nil)
	}

	// MARK: Private methods

	fileprivate func openImagePicker(_ sourceType: UIImagePickerControllerSourceType) {
		if let picker = imagePicker {
			picker.sourceType = sourceType
			present(picker, animated: true, completion: nil)
		}
	}
}
