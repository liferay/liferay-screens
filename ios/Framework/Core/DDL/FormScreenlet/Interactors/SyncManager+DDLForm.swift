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
						values: values)

					// this strategy saves the send date after the operation
					interactor.cacheStrategy = .CacheFirst

					interactor.onSuccess = {
						self.delegate?.syncManager?(self,
							onItemSyncCompletedScreenlet: ScreenletName(DDLFormScreenlet),
							key: key,
							attributes: attributes)

						signal()
					}

					interactor.onFailure = { err in
						self.delegate?.syncManager?(self,
							onItemSyncFailedScreenlet: ScreenletName(DDLFormScreenlet),
							error: err,
							key: key,
							attributes: attributes)

						// TODO retry?
						signal()
					}

					if !interactor.start() {
						signal()
					}
				}
				else {
					self.delegate?.syncManager?(self,
						onItemSyncFailedScreenlet: ScreenletName(DDLFormScreenlet),
						error: NSError.errorWithCause(.NotAvailable),
						key: key,
						attributes: attributes)

					signal()
				}
			}
		}
	}

}
