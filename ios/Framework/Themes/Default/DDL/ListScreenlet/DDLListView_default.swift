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


public class DDLListView_default: BaseListTableView, DDLListViewModel {

	// DDLListViewModel

	public var labelFields: [String] = []

	override public func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
		if let record = object as? DDLRecord {
			cell.textLabel?.text = composeLabel(record)
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
			cell.accessoryView!.frame = CGRectMake(0, 0, image.size.width, image.size.height)
		}
	}

	public func composeLabel(record: DDLRecord) -> String {
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

		if result.endIndex == result.startIndex {
			print("[ERROR] Can't compose the label for record. It seems the fields specified are not valid\n")
		}
		else {
			result.removeAtIndex(result.endIndex.predecessor())
		}

		return result
	}


	//MARK: DDLFormTableView

	override public func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}

}
