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

public class CommentUpdateLiferayConnector: ServerConnector {

	public let groupId: Int64
	public let className: String
	public let classPK: Int64
	public let commentId: Int64
	public let body: String?

	public var resultComment: Comment?

	public init(groupId: Int64,
	            className: String,
	            classPK: Int64,
	            commentId: Int64,
	            body: String?) {

		self.groupId = groupId
		self.className = className
		self.classPK = classPK
		self.commentId = commentId
		self.body = body

		super.init()
	}

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if groupId <= 0 {
				return ValidationError("comment-display-screenlet", "undefined-groupId")
			}

			if className.isEmpty {
				return ValidationError("comment-display-screenlet", "undefined-className")
			}

			if classPK <= 0 {
				return ValidationError("comment-display-screenlet", "undefined-classPK")
			}

			if commentId <= 0 {
				return ValidationError("comment-display-screenlet", "undefined-commentId")
			}

			if body == nil || body == "" {
				return ValidationError("comment-display-screenlet", "undefined-body")
			}
		}

		return error
	}
}

public class Liferay70CommentUpdateConnector: CommentUpdateLiferayConnector {
	override public func doRun(session session: LRSession) {
		resultComment = nil

		let service = LRCommentmanagerjsonwsService_v70(session: session)

		let formattedBody = body!
			.stringByReplacingOccurrencesOfString("<", withString: "&lt;")
			.stringByReplacingOccurrencesOfString(">", withString: "&gt;")
			.characters.split("\n").map({"<p>\(String($0))</p>"}).joinWithSeparator("")

		do {
			let result = try service.updateCommentWithGroupId(groupId, className: className, classPK: classPK, commentId: commentId, body: formattedBody)

			lastError = nil

			if let result = result as? [String: AnyObject] {
				resultComment = Comment(attributes: result)
			}

		}
		catch let error as NSError {
			lastError = error
		}
		
	}
}