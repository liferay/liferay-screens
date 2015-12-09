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


@objc public protocol AssetListScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: AssetListScreenlet,
			onAssetListResponseEntries entries: [AssetListScreenletEntry])

	optional func screenlet(screenlet: AssetListScreenlet,
			onAssetListError error: NSError)

	optional func screenlet(screenlet: AssetListScreenlet,
			onAssetSelectedEntry entry: AssetListScreenletEntry)

}


@objc public class AssetListScreenletEntry : NSObject {

	public let attributes:[String:AnyObject]

	public var title: String {
		return attributes["title"] as! String
	}

	override public var description: String {
		return attributes["description"] as! String
	}

	public var classNameId: Int64 {
		return (attributes["classNameId"] as! NSNumber).longLongValue
	}

	public var classPK: Int64 {
		return (attributes["classPK"] as! NSNumber).longLongValue
	}

	public var groupId: Int64 {
		return (attributes["groupId"] as! NSNumber).longLongValue
	}

	public var companyId: Int64 {
		return (attributes["companyId"] as! NSNumber).longLongValue
	}

	public var entryId: Int64 {
		return (attributes["entryId"] as! NSNumber).longLongValue
	}

	//MARK: Init

	public init(attributes:[String:AnyObject]) {
		self.attributes = attributes
	}

}


@IBDesignable public class AssetListScreenlet: BaseListScreenlet {

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var classNameId: Int64 = 0
	@IBInspectable public var portletItemName: String?
	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue


	public var assetListDelegate: AssetListScreenletDelegate? {
		return delegate as? AssetListScreenletDelegate
	}

	public var customEntryQuery: [String:AnyObject]?


	//MARK: BaseListScreenlet

	override internal func createPageLoadInteractor(
			page page: Int,
			computeRowCount: Bool)
			-> BaseListPageLoadInteractor {

		let interactor = AssetListPageLoadInteractor(
			screenlet: self,
			page: page,
			computeRowCount: computeRowCount,
			groupId: self.groupId,
			classNameId: self.classNameId,
			portletItemName: self.portletItemName)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst
		interactor.customEntryQuery = self.customEntryQuery

		return interactor
	}

	override internal func onLoadPageError(page page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		assetListDelegate?.screenlet?(self, onAssetListError: error)
	}

	override internal func onLoadPageResult(page page: Int, rows: [AnyObject], rowCount: Int) {
		super.onLoadPageResult(page: page, rows: rows, rowCount: rowCount)

		let assetEntries = rows as! [AssetListScreenletEntry]

		assetListDelegate?.screenlet?(self, onAssetListResponseEntries: assetEntries)
	}

	override internal func onSelectedRow(row: AnyObject) {
		assetListDelegate?.screenlet?(self, onAssetSelectedEntry: row as! AssetListScreenletEntry)
	}

}
