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


@objc public protocol DDLListScreenletDelegate {

	optional func onDDLListResponse(records: [DDLRecord])
	optional func onDDLListError(error: NSError)

	optional func onDDLRecordSelected(record: DDLRecord)

}


@IBDesignable public class DDLListScreenlet: BaseListScreenlet {

	@IBInspectable public var userId: Int64 = 0
	@IBInspectable public var recordSetId: Int64 = 0

	@IBInspectable public var labelField: String? {
		didSet {
			(screenletView as? DDLListData)?.labelField = labelField ?? ""
		}
	}

	@IBOutlet public var delegate: DDLListScreenletDelegate?

	internal var ddlListData: DDLListData {
		return screenletView as DDLListData
	}


	//MARK: BaseListScreenlet

	override public func onCreated() {
		super.onCreated()

		ddlListData.labelField = labelField ?? ""
	}

	override internal func createPaginationOperation(
			#page: Int,
			computeRowCount: Bool)
			-> LiferayPaginationOperation {

		let operation = LiferayDDLListPageOperation(
				screenlet: self,
				page: page,
				computeRowCount: computeRowCount)

		operation.userId = self.userId
		operation.recordSetId = self.recordSetId

		return operation
	}

	override internal func convert(#serverResult:[String:AnyObject]) -> AnyObject {
		return DDLRecord(recordData: serverResult)
	}

	override internal func onLoadPageError(#page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		delegate?.onDDLListError?(error)
	}

	override internal func onLoadPageResult(#page: Int,
			serverRows: [[String:AnyObject]],
			rowCount: Int)
			-> [AnyObject] {

		let rowObjects = super.onLoadPageResult(page: page, serverRows: serverRows, rowCount: rowCount)

		let records = rowObjects.map() { $0 as DDLRecord }

		delegate?.onDDLListResponse?(records)

		return records
	}

	override internal func onSelectedRow(row: AnyObject) {
		delegate?.onDDLRecordSelected?(row as DDLRecord)
	}

}
