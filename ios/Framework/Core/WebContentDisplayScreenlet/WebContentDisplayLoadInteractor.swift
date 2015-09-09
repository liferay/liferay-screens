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


class WebContentDisplayLoadInteractor: ServerReadOperationInteractor {

	var resultHTML: String?


	override func createOperation() -> LiferayWebContentLoadOperation {
		let screenlet = self.screenlet as! WebContentDisplayScreenlet

		let operation = LiferayWebContentLoadOperation()

		operation.groupId = (screenlet.groupId != 0)
				? screenlet.groupId : LiferayServerContext.groupId

		operation.articleId = screenlet.articleId

		return operation
	}

	override func completedOperation(op: ServerOperation) {
		self.resultHTML = (op as? LiferayWebContentLoadOperation)?.resultHTML
	}

	override func readFromCache(op: ServerOperation, result: AnyObject? -> Void) {
		if let loadOp = op as? LiferayWebContentLoadOperation,
				groupId = loadOp.groupId,
				articleId = loadOp.articleId {

			SessionContext.currentCacheManager!.getString(
					collection: ScreenletName(WebContentDisplayScreenlet),
					key: cacheKey(groupId, articleId)) {
				loadOp.resultHTML = $0
				result($0)
			}
		}
	}

	override func writeToCache(op: ServerOperation) {
		if let loadOp = op as? LiferayWebContentLoadOperation,
				html = loadOp.resultHTML,
				groupId = loadOp.groupId,
				articleId = loadOp.articleId {

			SessionContext.currentCacheManager?.setClean(
				collection: ScreenletName(WebContentDisplayScreenlet),
				key: cacheKey(groupId, articleId),
				value: html,
				attributes: [
					"groupId": NSNumber(longLong: groupId),
					"articleId": articleId])
		}
	}

	private func cacheKey(groupId: Int64, _ articleId: String) -> String {
		return "\(groupId)-\(articleId)"
	}

}
