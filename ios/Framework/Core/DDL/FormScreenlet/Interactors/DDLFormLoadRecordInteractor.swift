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
		let recordData: [String:AnyObject]?
		let recordId: Int64?

		if let chain = op as? ServerOperationChain,
				loadFormOp = chain.headOperation as? LiferayDDLFormLoadOperation,
				loadRecordOp = chain.currentOperation as? LiferayDDLFormRecordLoadOperation,
				recordForm = loadFormOp.resultRecord,
				formUserId = loadFormOp.resultUserId {

			recordData = loadRecordOp.resultRecord
			recordId = loadRecordOp.resultRecordId

			self.resultFormRecord = recordForm
			self.resultFormUserId = formUserId
		}
		else if let loadRecordOp = op as? LiferayDDLFormRecordLoadOperation {
			recordData = loadRecordOp.resultRecord
			recordId = loadRecordOp.resultRecordId
		}
		else {
			recordData = nil
			recordId = nil
		}

		self.resultRecordData = recordData
		self.resultRecordId = recordId

		if let recordDataValue = recordData {
			self.resultFormRecord?.updateCurrentValues(recordDataValue)
		}
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerOperation) {
		if let chain = op as? ServerOperationChain,
				loadFormOp = chain.headOperation as? LiferayDDLFormLoadOperation,
				recordForm = loadFormOp.resultRecord,
				formUserId = loadFormOp.resultUserId,
				loadRecordOp = chain.currentOperation as? LiferayDDLFormRecordLoadOperation,
				recordData = loadRecordOp.resultRecord,
				recordId = loadRecordOp.recordId {

			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "structureId-\(self.structureId)",
				value: recordForm,
				attributes: [
					"userId": NSNumber(longLong: formUserId)])

			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "recordId-\(recordId)",
				value: recordData,
				attributes: [:])
		}
		else if let loadRecordOp = op as? LiferayDDLFormRecordLoadOperation,
					recordData = loadRecordOp.resultRecord,
					recordId = loadRecordOp.recordId {

			// save just record data
			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "recordId-\(recordId)",
				value: recordData,
				attributes: [:])

		}
	}

	override func readFromCache(op: ServerOperation, result: AnyObject? -> Void) {
		let cacheMgr = SessionContext.currentCacheManager!

		if let chain = op as? ServerOperationChain,
				loadFormOp = chain.headOperation as? LiferayDDLFormLoadOperation
				where structureId != nil {

			// load form and record

			cacheMgr.getSomeWithAttributes(
					collection: ScreenletName(DDLFormScreenlet),
					keys: ["structureId-\(structureId)", "recordId-\(recordId)"]) {
				objects, attributes in

				if let recordForm = objects[0] as? DDLRecord,
						recordData = objects[1] as? [String:AnyObject] {

					loadFormOp.resultRecord = recordForm
					loadFormOp.resultUserId = (attributes[0]?["userId"] as? NSNumber)?.longLongValue

					let loadRecordOp = LiferayDDLFormRecordLoadOperation()
					loadRecordOp.resultRecord = recordData
					loadRecordOp.resultRecordId = self.recordId
					chain.currentOperation = loadRecordOp

					result(true)
				}
				else {
					result(nil)
				}
			}
		}
		else if let loadRecordOp = op as? LiferayDDLFormRecordLoadOperation,
					recordId = loadRecordOp.recordId {

			// load just record
			cacheMgr.getAny(
					collection: ScreenletName(DDLFormScreenlet),
					key: "recordId-\(recordId)") {

				loadRecordOp.resultRecord = $0 as? [String:AnyObject]
				loadRecordOp.resultRecordId = recordId

				result(loadRecordOp.resultRecord)
			}
		}
		
	}

}
