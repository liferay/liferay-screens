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


class AssetListScreenletViewController: UIViewController, AssetListScreenletDelegate,
	UIPickerViewDelegate, UIPickerViewDataSource {

	@IBOutlet var screenlet: AssetListScreenlet?
	@IBOutlet var picker: UIPickerView?

	var selectedAsset = AssetClasses.getClassNameId(AssetClassNameKey_Group)
    
	var selectAssetEntry: Asset?

	var pickerData:[AssetClassEntry] = []

	@IBAction func changeAssetAction(sender: AnyObject) {
		showPicker(false, animated:true)
		screenlet?.classNameId = selectedAsset!
		screenlet?.loadList()
	}

	@IBAction func openAssetPickerAction(sender: AnyObject) {
		showPicker(true, animated:true)
	}

	@IBAction func refreshControlChanged(sender: UISwitch) {
		self.screenlet?.refreshControl = sender.on
	}

	func pickerView(pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
		selectedAsset = pickerData[row].classNameId
		print("[ASSET TYPE] -> Selected \(selectedAsset!)\n")
	}

    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
		return 1
	}

    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
		return pickerData.count
	}

	func pickerView(pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
		return NSAttributedString(string: pickerData[row].className)
	}

	override func viewDidLoad() {
		super.viewDidLoad()

		// demo.liferay.com identifiers are not the standard ones

		AssetClasses.set(AssetClassNameKey_Group, newId: 20001)
		AssetClasses.set(AssetClassNameKey_Layout, newId: 20002)
		AssetClasses.set(AssetClassNameKey_Organization, newId: 20003)
		AssetClasses.set(AssetClassNameKey_User, newId: 20005)
		AssetClasses.set(AssetClassNameKey_UserGroup, newId: 20006)
		AssetClasses.set(AssetClassNameKey_BlogsEntry, newId: 20007)
		AssetClasses.set(AssetClassNameKey_BookmarksEntry, newId: 20392)
		AssetClasses.set(AssetClassNameKey_BookmarksFolder, newId: 20393)
		AssetClasses.set(AssetClassNameKey_DLFileEntry, newId: 20008)
		AssetClasses.set(AssetClassNameKey_DLFolder, newId: 20009)
		AssetClasses.set(AssetClassNameKey_DLFileEntryMetadata, newId: 20086)
		AssetClasses.set(AssetClassNameKey_DLFileEntryType, newId: 20087)
		AssetClasses.set(AssetClassNameKey_DLFileRank, newId: 20088)
		AssetClasses.set(AssetClassNameKey_DLFileShortcut, newId: 20089)
		AssetClasses.set(AssetClassNameKey_DLFileVersion, newId: 20090)
		AssetClasses.set(AssetClassNameKey_DDLRecord, newId: 20422)
		AssetClasses.set(AssetClassNameKey_DDLRecordSet, newId: 20423)
		AssetClasses.set(AssetClassNameKey_DDLRecordVersion, newId: 20424)
		AssetClasses.set(AssetClassNameKey_JournalArticle, newId: 20109)
		AssetClasses.set(AssetClassNameKey_JournalArticleImage, newId: 20481)
		AssetClasses.set(AssetClassNameKey_JournalFolder, newId: 20485)
		AssetClasses.set(AssetClassNameKey_MBMessage, newId: 20010)
		AssetClasses.set(AssetClassNameKey_MBThread, newId: 20011)
		AssetClasses.set(AssetClassNameKey_MBCategory, newId: 20098)
		AssetClasses.set(AssetClassNameKey_MBDiscussion, newId: 20099)
		AssetClasses.set(AssetClassNameKey_MBMailingList, newId: 20100)
		AssetClasses.set(AssetClassNameKey_WikiPage, newId: 20374)
		AssetClasses.set(AssetClassNameKey_WikiPageResource, newId: 20375)
		AssetClasses.set(AssetClassNameKey_WikiNode, newId: 20373)

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

		for assetKey in assetClasses {
			pickerData.append(
				AssetClassEntry(
					AssetClasses.getClassNameId(assetKey)!,
					AssetClasses.getClassName(assetKey)!))
		}
		
		self.screenlet?.delegate = self
	}
	
	func screenlet(screenlet: AssetListScreenlet, onAssetListResponse assets: [Asset]) {
		print("DELEGATE: onAssetListResponse called -> \(assets.count)\n");
		for e in assets {
			print("     -> \(e.entryId)\n");
		}
	}

	func screenlet(screenlet: AssetListScreenlet,
			onAssetListError error: NSError) {
		print("DELEGATE: onAssetListError called -> \(error)\n");
	}
	
	func screenlet(screenlet: AssetListScreenlet, onAssetSelected asset: Asset) {
		print("DELEGATE: onAssetSelected called -> \(asset.entryId)\n");
		selectAssetEntry = asset
		performSegueWithIdentifier("assetDisplay", sender: self)
	}

	func showPicker(show:Bool, animated:Bool) {
		UIView.animateWithDuration(animated ? 0.5 : 0) {
			self.picker!.superview!.frame = CGRectMake(
				self.picker!.superview!.frame.origin.x,
				show ? 0 : self.view.frame.size.height,
				self.picker!.superview!.frame.size.width,
				self.picker!.superview!.frame.size.height)
		}
	}
	
	override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
		if segue.identifier == "assetDisplay" {
			let viewController = segue.destinationViewController as? AssetDisplayViewController
			viewController?.entryId = selectAssetEntry!.entryId
		}
	}
}
