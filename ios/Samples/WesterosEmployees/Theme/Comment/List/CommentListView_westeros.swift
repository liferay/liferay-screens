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

open class CommentListView_westeros: CommentListView_default {

	let CommentCellId = "commentCell"

	// MARK: BaseListTableView

	override open func doRegisterCellNibs() {
		let nib = Bundle.nibInBundles(
			name: "CommentTableViewCell_westeros", currentClass: type(of: self))

		if let commentNib = nib {
			tableView?.register(commentNib, forCellReuseIdentifier: CommentCellId)
		}
	}

	override open func doFillLoadedCell(row: Int, cell: UITableViewCell, object: AnyObject) {
		if let comment = object as? Comment, let commentCell = cell as? CommentTableViewCell_default {
			commentCell.commentDisplayScreenlet?.comment = comment

			cell.backgroundColor = UIColor.clear
			cell.accessoryType = .none
			cell.accessoryView = nil
		}
	}

	// MARK: BaseScreenletView

	override open func createProgressPresenter() -> ProgressPresenter {
		return WesterosCardProgressPresenter(screenlet: self.screenlet)
	}

	// MARK: UITableViewDelegate

	override open func tableView(_ tableView: UITableView,
	                      editActionsForRowAtIndexPath indexPath: IndexPath) -> [AnyObject]? {
		let editRowAction = UITableViewRowAction(style: .normal,
			title: "Edit", handler: { action, indexPath in
				if let comment = self.rows[self.sections[indexPath.section]]?[indexPath.row] as? Comment {
					self.userAction(name: "edit-comment", sender: comment)
				}

				tableView.setEditing(false, animated: true)
		})

		let deleteRowAction = UITableViewRowAction(style: .destructive,
			title: "Delete", handler: { action, indexPath in
				let cell = tableView.cellForRow(at: indexPath) as? CommentTableViewCell_default
				cell?.commentDisplayScreenlet?.deleteComment()
				tableView.setEditing(false, animated: true)
		})
		deleteRowAction.backgroundColor = DefaultResources.OddColorBackground

		return [deleteRowAction, editRowAction]
	}
}
