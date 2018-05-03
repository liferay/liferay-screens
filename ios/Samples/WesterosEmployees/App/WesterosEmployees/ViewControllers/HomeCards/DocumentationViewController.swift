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

class DocumentationViewController: CardViewController, AssetListScreenletDelegate {

	var selectedFileEntry: FileEntry?

	var loaded: Bool = false

	// MARK: Outlets

	@IBOutlet weak var assetListScreenlet: AssetListScreenlet? {
		didSet {
			self.assetListScreenlet?.delegate = self
			self.assetListScreenlet?.classNameId =
				AssetClasses.getClassNameId(AssetClassNameKey_DLFileEntry)!
			self.assetListScreenlet?.portletItemName =
				LiferayServerContext.stringPropertyForKey("documentationPortletItemName")
		}
	}

	// MARK: Initializers

	convenience init() {
		self.init(nibName: "DocumentationViewController", bundle: nil)
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
		self.selectedFileEntry = FileEntry(attributes: asset.attributes)
		cardView?.moveRight()
	}
}
