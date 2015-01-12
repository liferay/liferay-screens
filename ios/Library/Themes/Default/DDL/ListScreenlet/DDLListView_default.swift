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


public class DDLListView_default: BaseListTableView, DDLListData {

	// DDLListData

	public var labelFields: [String] = []

	override internal func doFillLoadedCell(#row: Int, cell: UITableViewCell, object:AnyObject) {
		if let record = object as? DDLRecord {
			cell.textLabel?.text = composeLabel(record)
			cell.accessoryType = .DisclosureIndicator
			cell.accessoryView = nil
		}
	}

	override internal func doFillInProgressCell(#row: Int, cell: UITableViewCell) {
		cell.textLabel?.text = "..."
		cell.accessoryType = .None
		let image = UIImage(named: "default-hourglass")!
		cell.accessoryView = UIImageView(image: image)
		cell.accessoryView!.frame = CGRectMake(0, 0, image.size.width, image.size.height)
	}


	//MARK: DDLFormTableView 

	override internal func onCreated() {
		super.onCreated()

		BaseScreenlet.setHUDCustomColor(DefaultThemeBasicBlue)
	}

	internal func composeLabel(record: DDLRecord) -> String {
		var result: String = ""

		for labelField in labelFields {
			if let field = record[labelField] {
				if let currentValue = field.currentValueAsLabel {
					if currentValue != "" {
						result += currentValue
						result += " "
					}
				}
			}
		}

		result.removeAtIndex(result.endIndex.predecessor())

		return result
	}


}
