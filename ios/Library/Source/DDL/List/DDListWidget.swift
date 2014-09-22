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


@objc public protocol DDListWidgetDelegate {

	optional func onDDListResponse(records: [DDLRecord])
	optional func onDDListError(error: NSError)

	optional func onDDLRecordSelected(record: DDLRecord)

}


@IBDesignable public class DDListWidget: BaseListWidget {

	// FIXME user id should be obtained - See LMW-72
	@IBInspectable public var userId = 0
	@IBInspectable public var recordSetId = 0

	@IBInspectable public var labelField: String = "" {
		didSet {
			onCreated()
		}
	}

	@IBOutlet public var delegate: DDListWidgetDelegate?

	internal var recordListView: DDListView {
		return widgetView as DDListView
	}


	//MARK: BaseListWidget

	override public func onCreated() {
		super.onCreated()
		
		recordListView.labelField = labelField
	}

	override internal func doGetPageRowsOperation(#session: LRSession, page: Int) {
		let service = LRMobilewidgetsddlrecordService_v62(session: session)

		service.getDdlRecordsWithDdlRecordSetId((recordSetId as NSNumber).longLongValue,
				userId: (userId as NSNumber).longLongValue,
				locale: NSLocale.currentLocaleString(),
				start: (firstRowForPage(page) as NSNumber).intValue,
				end: (firstRowForPage(page + 1) as NSNumber).intValue,
				error: nil)
	}

	override internal func doGetRowCountOperation(#session: LRSession) {
		let service = LRMobilewidgetsddlrecordService_v62(session: session)

		service.getDdlRecordsCountWithDdlRecordSetId((recordSetId as NSNumber).longLongValue,
				userId: (userId as NSNumber).longLongValue,
				error: nil)
	}

	override internal func convert(#serverResult:[String:AnyObject]) -> AnyObject {
		return DDLRecord(recordData:serverResult)
	}

	override internal func onLoadPageError(page: Int, error: NSError) {
		super.onLoadPageError(page, error: error)

		delegate?.onDDListError?(error)
	}

	override internal func onLoadPageResult(page: Int,
			serverRows: [[String:AnyObject]],
			rowCount: Int)
			-> [AnyObject] {

		let rowObjects = super.onLoadPageResult(page, serverRows: serverRows, rowCount: rowCount)

		let records = rowObjects.map() { $0 as DDLRecord }

		delegate?.onDDListResponse?(records)

		return rowObjects
	}

	override internal func onSelectedRow(row: AnyObject) {
		delegate?.onDDLRecordSelected?(row as DDLRecord)
	}

}
