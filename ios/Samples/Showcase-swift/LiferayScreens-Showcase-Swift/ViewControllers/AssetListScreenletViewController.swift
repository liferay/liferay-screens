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

	var selectedAsset = AssetClassNameIds.get(AssetClassNameIdGroup)

	var pickerData:[(name:String, assetId:Int64)] = []

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
		selectedAsset = pickerData[row].assetId
		print("[ASSET TYPE] -> Selected \(selectedAsset!)\n")
	}

    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
		return 1
	}

    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
		return pickerData.count
	}

	func pickerView(pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
		return NSAttributedString(string: pickerData[row].name)
	}

	override func viewDidLoad() {
		super.viewDidLoad()

		// demo.liferay.com identifiers are not the standard ones

		AssetClassNameIds.set(AssetClassNameIdGroup, newId: 20001)
		AssetClassNameIds.set(AssetClassNameIdLayout, newId: 20002)
		AssetClassNameIds.set(AssetClassNameIdOrganization, newId: 20003)
		AssetClassNameIds.set(AssetClassNameIdUser, newId: 20005)
		AssetClassNameIds.set(AssetClassNameIdUserGroup, newId: 20006)
		AssetClassNameIds.set(AssetClassNameIdBlogsEntry, newId: 20007)
		AssetClassNameIds.set(AssetClassNameIdBookmarksEntry, newId: 20008)
		AssetClassNameIds.set(AssetClassNameIdBookmarksFolder, newId: 20009)
		AssetClassNameIds.set(AssetClassNameIdCalendarEvent, newId: 20010)
		AssetClassNameIds.set(AssetClassNameIdDLFileEntry, newId: 20011)
		AssetClassNameIds.set(AssetClassNameIdDLFolder, newId: 20012)
		AssetClassNameIds.set(AssetClassNameIdDLFileEntryMetadata, newId: 20091)
		AssetClassNameIds.set(AssetClassNameIdDLFileEntryType, newId: 20092)
		AssetClassNameIds.set(AssetClassNameIdDLFileRank, newId: 20093)
		AssetClassNameIds.set(AssetClassNameIdDLFileShortcut, newId: 20094)
		AssetClassNameIds.set(AssetClassNameIdDLFileVersion, newId: 20095)
		AssetClassNameIds.set(AssetClassNameIdDDLRecord, newId: 20097)
		AssetClassNameIds.set(AssetClassNameIdDDLRecordSet, newId: 20098)
		AssetClassNameIds.set(AssetClassNameIdDDLRecordVersion, newId: 20099)
		AssetClassNameIds.set(AssetClassNameIdJournalArticle, newId: 20109)
		AssetClassNameIds.set(AssetClassNameIdJournalArticleImage, newId: 20110)
		AssetClassNameIds.set(AssetClassNameIdJournalFolder, newId: 20013)
		AssetClassNameIds.set(AssetClassNameIdMBMessage, newId: 20014)
		AssetClassNameIds.set(AssetClassNameIdMBThread, newId: 20015)
		AssetClassNameIds.set(AssetClassNameIdMBCategory, newId: 20115)
		AssetClassNameIds.set(AssetClassNameIdMBDiscussion, newId: 20116)
		AssetClassNameIds.set(AssetClassNameIdMBMailingList, newId: 20117)
		AssetClassNameIds.set(AssetClassNameIdWikiPage, newId: 20016)
		AssetClassNameIds.set(AssetClassNameIdWikiPageResource, newId: 20153)
		AssetClassNameIds.set(AssetClassNameIdWikiNode, newId: 20152)

		let assets = [
			AssetClassNameIdGroup,
			AssetClassNameIdLayout,
			AssetClassNameIdOrganization,
			AssetClassNameIdUser,
			AssetClassNameIdUserGroup,
			AssetClassNameIdBlogsEntry,
			AssetClassNameIdBookmarksEntry,
			AssetClassNameIdBookmarksFolder,
			AssetClassNameIdCalendarEvent,
			AssetClassNameIdDLFileEntry,
			AssetClassNameIdDLFileEntryMetadata,
			AssetClassNameIdDLFileEntryType,
			AssetClassNameIdDLFileRank,
			AssetClassNameIdDLFileShortcut,
			AssetClassNameIdDLFileVersion,
			AssetClassNameIdDDLRecord,
			AssetClassNameIdDDLRecordSet,
			AssetClassNameIdJournalArticle,
			AssetClassNameIdJournalFolder,
			AssetClassNameIdMBMessage,
			AssetClassNameIdMBThread,
			AssetClassNameIdMBCategory,
			AssetClassNameIdMBDiscussion,
			AssetClassNameIdMBMailingList,
			AssetClassNameIdWikiPage,
			AssetClassNameIdWikiPageResource,
			AssetClassNameIdWikiNode
		]

		for asset in assets {
			pickerData.append(
				(
					name: asset,
					assetId: AssetClassNameIds.get(asset)!
				))
		}

		self.screenlet?.delegate = self
	}

	func screenlet(screenlet: AssetListScreenlet,
			onAssetListResponseEntries entries: [AssetListScreenletEntry]) {
		print("DELEGATE: onAssetListResponse called -> \(entries)\n");
	}

	func screenlet(screenlet: AssetListScreenlet,
			onAssetListError error: NSError) {
		print("DELEGATE: onAssetListError called -> \(error)\n");
	}

	func screenlet(screenlet: AssetListScreenlet,
			onAssetSelectedEntry entry: AssetListScreenletEntry) {
		print("DELEGATE: onAssetSelected called -> \(entry)\n");
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

}
