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

public class CommentListPageLoadInteractor: BaseListPageLoadInteractor {

	let groupId: Int64
	let className: String
	let classPK: Int64

	public init(
			screenlet: BaseListScreenlet,
			page: Int,
			computeRowCount: Bool,
			groupId: Int64,
			className: String,
			classPK: Int64) {
		self.groupId = (groupId != 0) ? groupId : LiferayServerContext.groupId
		self.className = className
		self.classPK = classPK

		super.init(screenlet: screenlet, page: page, computeRowCount: computeRowCount)
	}

	override public func createConnector() -> CommentListPageLiferayConnector {
		let pager = (self.screenlet as! BaseListScreenlet).firstRowForPage

		return LiferayServerContext.connectorFactory.createCommentListPageConnector(
			groupId: groupId,
			className: className,
			classPK: classPK,
			startRow: pager(self.page),
			endRow: pager(self.page + 1),
			computeRowCount: self.computeRowCount)!
	}

	override public func convertResult(serverResult: [String:AnyObject]) -> AnyObject {
		return Comment(attributes: serverResult)
	}

	override public func cacheKey(op: PaginationLiferayConnector) -> String {
		return "COMMENTS_\(groupId)_\(classPK)_\(className)"
	}

}
