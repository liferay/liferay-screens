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


public class CommentUpdateInteractor: ServerWriteConnectorInteractor {
	let groupId: Int64
	let className: String
	let classPK: Int64
	let commentId: Int64
	let body: String?

	public var resultComment: Comment?

	init(screenlet: BaseScreenlet,
		groupId: Int64,
		className: String,
		classPK: Int64,
		commentId: Int64,
		body: String?) {

		self.groupId = (groupId != 0) ? groupId : LiferayServerContext.groupId
		self.className = className
		self.classPK = classPK
		self.commentId = commentId
		self.body = body

		super.init(screenlet: screenlet)
	}

	override public func createConnector() -> CommentUpdateLiferayConnector? {
		return LiferayServerContext.connectorFactory.createCommentUpdateConnector(groupId: groupId,
																			className: className,
																			classPK: classPK,
																			commentId: commentId,
																			body: body)
	}

	public override func completedConnector(c: ServerConnector) {
		if let updateCon = (c as? CommentUpdateLiferayConnector),
			comment = updateCon.resultComment {
			self.resultComment = comment
		}
	}
}
