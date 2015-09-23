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


@objc public enum SyncConflictResolution: Int {

	case UseRemote
	case UseLocal
	case Discard
	case Ignore

}


@objc public protocol SyncManagerDelegate {

	optional func syncManager(manager: SyncManager,
		itemsCount: UInt)

	optional func syncManager(manager: SyncManager,
		onItemSyncScreenlet screenlet: String,
		startKey: String,
		attributes: [String:AnyObject])

	optional func syncManager(manager: SyncManager,
		onItemSyncScreenlet screenlet: String,
		completedKey: String,
		attributes: [String:AnyObject])

	optional func syncManager(manager: SyncManager,
		onItemSyncScreenlet screenlet: String,
		failedKey: String,
		attributes: [String:AnyObject],
		error: NSError)

	optional func syncManager(manager: SyncManager,
		onItemSyncScreenlet screenlet: String,
		conflictedKey: String,
		remoteValue: AnyObject,
		localValue: AnyObject,
		resolve: SyncConflictResolution -> ())

}


@objc public class SyncManager: NSObject {

	public weak var delegate: SyncManagerDelegate?

	public let cacheManager: CacheManager

	private let syncQueue: NSOperationQueue

	public init(cacheManager: CacheManager) {
		self.cacheManager = cacheManager

		self.syncQueue = NSOperationQueue()
		self.syncQueue.maxConcurrentOperationCount = 1

		super.init()
	}

	public func startSync() {
		cacheManager.countPendingToSync { count in
			self.delegate?.syncManager?(self, itemsCount: count)

			if count > 0 {
				self.cacheManager.pendingToSync { (screenlet, key, attributes) -> Bool in
					self.delegate?.syncManager?(self,
						onItemSyncScreenlet: screenlet,
						startKey: key,
						attributes: attributes)

					self.enqueueSyncForScreenlet(screenlet, key, attributes)

					return true
				}
			}
		}
	}

	private func enqueueSyncForScreenlet(
			screenletName: String,
			_ key: String,
			_ attributes: [String:AnyObject]) {

		let sychronizers = [
				ScreenletName(UserPortraitScreenlet): userPortraitSynchronizer,
				ScreenletName(DDLFormScreenlet): formSynchronizer,
			]

		if let sychronizerBuilder = sychronizers[screenletName] {
			let synchronizer = sychronizerBuilder(key, attributes: attributes)

			syncQueue.addOperationWithBlock(to_sync(synchronizer))
		}
	}

}
