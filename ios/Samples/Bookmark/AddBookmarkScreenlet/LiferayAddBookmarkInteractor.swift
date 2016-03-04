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


public class LiferayAddBookmarkInteractor: ServerWriteConnectorInteractor {

	public var resultBookmarkInfo: [NSObject:AnyObject]?

	private let title: String
	private let url: String
	private let folderId: Int64


	init(screenlet: BaseScreenlet?, folderId: Int64, title: String, url: String) {
		self.folderId = folderId
		self.title = title
		self.url = url

		super.init(screenlet: screenlet)
	}

	public override func createConnector() -> ServerConnector? {
		return LiferayAddBookmarkConnector(
			groupId: LiferayServerContext.groupId,
			folderId: self.folderId,
			title: self.title,
			url: self.url)
	}

	override public func completedConnector(op: ServerConnector) {
		self.resultBookmarkInfo = (op as! LiferayAddBookmarkConnector).resultBookmarkInfo
	}

	//MARK: Cache methods

	override public func writeToCache(op: ServerConnector) {
		let cacheFunction = (cacheStrategy == .CacheFirst || op.lastError != nil)
			? SessionContext.currentContext?.cacheManager.setDirty
			: SessionContext.currentContext?.cacheManager.setClean

		cacheFunction?(
			collection: ScreenletName(AddBookmarkScreenlet),
			key: "url-\(self.url)",
			value: self.title,
			attributes: [
					"url": self.url,
					"folderId": NSNumber(longLong: self.folderId)
				])
	}

	override public func callOnSuccess() {
		if cacheStrategy == .CacheFirst {
			// update cache with date sent
			SessionContext.currentContext?.cacheManager.setClean(
				collection: ScreenletName(AddBookmarkScreenlet),
				key: "url-\(self.url)",
				attributes: [
						"url": self.url,
						"folderId": NSNumber(longLong: self.folderId)
					])
		}

		super.callOnSuccess()
	}

}
