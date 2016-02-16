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


class WebContentDisplayLoadInteractor: ServerReadConnectorInteractor {

	var resultHTML: String?


	override func createConnector() -> LiferayWebContentLoadBaseConnector? {
		let screenlet = self.screenlet as! WebContentDisplayScreenlet

		let connector: LiferayWebContentLoadBaseConnector?

		if screenlet.articleId != "" {
			connector = LiferayServerContext.connectorFactory.createWebContentLoadFromArticleId(articleId: screenlet.articleId)
		}
		else if screenlet.classPK != 0 {
			connector = LiferayServerContext.connectorFactory.createWebContentLoadFromClassPK(classPK: screenlet.classPK)
		}
		else {
			connector = nil
		}

		if let connector = connector {
			connector.groupId = (screenlet.groupId != 0)
				? screenlet.groupId : LiferayServerContext.groupId
			connector.templateId = screenlet.templateId
		}

		return connector
	}

	override func completedConnector(op: ServerConnector) {
		self.resultHTML = (op as? LiferayWebContentLoadBaseConnector)?.resultHTML
	}

	override func readFromCache(op: ServerConnector, result: AnyObject? -> Void) {
		if let loadOp = op as? LiferayWebContentLoadFromArticleIdConnector,
				groupId = loadOp.groupId,
				articleId = loadOp.articleId {

			SessionContext.currentContext?.cacheManager.getString(
					collection: ScreenletName(WebContentDisplayScreenlet),
					key: articleCacheKey(groupId, articleId)) {
				loadOp.resultHTML = $0
				result($0)
			}
		}
	}

	override func writeToCache(op: ServerConnector) {
		if let loadOp = op as? LiferayWebContentLoadFromArticleIdConnector,
				html = loadOp.resultHTML,
				groupId = loadOp.groupId,
				articleId = loadOp.articleId {

			SessionContext.currentContext?.cacheManager.setClean(
				collection: ScreenletName(WebContentDisplayScreenlet),
				key: articleCacheKey(groupId, articleId),
				value: html,
				attributes: [
					"groupId": NSNumber(longLong: groupId),
					"articleId": articleId])
		}
	}

	private func articleCacheKey(groupId: Int64, _ articleId: String) -> String {
		return "\((groupId != 0) ? groupId : LiferayServerContext.groupId)-articleId-\(articleId)"
	}

}
