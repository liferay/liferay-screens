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

public class UpdateRatingInteractor: ServerWriteConnectorInteractor {
	
	let className: String
	let classPK: Int64
	let score: Double
	let ratingsGroupCount: Int32

	var resultRating: RatingEntry?

	init(className: String,
			classPK: Int64,
			score: Double?,
			ratingsGroupCount: Int32) {
		self.className = className
		self.classPK = classPK
		self.ratingsGroupCount = ratingsGroupCount
		self.score = score ?? -1

		super.init(screenlet: nil)
	}

	init(screenlet: BaseScreenlet?,
			className: String,
			classPK: Int64,
			score: Double?,
			ratingsGroupCount: Int32) {
		self.className = className
		self.classPK = classPK
		self.ratingsGroupCount = ratingsGroupCount
		self.score = score ?? -1

		super.init(screenlet: screenlet)
	}
	
	override public func createConnector() -> ServerConnector? {
		return LiferayServerContext.connectorFactory.createRatingUpdateConnector(
			classPK: classPK,
			className: className,
			score: score,
			ratingsGroupCount: ratingsGroupCount)
	}
	
	override public func completedConnector(c: ServerConnector) {
		if let updateCon = c as? RatingUpdateLiferayConnector {
			self.resultRating = updateCon.resultRating
		}
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
			collection: "RatingsScreenlet",
			key: "update-className=\(className)-classPK=\(classPK)",
			value: "",
			attributes: [
				"className": className,
				"classPK": NSNumber(longLong: classPK),
				"ratingsGroupCount": Int(ratingsGroupCount),
				"score": NSNumber(double: score),
			],
			onCompletion: nil)
	}

}
