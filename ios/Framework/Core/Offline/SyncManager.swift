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
		onItemStartSync item: AnyObject,
		screenlet: String,
		key: String)

	optional func syncManager(manager: SyncManager,
		onItemSyncCompleted item: AnyObject,
		screenlet: String,
		key: String)

	optional func syncManager(manager: SyncManager,
		onItemSyncFailed error: NSError,
		screenlet: String,
		key: String)

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
				self.cacheManager.pendingToSync { (screenlet, key, value) -> Bool in
					self.delegate?.syncManager?(self,
						onItemStartSync: value,
						screenlet: screenlet,
						key: key)

					println("sync \(screenlet)-\(key)-\(value)")

					self.delegate?.syncManager?(self,
						onItemSyncCompleted: value,
						screenlet: screenlet,
						key: key)

					return true
				}
			}
		}


	}

}
