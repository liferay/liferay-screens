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

class BlogsViewController: CardViewController, AssetListScreenletDelegate {

	var selectedBlogEntry: BlogsEntry?

	var loaded: Bool = false

	// MARK: Outlets

	@IBOutlet weak var assetListScreenlet: AssetListScreenlet? {
		didSet {
			assetListScreenlet?.delegate = self
			assetListScreenlet?.classNameId =
				AssetClasses.getClassNameId(AssetClassNameKey_BlogsEntry)!
		}
	}

	// MARK: Initializers

	convenience init() {
		self.init(nibName: "BlogsViewController", bundle: nil)
	}

	// MARK: CardViewController

	override func pageWillAppear() {
		if !loaded {
			assetListScreenlet?.loadList()
			loaded = true
		}
	}

	// MARK: AssetListScreenletDelegate

	func screenlet(_ screenlet: AssetListScreenlet, onAssetSelected asset: Asset) {
		self.selectedBlogEntry = BlogsEntry(attributes: asset.attributes)
		cardView?.moveRight()
	}
}
