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

class AssetListView_westeros_blog: AssetListView_westeros {

	let BlogCellId = "blogsEntryCell"

	fileprivate let dateFormatter: DateFormatter = {
		let dateFormatter = DateFormatter()
		dateFormatter.dateStyle = DateFormatter.Style.long
		dateFormatter.locale = Locale(
			identifier: NSLocale.currentLocaleString)
		return dateFormatter
	}()

	// MARK: BaseScreenletView

	override func doGetCellId(row: Int, object: AnyObject?) -> String {
		if let _ = object {
			return BlogCellId
		}

		return super.doGetCellId(row: row, object: object)
	}

	override func onCreated() {
		super.onCreated()

		tableView?.rowHeight = UITableViewAutomaticDimension
		tableView?.estimatedRowHeight = 100
	}

	override func doRegisterCellNibs() {
		let nib = Bundle.nibInBundles(
			name: "BlogsEntryTableViewCell_westeros", currentClass: type(of: self))

		if let blogsEntryNib = nib {
			tableView?.register(blogsEntryNib, forCellReuseIdentifier: BlogCellId)
		}
	}

	override func doFillLoadedCell(row: Int, cell: UITableViewCell, object: AnyObject) {
		guard let blogsCell = cell as? BlogsEntryTableViewCell_westeros, let entry = object as? Asset else {
			return
		}

		blogsCell.backgroundColor = UIColor.clear
		blogsCell.accessoryType = .disclosureIndicator
		blogsCell.accessoryView = nil

		let blogsEntry = BlogsEntry(attributes: entry.attributes)

		blogsCell.title = blogsEntry.title
		blogsCell.imageEntryId = blogsEntry.coverImageFileEntryId

		if let date = blogsEntry.displayDate {
			let dateString = dateFormatter.string(from: date)
			blogsCell.subtitle = "\(dateString) · \(blogsEntry.userName)"
		}
		else {
			blogsCell.subtitle = blogsEntry.userName
		}
	}
}
