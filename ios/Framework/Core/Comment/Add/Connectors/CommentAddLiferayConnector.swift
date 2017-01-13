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

public class CommentAddLiferayConnector: ServerConnector {

	public let className: String
	public let classPK: Int64
	public let body: String

	public var resultComment: Comment?


	//MARK: Initializers

	public init(className: String, classPK: Int64, body: String) {
		self.className = className
		self.classPK = classPK
		self.body = body
		super.init()
	}


	//MARK: ServerConnector

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {

			if className.isEmpty {
				return ValidationError("comment-add-screenlet", "undefined-className")
			}

			if classPK <= 0 {
				return ValidationError("comment-add-screenlet", "undefined-classPK")
			}

			if body.isEmpty {
				return ValidationError("comment-add-screenlet", "empty-body")
			}
		}

		return error
	}

}

public class Liferay70CommentAddConnector: CommentAddLiferayConnector {

	
	//MARK: ServerConnector

	override public func doRun(session session: LRSession) {
		let service = LRScreenscommentService_v70(session: session)

		do {
			let result = try service.addCommentWithClassName(className,
					classPK: classPK,
					body: Comment.plainBodyToHtml(body))

			if let result = result as? [String: AnyObject] {
				resultComment = Comment(attributes: result)
				lastError = nil
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse,
				                                   message: "Could not add comment.")
				resultComment = nil
			}
		}
		catch let error as NSError {
			lastError = error
			resultComment = nil
		}

	}

}
