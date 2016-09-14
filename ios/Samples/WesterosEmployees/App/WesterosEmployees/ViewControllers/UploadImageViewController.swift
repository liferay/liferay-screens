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

class UploadImageViewController: CardViewController,
		UIImagePickerControllerDelegate, UINavigationControllerDelegate {

	var imagePicker: UIImagePickerController? {
		didSet {
			imagePicker?.allowsEditing = true
			imagePicker?.delegate = self
		}
	}

	//MARK: Outlets

	@IBOutlet weak var takePhotoButton: UIButton? {
		didSet {
			takePhotoButton?.layer.borderWidth = 3.0
			takePhotoButton?.layer.borderColor = DefaultResources.EvenColorBackground.CGColor
		}
	}


	//MARK: View methods

	@IBAction func selectImageButtonClicked() {
		openImagePicker(.PhotoLibrary)
	}

	@IBAction func takePhotoButtonClicked() {
		openImagePicker(.Camera)
	}

	//MARK: Init methods

	convenience init() {
		self.init(nibName: "UploadImageViewController", bundle: nil)
	}


	//MARK: UIViewController

	override func viewDidLoad() {
		imagePicker = UIImagePickerController()
	}


	//MARK: UIImagePickerControllerDelegate

	func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : AnyObject]) {
		if let _ = info[UIImagePickerControllerOriginalImage] as? UIImage {
			imagePicker?.dismissViewControllerAnimated(true, completion: nil)
		}
	}


	//MARK: Private methods

	private func openImagePicker(sourceType: UIImagePickerControllerSourceType) {
		if let picker = imagePicker {
			self.cardView?.changeToState(.Minimized)
			
			picker.sourceType = sourceType
			presentViewController(picker, animated: true, completion: nil)
		}
	}
}