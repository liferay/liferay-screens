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
import LiferayScreens

extension SyncManager {

	func addBookmarkSynchronizer(
			key: String,
			attributes: [String:AnyObject])
			-> Signal -> () {

		let url = attributes["url"] as! String
		let folderId = attributes["url"] as! NSNumber

		return { signal in
			self.cacheManager.getString(
					collection: ScreenletName(AddBookmarkScreenlet),
					key: key) {

				if let title = $0 {
					let interactor = LiferayAddBookmarkInteractor(
						screenlet: nil,
						folderId: folderId.longLongValue,
						title: title,
						url: url)

					self.prepareInteractorForSync(interactor,
						key: key,
						attributes: attributes,
						signal: signal,
						screenletClassName: ScreenletName(AddBookmarkScreenlet))

					if !interactor.start() {
						signal()
					}
				}
				else {
					self.delegate?.syncManager?(self,
						onItemSyncScreenlet: ScreenletName(AddBookmarkScreenlet),
						failedKey: key,
						attributes: attributes,
						error: NSError.errorWithCause(.NotAvailable))
					
					signal()
				}
			}
		}
	}
	
}
