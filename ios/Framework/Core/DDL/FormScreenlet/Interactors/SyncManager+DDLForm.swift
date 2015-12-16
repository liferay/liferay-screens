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
import Foundation

extension SyncManager {

	func formSynchronizer(
			key: String,
			attributes: [String:AnyObject])
			-> Signal -> () {

		let recordSynchronizer = { (signal: Signal) -> () in
			let record = attributes["record"] as! DDLRecord

			if record.recordId != nil {
				// update
				self.checkConflictAndSendLocalRecord(
					record: record,
					key: key,
					attributes: attributes,
					signal: signal)
			}
			else {
				// add
				self.sendLocalRecord(
					record: record,
					key: key,
					attributes: attributes,
					signal: signal)
			}
		}

		let documentSynchronizer = { (signal: Signal) -> () in
			// Do nothing. 
			// When the record is sync-ed the documents will be sync-ed too
			// Notify as this entry is finished
			dispatch_main() {
				self.delegate?.syncManager?(self,
					onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
					completedKey: key, attributes: attributes)
				signal()
			}
		}

		return key.hasPrefix("document-") ? documentSynchronizer : recordSynchronizer
	}

	private func checkConflictAndSendLocalRecord(
			record localRecord: DDLRecord,
			key: String,
			attributes: [String:AnyObject],
			signal: Signal) {

		precondition(localRecord.recordId != nil, "RecordId must be defined")

		// updating record: check consistency first
		loadRecord(localRecord.recordId!) { remoteRecord in

			if remoteRecord == nil {
				self.delegate?.syncManager?(self,
					onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
					failedKey: key,
					attributes: attributes,
					error: NSError.errorWithCause(.NotAvailable))
				signal()
			}
			else if let localModifiedDate = localRecord.attributes["modifiedDate"] as? NSNumber,
					remoteModifiedDate = remoteRecord!.attributes["modifiedDate"] as? NSNumber {

				if remoteModifiedDate.longLongValue <= localModifiedDate.longLongValue {
					self.sendLocalRecord(
						record: localRecord,
						key: key,
						attributes: attributes,
						signal: signal)
				}
				else {
					self.resolveConflict(
						remoteRecord: remoteRecord!,
						localRecord: localRecord,
						key: key,
						attributes: attributes,
						signal: signal)
				}
			}
			else {
				self.delegate?.syncManager?(self,
					onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
					failedKey: key,
					attributes: attributes,
					error: NSError.errorWithCause(.InvalidServerResponse))
				signal()
			}
		}
	}

	private func resolveConflict(
			remoteRecord remoteRecord: DDLRecord,
			localRecord: DDLRecord,
			key: String,
			attributes: [String:AnyObject],
			signal: Signal) {

		self.delegate?.syncManager?(self,
				onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
				conflictedKey: key,
				remoteValue: remoteRecord,
				localValue: localRecord) { resolution in

			switch resolution {
			case .UseLocal:
				// send local to server
				self.sendLocalRecord(
					record: localRecord,
					key: key,
					attributes: attributes,
					signal: signal)

			case .UseRemote:
				// overwrite cache with remote record
				var newAttrs = attributes
				newAttrs["record"] = remoteRecord

				self.cacheManager.setClean(
					collection: ScreenletName(DDLFormScreenlet),
					key: DDLFormSubmitFormInteractor.cacheKey(localRecord.recordId),
					value: remoteRecord.values,
					attributes: newAttrs)

			case .Discard:
				// clear cache
				self.cacheManager.remove(
					collection: ScreenletName(DDLFormScreenlet),
					key: key)

			case .Ignore:
				// notify but keep cache
				self.delegate?.syncManager?(self,
					onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
					failedKey: key,
					attributes: attributes,
					error: NSError.errorWithCause(.AbortedDueToPreconditions))
				signal()
			}
		}
	}

	private func loadRecord(recordId: Int64, result: DDLRecord? -> ()) {
		let op = LiferayDDLFormRecordLoadOperation(recordId: recordId)

		op.validateAndEnqueue {
			if let op = $0 as? LiferayDDLFormRecordLoadOperation,
					recordData = op.resultRecordData,
					recordAttributes = op.resultRecordAttributes {

				let remoteRecord = DDLRecord(
					data: recordData,
					attributes: recordAttributes)

				result(remoteRecord)
			}
			else {
				result(nil)
			}
		}
	}

	private func sendLocalRecord(
			record localRecord: DDLRecord,
			key: String,
			attributes: [String:AnyObject],
			signal: Signal) {

		let cachedDocument = localRecord.fieldsBy(type: DDLFieldDocument.self)
			.map {
				$0 as! DDLFieldDocument
			}.filter {
				return $0.cachedKey != nil
			}.first

		if let cachedDocument = cachedDocument {
			sendLocalDocument(cachedDocument,
				record: localRecord,
				recordKey: key,
				recordAttributes: attributes,
				signal: signal)
			return
		}

		let interactor = DDLFormSubmitFormInteractor(
			cacheKey: key,
			record: localRecord)

		self.prepareInteractorForSync(interactor,
			key: key,
			attributes: attributes,
			signal: signal,
			screenletClassName: ScreenletName(DDLFormScreenlet))

		interactor.cacheStrategy = .RemoteFirst

		if !interactor.start() {
			dispatch_main() {
				self.delegate?.syncManager?(self,
					onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
					failedKey: key,
					attributes: attributes,
					error: NSError.errorWithCause(.ValidationFailed))

				signal()
			}
		}
	}

	private func sendLocalDocument(
			document: DDLFieldDocument,
			record: DDLRecord,
			recordKey: String,
			recordAttributes: [String:AnyObject],
			signal: Signal) {

		precondition(
			document.cachedKey != nil,
			"Cached key is missing on local document")

		self.cacheManager.getAnyWithAttributes(
				collection: ScreenletName(DDLFormScreenlet),
				key: document.cachedKey!) { object, attributes in

			if let filePrefix = attributes?["filePrefix"] as? String,
					folderId = attributes?["folderId"] as? NSNumber,
					repositoryId = attributes?["repositoryId"] as? NSNumber,
					groupId = attributes?["groupId"] as? NSNumber {

				document.currentValue = object

				let interactor = DDLFormUploadDocumentInteractor(
					filePrefix: filePrefix,
					repositoryId: repositoryId.longLongValue,
					groupId: groupId.longLongValue,
					folderId: folderId.longLongValue,
					document: document)

				interactor.cacheStrategy = .CacheFirst

				interactor.onSuccess = {
					document.uploadStatus = .Uploaded(interactor.resultResponse!)

					// go on with record recursively
					self.sendLocalRecord(
						record: record,
						key: recordKey,
						attributes: recordAttributes,
						signal: signal)
				}

				interactor.onFailure = { err in
					self.delegate?.syncManager?(self,
						onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
						failedKey: recordKey,
						attributes: recordAttributes,
						error: err)

					// TODO retry?
					signal()
				}
				
				if !interactor.start() {
					dispatch_main() {
						self.delegate?.syncManager?(self,
							onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
							failedKey: recordKey,
							attributes: recordAttributes,
							error: NSError.errorWithCause(.ValidationFailed))
						signal()
					}
				}
			}
			else {
				dispatch_main() {
					self.delegate?.syncManager?(self,
						onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
						failedKey: recordKey,
						attributes: recordAttributes,
						error: NSError.errorWithCause(.NotAvailable))

					signal()
				}
			}
		}
	}

}
