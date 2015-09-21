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
	let recordId: Int64?
	let userId: Int64?
	let values: [String:AnyObject]?

	var recordModifiedDate: NSDate?

	var resultRecordId: Int64?
	var resultAttributes: NSDictionary?

	private var lastCacheKeyUsed: String?


	override init(screenlet: BaseScreenlet?) {
		let formScreenlet = screenlet as! DDLFormScreenlet

		groupId = (formScreenlet.groupId != 0)
			? formScreenlet.groupId
			: LiferayServerContext.groupId

		userId = (formScreenlet.userId != 0)
			? formScreenlet.userId
			: SessionContext.currentUserId

		recordId = (formScreenlet.recordId != 0)
			? formScreenlet.recordId
			: nil

		recordSetId = formScreenlet.recordSetId
		values = nil
		recordModifiedDate = nil

		super.init(screenlet: formScreenlet)
	}

	init(groupId: Int64,
			recordSetId: Int64,
			recordId: Int64?,
			userId: Int64?,
			values: [String:AnyObject],
			cacheKey: String) {

		self.groupId = (groupId != 0)
			? groupId
			: LiferayServerContext.groupId

		self.userId = (userId ?? 0 != 0)
			? userId
			: SessionContext.currentUserId

		self.recordId = recordId
		self.recordSetId = recordSetId
		self.values = values
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
		operation.recordId = recordId
		operation.recordSetId = recordSetId

		return operation
	}

	override func completedOperation(op: ServerOperation) {
		if let loadOp = op as? LiferayDDLFormSubmitOperation {
			self.resultRecordId = loadOp.resultRecordId
			self.resultAttributes = loadOp.resultAttributes

			if let modifiedDate = loadOp.resultAttributes?["modifiedDate"] as? NSNumber {
				let epoch = modifiedDate.doubleValue / 1000
				self.recordModifiedDate = NSDate(timeIntervalSince1970: epoch)
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

		lastCacheKeyUsed = lastCacheKeyUsed ?? cacheKey(submitOp.recordId)

		SessionContext.currentCacheManager?.setDirty(
			collection: ScreenletName(DDLFormScreenlet),
			key: lastCacheKeyUsed!,
			value: formData,
			attributes: cacheAttributes())
	}

	override func callOnSuccess() {
		if let lastCacheKey = lastCacheKeyUsed
				where cacheStrategy == .CacheFirst {

			if let resultRecordId = resultRecordId {
				// create new cache entry and delete the draft one
				if recordId == nil && lastCacheKey.hasPrefix("draft-") {
					SessionContext.currentCacheManager?.remove(
						collection: ScreenletName(DDLFormScreenlet),
						key: lastCacheKey)
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
					key: lastCacheKey,
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
		var attributes = [
			"groupId": NSNumber(longLong: groupId),
			"recordSetId": NSNumber(longLong: recordSetId)]

		if let userId = self.userId {
			attributes["userId"] = NSNumber(longLong: userId)
		}
		if let recordId = self.recordId {
			attributes["recordId"] = NSNumber(longLong: recordId)
		}
		if let recordId = self.resultRecordId {
			attributes["recordId"] = NSNumber(longLong: recordId)
		}
		if let recordModifiedDate = recordModifiedDate {
			attributes["modifiedDate"] = NSNumber(double: recordModifiedDate.timeIntervalSince1970)
		}

		return attributes
	}

}
