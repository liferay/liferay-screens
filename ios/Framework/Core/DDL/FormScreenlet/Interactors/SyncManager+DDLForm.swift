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

		if let cachedModifiedDate = attributes["modifiedDate"] as? NSNumber {
			// updating record: check consistency first
			loadRecordModifiedDate(recordId) { freshModifiedDate in
				if freshModifiedDate != nil
					&& freshModifiedDate < cachedModifiedDate.longLongValue {
						self.sendOfflineRecord(
							key: key,
							attributes: attributes,
							signal: signal)
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
			// no cached modified date: overwrite
			self.sendOfflineRecord(
				key: key,
				attributes: attributes,
				signal: signal)
		}


	}

	private func loadRecordModifiedDate(recordId: Int64, result: Int64? -> ()) {
		let op = LiferayDDLFormRecordLoadOperation(recordId: recordId)

		op.validateAndEnqueue {
			if let op = $0 as? LiferayDDLFormRecordLoadOperation,
					modifiedDate = op.resultRecordAttributes?["modifiedDate"] as? NSNumber {
				result(modifiedDate.longLongValue)
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
