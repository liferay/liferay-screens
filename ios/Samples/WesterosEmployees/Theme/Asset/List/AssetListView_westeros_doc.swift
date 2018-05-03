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

class AssetListView_westeros_doc: AssetListView_westeros {

	let DocumentCellId = "documentCell"

	// MARK: BaseScreenletView

	override func doGetCellId(row: Int, object: AnyObject?) -> String {
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
		let nib = Bundle.nibInBundles(
			name: "DocumentationTableViewCell_westeros", currentClass: type(of: self))

		if let documentNib = nib {
			tableView?.register(documentNib, forCellReuseIdentifier: DocumentCellId)
		}
	}

	override func doFillLoadedCell(row: Int, cell: UITableViewCell, object: AnyObject) {
		guard let docCell = cell as? DocumentationTableViewCell_westeros, let entry = object as? Asset else {
			return
		}

		docCell.backgroundColor = .clear
		docCell.accessoryType = .disclosureIndicator
		docCell.accessoryView = nil

		docCell.title = entry.title
		docCell.fileDescription = entry.description

		let fileEntry = FileEntry(attributes: entry.attributes)
		docCell.fileExtension = fileEntry.fileExtension!
	}
}
