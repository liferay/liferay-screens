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

	/// Called when a page of assets is received. Note that this method may be called 
	/// more than once; one call for each page received.
	///
	/// - Parameters:
	///   - screenlet
	///   - assets: list of assets.
	optional func screenlet(screenlet: AssetListScreenlet,
			onAssetListResponse assets: [Asset])

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error loading asset list.
	optional func screenlet(screenlet: AssetListScreenlet,
			onAssetListError error: NSError)

	/// Called when an item in the list is selected.
	///
	/// - Parameters:
	///   - screenlet
	///   - asset: selected asset.
	optional func screenlet(screenlet: AssetListScreenlet,
			onAssetSelected asset: Asset)

}


public class AssetListScreenlet: BaseListScreenlet {


	//MARK: Inspectables

	@IBInspectable public var groupId: Int64 = 0

	@IBInspectable public var classNameId: Int64 = 0

	@IBInspectable public var portletItemName: String?

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue

	public var assetListDelegate: AssetListScreenletDelegate? {
		return delegate as? AssetListScreenletDelegate
	}

	public var customEntryQuery: [String:AnyObject]?


	//MARK: BaseListScreenlet

	override public func createPageLoadInteractor(
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

	override public func onLoadPageError(page page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		assetListDelegate?.screenlet?(self, onAssetListError: error)
	}

	override public func onLoadPageResult(page page: Int, rows: [AnyObject], rowCount: Int) {
		super.onLoadPageResult(page: page, rows: rows, rowCount: rowCount)

		let assets = rows as! [Asset]

		assetListDelegate?.screenlet?(self, onAssetListResponse: assets)
	}

	override public func onSelectedRow(row: AnyObject) {
		assetListDelegate?.screenlet?(self, onAssetSelected: row as! Asset)
	}
}
