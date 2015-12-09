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


public class AssetListView_default: AssetListTableView {

	//MARK: BaseScreenletView

	override public func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}

	override public func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
		if let entry = object as? AssetListScreenletEntry {
			cell.textLabel?.text = entry.title
			cell.accessoryType = .DisclosureIndicator
			cell.accessoryView = nil
		}
	}

	override public func doFillInProgressCell(row row: Int, cell: UITableViewCell) {
		cell.textLabel?.text = "..."
		cell.accessoryType = .None

		if let image = NSBundle.imageInBundles(
				name: "default-hourglass",
				currentClass: self.dynamicType) {

			cell.accessoryView = UIImageView(image: image)
			cell.accessoryView?.frame = CGRectMake(0, 0, image.size.width, image.size.height)
		}
	}

}
