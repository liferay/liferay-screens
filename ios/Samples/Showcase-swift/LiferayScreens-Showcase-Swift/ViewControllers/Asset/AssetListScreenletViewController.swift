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


class AssetListScreenletViewController: UIViewController, AssetListScreenletDelegate {
	
	let AssetDisplaySegue = "assetDisplay"
	
	
	//MARK: IBOutlet
	
	@IBOutlet var screenlet: AssetListScreenlet? {
		didSet {
			screenlet?.delegate = self
			screenlet?.classNameId = AssetClasses.getClassNameId(assetType)!
		}
	}
	@IBOutlet var assetTypeLabel: UILabel? {
		didSet {
			assetTypeLabel?.text = assetType
		}
	}
	
	var assetType: String = ""
	
	var selectAssetEntry: Asset?
	
	
	//MARK: AssetListScreenletDelegate
	
	func screenlet(screenlet: AssetListScreenlet, onAssetListResponse assets: [Asset]) {
		LiferayLogger.logDelegateMessage(args: assets)
	}
	
	func screenlet(screenlet: AssetListScreenlet, onAssetListError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}
	
	func screenlet(screenlet: AssetListScreenlet, onAssetSelected asset: Asset) {
		LiferayLogger.logDelegateMessage(args: asset)
		selectAssetEntry = asset
		performSegueWithIdentifier(AssetDisplaySegue, sender: self)
	}
	
	
	//MARK: UIViewController
	
	override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
		if segue.identifier == AssetDisplaySegue {
			let viewController = segue.destinationViewController as? AssetDisplayViewController
			viewController?.entryId = selectAssetEntry!.entryId
		}
	}
}
