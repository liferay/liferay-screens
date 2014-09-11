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

public class AssetListTableView: AssetListView, UITableViewDataSource, UITableViewDelegate {

	@IBOutlet var tableView: UITableView?

	override internal func onChangedEntries() {
		super.onChangedEntries()

		tableView!.reloadData()
	}


	// MARK: UITableViewDataSource

	public func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return entries.count
	}

	public func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {

		var cell = tableView.dequeueReusableCellWithIdentifier("assetCell") as? UITableViewCell

		if cell == nil {
			cell = UITableViewCell(style: .Default, reuseIdentifier: "assetCell")
		}

		cell!.textLabel?.text = entries[indexPath.row].title

		return cell!
	}

}