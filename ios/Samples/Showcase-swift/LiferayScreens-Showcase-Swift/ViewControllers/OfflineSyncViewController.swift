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

	@IBAction func clear(sender: AnyObject) {
		syncManager?.clear()
		log.text = log.text + "Cleared!\n\n"
	}

	func syncManager(manager: SyncManager, itemsCount: UInt) {
		log.text = log.text + "Start sync...  \(itemsCount) items\n\n"
	}

	func syncManager(manager: SyncManager,
			onItemSyncScreenlet screenlet: String,
			startKey: String,
			attributes: [String:AnyObject]) {
		log.text = log.text + "[o] Start item. screenlet=\(screenlet) key=\(startKey) attrs=\(attributes)\n\n"
	}

	func syncManager(manager: SyncManager,
			onItemSyncScreenlet screenlet: String,
			completedKey: String,
			attributes: [String:AnyObject]) {
		log.text = log.text + "[o] Item completed. screenlet=\(screenlet) key=\(completedKey) attrs=\(attributes)\n\n"
	}

	func syncManager(manager: SyncManager,
			onItemSyncScreenlet screenlet: String,
			failedKey: String,
			attributes: [String:AnyObject],
			error: NSError) {
		log.text = log.text + "[o] Item failed. screenlet=\(screenlet) key=\(failedKey) attrs=\(attributes) error=\(error)\n\n"
	}

	func syncManager(manager: SyncManager,
		onItemSyncScreenlet screenlet: String,
		conflictedKey: String,
		remoteValue: AnyObject,
		localValue: AnyObject,
		resolve: SyncConflictResolution -> ()) {

		log.text = log.text + "[o] Item conflicted. screenlet=\(screenlet) key=\(conflictedKey) remote=\(remoteValue) local=\(localValue)\nProcessing... "

		let alert = UIAlertController(title: "Conflicted", message: "Choose resolve action", preferredStyle: .ActionSheet)

		alert.addAction(
			UIAlertAction(
					title: "Use local",
					style: .Default) { action in
				self.log.text = self.log.text + "using local version\n\n"
				resolve(.UseLocal)
			})
		alert.addAction(
			UIAlertAction(
					title: "Use remote",
					style: .Default) { action in
				self.log.text = self.log.text + "using remote version\n\n"
				resolve(.UseRemote)
			})
		alert.addAction(
			UIAlertAction(
					title: "Discard",
					style: .Destructive) { action in
				self.log.text = self.log.text + "conflict discarded\n\n"
				resolve(.Discard)
			})
		alert.addAction(
			UIAlertAction(
					title: "Ignore",
					style: .Cancel) { action in
				self.log.text = self.log.text + "conflict ignored\n\n"
				resolve(.Ignore)
			})

		self.presentViewController(alert, animated: true, completion: nil)
	}

}
