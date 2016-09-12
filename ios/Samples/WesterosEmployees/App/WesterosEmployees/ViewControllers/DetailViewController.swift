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

class DetailViewController: CardViewController, AssetDisplayScreenletDelegate {

	@IBOutlet weak var assetDisplayScreenlet: AssetDisplayScreenlet? {
		didSet {
			self.assetDisplayScreenlet?.delegate = self
		}
	}

	@IBOutlet weak var ratingScreenlet: RatingScreenlet?

	var fileEntryId: Int64? {
		didSet {
			if let entryId = fileEntryId {

				/* Load asset */
				assetDisplayScreenlet?.className = AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!
				assetDisplayScreenlet?.classPK = entryId
				assetDisplayScreenlet?.load()

				/* Load asset rating */
				ratingScreenlet?.className = AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!
				ratingScreenlet?.classPK = entryId
				ratingScreenlet?.loadRatings()
			}
		}
	}


	//MARK: Init methods

	convenience init() {
		self.init(nibName: "DetailViewController", bundle: nil)
	}


	//MARK: AssetDisplayScreenletDelegate

	func screenlet(screenlet: AssetDisplayScreenlet, onAssetResponse asset: Asset) {
		self.cardView?.changeButtonText(asset.title)
	}
}
