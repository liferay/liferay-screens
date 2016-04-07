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


class DDLFormLoadRecordInteractor: ServerReadConnectorInteractor {

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

	override func createConnector() -> ServerConnector {
		let connector: ServerConnector

		let loadRecordOp = LiferayServerContext.connectorFactory.createDDLFormRecordLoadConnector(recordId)

		if let structureId = structureId {
			let loadFormOp = LiferayServerContext.connectorFactory.createDDLFormLoadConnector(structureId)

			let connectorChain = ServerConnectorChain(head: loadFormOp)

			connectorChain.onNextStep = { (op, seq) -> ServerConnector? in
				if let loadFormOp = op as? DDLFormLoadLiferayConnector {
					self.resultFormRecord = loadFormOp.resultRecord
					self.resultFormUserId = loadFormOp.resultUserId

					return loadRecordOp
				}

				return nil
			}

			connector = connectorChain
		}
		else {
			connector = loadRecordOp
		}

		return connector
	}

	override func completedConnector(op: ServerConnector) {
		let recordData: [String:AnyObject]?
		let recordAttributes: [String:AnyObject]?
		let recordId: Int64?

		if let chain = op as? ServerConnectorChain,
				loadFormOp = chain.headConnector as? DDLFormLoadLiferayConnector,
				loadRecordOp = chain.currentConnector as? DDLFormRecordLoadLiferayConnector,
				recordForm = loadFormOp.resultRecord,
				formUserId = loadFormOp.resultUserId {

			recordData = loadRecordOp.resultRecordData
			recordAttributes = loadRecordOp.resultRecordAttributes
			recordId = loadRecordOp.resultRecordId

			self.resultFormRecord = recordForm
			self.resultFormUserId = formUserId
		}
		else if let loadRecordOp = op as? DDLFormRecordLoadLiferayConnector {
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
				self.resultFormRecord?.updateCurrentValues(values: recordDataValue)
			self.resultFormRecord?.attributes = recordAttributesValue
		}
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}

		if let chain = op as? ServerConnectorChain,
				loadFormOp = chain.headConnector as? DDLFormLoadLiferayConnector,
				recordForm = loadFormOp.resultRecord,
				formUserId = loadFormOp.resultUserId,
				loadRecordOp = chain.currentConnector as? DDLFormRecordLoadLiferayConnector,
				recordData = loadRecordOp.resultRecordData,
				recordAttributes = loadRecordOp.resultRecordAttributes {

			cacheManager.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "structureId-\(self.structureId)",
				value: recordForm,
				attributes: [
					"userId": formUserId.description])

			let record = DDLRecord(
				data: recordData,
				attributes: recordAttributes)

			cacheManager.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "recordId-\(loadRecordOp.recordId)",
				value: recordData,
				attributes: ["record": record])
		}
		else if let loadRecordOp = op as? DDLFormRecordLoadLiferayConnector,
					recordData = loadRecordOp.resultRecordData,
					recordAttributes = loadRecordOp.resultRecordAttributes {

			let record = DDLRecord(
				data: recordData,
				attributes: recordAttributes)

			// save just record data
			cacheManager.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: "recordId-\(loadRecordOp.recordId)",
				value: recordData,
				attributes: ["record": record])
		}
	}

	override func readFromCache(op: ServerConnector, result: AnyObject? -> ()) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			result(nil)
			return
		}

		if let chain = op as? ServerConnectorChain,
				loadFormOp = chain.headConnector as? DDLFormLoadLiferayConnector
				where structureId != nil {

			// load form and record

			cacheManager.getSomeWithAttributes(
					collection: ScreenletName(DDLFormScreenlet),
					keys: ["structureId-\(structureId)", "recordId-\(recordId)"]) {
				objects, attributes in

				if let recordForm = objects[0] as? DDLRecord,
						recordUserId = attributes[0]?["userId"]?.description.asLong,
						recordData = objects[1] as? [String:AnyObject],
						recordAttributes = attributes[1] {

					let record = recordAttributes["record"] as! DDLRecord

					precondition(self.recordId == record.recordId, "RecordId is not consistent")

					loadFormOp.resultRecord = recordForm
					loadFormOp.resultUserId = recordUserId

					let loadRecordOp = LiferayServerContext.connectorFactory.createDDLFormRecordLoadConnector(self.recordId)

					loadRecordOp.resultRecordData = recordData
					loadRecordOp.resultRecordAttributes = record.attributes
					loadRecordOp.resultRecordId = self.recordId
					chain.currentConnector = loadRecordOp

					result(true)
				}
				else {
					result(nil)
				}
			}
		}
		else if let loadRecordOp = op as? DDLFormRecordLoadLiferayConnector {
			// load just record
			cacheManager.getAnyWithAttributes(
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
		else {
			result(nil)
		}
	}

}
