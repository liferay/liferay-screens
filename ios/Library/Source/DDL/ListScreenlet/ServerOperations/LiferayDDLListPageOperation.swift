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

	public var userId: Int64 = 0
	public var recordSetId: Int64 = 0

	internal var ddlListScreenlet: DDLListScreenlet {
		return self.screenlet as DDLListScreenlet
	}

	internal var ddlListData: DDLListData {
		return screenlet.screenletView as DDLListData
	}

	override func validateData() -> Bool {
		//FIXME this screenlet should work without user
		if userId == 0 {
			return false
		}

		if recordSetId == 0 {
			return false
		}

		if ddlListData.labelField == "" {
			return false
		}

		return true
	}

	override internal func doGetPageRowsOperation(#session: LRBatchSession, page: Int) {
		let service = LRMobilewidgetsddlrecordService_v62(session: session)

		service.getDdlRecordsWithDdlRecordSetId(recordSetId,
				userId: userId,
				locale: NSLocale.currentLocaleString,
				start: Int32(ddlListScreenlet.firstRowForPage(page)),
				end: Int32(ddlListScreenlet.firstRowForPage(page + 1)),
				error: nil)
	}

	override internal func doGetRowCountOperation(#session: LRBatchSession) {
		let service = LRMobilewidgetsddlrecordService_v62(session: session)

		service.getDdlRecordsCountWithDdlRecordSetId(recordSetId,
			userId: userId,
			error: nil)
	}

}
