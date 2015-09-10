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


class DDLFormLoadRecordInteractor: ServerReadOperationInteractor {

	let recordId: Int64
	let structureId: Int64?

	var resultRecordData: [String:AnyObject]?
	var resultRecordId: Int64?

	var resultFormRecord: DDLRecord?
	var resultFormUserId: Int64?


	init(screenlet: BaseScreenlet?, recordId: Int64, structureId: Int64?) {
		self.recordId = recordId
		self.structureId = structureId

		super.init(screenlet: screenlet)
	}

	override func createOperation() -> LiferayDDLFormRecordLoadOperation? {
		let loadRecordOperation = LiferayDDLFormRecordLoadOperation()

		loadRecordOperation.recordId = recordId

		if let structureId = structureId {
			let loadFormOperation = LiferayDDLFormLoadOperation()

			loadFormOperation.structureId = structureId

			loadRecordOperation.addDependency(loadFormOperation)

			let result = loadFormOperation.validateAndEnqueue() {
				if let loadFormOp = $0 as? LiferayDDLFormLoadOperation {
					self.resultFormRecord = loadFormOp.resultRecord
					self.resultFormUserId = loadFormOp.resultUserId
				}
			}

			if result != nil {
				return nil
			}
		}

		return loadRecordOperation
	}

	override func completedOperation(op: ServerOperation) {
		if let recordOp = op as? LiferayDDLFormRecordLoadOperation {
			self.resultRecordData = recordOp.resultRecord
			self.resultRecordId = recordOp.resultRecordId

			if let resultRecordValue = recordOp.resultRecord {
				self.resultFormRecord?.updateCurrentValues(resultRecordValue)
			}
		}
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerOperation) {
		if let loadOp = op as? LiferayDDLFormRecordLoadOperation,
				recordData = loadOp.resultRecord,
				recordId = loadOp.recordId {

			if let resultFormRecord = self.resultFormRecord,
					resultFormUserId = self.resultFormUserId
					where self.structureId != nil {

				SessionContext.currentCacheManager?.setClean(
					collection: ScreenletName(DDLFormScreenlet),
					key: "structureId-\(self.structureId)",
					value: resultFormRecord,
					attributes: [
						"userId": NSNumber(longLong: resultFormUserId)])
			}

			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "recordId-\(recordId)",
				value: recordData,
				attributes: [:])
		}
	}

	override func readFromCache(op: ServerOperation, result: AnyObject? -> Void) {
		if let loadOp = op as? LiferayDDLFormLoadOperation {
			let cacheMgr = SessionContext.currentCacheManager!

			cacheMgr.getAnyWithMetadata(
				collection: ScreenletName(DDLFormScreenlet),
				key: "structureId-\(loadOp.structureId)") {
					record, metadata in

					loadOp.resultRecord = record as? DDLRecord
					loadOp.resultUserId = (metadata?["userId"] as? NSNumber)?.longLongValue

					result(loadOp.resultRecord)
			}
		}
		
	}

}
