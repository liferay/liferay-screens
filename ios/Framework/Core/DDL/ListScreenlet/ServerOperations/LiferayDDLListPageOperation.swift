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


public class LiferayDDLListPageOperation: LiferayPaginationOperation {

	public var userId: Int64?
	public var recordSetId: Int64?

	internal let viewModel: DDLListViewModel


	public init(viewModel: DDLListViewModel, startRow: Int, endRow: Int, computeRowCount: Bool) {
		self.viewModel = viewModel

		super.init(startRow: startRow, endRow: endRow, computeRowCount: computeRowCount)
	}


	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if recordSetId == nil {
				return ValidationError("ddllist-screenlet", "undefined-recordset")
			}

			if viewModel.labelFields.count == 0 {
				return ValidationError("ddllist-screenlet", "undefined-fields")
			}
		}

		return error
	}

	override internal func doGetPageRowsOperation(session session: LRBatchSession, startRow: Int, endRow: Int) {
		let service = LRScreensddlrecordService_v62(session: session)

		do {
			if userId == nil {
				try service.getDdlRecordsWithDdlRecordSetId(recordSetId!,
					locale: NSLocale.currentLocaleString,
					start: Int32(startRow),
					end: Int32(endRow))
			}
			else {
				try service.getDdlRecordsWithDdlRecordSetId(recordSetId!,
					userId: userId!,
					locale: NSLocale.currentLocaleString,
					start: Int32(startRow),
					end: Int32(endRow))
			}
		}
		catch _ as NSError {
		}
	}

	override internal func doGetRowCountOperation(session session: LRBatchSession) {
		let service = LRScreensddlrecordService_v62(session: session)

		do {
			if userId == nil {
				try service.getDdlRecordsCountWithDdlRecordSetId(recordSetId!)
			}
			else {
				try service.getDdlRecordsCountWithDdlRecordSetId(recordSetId!,
						userId: userId!)
			}
		}
		catch _ as NSError {
		}
	}

}
