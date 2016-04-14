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

	let groupId: Int64
	let articleId: String
	let structureId: Int64?
	let templateId: Int64?

	var resultHTML: String?
	var resultRecord: DDLRecord?

	init(groupId: Int64, articleId: String, structureId: Int64?, templateId: Int64?) {
		self.groupId = (groupId != 0) ? groupId : LiferayServerContext.groupId
		self.articleId = articleId
		self.structureId = (structureId ?? 0 == 0) ? nil : structureId
		self.templateId = (templateId ?? 0 == 0) ? nil : templateId

		super.init(screenlet: nil)
	}

	override func createConnector() -> WebContentLoadBaseLiferayConnector? {
		if let structureId = self.structureId where structureId != 0 {
			return LiferayServerContext.connectorFactory.createWebContentLoadStructuredConnector(
				groupId: groupId,
				articleId: articleId,
				structureId: structureId)
		}
		else {
			let htmlConnector = LiferayServerContext.connectorFactory.createWebContentLoadHtmlConnector(
				groupId: groupId,
				articleId: articleId)
			htmlConnector.templateId = templateId
			return htmlConnector
		}
	}

	override func completedConnector(op: ServerConnector) {
		if let htmlConnector = (op as? WebContentLoadHtmlLiferayConnector),
				html = htmlConnector.resultHTML {
			self.resultHTML = html
		}
		else if let record = (op as? WebContentLoadStructuredLiferayConnector)?.resultRecord {
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

		if let loadOp = op as? WebContentLoadHtmlLiferayConnector,
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

		if let loadOp = op as? WebContentLoadHtmlLiferayConnector,
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
