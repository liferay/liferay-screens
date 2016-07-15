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
	
	var resultRating: RatingEntry?
	let entryId: Int64
	let classPK: Int64
	let className: String
	let ratingsGroupCount: Int32
	
	init(screenlet: BaseScreenlet?, entryId: Int64, classPK: Int64, className: String, ratingsGroupCount: Int32) {
		self.entryId = entryId
		self.classPK = classPK
		self.className = className
		self.ratingsGroupCount = ratingsGroupCount
		super.init(screenlet: screenlet)
	}
	
	override public func createConnector() -> ServerConnector? {
		if entryId != 0 {
			return LiferayServerContext.connectorFactory.createRatingLoadByEntryIdConnector(
				entryId: entryId, ratingsGroupCount: ratingsGroupCount)
		}
		
		return LiferayServerContext.connectorFactory.createRatingLoadByClassPKConnector(
			classPK, className: className, ratingsGroupCount: ratingsGroupCount)
	}
	
	override public func completedConnector(op: ServerConnector) {
		if let loadOp = op as? RatingLoadByEntryIdLiferayConnector {
			self.resultRating = loadOp.resultRating
		} else if let loadOp = op as? RatingLoadByClassPKLiferayConnector {
			self.resultRating = loadOp.resultRating
		}
	}

}
