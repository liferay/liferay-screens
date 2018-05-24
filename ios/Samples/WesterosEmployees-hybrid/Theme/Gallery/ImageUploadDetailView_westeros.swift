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
import LiferayScreens

open class ImageUploadDetailView_westeros: ImageUploadDetailViewBase {

	open override var image: UIImage? {
		didSet {
			self.imagePreview?.image = image
		}
	}

	// MARK: Outlets

	@IBOutlet weak var uploadButton: UIButton? {
		didSet {
			uploadButton?.layer.borderWidth = 3.0
			uploadButton?.layer.borderColor = DefaultResources.EvenColorBackground.cgColor
		}
	}

	// MARK: Actions

	@IBAction func uploadButtonClicked(_ sender: AnyObject) {
		startUpload()
	}
}
