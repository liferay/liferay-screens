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
import LiferayScreens

class OfflineSyncViewController: UIViewController, SyncManagerDelegate {

	@IBOutlet weak var button: UIButton!
	@IBOutlet weak var log: UITextView!

	private var syncManager: SyncManager?

	override func viewDidAppear(animated: Bool) {
		if let cacheManager = SessionContext.currentCacheManager {
			syncManager = SyncManager(cacheManager: cacheManager)
			syncManager?.delegate = self
			button.enabled = true
		}
		else {
			button.enabled = false
		}
	}

	@IBAction func startSync(sender: AnyObject) {
		log.text = ""
		syncManager?.startSync()
	}

	func syncManager(manager: SyncManager, itemsCount: UInt) {
		log.text = log.text + "Start sync...  \(itemsCount) items\n\n"
	}

	func syncManager(manager: SyncManager,
			onItemSyncStartScreenlet screenlet: String,
			key: String,
			attributes: [String:AnyObject]) {
		log.text = log.text + "Start item. screenlet=\(screenlet) key=\(key) attrs=\(attributes)\n\n"
	}

	func syncManager(manager: SyncManager,
			onItemSyncCompletedScreenlet screenlet: String,
			key: String,
			attributes: [String:AnyObject]) {
		log.text = log.text + "Item completed. screenlet=\(screenlet) key=\(key) attrs=\(attributes)\n\n"
	}

	func syncManager(manager: SyncManager,
			onItemSyncFailedScreenlet screenlet: String,
			error: NSError,
			key: String,
			attributes: [String:AnyObject]) {
		log.text = log.text + "Item failed. screenlet=\(screenlet) key=\(key) attrs=\(attributes) error=\(error)\n\n"
	}

}
