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
		AssetClassNameKey_Group,
		AssetClassNameKey_Layout,
		AssetClassNameKey_Organization,
		AssetClassNameKey_User,
		AssetClassNameKey_UserGroup,
		AssetClassNameKey_BlogsEntry,
		AssetClassNameKey_BookmarksEntry,
		AssetClassNameKey_BookmarksFolder,
		AssetClassNameKey_DLFileEntry,
		AssetClassNameKey_DLFileEntryMetadata,
		AssetClassNameKey_DLFileEntryType,
		AssetClassNameKey_DLFileRank,
		AssetClassNameKey_DLFileShortcut,
		AssetClassNameKey_DLFileVersion,
		AssetClassNameKey_DDLRecord,
		AssetClassNameKey_DDLRecordSet,
		AssetClassNameKey_JournalArticle,
		AssetClassNameKey_JournalFolder,
		AssetClassNameKey_MBMessage,
		AssetClassNameKey_MBThread,
		AssetClassNameKey_MBCategory,
		AssetClassNameKey_MBDiscussion,
		AssetClassNameKey_MBMailingList,
		AssetClassNameKey_WikiPage,
		AssetClassNameKey_WikiPageResource,
		AssetClassNameKey_WikiNode
	]
	
	let AssetListSegue = "assetList"
	
	var selectedAssetType: String = ""

	override func viewDidLoad() {
		super.viewDidLoad()

		AssetClasses.set(AssetClassNameKey_Group, newId: 20045)
		AssetClasses.set(AssetClassNameKey_Layout, newId: 20047)
		AssetClasses.set(AssetClassNameKey_Organization, newId: 20059)
		AssetClasses.set(AssetClassNameKey_User, newId: 20087)
		AssetClasses.set(AssetClassNameKey_UserGroup, newId: 20088)
		AssetClasses.set(AssetClassNameKey_BlogsEntry, newId: 20011)
		AssetClasses.set(AssetClassNameKey_BookmarksEntry, newId: 27301)
		AssetClasses.set(AssetClassNameKey_BookmarksFolder, newId: 27302)
		AssetClasses.set(AssetClassNameKey_DLFileEntry, newId: 20015)
		AssetClasses.set(AssetClassNameKey_DLFolder, newId: 20021)
		AssetClasses.set(AssetClassNameKey_DLFileEntryMetadata, newId: 20016)
		AssetClasses.set(AssetClassNameKey_DLFileEntryType, newId: 20017)
		AssetClasses.set(AssetClassNameKey_DLFileRank, newId: 20018)
		AssetClasses.set(AssetClassNameKey_DLFileShortcut, newId: 20019)
		AssetClasses.set(AssetClassNameKey_DLFileVersion, newId: 20020)
		AssetClasses.set(AssetClassNameKey_DDLRecord, newId: 29413)
		AssetClasses.set(AssetClassNameKey_DDLRecordSet, newId: 29414)
		AssetClasses.set(AssetClassNameKey_DDLRecordVersion, newId: 29415)
		AssetClasses.set(AssetClassNameKey_JournalArticle, newId: 29591)
		AssetClasses.set(AssetClassNameKey_JournalArticleImage, newId: 29592)
		AssetClasses.set(AssetClassNameKey_JournalFolder, newId: 29596)
		AssetClasses.set(AssetClassNameKey_MBMessage, newId: 20032)
		AssetClasses.set(AssetClassNameKey_MBThread, newId: 20034)
		AssetClasses.set(AssetClassNameKey_MBCategory, newId: 20029)
		AssetClasses.set(AssetClassNameKey_MBDiscussion, newId: 20030)
		AssetClasses.set(AssetClassNameKey_MBMailingList, newId: 20031)
		AssetClasses.set(AssetClassNameKey_WikiPage, newId: 27802)
		AssetClasses.set(AssetClassNameKey_WikiPageResource, newId: 27803)
		AssetClasses.set(AssetClassNameKey_WikiNode, newId: 27801)
	}
	
	override func viewWillAppear(animated: Bool) {
		super.viewWillAppear(animated)
		
		self.title = NSLocalizedString("choose-asset-type", comment: "Choose an asset type")
	}
	
	override func viewWillDisappear(animated: Bool) {
		super.viewWillDisappear(animated)
		
		self.title = nil
	}
	
	
	//MARK: UITableViewDataSource
	
	override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
		return 1
	}
	
	override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return assetClasses.count
	}
	
	override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
		let cell = UITableViewCell(style: .Default, reuseIdentifier: "default-cell");
		
		cell.textLabel?.text = assetClasses[indexPath.row]
		
		return cell
	}
	
	
	//MARK: UITableViewDelegate
	
	override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
		selectedAssetType = assetClasses[indexPath.row]
		performSegueWithIdentifier(AssetListSegue, sender: self)
	}
	
	
	//MARK: UIViewController
	
	override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
		if segue.identifier == AssetListSegue {
			let viewController = segue.destinationViewController as? AssetListScreenletViewController
			viewController?.assetType = selectedAssetType
		}
	}
}
