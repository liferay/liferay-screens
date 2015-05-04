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
import MGSwipeTableCell


public class DDLListView_westeros: DDLListView_default, MGSwipeTableCellDelegate {

	var onViewAction: (DDLRecord -> Void)?
	var onEditAction: (DDLRecord -> Void)?

	override public func doRegisterCellNib(#id: String) {
		let nib = UINib(
				nibName: "DDLListViewCell_westeros",
				bundle: NSBundle(forClass: self.dynamicType))

		self.tableView!.registerNib(nib, forCellReuseIdentifier: id)
	}

	override public func doFillLoadedCell(#row: Int, cell: UITableViewCell, object:AnyObject) {
		if let record = object as? DDLRecord,
			issueCell = cell as? DDLListViewCell_westeros {

			issueCell.record = record

			let viewButton = MGSwipeButton(
					title: "View",
					backgroundColor: UIColor(
							red: 251.0/255.0,
							green: 179.0/255.0,
							blue: 81.0/255.0,
							alpha: 1.0))

			let editButton = MGSwipeButton(
					title: "Edit",
					backgroundColor: UIColor(
							red: 79.0/255.0,
							green: 146.0/255.0,
							blue: 184.0/255.0,
							alpha: 1.0))

			issueCell.rightButtons = [viewButton, editButton]
			issueCell.rightSwipeSettings.transition = MGSwipeTransition.Rotate3D

			issueCell.delegate = self
		}
	}

	public func swipeTableCell(
			cell: MGSwipeTableCell!,
			tappedButtonAtIndex index: Int,
			direction: MGSwipeDirection,
			fromExpansion: Bool)
			-> Bool {

		if let issueCell = cell as? DDLListViewCell_westeros {
			if index == 0 {
				onViewAction?(issueCell.record!)
			}
			else {
				onEditAction?(issueCell.record!)
			}
		}

		return true
	}


}
