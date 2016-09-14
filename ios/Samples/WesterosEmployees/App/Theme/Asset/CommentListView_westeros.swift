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


public class CommentListView_westeros: CommentListView_default {

	let CommentCellId = "commentCell"

	//MARK: BaseListTableView

	override public func doRegisterCellNibs() {
		let nib = NSBundle.nibInBundles(
			name: "CommentTableViewCell_westeros", currentClass: self.dynamicType)

		if let commentNib = nib {
			tableView?.registerNib(commentNib, forCellReuseIdentifier: CommentCellId)
		}
	}

	override public func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
		if let comment = object as? Comment, commentCell = cell as? CommentTableViewCell_default {
			commentCell.commentDisplayScreenlet?.comment = comment

			cell.backgroundColor = UIColor.clearColor()
			cell.accessoryType = .None
			cell.accessoryView = nil
		}
	}
}
