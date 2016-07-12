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
	
	var resultRating: RatingEntry?
	let classPK: Int64
	let className: String
	let score: Double
	let stepCount: Int32
	
	init(screenlet: BaseScreenlet?, classPK: Int64, className: String, score: Double?, stepCount: Int32) {
		self.classPK = classPK
		self.className = className
		self.stepCount = stepCount
		self.score = score ?? -1
		super.init(screenlet: screenlet)
	}
	
	override public func createConnector() -> ServerConnector? {
		return LiferayServerContext.connectorFactory.createRatingUpdateConnector(
			classPK: classPK,
			className: className,
			score: score,
			stepCount: stepCount)
	}
	
	override public func completedConnector(op: ServerConnector) {
		if let updateOp = op as? RatingUpdateLiferayConnector {
			self.resultRating = updateOp.resultRating
		}
	}
	
}
