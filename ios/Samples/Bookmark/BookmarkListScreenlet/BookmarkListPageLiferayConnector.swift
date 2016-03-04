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
import LiferayScreens


public class BookmarkListPageLiferayConnector: PaginationLiferayConnector {

	public let groupId: Int64
	public let folderId: Int64

	public init(startRow: Int, endRow: Int, computeRowCount: Bool, groupId: Int64, folderId: Int64) {
		self.groupId = groupId
		self.folderId = folderId

		super.init(startRow: startRow, endRow: endRow, computeRowCount: computeRowCount)
	}

	override public func doAddPageRowsServiceCall(session session: LRBatchSession, startRow: Int, endRow: Int) {
		let service = LRBookmarksEntryService_v62(session: session)

		do {
			try service.getEntriesWithGroupId(groupId,
				folderId: folderId,
				start: Int32(startRow),
				end: Int32(endRow))
		}
		catch  {
			// ignore error: the method returns nil (converted to an error) because
			// the request is not actually sent
		}
	}

	override public func doAddRowCountServiceCall(session session: LRBatchSession) {
		let service = LRBookmarksEntryService_v62(session: session)

		do {
			try service.getEntriesCountWithGroupId(groupId, folderId: folderId)
		}
		catch  {
			// ignore error: the method returns nil (converted to an error) because
			// the request is not actually sent
		}
	}

}
