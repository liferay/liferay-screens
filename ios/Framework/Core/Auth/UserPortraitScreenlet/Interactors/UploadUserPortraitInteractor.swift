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


class UploadUserPortraitInteractor: ServerOperationInteractor {

	var uploadResult: [String:AnyObject]?

	let userId: Int64
	let image: UIImage

	init(screenlet: BaseScreenlet, userId: Int64, image: UIImage) {
		self.userId = userId
		self.image = image

		super.init(screenlet: screenlet)
	}


	override func createOperation() -> LiferayUploadUserPortraitOperation {
		return LiferayUploadUserPortraitOperation(
				screenlet: self.screenlet,
				userId: self.userId,
				image: self.image)
	}

	override func completedOperation(op: ServerOperation) {
		self.uploadResult = (op as! LiferayUploadUserPortraitOperation).uploadResult
	}

}
