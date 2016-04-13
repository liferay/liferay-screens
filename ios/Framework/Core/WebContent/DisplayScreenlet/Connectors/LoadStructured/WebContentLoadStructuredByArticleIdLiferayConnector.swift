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


public class WebContentLoadStructuredByArticleIdLiferayConnector: WebContentLoadStructuredBaseLiferayConnector {

	public let articleId: String

	public init(structureId: Int64, groupId: Int64, articleId: String) {
		self.articleId = articleId

		super.init(groupId: groupId, structureId: structureId)
	}

}


public class Liferay62WebContentLoadStructuredByArticleIdConnector: WebContentLoadStructuredByArticleIdLiferayConnector {

	override internal func doGetJournalArticleStructure(session: LRSession) -> DDLRecord? {
		let batchSession = LRBatchSession(session: session)

		let structureSrv = LRDDMStructureService_v62(session: batchSession)
		_ = try? structureSrv.getStructureWithStructureId(self.structureId)

		let journalArticleSrv = LRJournalArticleService_v62(session: batchSession)
		_ = try? journalArticleSrv.getArticleWithGroupId(self.groupId!, articleId: self.articleId)

		guard batchSession.commands.count == 2 else {
			return nil
		}

		do {
			let results = try batchSession.invoke()
			guard results.count == 2 else {
				return nil
			}
			guard var structureResult = results[0] as? [String:AnyObject] else {
				return nil
			}
			guard let xsd = structureResult["xsd"] as? String else {
				return nil
			}
			guard var articleResult = results[1] as? [String:AnyObject] else {
				return nil
			}
			guard let xmlContent = articleResult["content"] as? String else {
				return nil
			}

			structureResult.removeValueForKey("xsd")
			articleResult.removeValueForKey("content")

			guard let structure = DDMStructure(
					xsd: xsd,
					locale: NSLocale(localeIdentifier: NSLocale.currentLocaleString),
					attributes: structureResult) else {
				return nil
			}

			let record = DDLRecord(structure: structure)
			record.updateCurrentValues(xmlValues: xmlContent)
			record.attributes = articleResult

			return record
		}
		catch {
		}

		return nil
	}

}


public class Liferay70WebContentLoadStructuredByArticleIdConnector: WebContentLoadStructuredByArticleIdLiferayConnector {

	override internal func doGetJournalArticleStructure(session: LRSession) -> DDLRecord? {
		fatalError("TODO")
	}

}
