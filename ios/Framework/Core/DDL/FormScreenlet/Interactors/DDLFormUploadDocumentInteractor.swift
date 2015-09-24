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


class DDLFormUploadDocumentInteractor: ServerWriteOperationInteractor {

	typealias OnProgress = LiferayDDLFormUploadOperation.OnProgress

	let filePrefix: String
	let repositoryId: Int64
	let groupId: Int64
	let folderId: Int64
	let document: DDLFieldDocument

	let onProgressClosure: OnProgress?

	var resultResponse: [String:AnyObject]?

	var lastCacheKey: String?


	init(screenlet: BaseScreenlet?,
			document: DDLFieldDocument,
			onProgressClosure: OnProgress) {

		let formScreenlet = screenlet as! DDLFormScreenlet

		self.groupId = (formScreenlet.groupId != 0)
			? formScreenlet.groupId
			: LiferayServerContext.groupId

		self.filePrefix = formScreenlet.filePrefix
		self.folderId = formScreenlet.folderId
		self.repositoryId = (formScreenlet.repositoryId != 0)
			? formScreenlet.repositoryId
			: self.groupId

		self.document = document
		self.onProgressClosure = onProgressClosure

		super.init(screenlet: screenlet)
	}

	init(filePrefix: String,
			repositoryId: Int64,
			groupId: Int64,
			folderId: Int64,
			document: DDLFieldDocument) {

		self.groupId = (groupId != 0)
			? groupId
			: LiferayServerContext.groupId

		self.filePrefix = filePrefix
		self.folderId = folderId
		self.repositoryId = (repositoryId != 0)
			? repositoryId
			: self.groupId

		self.document = document
		self.lastCacheKey = document.cachedKey
		self.onProgressClosure = nil

		super.init(screenlet: nil)
	}

	override func createOperation() -> LiferayDDLFormUploadOperation {
		let operation = LiferayDDLFormUploadOperation()

		operation.document = self.document
		operation.filePrefix = self.filePrefix
		operation.folderId = self.folderId
		operation.repositoryId = self.repositoryId
		operation.onUploadedBytes = self.onProgressClosure

		return operation
	}

	override func completedOperation(op: ServerOperation) {
		if let lastErrorValue = op.lastError {
			if lastErrorValue.code == ScreensErrorCause.NotAvailable.rawValue {
				let cacheResult = DDLFieldDocument.UploadStatus.CachedStatusData(cacheKey())
				self.resultResponse = cacheResult
				document.uploadStatus = .Uploaded(cacheResult)
			}
			else {
				document.uploadStatus = .Failed(lastErrorValue)
			}
		}
		else if let uploadOp = op as? LiferayDDLFormUploadOperation {
			self.resultResponse = uploadOp.uploadResult
			document.uploadStatus = .Uploaded(uploadOp.uploadResult!)
		}
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerOperation) {
		// cache only supports images (right now)
		if let image = document.currentValue as? UIImage {
			let cacheFunction = (cacheStrategy == .CacheFirst || op.lastError != nil)
				? SessionContext.currentCacheManager?.setDirty
				: SessionContext.currentCacheManager?.setClean

			cacheFunction?(
				collection: ScreenletName(DDLFormScreenlet),
				key: cacheKey(),
				value: image,
				attributes: cacheAttributes())
		}
	}

	override func callOnSuccess() {
		if cacheStrategy == .CacheFirst {
			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(DDLFormScreenlet),
				key: cacheKey(),
				attributes: cacheAttributes())
		}

		super.callOnSuccess()
	}


	//MARK: Private methods

	private func cacheKey() -> String {
		lastCacheKey = lastCacheKey ?? "document-\(NSDate().timeIntervalSince1970)"
		return lastCacheKey!
	}

	private func cacheAttributes() -> [String:AnyObject] {
		return [
			"document": self.document,
			"filePrefix": self.filePrefix,
			"folderId": NSNumber(longLong: self.folderId),
			"groupId": NSNumber(longLong: self.groupId),
			"repositoryId": NSNumber(longLong: self.repositoryId)
		]
	}

}
