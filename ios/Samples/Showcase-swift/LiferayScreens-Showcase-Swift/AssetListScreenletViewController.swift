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

class AssetListScreenletViewController: UIViewController, AssetListScreenletDelegate,
	UIPickerViewDelegate, UIPickerViewDataSource {

	@IBOutlet var screenlet: AssetListScreenlet?
	@IBOutlet var picker: UIPickerView?

	var selectedAsset = AssetListScreenlet.AssetClassNameId.Group

	var pickerData:[(name:String, assetId:AssetListScreenlet.AssetClassNameId)] = []

	@IBAction func changeAssetAction(sender: AnyObject) {
		showPicker(false, animated:true)
		screenlet?.classNameId = selectedAsset.rawValue
		screenlet?.loadList()
	}

	@IBAction func openAssetPickerAction(sender: AnyObject) {
		showPicker(true, animated:true)
	}

	func pickerView(pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
		selectedAsset = pickerData[row].assetId
	}

    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
		return 1
	}

    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
		return pickerData.count
	}

	func pickerView(pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String! {
		return pickerData[row].name
	}

	override func viewDidLoad() {
		super.viewDidLoad()

		pickerData.append(name:"Group", assetId:AssetListScreenlet.AssetClassNameId.Group)
		pickerData.append(name:"Layout", assetId:AssetListScreenlet.AssetClassNameId.Layout)
		pickerData.append(name:"Organization", assetId:AssetListScreenlet.AssetClassNameId.Organization)
		pickerData.append(name:"User", assetId:AssetListScreenlet.AssetClassNameId.User)
		pickerData.append(name:"UserGroup", assetId:AssetListScreenlet.AssetClassNameId.UserGroup)
		pickerData.append(name:"BlogsEntry", assetId:AssetListScreenlet.AssetClassNameId.BlogsEntry)
		pickerData.append(name:"BookmarksEntry", assetId:AssetListScreenlet.AssetClassNameId.BookmarksEntry)
		pickerData.append(name:"BookmarksFolder", assetId:AssetListScreenlet.AssetClassNameId.BookmarksFolder)
		pickerData.append(name:"CalendarEvent", assetId:AssetListScreenlet.AssetClassNameId.CalendarEvent)
		pickerData.append(name:"DLFileEntry", assetId:AssetListScreenlet.AssetClassNameId.DLFileEntry)
		pickerData.append(name:"DLFileEntryMetadata", assetId:AssetListScreenlet.AssetClassNameId.DLFileEntryMetadata)
		pickerData.append(name:"DLFileEntryType", assetId:AssetListScreenlet.AssetClassNameId.DLFileEntryType)
		pickerData.append(name:"DLFileRank", assetId:AssetListScreenlet.AssetClassNameId.DLFileRank)
		pickerData.append(name:"DLFileShortcut", assetId:AssetListScreenlet.AssetClassNameId.DLFileShortcut)
		pickerData.append(name:"DLFileVersion", assetId:AssetListScreenlet.AssetClassNameId.DLFileVersion)
		pickerData.append(name:"DDLRecord", assetId:AssetListScreenlet.AssetClassNameId.DDLRecord)
		pickerData.append(name:"DDLRecordSet", assetId:AssetListScreenlet.AssetClassNameId.DDLRecordSet)
		pickerData.append(name:"JournalArticle", assetId:AssetListScreenlet.AssetClassNameId.JournalArticle)
		pickerData.append(name:"JournalFolder", assetId:AssetListScreenlet.AssetClassNameId.JournalFolder)
		pickerData.append(name:"MBMessage", assetId:AssetListScreenlet.AssetClassNameId.MBMessage)
		pickerData.append(name:"MBThread", assetId:AssetListScreenlet.AssetClassNameId.MBThread)
		pickerData.append(name:"MBCategory", assetId:AssetListScreenlet.AssetClassNameId.MBCategory)
		pickerData.append(name:"MBDiscussion", assetId:AssetListScreenlet.AssetClassNameId.MBDiscussion)
		pickerData.append(name:"MBMailingList", assetId:AssetListScreenlet.AssetClassNameId.MBMailingList)
		pickerData.append(name:"WikiPage", assetId:AssetListScreenlet.AssetClassNameId.WikiPage)
		pickerData.append(name:"WikiPageResource", assetId:AssetListScreenlet.AssetClassNameId.WikiPageResource)
		pickerData.append(name:"WikiNode", assetId:AssetListScreenlet.AssetClassNameId.WikiNode)

		self.screenlet?.delegate = self
	}

	func onAssetListResponse(entries:[AssetListScreenletEntry]) {
		println("DELEGATE: onAssetListResponse called -> \(entries)");
	}

	func onAssetListError(error: NSError) {
		println("DELEGATE: onAssetListError called -> \(error)");
	}

	func onAssetSelected(entry: AssetListScreenletEntry) {
		println("DELEGATE: onAssetSelected called -> \(entry)");
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

