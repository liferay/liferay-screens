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


class AssetDisplayViewController: UIViewController, AssetDisplayScreenletDelegate {
	
	@IBOutlet var screenlet: AssetDisplayScreenlet?
	
	var entryId: Int64?
	
	override func viewDidLoad() {
		super.viewDidLoad()

		if let id = entryId {
			self.screenlet?.assetEntryId = id
		}
		self.screenlet?.delegate = self
		self.screenlet?.presentingViewController = self
		self.screenlet?.load()
	}
	
	func screenlet(screenlet: AssetDisplayScreenlet,
	               onAssetEntryResponse assetEntry: Asset) {
		print("DELEGATE: onAssetEntryResponse -> \(assetEntry)\n");
	}
	
	func screenlet(screenlet: AssetDisplayScreenlet,
	               onAssetEntryError error: NSError) {
		print("DELEGATE: onAssetEntryError -> \(error)\n");
	}

	func screenlet(screenlet: AssetDisplayScreenlet, onAsset asset: Asset) -> UIView? {
		if let type = asset.attributes["object"]?.allKeys.first as? String {
			if type == "user" {
				let vc = self.storyboard?.instantiateViewControllerWithIdentifier("UserDisplay") as? UserDisplayViewController
				if let userVc = vc {
					self.addChildViewController(userVc)
					screenlet.addSubview(userVc.view)
					userVc.view.frame = screenlet.bounds
					userVc.user = User(attributes: asset.attributes)
				}
			}
		}
		return nil
	}
}
