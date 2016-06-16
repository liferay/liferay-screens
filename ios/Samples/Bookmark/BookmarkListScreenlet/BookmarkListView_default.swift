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


public class BookmarkListView_default: BaseListTableView {

	override public func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
		let bookmarkCell = cell as! BookmarkCell_default
		let bookmark = object as! Bookmark

		bookmarkCell.nameLabel.text = bookmark.name
		bookmarkCell.urlLabel.text = bookmark.url
	}

	override public func doFillInProgressCell(row row: Int, cell: UITableViewCell) {
		let bookmarkCell = cell as! BookmarkCell_default

		bookmarkCell.nameLabel.text = "Loading..."
	}

	override public func doRegisterCellNib(id id: String) {
		let nib = UINib(nibName: "BookmarkCell_default", bundle: NSBundle.mainBundle())

		tableView?.registerNib(nib, forCellReuseIdentifier: id)
	}

}
