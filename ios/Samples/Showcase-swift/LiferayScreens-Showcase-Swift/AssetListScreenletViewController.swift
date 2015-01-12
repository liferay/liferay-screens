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

	var selectedAsset = AssetClassNameId.Group

	var pickerData:[(name:String, assetId:AssetClassNameId)] = []

	@IBAction func changeAssetAction(sender: AnyObject) {
		showPicker(false, animated:true)
		screenlet?.classNameId = selectedAsset.rawValue
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
		println("[ASSET TYPE] -> Selected \(selectedAsset.rawValue)")
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

		pickerData.append(name:"Group", assetId:.Group)
		pickerData.append(name:"Layout", assetId:.Layout)
		pickerData.append(name:"Organization", assetId:.Organization)
		pickerData.append(name:"User", assetId:.User)
		pickerData.append(name:"UserGroup", assetId:.UserGroup)
		pickerData.append(name:"BlogsEntry", assetId:.BlogsEntry)
		pickerData.append(name:"BookmarksEntry", assetId:.BookmarksEntry)
		pickerData.append(name:"BookmarksFolder", assetId:.BookmarksFolder)
		pickerData.append(name:"CalendarEvent", assetId:.CalendarEvent)
		pickerData.append(name:"DLFileEntry", assetId:.DLFileEntry)
		pickerData.append(name:"DLFileEntryMetadata", assetId:.DLFileEntryMetadata)
		pickerData.append(name:"DLFileEntryType", assetId:.DLFileEntryType)
		pickerData.append(name:"DLFileRank", assetId:.DLFileRank)
		pickerData.append(name:"DLFileShortcut", assetId:.DLFileShortcut)
		pickerData.append(name:"DLFileVersion", assetId:.DLFileVersion)
		pickerData.append(name:"DDLRecord", assetId:.DDLRecord)
		pickerData.append(name:"DDLRecordSet", assetId:.DDLRecordSet)
		pickerData.append(name:"JournalArticle", assetId:.JournalArticle)
		pickerData.append(name:"JournalFolder", assetId:.JournalFolder)
		pickerData.append(name:"MBMessage", assetId:.MBMessage)
		pickerData.append(name:"MBThread", assetId:.MBThread)
		pickerData.append(name:"MBCategory", assetId:.MBCategory)
		pickerData.append(name:"MBDiscussion", assetId:.MBDiscussion)
		pickerData.append(name:"MBMailingList", assetId:.MBMailingList)
		pickerData.append(name:"WikiPage", assetId:.WikiPage)
		pickerData.append(name:"WikiPageResource", assetId:.WikiPageResource)
		pickerData.append(name:"WikiNode", assetId:.WikiNode)

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
