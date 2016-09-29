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

class ViewController: UIViewController {


	//MARK: Outlets

	@IBOutlet weak var listBookmarkScreenlet: BookmarkListScreenlet?

	@IBOutlet weak var addBookmarkScreenlet: AddBookmarkScreenlet?

	@IBOutlet weak var selectViewControl: UISegmentedControl!


	//MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		let folderId = (LiferayServerContext.propertyForKey("folderId") as! NSNumber).longLongValue
		listBookmarkScreenlet?.folderId = folderId
		addBookmarkScreenlet?.folderId = folderId
	}


	@IBAction func controlValueChanged(sender: UISegmentedControl) {
		listBookmarkScreenlet?.themeName =
				selectViewControl.selectedSegmentIndex == 0 ? "default" : "default-collection"
		listBookmarkScreenlet?.loadList()
	}

}

