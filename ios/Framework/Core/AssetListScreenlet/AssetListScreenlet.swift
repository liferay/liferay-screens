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
			onAssetListResponse assets: [Asset])

	optional func screenlet(screenlet: AssetListScreenlet,
			onAssetListError error: NSError)

	optional func screenlet(screenlet: AssetListScreenlet,
			onAssetSelected asset: Asset)

}


@objc public class Asset : NSObject, NSCoding {

	public let attributes :[String:AnyObject]

	public let title: String

	override public var description: String {
		return attributes["description"] as! String
	}

	public var classNameId: Int64 {
		return attributes["classNameId"]!.description.asLong!
	}

	public var classPK: Int64 {
		return attributes["classPK"]!.description.asLong!
	}

	public var groupId: Int64 {
		return attributes["groupId"]!.description.asLong!
	}

	public var companyId: Int64 {
		return attributes["companyId"]!.description.asLong!
	}

	public var entryId: Int64 {
		return attributes["entryId"]!.description.asLong!
	}

	override public var debugDescription: String {
		return attributes.debugDescription
	}

	//MARK: Init

	public init(attributes: [String:AnyObject]) {
		self.attributes = attributes

		let xmlTitle = attributes["title"] as! String
		title = xmlTitle.asLocalized(NSLocale(localeIdentifier: NSLocale.currentLocaleString))
	}

	public required init?(coder aDecoder: NSCoder) {
		let keys = (aDecoder.decodeObjectForKey("asset-attr-keys") as? [String]) ?? [String]()

		var attrs = [String:AnyObject]()

		for k in keys {
			if let v = aDecoder.decodeObjectForKey("asset-attr-\(k)") {
				attrs[k] = v
			}
		}

		self.attributes = attrs

		let xmlTitle = (attributes["title"] as? String) ?? ""
		title = xmlTitle.asLocalized(NSLocale(localeIdentifier: NSLocale.currentLocaleString))

		super.init()
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		let keys = Array(self.attributes.keys)

		aCoder.encodeObject(keys, forKey:"asset-attr-keys")

		for (k,v) in self.attributes {
			if let coderValue = v as? NSCoder {
				aCoder.encodeObject(coderValue, forKey:"asset-attr-\(k)")
			}
		}
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
