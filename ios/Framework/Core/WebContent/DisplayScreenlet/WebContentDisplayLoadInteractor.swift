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
	var resultRecord: DDLRecord?

	override func createConnector() -> WebContentLoadBaseLiferayConnector? {
		let screenlet = self.screenlet as! WebContentDisplayScreenlet
		let groupId = (screenlet.groupId != 0)
			? screenlet.groupId
			: LiferayServerContext.groupId

		let connector: WebContentLoadBaseLiferayConnector?

		if screenlet.articleId != "" {
			if screenlet.structureId != 0 {
				connector = LiferayServerContext.connectorFactory.createWebContentLoadStructuredByArticleIdConnector(
					structureId: screenlet.structureId,
					groupId: groupId,
					articleId: screenlet.articleId)
			}
			else {
				connector = LiferayServerContext.connectorFactory.createWebContentLoadHtmlByArticleIdConnector(
					groupId: groupId,
					articleId: screenlet.articleId)
				(connector as! WebContentLoadHtmlBaseLiferayConnector).templateId = screenlet.templateId
			}
		}
		else if screenlet.classPK != 0 {
			connector = LiferayServerContext.connectorFactory.createWebContentLoadHtmlByClassPKConnector(classPK: screenlet.classPK)
			(connector as! WebContentLoadHtmlBaseLiferayConnector).templateId = screenlet.templateId
		}
		else {
			connector = nil
		}

		return connector
	}

	override func completedConnector(op: ServerConnector) {
		if let html = (op as? WebContentLoadHtmlBaseLiferayConnector)?.resultHTML {
			self.resultHTML = html
		}
		else if let record = (op as? WebContentLoadStructuredBaseLiferayConnector)?.resultRecord {
			self.resultRecord = record
		}
		else {
			self.resultHTML = nil
			self.resultRecord = nil
		}
	}

	override func readFromCache(op: ServerConnector, result: AnyObject? -> ()) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			result(nil)
			return
		}

		if let loadOp = op as? WebContentLoadHtmlByArticleIdLiferayConnector,
				groupId = loadOp.groupId {

			cacheManager.getString(
					collection: ScreenletName(WebContentDisplayScreenlet),
					key: articleCacheKey(groupId, loadOp.articleId)) {
				loadOp.resultHTML = $0
				result($0)
			}
		}
		else {
			result(nil)
		}
	}

	override func writeToCache(op: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}

		if let loadOp = op as? WebContentLoadHtmlByArticleIdLiferayConnector,
				html = loadOp.resultHTML,
				groupId = loadOp.groupId {

			cacheManager.setClean(
				collection: ScreenletName(WebContentDisplayScreenlet),
				key: articleCacheKey(groupId, loadOp.articleId),
				value: html,
				attributes: [
					"groupId": NSNumber(longLong: groupId),
					"articleId": loadOp.articleId])
		}
	}

	private func articleCacheKey(groupId: Int64, _ articleId: String) -> String {
		return "\((groupId != 0) ? groupId : LiferayServerContext.groupId)-articleId-\(articleId)"
	}

}
