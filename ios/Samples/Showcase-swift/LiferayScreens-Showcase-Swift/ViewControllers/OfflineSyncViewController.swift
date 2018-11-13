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

	fileprivate var syncManager: SyncManager?

	override func viewDidAppear(_ animated: Bool) {
		if let cacheManager = SessionContext.currentContext?.cacheManager {
			syncManager = SyncManager(cacheManager: cacheManager)
			syncManager?.delegate = self
			button.isEnabled = true
		}
		else {
			button.isEnabled = false
		}
	}

	@IBAction func startSync(_ sender: AnyObject) {
		log.text = ""
		syncManager?.startSync()
	}

	@IBAction func clear(_ sender: AnyObject) {
		syncManager?.clear()
		log.text = log.text + "Cleared!\n\n"
	}

	// MARK: SyncManagerDelegate

	func syncManager(_ manager: SyncManager, itemsCount: UInt) {
		log.text.append("Start sync...  \(itemsCount) items\n\n")
	}

	func syncManager(_ manager: SyncManager,
			onItemSyncScreenlet screenlet: String,
			startKey: String,
			attributes: [String: Any]) {
		log.text.append("[o] Start item. screenlet=\(screenlet) " +
			"key=\(startKey) attrs=\(attributes)\n\n")
	}

	func syncManager(_ manager: SyncManager,
			onItemSyncScreenlet screenlet: String,
			completedKey: String,
			attributes: [String: Any]) {
		log.text.append("[o] Item completed. screenlet=\(screenlet) " +
			"key=\(completedKey) attrs=\(attributes)\n\n")
	}

	func syncManager(_ manager: SyncManager,
			onItemSyncScreenlet screenlet: String,
			failedKey: String,
			attributes: [String: Any],
			error: NSError) {
		log.text.append("[o] Item failed. screenlet=\(screenlet) " +
			"key=\(failedKey) attrs=\(attributes) error=\(error)\n\n")
	}

	func syncManager(_ manager: SyncManager,
					 onItemSyncScreenlet screenlet: String,
					 conflictedKey: String,
					 remoteValue: AnyObject,
					 localValue: AnyObject,
					 resolve: @escaping (SyncConflictResolution) -> Void) {

		log.text.append("[o] Item conflicted. screenlet=\(screenlet) " +
			"key=\(conflictedKey) remote=\(remoteValue) local=\(localValue)\nProcessing... ")

		let alert = UIAlertController(title: "Conflicted", message: "Choose resolve action",
		                              preferredStyle: .actionSheet)

		alert.addAction(
			UIAlertAction(title: "Use local", style: .default) { _ in
				self.log.text.append("using local version\n\n")
				resolve(.useLocal)
			})
		alert.addAction(
			UIAlertAction(title: "Use remote", style: .default) { _ in
				self.log.text.append("using remote version\n\n")
				resolve(.useRemote)
			})
		alert.addAction(
			UIAlertAction(title: "Discard", style: .destructive) { _ in
				self.log.text.append("conflict discarded\n\n")
				resolve(.discard)
			})
		alert.addAction(
			UIAlertAction(title: "Ignore", style: .cancel) { _ in
				self.log.text.append("conflict ignored\n\n")
				resolve(.ignore)
			})

		self.present(alert, animated: true, completion: nil)
	}

}
