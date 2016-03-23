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


class DDLFormUploadDocumentInteractor: ServerWriteConnectorInteractor {

	typealias OnProgress = DDLFormUploadLiferayConnector.OnProgress

	let filePrefix: String
	let repositoryId: Int64
	let groupId: Int64
	let folderId: Int64
	let document: DDMFieldDocument

	let onProgressClosure: OnProgress?

	var resultResponse: [String:AnyObject]?

	var lastCacheKey: String?


	init(screenlet: BaseScreenlet?,
			document: DDMFieldDocument,
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
			document: DDMFieldDocument) {

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

	override func createConnector() -> DDLFormUploadLiferayConnector {
		return LiferayServerContext.connectorFactory.createDDLFormUploadConnector(
			document: document,
			filePrefix: filePrefix,
			repositoryId: repositoryId,
			folderId: folderId,
			onProgress: self.onProgressClosure)
	}

	override func completedConnector(op: ServerConnector) {
		if let lastErrorValue = op.lastError {
			if lastErrorValue.code == ScreensErrorCause.NotAvailable.rawValue {
				let cacheResult = DDMFieldDocument.UploadStatus.CachedStatusData(cacheKey())
				self.resultResponse = cacheResult
				document.uploadStatus = .Uploaded(cacheResult)
			}
			else {
				document.uploadStatus = .Failed(lastErrorValue)
			}
		}
		else if let uploadOp = op as? DDLFormUploadLiferayConnector {
			self.resultResponse = uploadOp.uploadResult
			document.uploadStatus = .Uploaded(uploadOp.uploadResult!)
		}
	}


	//MARK: Cache methods

	override func writeToCache(op: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}

		// cache only supports images (right now)
		if let image = document.currentValue as? UIImage {
			let cacheFunction = (cacheStrategy == .CacheFirst || op.lastError != nil)
				? cacheManager.setDirty
				: cacheManager.setClean

			cacheFunction(
				collection: ScreenletName(DDLFormScreenlet),
				key: cacheKey(),
				value: image,
				attributes: cacheAttributes())
		}
	}

	override func callOnSuccess() {
		if cacheStrategy == .CacheFirst {
			SessionContext.currentContext?.cacheManager.setClean(
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
			"folderId": self.folderId.description,
			"groupId": self.groupId.description,
			"repositoryId": self.repositoryId.description
		]
	}

}
