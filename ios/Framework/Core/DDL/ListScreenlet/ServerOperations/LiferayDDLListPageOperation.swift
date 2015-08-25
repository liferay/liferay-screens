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


	override func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if recordSetId == nil {
				return ValidationError(message: "Record set is undefined")
			}

			if viewModel.labelFields.count == 0 {
				return ValidationError(message: "Label fields is undefined")
			}
		}

		return error
	}

	override internal func doGetPageRowsOperation(#session: LRBatchSession, startRow: Int, endRow: Int) {
		let service = LRScreensddlrecordService_v62(session: session)

		if userId == nil {
			service.getDdlRecordsWithDdlRecordSetId(recordSetId!,
					locale: NSLocale.currentLocaleString,
					start: Int32(startRow),
					end: Int32(endRow),
					error: nil)
		}
		else {
			service.getDdlRecordsWithDdlRecordSetId(recordSetId!,
					userId: userId!,
					locale: NSLocale.currentLocaleString,
					start: Int32(startRow),
					end: Int32(endRow),
					error: nil)
		}
	}

	override internal func doGetRowCountOperation(#session: LRBatchSession) {
		let service = LRScreensddlrecordService_v62(session: session)

		if userId == nil {
			service.getDdlRecordsCountWithDdlRecordSetId(recordSetId!, error: nil)
		}
		else {
			service.getDdlRecordsCountWithDdlRecordSetId(recordSetId!, userId: userId!, error: nil)
		}
	}

}
