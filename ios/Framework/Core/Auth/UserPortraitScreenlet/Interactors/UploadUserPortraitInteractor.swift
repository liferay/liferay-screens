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


class UploadUserPortraitInteractor: ServerWriteOperationInteractor {

	var uploadResult: [String:AnyObject]?

	let userId: Int64
	let image: UIImage

	init(screenlet: BaseScreenlet?, userId: Int64, image: UIImage) {
		self.userId = userId
		self.image = image

		super.init(screenlet: screenlet)
	}

	override func createOperation() -> LiferayUploadUserPortraitOperation {
		return LiferayUploadUserPortraitOperation(
				userId: self.userId,
				image: self.image)
	}

	override func completedOperation(op: ServerOperation) {
		self.uploadResult = (op as! LiferayUploadUserPortraitOperation).uploadResult
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerOperation) {
		let cacheFunction = (cacheStrategy == .CacheFirst || op.lastError != nil)
			? SessionContext.currentCacheManager?.setDirty
			: SessionContext.currentCacheManager?.setClean

		cacheFunction?(
			collection: ScreenletName(UserPortraitScreenlet),
			key: "userId-\(userId)",
			value: image,
			attributes: ["userId": NSNumber(longLong: userId)])
	}

	override func callOnSuccess() {
		if cacheStrategy == .CacheFirst {
			// update cache with date sent
			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(UserPortraitScreenlet),
				key: "userId-\(userId)",
				attributes: [
					"userId": NSNumber(longLong: userId)])
		}

		super.callOnSuccess()
	}

}
