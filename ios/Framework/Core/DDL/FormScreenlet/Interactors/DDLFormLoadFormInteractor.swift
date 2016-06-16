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


class DDLFormLoadFormInteractor: ServerReadConnectorInteractor {

	var resultRecord: DDLRecord?
	var resultUserId: Int64?

	override func createConnector() -> ServerConnector {
		let screenlet = self.screenlet as! DDLFormScreenlet

		return LiferayServerContext.connectorFactory.createDDLFormLoadConnector(screenlet.structureId)
	}

	override func completedConnector(op: ServerConnector) {
		if let loadOp = op as? DDLFormLoadLiferayConnector {
			self.resultRecord = loadOp.resultRecord
			self.resultUserId = loadOp.resultUserId
		}
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}

		if let loadOp = op as? DDLFormLoadLiferayConnector,
				record = loadOp.resultRecord,
				userId = loadOp.resultUserId {

			cacheManager.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "structureId-\(loadOp.structureId)",
				value: record,
				attributes: [
					"userId": userId.description])
		}
	}

	override func readFromCache(op: ServerConnector, result: AnyObject? -> ()) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			result(nil)
			return
		}

		if let loadOp = op as? DDLFormLoadLiferayConnector {
			cacheManager.getAnyWithAttributes(
					collection: ScreenletName(DDLFormScreenlet),
					key: "structureId-\(loadOp.structureId)") {
				record, attributes in

				loadOp.resultRecord = record as? DDLRecord
				loadOp.resultUserId = attributes?["userId"]?.description.asLong

				result(loadOp.resultRecord)
			}
		}
		else {
			result(nil)
		}
	}

}
