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

class AssetListView_westeros_custom: AssetListView_westeros {

	let DocumentCellId = "documentCell"

	//MARK: BaseScreenletView

	override func doGetCellId(row row: Int, object: AnyObject?) -> String {
		if let _ = object {
			return DocumentCellId
		}

		return super.doGetCellId(row: row, object: object)
	}

	override func onCreated() {
		super.onCreated()

		tableView?.rowHeight = UITableViewAutomaticDimension
		tableView?.estimatedRowHeight = 100
	}

	override func doRegisterCellNibs() {
		let nib = NSBundle.nibInBundles(
			name: "DocumentationTableViewCell_westeros", currentClass: self.dynamicType)

		if let documentNib = nib {
			tableView?.registerNib(documentNib, forCellReuseIdentifier: DocumentCellId)
		}
	}

	override func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
		guard let docCell = cell as? DocumentationTableViewCell_westeros, entry = object as? Asset else {
			return
		}

		docCell.backgroundColor = UIColor.clearColor()
		docCell.accessoryType = .DisclosureIndicator
		docCell.accessoryView = nil

		docCell.title = entry.title
		docCell.fileDescription = entry.description

		let fileEntry = FileEntry(attributes: entry.attributes)
		docCell.fileExtension = fileEntry.fileExtension!
	}

	override func doFillInProgressCell(row row: Int, cell: UITableViewCell) {
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
