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
	var resultRecordAttributes: [String:AnyObject]?
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

		let loadRecordOp = LiferayDDLFormRecordLoadOperation(recordId: recordId)

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
		let recordAttributes: [String:AnyObject]?
		let recordId: Int64?

		if let chain = op as? ServerOperationChain,
				loadFormOp = chain.headOperation as? LiferayDDLFormLoadOperation,
				loadRecordOp = chain.currentOperation as? LiferayDDLFormRecordLoadOperation,
				recordForm = loadFormOp.resultRecord,
				formUserId = loadFormOp.resultUserId {

			recordData = loadRecordOp.resultRecordData
			recordAttributes = loadRecordOp.resultRecordAttributes
			recordId = loadRecordOp.resultRecordId

			self.resultFormRecord = recordForm
			self.resultFormUserId = formUserId
		}
		else if let loadRecordOp = op as? LiferayDDLFormRecordLoadOperation {
			recordData = loadRecordOp.resultRecordData
			recordAttributes = loadRecordOp.resultRecordAttributes
			recordId = loadRecordOp.resultRecordId
		}
		else {
			recordData = nil
			recordAttributes = nil
			recordId = nil
		}

		self.resultRecordData = recordData
		self.resultRecordAttributes = recordAttributes
		self.resultRecordId = recordId

		if let recordDataValue = recordData,
				recordAttributesValue = recordAttributes {
			self.resultFormRecord?.updateCurrentValues(recordDataValue)
			self.resultFormRecord?.attributes = recordAttributesValue
		}
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerOperation) {
		if let chain = op as? ServerOperationChain,
				loadFormOp = chain.headOperation as? LiferayDDLFormLoadOperation,
				recordForm = loadFormOp.resultRecord,
				formUserId = loadFormOp.resultUserId,
				loadRecordOp = chain.currentOperation as? LiferayDDLFormRecordLoadOperation,
				recordData = loadRecordOp.resultRecordData,
				recordAttributes = loadRecordOp.resultRecordAttributes {

			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "structureId-\(self.structureId)",
				value: recordForm,
				attributes: [
					"userId": NSNumber(longLong: formUserId)])

			let record = DDLRecord(
				data: recordData,
				attributes: recordAttributes)

			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "recordId-\(loadRecordOp.recordId)",
				value: recordData,
				attributes: ["record": record])
		}
		else if let loadRecordOp = op as? LiferayDDLFormRecordLoadOperation,
					recordData = loadRecordOp.resultRecordData,
					recordAttributes = loadRecordOp.resultRecordAttributes {

			let record = DDLRecord(
				data: recordData,
				attributes: recordAttributes)

			// save just record data
			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "recordId-\(loadRecordOp.recordId)",
				value: recordData,
				attributes: ["record": record])
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
						recordUserId = attributes[0]?["userId"] as? NSNumber,
						recordData = objects[1] as? [String:AnyObject],
						recordAttributes = attributes[1] {

					let record = recordAttributes["record"] as! DDLRecord

					precondition(self.recordId == record.recordId, "RecordId is not consistent")

					loadFormOp.resultRecord = recordForm
					loadFormOp.resultUserId = recordUserId.longLongValue

					let loadRecordOp = LiferayDDLFormRecordLoadOperation(recordId: self.recordId)

					loadRecordOp.resultRecordData = recordData
					loadRecordOp.resultRecordAttributes = record.attributes
					loadRecordOp.resultRecordId = self.recordId
					chain.currentOperation = loadRecordOp

					result(true)
				}
				else {
					result(nil)
				}
			}
		}
		else if let loadRecordOp = op as? LiferayDDLFormRecordLoadOperation {

			// load just record
			cacheMgr.getAnyWithAttributes(
					collection: ScreenletName(DDLFormScreenlet),
					key: "recordId-\(loadRecordOp.recordId)") {
				object, attributes in

				let record = attributes?["record"] as! DDLRecord

				precondition(self.recordId == record.recordId, "RecordId is not consistent")

				loadRecordOp.resultRecordData = object as? [String:AnyObject]
				loadRecordOp.resultRecordAttributes = record.attributes
				loadRecordOp.resultRecordId = loadRecordOp.recordId

				result(loadRecordOp.resultRecordData)
			}
		}
	}

}
