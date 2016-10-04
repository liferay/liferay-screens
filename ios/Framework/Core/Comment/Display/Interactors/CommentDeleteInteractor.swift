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


public class CommentDeleteInteractor: ServerWriteConnectorInteractor {

	let commentId: Int64

	override public init(screenlet: BaseScreenlet?) {
		self.commentId = (screenlet as! CommentDisplayScreenlet).commentId

		super.init(screenlet: screenlet)
	}

	public init(commentId: Int64) {
		self.commentId = commentId

		super.init(screenlet: nil)
	}

	override public func createConnector() -> CommentDeleteLiferayConnector? {
		return LiferayServerContext.connectorFactory.createCommentDeleteConnector(
			commentId: commentId)
	}

	//MARK: Cache methods

	override public func writeToCache(c: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}

		let cacheFunction = (cacheStrategy == .CacheFirst || c.lastError != nil)
			? cacheManager.setDirty
			: cacheManager.setClean

		cacheFunction(
			collection: "CommentsScreenlet",
			key: "delete-commentId-\(commentId)",
			value: "",
			attributes: ["commentId": NSNumber(longLong: commentId)],
			onCompletion: nil)
	}


}
