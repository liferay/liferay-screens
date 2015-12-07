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
	private var fileTooLarge = false


	public init(userId: Int64, image: UIImage) {
		self.userId = userId
		self.image = image

		super.init()
	}


	//MARK: ServerOperation

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if self.image == nil {
				return ValidationError("userportrait-screenlet", "undefined-image")
			}
		}

		return error
	}

	override public func doRun(session session: LRSession) {
		if let imageBytes = reduceImage(self.image!, factor: 0.95) {
			self.image = nil
			uploadBytes(imageBytes, withSession: session)
		}
		else {
			fileTooLarge = true
			uploadResult = nil
			lastError = NSError.errorWithCause(.AbortedDueToPreconditions)
		}
	}


	//MARK: Private methods

	private func reduceImage(src: UIImage, factor: Double) -> NSData? {
		if factor < 0.8 {
			return nil
		}

		if let imageBytes = UIImageJPEGRepresentation(src, CGFloat(factor)) {
			return (imageBytes.length < maxSize)
				? imageBytes
				: reduceImage(src, factor: factor - 0.05)
		}

		return nil
	}

	private func uploadBytes(imageBytes: NSData, withSession session: LRSession) {
		let service = LRUserService_v62(session: session)

		do {
			let result = try service.updatePortraitWithUserId(self.userId,
					bytes: imageBytes)

			if let result = result as? [String:AnyObject] {
				uploadResult = result
				lastError = nil
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
			}
		}
		catch let error as NSError {
			lastError = error
		}
	}

}
