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


public class BaseListTableView: BaseListView, UITableViewDataSource, UITableViewDelegate {

	@IBOutlet internal var tableView: UITableView?


	// MARK: AssetListView

	override internal func onChangedRows() {
		super.onChangedRows()

		tableView!.reloadData()
	}


	//MARK: UITableViewDataSource

	public func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return rowCount
	}

	public func tableView(tableView: UITableView,
			cellForRowAtIndexPath
			indexPath: NSIndexPath)
			-> UITableViewCell {

		let cell = doDequeueReusableCell(row: indexPath.row)

		if let row:AnyObject = rows[indexPath.row] {
			doFillLoadedCell(row: indexPath.row, cell: cell, object: row)
		}
		else {
			doFillInProgressCell(row: indexPath.row, cell: cell)

			fetchPageForRow?(indexPath.row)
		}

		return cell
	}

	public func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
		tableView.deselectRowAtIndexPath(indexPath, animated: false)

		if let row:AnyObject = rows[indexPath.row] {
			onSelectedRowClosure?(row)
		}
	}


	//MARK: Internal methods

	internal func doDequeueReusableCell(#row: Int) -> UITableViewCell {
		var result = tableView!.dequeueReusableCellWithIdentifier("listCell") as? UITableViewCell

		if result == nil {
			result = UITableViewCell(style: .Default, reuseIdentifier: "listCell")
		}

		return result!
	}

	internal func doFillLoadedCell(#row: Int, cell: UITableViewCell, object:AnyObject) {
	}

	internal func doFillInProgressCell(#row: Int, cell: UITableViewCell) {
	}

}
