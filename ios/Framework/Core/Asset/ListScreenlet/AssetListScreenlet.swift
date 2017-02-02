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
	@objc optional func screenlet(_ screenlet: AssetListScreenlet,
			onAssetListResponse assets: [Asset])

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error loading asset list.
	@objc optional func screenlet(_ screenlet: AssetListScreenlet,
			onAssetListError error: NSError)

	/// Called when an item in the list is selected.
	///
	/// - Parameters:
	///   - screenlet
	///   - asset: selected asset.
	@objc optional func screenlet(_ screenlet: AssetListScreenlet,
			onAssetSelected asset: Asset)

}


open class AssetListScreenlet: BaseListScreenlet {


	//MARK: Inspectables

	@IBInspectable open var groupId: Int64 = 0

	@IBInspectable open var classNameId: Int64 = 0

	@IBInspectable open var portletItemName: String?

	@IBInspectable open var offlinePolicy: String? = CacheStrategyType.remoteFirst.rawValue

	open var assetListDelegate: AssetListScreenletDelegate? {
		return delegate as? AssetListScreenletDelegate
	}

	open var customEntryQuery: [String:AnyObject]?


	//MARK: BaseListScreenlet

	override open func createPageLoadInteractor(
			page: Int,
			computeRowCount: Bool)
			-> BaseListPageLoadInteractor {

		let interactor = AssetListPageLoadInteractor(
			screenlet: self,
			page: page,
			computeRowCount: computeRowCount,
			groupId: self.groupId,
			classNameId: self.classNameId,
			portletItemName: self.portletItemName)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .remoteFirst
		interactor.customEntryQuery = self.customEntryQuery

		return interactor
	}

	override open func onLoadPageError(page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		assetListDelegate?.screenlet?(self, onAssetListError: error)
	}

	override open func onLoadPageResult(page: Int, rows: [AnyObject], rowCount: Int) {
		super.onLoadPageResult(page: page, rows: rows, rowCount: rowCount)

		let assets = rows as! [Asset]

		assetListDelegate?.screenlet?(self, onAssetListResponse: assets)
	}

	override open func onSelectedRow(_ row: AnyObject) {
		assetListDelegate?.screenlet?(self, onAssetSelected: row as! Asset)
	}
}
