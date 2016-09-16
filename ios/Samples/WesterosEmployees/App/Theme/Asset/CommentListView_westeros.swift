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


	//MARK: UITableViewDelegate

	override public func tableView(tableView: UITableView,
	                      editActionsForRowAtIndexPath indexPath: NSIndexPath) -> [AnyObject]? {
		let editRowAction = UITableViewRowAction(style: UITableViewRowActionStyle.Normal,
			title: "Edit", handler:{action, indexPath in
				if let comment = self.rows[self.sections[indexPath.section]]?[indexPath.row] as? Comment {
					self.userAction(name: "edit-comment", sender: comment)
				}

				tableView.setEditing(false, animated: true)
		})

		let deleteRowAction = UITableViewRowAction(style: UITableViewRowActionStyle.Destructive,
			title: "Delete", handler:{action, indexPath in
				let cell = tableView.cellForRowAtIndexPath(indexPath) as? CommentTableViewCell_default
				cell?.commentDisplayScreenlet?.deleteComment()
				tableView.setEditing(false, animated: true)
		})
		deleteRowAction.backgroundColor = DefaultResources.OddColorBackground

		return [deleteRowAction, editRowAction];
	}
}
