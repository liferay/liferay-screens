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


class DDLFormLoadFormInteractor: ServerReadOperationInteractor {

	var resultRecord: DDLRecord?
	var resultUserId: Int64?

	override func createOperation() -> ServerOperation {
		let screenlet = self.screenlet as! DDLFormScreenlet

		return LiferayServerContext.operationFactory.createDDLFormLoadOperation(screenlet.structureId) as! ServerOperation
	}

	override func completedOperation(op: ServerOperation) {
		if let loadOp = op as? LiferayDDLFormLoadOperation {
			self.resultRecord = loadOp.resultRecord
			self.resultUserId = loadOp.resultUserId?.longLongValue
		}
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerOperation) {
		if let loadOp = op as? LiferayDDLFormLoadOperation,
				record = loadOp.resultRecord,
				userId = loadOp.resultUserId {

			SessionContext.currentContext?.cacheManager.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "structureId-\(loadOp.structureId)",
				value: record,
				attributes: [
					"userId": userId])
		}
	}

	override func readFromCache(op: ServerOperation, result: AnyObject? -> Void) {
		if let loadOp = op as? LiferayDDLFormLoadOperation {
			SessionContext.currentContext!.cacheManager.getAnyWithAttributes(
					collection: ScreenletName(DDLFormScreenlet),
					key: "structureId-\(loadOp.structureId)") {
				record, attributes in

				loadOp.resultRecord = record as? DDLRecord
				loadOp.resultUserId = attributes?["userId"] as? NSNumber

				result(loadOp.resultRecord)
			}
		}
	}

}
