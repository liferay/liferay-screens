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


@objc public class AssetEntry {

	public var title:String

	public init(title:String) {
		self.title = title
	}

}

@objc protocol AssetListWidgetDelegate {

	optional func onAssetListResponse(entries:[AssetEntry])
	optional func onAssetListError(error: NSError)

	optional func onAssetSelected(entry:AssetEntry)

}

@IBDesignable public class AssetListWidget: BaseWidget {

	@IBInspectable var groupId: Int = 0
	@IBInspectable var classNameId: Int = 0

	public enum AssetClassNameId: Int {
		case WebContent = 10109
	}

	@IBOutlet var delegate: AssetListWidgetDelegate?

	override public func onCreated() {
		assetListView().onSelectedEntryClosure = onSelectedEntry
	}

	public func loadList() -> Bool {
		if LiferayContext.instance.currentSession == nil {
			println("ERROR: No session initialized. Can't load the asset list without session")
			return false
		}

		if classNameId == 0 {
			println("ERROR: ClassNameId is empty. Can't load the asset list without it.")
			return false
		}

		startOperationWithMessage("Loading list...", details:"Wait few seconds...")

		let session = LRSession(session: LiferayContext.instance.currentSession)
		session.callback = self

		let groupIdToUse = (groupId != 0 ? groupId : LiferayContext.instance.groupId) as NSNumber

		let service = LRMobilewidgetsassetService_v62(session: session)

		let entryQueryAttributes = [
				"classNameIds":classNameId,
				"groupIds":groupIdToUse]

		let entryQuery = LRJSONObjectWrapper(
				className: "com.liferay.portlet.asset.service.persistence.AssetEntryQuery",
				jsonObject: entryQueryAttributes)

		var outError: NSError?

		service.getEntriesWithEntryQuery(entryQuery, locale: NSLocale.currentLocaleString(), error: &outError)

		if let error = outError {
			onFailure(error)
			return false
		}

		return true
	}


	// MARK: BaseWidget METHODS

	override internal func onServerError(error: NSError) {
		delegate?.onAssetListError?(error)

		finishOperationWithError(error, message:"Error requesting password!")
	}

	override internal func onServerResult(result: [String:AnyObject]) {
		if let responseArray = (result["result"] ?? nil) as? [[String:AnyObject]] {

			assetListView().entries = responseArray.map() {
				(var attrs) -> AssetEntry in

				let title = (attrs["titleCurrentValue"] ?? "") as String

				return AssetEntry(title: title)
			}

			delegate?.onAssetListResponse?(assetListView().entries)
			finishOperation()
		}
		else {
			finishOperationWithMessage("An error happened", details: "Can't load the list")
		}
	}

	internal func onSelectedEntry(entry:AssetEntry) {
		delegate?.onAssetSelected?(entry)
	}

	internal func assetListView() -> AssetListView {
		return widgetView as AssetListView
	}
}
