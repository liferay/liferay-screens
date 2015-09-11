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

	override func createOperation() -> ServerOperation {
		let operation: ServerOperation

		let loadRecordOp = LiferayDDLFormRecordLoadOperation()

		loadRecordOp.recordId = recordId

		if let structureId = structureId {
			let loadFormOp = LiferayDDLFormLoadOperation()
			loadFormOp.structureId = structureId

			let operationChain = ServerOperationChain(head: loadFormOp)

			operationChain.onNextStep = { (op, seq) -> ServerOperation? in
				if let loadFormOp = op as? LiferayDDLFormLoadOperation {
					self.resultFormRecord = loadFormOp.resultRecord
					self.resultFormUserId = loadFormOp.resultUserId

					return loadRecordOp
				}

				return nil
			}

			operation = operationChain
		}
		else {
			operation = loadRecordOp
		}

		return operation
	}

	override func completedOperation(op: ServerOperation) {
		if let chain = op as? ServerOperationChain,
				loadRecordOp = chain.currentOperation as? LiferayDDLFormRecordLoadOperation {

			self.resultRecordData = loadRecordOp.resultRecord
			self.resultRecordId = loadRecordOp.resultRecordId

			if let resultRecordValue = loadRecordOp.resultRecord {
				self.resultFormRecord?.updateCurrentValues(resultRecordValue)
			}
		}
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerOperation) {
		if let chain = op as? ServerOperationChain,
				loadRecordOp = chain.currentOperation as? LiferayDDLFormRecordLoadOperation,
				recordData = loadRecordOp.resultRecord,
				recordId = loadRecordOp.recordId {

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
		if let chain = op as? ServerOperationChain,
				loadRecordOp = chain.currentOperation as? LiferayDDLFormRecordLoadOperation {
/*
			let cacheMgr = SessionContext.currentCacheManager!

			if let structureId = self.structureId {
				cacheMgr.getAnyWithMetadata(
					collection: ScreenletName(DDLFormScreenlet),
					key: "structureId-\(structureId)") {
						record, metadata in

						loadOp.resultRecord = record as? DDLRecord
						loadOp.resultUserId = (metadata?["userId"] as? NSNumber)?.longLongValue

						result(loadOp.resultRecord)
				}

			}
*/
		}
		
	}

}
