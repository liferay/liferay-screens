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


public class WebContentListView_book: WebContentListView_default {

	//MARK: BaseScreenletView

	override public func doRegisterCellNibs() {
		// register html cell
		let htmlNib = UINib(nibName: "HtmlTableViewCell", bundle: nil)

		tableView?.registerNib(htmlNib, forCellReuseIdentifier: "htmlCell")

		// register book cell
		let bookNib = UINib(nibName: "BookTableViewCell", bundle: nil)

		tableView?.registerNib(bookNib, forCellReuseIdentifier: "bookCell")
	}

	override public func doCreateCell(cellId: String) -> UITableViewCell {
		switch cellId {
		case "bookCell":
			return BookTableViewCell(style: .Default, reuseIdentifier: cellId)
		default:
			return HtmlTableViewCell(style: .Default, reuseIdentifier: cellId)
		}
	}

	override public func doGetCellId(row row: Int, object: AnyObject?) -> String {
		if let entry = object as? WebContent {
			return (entry.structuredRecord == nil) ? "htmlCell" : "bookCell"
		}

		return super.doGetCellId(row: row, object: object)
	}

	override public func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
		if let entry = object as? WebContent {
			if let record = entry.structuredRecord,
					bookCell = cell as? BookTableViewCell {
				let title = entry.title
				let author = record.fieldBy(name: "author")?.currentValueAsLabel ?? ""
				bookCell.loadData(title, author: author)
			}
			else if let htmlCell = cell as? HtmlTableViewCell,
						html = entry.html {
				htmlCell.loadData(html)
			}
			cell.accessoryType = .None
			cell.accessoryView = nil
		}
	}

	public func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {

		let rows = rowsForSectionIndex(indexPath.section)
		
		if let entry = rows[indexPath.row] as? WebContent {
			return (entry.structuredRecord == nil) ? 50 : 75
		}

		return 50
	}


}
