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


public class WebContentLoadHtmlByClassPKLiferayConnector: WebContentLoadHtmlBaseLiferayConnector {

	public let classPK: Int64

	public init(classPK: Int64) {
		self.classPK = classPK

		super.init()
	}

}


public class Liferay62WebContentLoadHtmlByClassPKConnector: WebContentLoadHtmlByClassPKLiferayConnector {

	override internal func doGetJournalArticleWithTemplate(
			templateId: Int64,
			session: LRSession) -> String? {
		let result: String?
		let service = LRScreensjournalarticleService_v62(session: session)

		do {
			result = try service.getJournalArticleContentWithClassPK(classPK,
				ddmTemplateId: templateId,
				locale: NSLocale.currentLocaleString)
		}
		catch let error as NSError {
			lastError = error
			result = nil
		}

		return result
	}

	override internal func doGetJournalArticle(session: LRSession) -> String? {
		let result: String?
		let service = LRScreensjournalarticleService_v62(session: session)

		do {
			result = try service.getJournalArticleContentWithClassPK(classPK,
				locale: NSLocale.currentLocaleString)
		}
		catch let error as NSError {
			lastError = error
			result = nil
		}

		return result
	}
	
}


public class Liferay70WebContentLoadHtmlByClassPKConnector: WebContentLoadHtmlByClassPKLiferayConnector {


	override internal func doGetJournalArticleWithTemplate(
			templateId: Int64,
			session: LRSession) -> String? {
		let result: String?
		let service = LRScreensjournalarticleService_v70(session: session)

		do {
			result = try service.getJournalArticleContentWithClassPK(classPK,
				ddmTemplateId: templateId,
				locale: NSLocale.currentLocaleString)
		}
		catch let error as NSError {
			lastError = error
			result = nil
		}

		return result
	}

	override internal func doGetJournalArticle(session: LRSession) -> String? {
		let result: String?
		let service = LRScreensjournalarticleService_v70(session: session)

		do {
			result = try service.getJournalArticleContentWithClassPK(classPK,
				locale: NSLocale.currentLocaleString)
		}
		catch let error as NSError {
			lastError = error
			result = nil
		}
		
		return result
	}
	
}
