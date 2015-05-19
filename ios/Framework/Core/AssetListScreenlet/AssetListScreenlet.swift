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


@objc public protocol AssetListScreenletDelegate {

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
		return Int64(attributes["classNameId"] as! Int)
	}

	public var classPK: Int64 {
		return Int64(attributes["classPK"] as! Int)
	}

	public var groupId: Int64 {
		return Int64(attributes["groupId"] as! Int)
	}

	public var companyId: Int64 {
		return Int64(attributes["companyId"] as! Int)
	}

	public var entryId: Int64 {
		return Int64(attributes["entryId"] as! Int)
	}


	//MARK: Init

	public init(attributes:[String:AnyObject]) {
		self.attributes = attributes
	}

}


@IBDesignable public class AssetListScreenlet: BaseListScreenlet {

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var classNameId: Int = 0

	@IBOutlet public weak var delegate: AssetListScreenletDelegate?


	//MARK: BaseListScreenlet

	override internal func createPageLoadInteractor(
			#page: Int,
			computeRowCount: Bool)
			-> BaseListPageLoadInteractor {

		return AssetListPageLoadInteractor(
				screenlet: self,
				page: page,
				computeRowCount: computeRowCount,
				groupId: self.groupId,
				classNameId: self.classNameId)
	}

	override internal func onLoadPageError(#page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		delegate?.screenlet?(self, onAssetListError: error)
	}

	override internal func onLoadPageResult(#page: Int, rows: [AnyObject], rowCount: Int) {
		super.onLoadPageResult(page: page, rows: rows, rowCount: rowCount)

		let assetEntries = rows as! [AssetListScreenletEntry]

		delegate?.screenlet?(self, onAssetListResponseEntries: assetEntries)
	}

	override internal func onSelectedRow(row: AnyObject) {
		delegate?.screenlet?(self, onAssetSelectedEntry: row as! AssetListScreenletEntry)
	}

}
