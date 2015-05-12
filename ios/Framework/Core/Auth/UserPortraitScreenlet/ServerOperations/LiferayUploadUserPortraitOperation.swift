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


public class LiferayUploadUserPortraitOperation: ServerOperation {

	private var userId: Int64
	private var image: UIImage?

	var uploadResult: [String:AnyObject]?

	internal override var hudFailureMessage: HUDMessage? {
		return (LocalizedString("userportrait-screenlet", "uploading-error", self), details: nil)
	}


	public init(screenlet: BaseScreenlet, userId: Int64, image: UIImage) {
		self.userId = userId
		self.image = image

		super.init(screenlet: screenlet)
	}


	//MARK: ServerOperation

	override internal func validateData() -> Bool {
		var valid = super.validateData()
		
		valid = valid && (self.image != nil)

		return valid
	}

	override internal func doRun(#session: LRSession) {
		let resizedImage = resizeImage(self.image)

		self.image = nil

		startUpload(session, image: resizedImage)
	}


	private func resizeImage(src: UIImage?) -> UIImage {
		return src!
	}

	private func startUpload(session: LRSession, var image: UIImage) {
		let service = LRUserService_v62(session: session)

		lastError = nil

		var imageBytes = UIImageJPEGRepresentation(image, 0.9)

		let result = service.updatePortraitWithUserId(self.userId, bytes: imageBytes, error: &lastError)

		imageBytes = nil

		if lastError == nil {
			if result is [String:AnyObject] {
				uploadResult = result as? [String:AnyObject]
			}
			else {
				lastError = createError(cause: .InvalidServerResponse)
			}
		}

	}

}
