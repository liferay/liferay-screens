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


class AssetTypesViewController: UITableViewController {
	
	let assetClasses = [
		AssetClassNameKey_BlogsEntry,
		AssetClassNameKey_BookmarksEntry,
		AssetClassNameKey_BookmarksFolder,
		AssetClassNameKey_CalendarBooking,
		AssetClassNameKey_DDLRecord,
		AssetClassNameKey_DDLFormRecord,
		AssetClassNameKey_DLFileEntry,
		AssetClassNameKey_DLFolder,
		AssetClassNameKey_JournalArticle,
		AssetClassNameKey_JournalFolder,
		AssetClassNameKey_Layout,
		AssetClassNameKey_LayoutRevision,
		AssetClassNameKey_Organization,
		AssetClassNameKey_Site,
		AssetClassNameKey_User,
		AssetClassNameKey_MBCategory,
		AssetClassNameKey_MBDiscussion,
		AssetClassNameKey_MBMessage,
		AssetClassNameKey_MicroblogsEntry,
		AssetClassNameKey_WikiPage
	]
	
	let AssetListSegue = "assetList"
	
	var selectedAssetType: String = ""

	override func viewDidLoad() {
		super.viewDidLoad()

		AssetClasses.set(AssetClassNameKey_BlogsEntry, newId: 20011)
		AssetClasses.set(AssetClassNameKey_BookmarksEntry, newId: 28401)
		AssetClasses.set(AssetClassNameKey_BookmarksFolder, newId: 28402)
		AssetClasses.set(AssetClassNameKey_CalendarBooking, newId: 27702)
		AssetClasses.set(AssetClassNameKey_DDLRecord, newId: 29501)
		AssetClasses.set(AssetClassNameKey_DDLFormRecord, newId: 31330)
		AssetClasses.set(AssetClassNameKey_DLFileEntry, newId: 20015)
		AssetClasses.set(AssetClassNameKey_DLFolder, newId: 20021)
		AssetClasses.set(AssetClassNameKey_JournalArticle, newId: 29634)
		AssetClasses.set(AssetClassNameKey_JournalFolder, newId: 29639)
		AssetClasses.set(AssetClassNameKey_Layout, newId: 20047)
		AssetClasses.set(AssetClassNameKey_LayoutRevision, newId: 20051)
		AssetClasses.set(AssetClassNameKey_Organization, newId: 20059)
		AssetClasses.set(AssetClassNameKey_Site, newId: 20045)
		AssetClasses.set(AssetClassNameKey_User, newId: 20087)
		AssetClasses.set(AssetClassNameKey_MBCategory, newId: 20029)
		AssetClasses.set(AssetClassNameKey_MBMessage, newId: 20032)
		AssetClasses.set(AssetClassNameKey_MicroblogsEntry, newId: 28701)
		AssetClasses.set(AssetClassNameKey_WikiPage, newId: 28802)

		setTranslations()
	}
	
	override func viewWillAppear(_ animated: Bool) {
		super.viewWillAppear(animated)
		
		LiferayServerContext.groupId = 20143
	}
	
	override func viewWillDisappear(_ animated: Bool) {
		super.viewWillDisappear(animated)
		
		self.title = nil
	}
	
	
	//MARK: UITableViewDataSource
	
	override func numberOfSections(in tableView: UITableView) -> Int {
		return 1
	}
	
	override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return assetClasses.count
	}
	
	override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		let cell = UITableViewCell(style: .default, reuseIdentifier: "default-cell");
		
		cell.textLabel?.text = assetClasses[indexPath.row]
		
		return cell
	}
	
	
	//MARK: UITableViewDelegate
	
	override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
		selectedAssetType = assetClasses[indexPath.row]
		performSegue(withIdentifier: AssetListSegue, sender: self)
	}
	
	
	//MARK: UIViewController
	
	override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
		if segue.identifier == AssetListSegue {
			let viewController = segue.destination as? AssetListScreenletViewController
			if selectedAssetType == AssetClassNameKey_Organization ||
				selectedAssetType == AssetClassNameKey_Site ||
				selectedAssetType == AssetClassNameKey_User {
				LiferayServerContext.groupId = 20152
			}

			viewController?.assetType = selectedAssetType
		}
	}


	//MARK: Private methods

	fileprivate func setTranslations() {
		self.title = NSLocalizedString("assetlist-choose-asset-type", comment: "Choose an Asset type")
	}
}
