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


public class CommentAddInteractor: ServerWriteConnectorInteractor {

	let className: String
	let classPK: Int64
	let body: String

	var cacheKeyUsed: String?

	public var resultComment: Comment?

	public init(screenlet: CommentAddScreenlet, body: String) {
		self.className = screenlet.className
		self.classPK = screenlet.classPK
		self.body = body

		super.init(screenlet: screenlet)
	}

	public init(
		className: String,
		classPK: Int64,
		body: String) {

		self.className = className
		self.classPK = classPK
		self.body = body

		super.init(screenlet: nil)
	}

	public convenience init(
		className: String,
		classPK: Int64,
		body: String,
		cacheKeyUsed: String) {

		self.init(
				className: className,
				classPK: classPK,
				body: body)

		self.cacheKeyUsed = cacheKeyUsed
	}

	override public func createConnector() -> CommentAddLiferayConnector? {
		return LiferayServerContext.connectorFactory.createCommentAddConnector(
				className: className,
				classPK: classPK,
				body: body)
	}

	override public func completedConnector(c: ServerConnector) {
		if let addCon = (c as? CommentAddLiferayConnector),
				comment = addCon.resultComment {
			self.resultComment = comment
		}
	}

	//MARK: Cache methods

	override public func writeToCache(c: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}
		guard let addCon = c as? CommentAddLiferayConnector else {
			return
		}

		let cacheFunction = (cacheStrategy == .CacheFirst || c.lastError != nil)
			? cacheManager.setDirty
			: cacheManager.setClean

		let cacheKey: String

		if let cacheKeyUsed = cacheKeyUsed {
			cacheKey = cacheKeyUsed
		}
		else {
			cacheKey = "add-comment-\(NSUUID().UUIDString)"
			cacheKeyUsed = cacheKey
		}

		cacheFunction(
			collection: "CommentsScreenlet",
			key: cacheKey,
			value: "",
			attributes: [
				"className": addCon.className,
				"classPK": NSNumber(longLong: addCon.classPK),
				"body": addCon.body,
			],
			onCompletion: nil)
	}

	public override func callOnSuccess() {
		if cacheStrategy == .CacheFirst {
			SessionContext.currentContext?.cacheManager.setClean(
				collection: "CommentsScreenlet",
				key: cacheKeyUsed!,
				value: "",
				attributes: [
					"className": className,
					"classPK": NSNumber(longLong: classPK),
					"body": body,
				],
				onCompletion: nil)
		}

		super.callOnSuccess()
	}

}
