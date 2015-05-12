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

	private let maxSize = 300 * 1024

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
		let imageBytes = reduceImage(self.image!, factor: 0.95)

		self.image = nil

		uploadBytes(imageBytes, withSession: session)
	}


	//MARK: Private methods

	private func reduceImage(src: UIImage, factor: Double) -> NSData {
		var imageBytes = UIImageJPEGRepresentation(src, CGFloat(factor))

		return (imageBytes.length < maxSize)
				? imageBytes
				: reduceImage(src, factor: factor - 0.1)
	}

	private func uploadBytes(imageBytes: NSData, withSession session: LRSession) {
		let service = LRUserService_v62(session: session)

		lastError = nil

		let result = service.updatePortraitWithUserId(self.userId, bytes: imageBytes, error: &lastError)

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
