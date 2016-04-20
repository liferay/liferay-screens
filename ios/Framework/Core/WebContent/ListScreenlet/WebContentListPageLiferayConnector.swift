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
import LRMobileSDK


public class WebContentListPageLiferayConnector: PaginationLiferayConnector {

	public let groupId: Int64
	public let folderId: Int64


	init(startRow: Int, endRow: Int, computeRowCount: Bool, groupId: Int64, folderId: Int64) {

		self.groupId = groupId
		self.folderId = folderId

		super.init(startRow: startRow, endRow: endRow, computeRowCount: computeRowCount)
	}

}

public class Liferay62WebContentListPageConnector: WebContentListPageLiferayConnector {

	override public func doAddPageRowsServiceCall(session session: LRBatchSession, startRow: Int, endRow: Int) {
		do {
			let service = LRJournalArticleService_v62(session: session)
			try service.getArticlesWithGroupId(groupId,
				folderId: folderId,
				start: Int32(startRow),
				end: Int32(endRow),
				obc: nil)
		}
		catch _ as NSError {
		}
	}

	override public func doAddRowCountServiceCall(session session: LRBatchSession) {
		do {
			let service = LRJournalArticleService_v62(session: session)
			try service.getArticlesCountWithGroupId(groupId, folderId: folderId)
		}
		catch _ as NSError {
		}
	}

}


public class Liferay70WebContentListPageConnector: WebContentListPageLiferayConnector {

	override public func doAddPageRowsServiceCall(session session: LRBatchSession, startRow: Int, endRow: Int) {
		do {
			let service = LRJournalArticleService_v7(session: session)
			try service.getArticlesWithGroupId(groupId,
			                                   folderId: folderId,
			                                   start: Int32(startRow),
			                                   end: Int32(endRow),
			                                   obc: nil)
		}
		catch _ as NSError {
		}
	}

	override public func doAddRowCountServiceCall(session session: LRBatchSession) {
		do {
			let service = LRJournalArticleService_v7(session: session)
			try service.getArticlesCountWithGroupId(groupId, folderId: folderId)
		}
		catch _ as NSError {
		}
	}

}
