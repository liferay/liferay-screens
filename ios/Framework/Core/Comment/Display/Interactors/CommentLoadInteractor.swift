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


public class CommentLoadInteractor: ServerReadConnectorInteractor {

	let commentId: Int64

	public var resultComment: Comment?

	init(screenlet: BaseScreenlet, commentId: Int64) {
		self.commentId = commentId

		super.init(screenlet: screenlet)
	}

	override public func createConnector() -> CommentLoadLiferayConnector? {
		return LiferayServerContext.connectorFactory.createCommentLoadConnector(
			commentId: commentId)
	}

	override public func completedConnector(c: ServerConnector) {
		guard let loadCon = c as? CommentLoadLiferayConnector else {
			return
		}
		guard let comment = loadCon.resultComment else {
			return
		}

		self.resultComment = comment
	}

	//MARK: Cache

	override public func readFromCache(c: ServerConnector, result: AnyObject? -> ()) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			result(nil)
			return
		}
		guard let loadCon = c as? CommentLoadLiferayConnector else {
			result(nil)
			return
		}

		cacheManager.getAny(
				collection: "CommentsScreenlet",
				key: commentCacheKey(loadCon.groupId, loadCon.commentId)) {
			guard let comment = $0 as? Comment else {
				result(nil)
				return
			}

			loadCon.resultComment = comment
			result($0)
		}
	}

	override public func writeToCache(c: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}
		guard let loadCon = c as? CommentLoadLiferayConnector else {
			return
		}
		guard let comment = loadCon.resultComment else {
			return
		}

		cacheManager.setClean(
			collection: "CommentsScreenlet",
			key: commentCacheKey(loadCon.groupId, loadCon.commentId),
			value: comment,
			attributes: [
				"groupId": NSNumber(longLong: loadCon.groupId),
				"commentId": NSNumber(longLong: loadCon.commentId)])
	}


	private func commentCacheKey(groupId: Int64, _ commentId: Int64) -> String {
		return "group-\(groupId)-comment-\(commentId)"
	}

}
