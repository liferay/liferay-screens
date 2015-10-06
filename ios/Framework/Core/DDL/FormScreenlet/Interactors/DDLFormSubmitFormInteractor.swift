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

	let record: DDLRecord

	var resultRecordId: Int64?
	var resultAttributes: NSDictionary?

	private var lastCacheKeyUsed: String?


	//MARK: Class functions

	class func cacheKey(recordId: Int64?) -> String {
		if let recordId = recordId {
			return "recordId-\(recordId)"
		}
		else {
			return "draft-\(NSDate().timeIntervalSince1970)"
		}
	}


	//MARK: Inits

	init(screenlet: BaseScreenlet?, record: DDLRecord) {
		let formScreenlet = screenlet as! DDLFormScreenlet

		self.groupId = (formScreenlet.groupId != 0)
			? formScreenlet.groupId
			: LiferayServerContext.groupId

		self.userId = (formScreenlet.userId != 0)
			? formScreenlet.userId
			: SessionContext.currentUserId

		self.recordSetId = formScreenlet.recordSetId
		self.record = record

		super.init(screenlet: formScreenlet)
	}

	init(cacheKey: String, record: DDLRecord) {
		self.groupId = record.attributes["groupId"]?.longLongValue
			?? LiferayServerContext.groupId

		self.userId = record.attributes["userId"]?.longLongValue
			?? SessionContext.currentUserId

		self.recordSetId = record.attributes["recordSetId"]!.longLongValue
		self.record = record
		self.lastCacheKeyUsed = cacheKey

		super.init(screenlet: nil)
	}

	override func createOperation() -> LiferayDDLFormSubmitOperation {

		let operation: LiferayDDLFormSubmitOperation

		if let screenlet = self.screenlet as? DDLFormScreenlet {
			operation = LiferayDDLFormSubmitOperation(
					values: record.values,
					viewModel: screenlet.viewModel)

			operation.autoscrollOnValidation = screenlet.autoscrollOnValidation
		}
		else {
			operation = LiferayDDLFormSubmitOperation(
				values: record.values)
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

		let cacheFunction = (cacheStrategy == .CacheFirst || op.lastError != nil)
			? SessionContext.currentCacheManager?.setDirty
			: SessionContext.currentCacheManager?.setClean

		lastCacheKeyUsed = lastCacheKeyUsed
			?? DDLFormSubmitFormInteractor.cacheKey(submitOp.recordId)

		cacheFunction?(
			collection: ScreenletName(DDLFormScreenlet),
			key: lastCacheKeyUsed!,
			value: record.values,
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
					key: DDLFormSubmitFormInteractor.cacheKey(resultRecordId),
					attributes: cacheAttributes())
			}
			else {
				// update current cache entry with date sent
				SessionContext.currentCacheManager?.setClean(
					collection: ScreenletName(DDLFormScreenlet),
					key: lastCacheKeyUsed
						?? DDLFormSubmitFormInteractor.cacheKey(record.recordId),
					attributes: cacheAttributes())
			}
		}

		super.callOnSuccess()
	}

	private func cacheAttributes() -> [String:AnyObject] {
		let attrs = ["record": record]

		if record.recordId == nil {
			record.attributes["groupId"] = NSNumber(longLong: self.groupId)
			record.attributes["recordSetId"] = NSNumber(longLong: self.recordSetId)
			if let userId = self.userId {
				record.attributes["userId"] = NSNumber(longLong: userId)
			}
		}

		return attrs
	}

}
