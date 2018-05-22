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

open class WebContentListView_book: WebContentListView_default {

	// MARK: BaseScreenletView

	override open func doRegisterCellNibs() {
		// register html cell
		let htmlNib = UINib(nibName: "HtmlTableViewCell", bundle: nil)

		tableView?.register(htmlNib, forCellReuseIdentifier: "htmlCell")

		// register book cell
		let bookNib = UINib(nibName: "BookTableViewCell", bundle: nil)

		tableView?.register(bookNib, forCellReuseIdentifier: "bookCell")
	}

	override open func doCreateCell(_ cellId: String) -> UITableViewCell {
		switch cellId {
			case "bookCell":
				return BookTableViewCell(style: .default, reuseIdentifier: cellId)
			default:
				return HtmlTableViewCell(style: .default, reuseIdentifier: cellId)
		}
	}

	override open func doGetCellId(row: Int, object: AnyObject?) -> String {
		if let entry = object as? WebContent {
			return (entry.structuredRecord == nil) ? "htmlCell" : "bookCell"
		}

		return super.doGetCellId(row: row, object: object)
	}

	override open func doFillLoadedCell(row: Int, cell: UITableViewCell, object: AnyObject) {
		if let entry = object as? WebContent {
			if let record = entry.structuredRecord,
					let bookCell = cell as? BookTableViewCell {
				let title = entry.title
				let author = record.fieldBy(name: "author")?.currentValueAsLabel ?? ""
				bookCell.loadData(title, author: author)
			}
			else if let htmlCell = cell as? HtmlTableViewCell,
						let html = entry.html {
				htmlCell.loadData(html)
			}
			cell.accessoryType = .none
			cell.accessoryView = nil
		}
	}

	open func tableView(_ tableView: UITableView, heightForRowAtIndexPath indexPath: IndexPath) -> CGFloat {

		let rows = rowsForSectionIndex(indexPath.section)

		if let entry = rows[indexPath.row] as? WebContent {
			return (entry.structuredRecord == nil) ? 50 : 75
		}

		return 50
	}

}
