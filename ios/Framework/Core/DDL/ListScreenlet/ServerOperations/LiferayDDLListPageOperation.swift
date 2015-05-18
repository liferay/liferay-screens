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

	internal var ddlListScreenlet: DDLListScreenlet {
		return self.screenlet as! DDLListScreenlet
	}

	internal var viewModel: DDLListViewModel {
		return screenlet.screenletView as! DDLListViewModel
	}

	override func validateData() -> Bool {
		var valid = super.validateData()

		valid = valid && (recordSetId != nil)
		valid = valid && (viewModel.labelFields.count > 0)

		return valid
	}

	override internal func doGetPageRowsOperation(#session: LRBatchSession, page: Int) {
		let service = LRScreensddlrecordService_v62(session: session)

		if userId == nil {
			service.getDdlRecordsWithDdlRecordSetId(recordSetId!,
					locale: NSLocale.currentLocaleString,
					start: Int32(ddlListScreenlet.firstRowForPage(page)),
					end: Int32(ddlListScreenlet.firstRowForPage(page + 1)),
					error: nil)
		}
		else {
			service.getDdlRecordsWithDdlRecordSetId(recordSetId!,
					userId: userId!,
					locale: NSLocale.currentLocaleString,
					start: Int32(ddlListScreenlet.firstRowForPage(page)),
					end: Int32(ddlListScreenlet.firstRowForPage(page + 1)),
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
