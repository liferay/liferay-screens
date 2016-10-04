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


class BlogDisplayScreenletViewController: UIViewController, BlogsEntryDisplayScreenletDelegate {

	@IBOutlet weak var screenlet: BlogsEntryDisplayScreenlet?
	@IBOutlet weak var assetIdText: UITextField?

	override func viewDidLoad() {
		self.screenlet?.delegate = self
	}

	@IBAction func loadComment(sender: AnyObject) {
		if let assetId = Int(assetIdText?.text ?? "") {
			self.screenlet?.assetEntryId = Int64(assetId)
			self.screenlet?.load()
		}
	}

}
