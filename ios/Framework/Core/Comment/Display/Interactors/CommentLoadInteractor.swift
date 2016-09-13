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

	let groupId: Int64
	let commentId: Int64

	public var resultComment: Comment?

	init(screenlet: BaseScreenlet, groupId: Int64, commentId: Int64) {
		self.groupId = (groupId != 0) ? groupId : LiferayServerContext.groupId
		self.commentId = commentId

		super.init(screenlet: screenlet)
	}

	override public func createConnector() -> CommentLoadLiferayConnector? {
		return LiferayServerContext.connectorFactory.createCommentLoadConnector(
			commentId: commentId)
	}

	override public func completedConnector(c: ServerConnector) {
		if let loadCon = (c as? CommentLoadLiferayConnector),
				comment = loadCon.resultComment {
			self.resultComment = comment
		}
	}

}
