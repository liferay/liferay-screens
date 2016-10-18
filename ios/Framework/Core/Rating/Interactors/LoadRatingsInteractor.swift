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

public class LoadRatingsInteractor: ServerReadConnectorInteractor {
	
	let entryId: Int64?
	let className: String?
	let classPK: Int64?
	let ratingsGroupCount: Int32

	var resultRating: RatingEntry?

	private init(screenlet: BaseScreenlet?,
	     	entryId: Int64?,
	     	className: String?,
	     	classPK: Int64?,
	     	ratingsGroupCount: Int32) {
		self.entryId = entryId
		self.className = className
		self.classPK = classPK
		self.ratingsGroupCount = ratingsGroupCount

		super.init(screenlet: screenlet)
	}

	convenience init(screenlet: BaseScreenlet?,
			entryId: Int64,
			ratingsGroupCount: Int32) {
		self.init(screenlet: screenlet,
			entryId: entryId,
			className: nil,
			classPK: nil,
			ratingsGroupCount: ratingsGroupCount)
	}

	convenience init(screenlet: BaseScreenlet?,
			className: String?,
			classPK: Int64?,
			ratingsGroupCount: Int32) {
		self.init(screenlet: screenlet,
		          entryId: nil,
		          className: className,
		          classPK: classPK,
		          ratingsGroupCount: ratingsGroupCount)
	}

	override public func createConnector() -> ServerConnector? {
		if let entryId = self.entryId {
			return LiferayServerContext.connectorFactory.createRatingLoadByEntryIdConnector(
				entryId: entryId, ratingsGroupCount: ratingsGroupCount)
		}
		else if let classPK = self.classPK, className = self.className {
			return LiferayServerContext.connectorFactory.createRatingLoadByClassPKConnector(classPK,
					className: className,
					ratingsGroupCount: ratingsGroupCount)
		}

		return nil
	}
	
	override public func completedConnector(c: ServerConnector) {
		if let loadCon = c as? RatingLoadByEntryIdLiferayConnector {
			self.resultRating = loadCon.resultRating
		}
		else if let loadCon = c as? RatingLoadByClassPKLiferayConnector {
			self.resultRating = loadCon.resultRating
		}
	}

	//MARK: Cache

	override public func readFromCache(c: ServerConnector, result: AnyObject? -> ()) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			result(nil)
			return
		}

		cacheManager.getAny(
				collection: "RatingScreenlet",
				key: cacheKey()) {
			guard let rating = $0 as? RatingEntry else {
				result(nil)
				return
			}

			if let loadCon = c as? RatingLoadByEntryIdLiferayConnector {
				loadCon.resultRating = rating
				result(rating)
			}
			else if let loadCon = c as? RatingLoadByClassPKLiferayConnector {
				loadCon.resultRating = rating
				result(rating)
			}
			else {
				result(nil)
				return
			}
		}
	}

	override public func writeToCache(c: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}

		let result: RatingEntry?

		if let loadCon = c as? RatingLoadByEntryIdLiferayConnector {
			result = loadCon.resultRating
		}
		else if let loadCon = c as? RatingLoadByClassPKLiferayConnector {
			result = loadCon.resultRating
		}
		else {
			result = nil
		}

		guard let resultRating = result else {
			return
		}

		cacheManager.setClean(
			collection: "RatingScreenlet",
			key: cacheKey(),
			value: resultRating,
			attributes: [
				"ratingEntryId": NSNumber(longLong: self.entryId ?? 0),
				"className": self.className ?? "",
				"classPK": NSNumber(longLong: self.classPK ?? 0)
			])
	}

	private func cacheKey() -> String {
		if let entryId = self.entryId {
			return "ratingEntryId-\(entryId)"
		}
		else if let classPK = self.classPK, className = self.className {
			return "className=\(className)-classPK=\(classPK)"
		}

		return ""
	}

}
