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


class DDLFormSubmitFormInteractor: ServerWriteOperationInteractor {

	let groupId: Int64
	let recordSetId: Int64
	let userId: Int64?
	let values: [String:AnyObject]?

	let record: DDLRecord

	var resultRecordId: Int64?
	var resultAttributes: NSDictionary?

	private var lastCacheKeyUsed: String?


	init(screenlet: BaseScreenlet?, record: DDLRecord) {
		let formScreenlet = screenlet as! DDLFormScreenlet

		self.groupId = (formScreenlet.groupId != 0)
			? formScreenlet.groupId
			: LiferayServerContext.groupId

		self.userId = (formScreenlet.userId != 0)
			? formScreenlet.userId
			: SessionContext.currentUserId

		self.recordSetId = formScreenlet.recordSetId
		self.values = nil

		self.record = record

		super.init(screenlet: formScreenlet)
	}

	init(groupId: Int64,
			recordSetId: Int64,
			recordId: Int64?,
			userId: Int64?,
			recordData: [String:AnyObject],
			cacheKey: String) {

		self.groupId = (groupId != 0)
			? groupId
			: LiferayServerContext.groupId

		self.userId = (userId ?? 0 != 0)
			? userId
			: SessionContext.currentUserId

		let recordAtts = (recordId != nil)
			? ["recordId": NSNumber(longLong: recordId!)]
			: [String:AnyObject]()
		self.record = DDLRecord(data: recordData, attributes: recordAtts)
		self.recordSetId = recordSetId
		self.values = recordData
		self.lastCacheKeyUsed = cacheKey

		super.init(screenlet: nil)
	}

	override func createOperation() -> LiferayDDLFormSubmitOperation {

		let operation: LiferayDDLFormSubmitOperation

		if let screenlet = self.screenlet as? DDLFormScreenlet {
			operation = LiferayDDLFormSubmitOperation(
					viewModel: screenlet.viewModel)

			operation.autoscrollOnValidation = screenlet.autoscrollOnValidation
		}
		else if let values = values {
			operation = LiferayDDLFormSubmitOperation(
				values: values)
		}
		else {
			fatalError("You need either values or the screenlet to submit the DDLForm")
		}

		operation.groupId = groupId
		operation.userId = userId
		operation.recordId = record.recordId
		operation.recordSetId = recordSetId

		return operation
	}

	override func completedOperation(op: ServerOperation) {
		if let loadOp = op as? LiferayDDLFormSubmitOperation {
			self.resultRecordId = loadOp.resultRecordId
			self.resultAttributes = loadOp.resultAttributes

			if let modifiedDate = loadOp.resultAttributes?["modifiedDate"] as? NSNumber {
				record.attributes["modifiedDate"] = modifiedDate
			}
		}
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerOperation) {
		let submitOp = op as! LiferayDDLFormSubmitOperation

		let formData: [String:AnyObject]

		if let screenlet = self.screenlet as? DDLFormScreenlet {
			formData = screenlet.viewModel.values
		}
		else if let values = self.values {
			formData = values
		}
		else {
			return
		}

		let cacheFunction = (cacheStrategy == .CacheFirst || op.lastError != nil)
			? SessionContext.currentCacheManager?.setDirty
			: SessionContext.currentCacheManager?.setClean

		lastCacheKeyUsed = lastCacheKeyUsed ?? cacheKey(submitOp.recordId)

		cacheFunction?(
			collection: ScreenletName(DDLFormScreenlet),
			key: lastCacheKeyUsed!,
			value: formData,
			attributes: cacheAttributes())
	}

	override func callOnSuccess() {
		if cacheStrategy == .CacheFirst {
			precondition(
				lastCacheKeyUsed != nil,
				"CacheKey is expected on CacheFirst strategy")

			if let resultRecordId = resultRecordId {
				// create new cache entry and delete the draft one
				if lastCacheKeyUsed!.hasPrefix("draft-")
						&& record.recordId == nil {
					SessionContext.currentCacheManager?.remove(
						collection: ScreenletName(DDLFormScreenlet),
						key: lastCacheKeyUsed!)
				}

				SessionContext.currentCacheManager?.setClean(
					collection: ScreenletName(DDLFormScreenlet),
					key: cacheKey(resultRecordId),
					attributes: cacheAttributes())
			}
			else {
				// update current cache entry with date sent
				SessionContext.currentCacheManager?.setClean(
					collection: ScreenletName(DDLFormScreenlet),
					key: lastCacheKeyUsed ?? cacheKey(record.recordId),
					attributes: cacheAttributes())
			}
		}

		super.callOnSuccess()
	}


	private func cacheKey(recordId: Int64?) -> String {
		if let recordId = recordId {
			return "recordId-\(recordId)"
		}
		else {
			return "draft-\(NSDate().timeIntervalSince1970)"
		}
	}

	private func cacheAttributes() -> [String:AnyObject] {
		var attributes = record.attributes

		attributes["groupId"] = NSNumber(longLong: groupId)
		attributes["recordSetId"] = NSNumber(longLong: recordSetId)

		if let userId = self.userId {
			attributes["userId"] = NSNumber(longLong: userId)
		}

		return attributes
	}

}
