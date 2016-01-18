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


public class LiferayAddBookmarkInteractor: ServerWriteOperationInteractor {

	public var resultBookmarkInfo: [NSObject:AnyObject]?

	private let title: String
	private let url: String


	init(screenlet: BaseScreenlet?, title: String, url: String) {
		self.title = title
		self.url = url

		super.init(screenlet: screenlet)
	}

	public override func createOperation() -> ServerOperation? {
		let op = LiferayAddBookmarkOperation(
			groupId: LiferayServerContext.groupId,
			title: self.title,
			url: self.url)

		op.folderId = 20622 // this bookmark folder is writable by test user

		return op
	}

	override public func completedOperation(op: ServerOperation) {
		self.resultBookmarkInfo = (op as! LiferayAddBookmarkOperation).resultBookmarkInfo
	}

	//MARK: Cache methods

	override public func writeToCache(op: ServerOperation) {
		let cacheFunction = (cacheStrategy == .CacheFirst || op.lastError != nil)
			? SessionContext.currentCacheManager?.setDirty
			: SessionContext.currentCacheManager?.setClean

		cacheFunction?(
			collection: ScreenletName(AddBookmarkScreenlet),
			key: "url-\(self.url)",
			value: self.title,
			attributes: [
					"url": self.url
				])
	}

	override public func callOnSuccess() {
		if cacheStrategy == .CacheFirst {
			// update cache with date sent
			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(AddBookmarkScreenlet),
				key: "url-\(self.url)",
				attributes: [
						"url": self.url
					])
		}

		super.callOnSuccess()
	}


}
