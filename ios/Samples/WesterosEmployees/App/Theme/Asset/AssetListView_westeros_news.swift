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

class AssetListView_westeros_news: AssetListView_default {

	let BlogCellId = "blogsEntryCell"
	let DocumentCellId = "documentCell"

	static let MinimumTimeDifference: Double = 5000

	//MARK: BaseScreenletView

	override func createProgressPresenter() -> ProgressPresenter {
		return WesterosProgressPresenter()
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

	override func doGetCellId(row row: Int, object: AnyObject?) -> String {
		if let asset = object as? Asset {
			if asset.classNameId == AssetClasses.getClassNameId(AssetClassNameKey_BlogsEntry) {
				return BlogCellId
			} else {
				return DocumentCellId
			}
		}

		return super.doGetCellId(row: row, object: object)
	}

	override func onCreated() {
		super.onCreated()

		tableView?.rowHeight = 100
	}

	override func doRegisterCellNibs() {
		let docNib = NSBundle.nibInBundles(
			name: "DocumentationTableViewCell_westeros_news", currentClass: self.dynamicType)

		if let docNib = docNib {
			tableView?.registerNib(docNib, forCellReuseIdentifier: DocumentCellId)
		}

		let blogNib = NSBundle.nibInBundles(
			name: "BlogsEntryTableViewCell_westeros_news", currentClass: self.dynamicType)

		if let blogNib = blogNib {
			tableView?.registerNib(blogNib, forCellReuseIdentifier: BlogCellId)
		}
	}

	override func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
		if let asset = object as? Asset {

			cell.backgroundColor = UIColor.clearColor()
			cell.accessoryType = .DisclosureIndicator
			cell.accessoryView = nil

			if asset.classNameId == AssetClasses.getClassNameId(AssetClassNameKey_BlogsEntry) {
				guard let blogCell = cell as? BlogsEntryTableViewCell_westeros else { return }

				//Load blog information
				let blog = BlogsEntry(attributes: asset.attributes)
				blogCell.imageEntryId = blog.coverImageFileEntryId
				blogCell.title = blog.title
				blogCell.subtitle = blog.modifiedDate.timeIntervalSinceDate(blog.createDate)
					< AssetListView_westeros_news.MinimumTimeDifference ? "New blog" : "Updated blog"
			} else {
				guard let docCell = cell as? DocumentationTableViewCell_westeros else { return }

				//Load blog information
				let document = FileEntry(attributes: asset.attributes)
				docCell.title = document.modifiedDate.timeIntervalSinceDate(document.createDate)
					< AssetListView_westeros_news.MinimumTimeDifference ? "New file" : "Updated file"
				docCell.fileDescription = document.title
				docCell.fileExtension = document.fileExtension!
			}
		}
	}
}
