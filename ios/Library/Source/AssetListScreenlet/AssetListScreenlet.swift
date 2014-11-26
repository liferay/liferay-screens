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


@objc public class AssetListScreenletEntry {

	public var title:String

	public init(title:String) {
		self.title = title
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
		let title = (serverResult["title"] ?? "") as String

		return AssetListScreenletEntry(title: title)
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
