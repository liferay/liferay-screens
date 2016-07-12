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
	
	override public func createConnector() -> ServerConnector? {
		let screenlet = self.screenlet as! RatingScreenlet
		
		if screenlet.entryId != 0 {
			return LiferayServerContext.connectorFactory.createRatingLoadByEntryIdConnector(
				entryId: screenlet.entryId, stepCount: screenlet.stepCount)
		}
		
		return LiferayServerContext.connectorFactory.createRatingLoadByClassPKConnector(
			screenlet.classPK, className: screenlet.className, stepCount: screenlet.stepCount)
	}
	
	override public func completedConnector(op: ServerConnector) {
		if let loadOp = op as? RatingLoadByEntryIdLiferayConnector {
			self.resultRating = loadOp.resultRating
		} else if let loadOp = op as? RatingLoadByClassPKLiferayConnector {
			self.resultRating = loadOp.resultRating
		}
	}

}
