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

	optional func onAssetListResponse(entries:[AssetListScreenletEntry])
	optional func onAssetListError(error: NSError)

	optional func onAssetSelected(entry: AssetListScreenletEntry)

}


@objc public class AssetListScreenletEntry : NSObject {

	public let attributes:[String:AnyObject]

	public var title: String {
		return attributes["title"] as String
	}

	override public var description: String {
		return attributes["description"] as String
	}

	public var classNameId: Int64 {
		return Int64(attributes["classNameId"] as Int)
	}

	public var classPK: Int64 {
		return Int64(attributes["classPK"] as Int)
	}

	public var groupId: Int64 {
		return Int64(attributes["groupId"] as Int)
	}

	public var companyId: Int64 {
		return Int64(attributes["companyId"] as Int)
	}

	public var entryId: Int64 {
		return Int64(attributes["entryId"] as Int)
	}


	//MARK: Init

	public init(attributes:[String:AnyObject]) {
		self.attributes = attributes
	}

}


@IBDesignable public class AssetListScreenlet: BaseListScreenlet {

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var classNameId: Int = 0

	@IBOutlet public var delegate: AssetListScreenletDelegate?

	//MARK: BaseListScreenlet

	override internal func createPaginationOperation(
			#page: Int,
			computeRowCount: Bool)
			-> LiferayPaginationOperation {

		let operation = LiferayAssetListPageOperation(
				screenlet: self,
				page: page,
				computeRowCount: computeRowCount)

		operation.groupId = (self.groupId != 0)
				? self.groupId : LiferayServerContext.groupId

		operation.classNameId = Int64(self.classNameId)

		return operation
	}

	override internal func convert(#serverResult:[String:AnyObject]) -> AnyObject {
		return AssetListScreenletEntry(attributes: serverResult)
	}

	override internal func onLoadPageError(#page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		delegate?.onAssetListError?(error)
	}

	override internal func onLoadPageResult(
			#page: Int,
			serverRows: [[String:AnyObject]],
			rowCount: Int)
			-> [AnyObject] {

		let rowObjects = super.onLoadPageResult(
				page: page,
				serverRows: serverRows,
				rowCount: rowCount)

		let assetEntries = rowObjects.map() { $0 as AssetListScreenletEntry }

		delegate?.onAssetListResponse?(assetEntries)

		return rowObjects
	}

	override internal func onSelectedRow(row: AnyObject) {
		delegate?.onAssetSelected?(row as AssetListScreenletEntry)
	}

}
