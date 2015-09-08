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


@objc public protocol SyncManagerDelegate {

	optional func syncManager(manager: SyncManager,
		itemsCount: UInt)

	optional func syncManager(manager: SyncManager,
		onItemSyncStartScreenlet screenlet: String,
		key: String,
		attributes: [String:AnyObject])

	optional func syncManager(manager: SyncManager,
		onItemSyncCompletedScreenlet screenlet: String,
		key: String,
		attributes: [String:AnyObject])

	optional func syncManager(manager: SyncManager,
		onItemSyncFailedScreenlet screenlet: String,
		error: NSError,
		key: String,
		attributes: [String:AnyObject])

}


@objc public class SyncManager: NSObject {

	public weak var delegate: SyncManagerDelegate?

	private let cacheManager: CacheManager

	public init(cacheManager: CacheManager) {
		self.cacheManager = cacheManager

		super.init()
	}

	public func startSync() {
		cacheManager.countPendingToSync { count in
			self.delegate?.syncManager?(self, itemsCount: count)

			if count > 0 {
				self.cacheManager.pendingToSync { (screenlet, key, attributes) -> Bool in
					self.delegate?.syncManager?(self,
						onItemSyncStartScreenlet: screenlet,
						key: key,
						attributes: attributes)

					println("sync \(screenlet)-\(key)-\(attributes)")

					self.delegate?.syncManager?(self,
						onItemSyncCompletedScreenlet: screenlet,
						key: key,
						attributes: attributes)

					return true
				}
			}
		}


	}

}
