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

		return { signal in
			let recordId = attributes["recordId"] as? NSNumber

			if let recordId = recordId {
				self.checkAndSendOfflineRecord(
					recordId: recordId.longLongValue,
					key: key,
					attributes: attributes,
					signal: signal)
			}
			else {
				self.sendOfflineRecord(
					key: key,
					attributes: attributes,
					signal: signal)
			}
		}
	}

	private func checkAndSendOfflineRecord(
			#recordId: Int64,
			key: String,
			attributes: [String:AnyObject],
			signal: Signal) {

		if let localRecord = attributes["record"] as? DDLRecord {
			// updating record: check consistency first
			loadRecord(recordId) { remoteRecord in

				if let remoteRecord = remoteRecord,
						localModifiedDate = localRecord.attributes["modifiedDate"] as? NSNumber,
						remoteModifiedDate = remoteRecord.attributes["modifiedDate"] as? NSNumber {

					if remoteModifiedDate.longLongValue < localModifiedDate.longLongValue {
						self.sendOfflineRecord(
							key: key,
							attributes: attributes,
							signal: signal)
					}
					else {
						let useLocal = self.delegate?.syncManager?(self,
							onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
							conflictedKey: key,
							remoteValue: remoteRecord,
							localValue: localRecord) ?? false

						if useLocal {
							self.sendOfflineRecord(
								key: key,
								attributes: attributes,
								signal: signal)
						}
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
		else {
			// adding record
			self.sendOfflineRecord(
				key: key,
				attributes: attributes,
				signal: signal)
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

	private func sendOfflineRecord(
			#key: String,
			attributes: [String:AnyObject],
			signal: Signal) {

		let groupId = attributes["groupId"] as! NSNumber
		let recordSetId = attributes["recordSetId"] as! NSNumber
		let recordId = attributes["recordId"] as? NSNumber
		let userId = attributes["userId"] as? NSNumber

		self.cacheManager.getAny(
				collection: ScreenletName(DDLFormScreenlet),
				key: key) {

			if let values = $0 as? [String:AnyObject] {
				let interactor = DDLFormSubmitFormInteractor(
					groupId: groupId.longLongValue,
					recordSetId: recordSetId.longLongValue,
					recordId: recordId?.longLongValue,
					userId: userId?.longLongValue,
					recordData: values,
					cacheKey: key)

				// this strategy saves the "send date" after the operation
				interactor.cacheStrategy = .CacheFirst

				interactor.onSuccess = {
					self.delegate?.syncManager?(self,
						onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
						completedKey: key,
						attributes: attributes)

					signal()
				}

				interactor.onFailure = { err in
					self.delegate?.syncManager?(self,
						onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
						failedKey: key,
						attributes: attributes,
						error: err)

					// TODO retry?
					signal()
				}

				if !interactor.start() {
					signal()
				}
			}
			else {
				self.delegate?.syncManager?(self,
					onItemSyncScreenlet: ScreenletName(DDLFormScreenlet),
					failedKey: key,
					attributes: attributes,
					error: NSError.errorWithCause(.NotAvailable))

				signal()
			}
		}
	}

}
