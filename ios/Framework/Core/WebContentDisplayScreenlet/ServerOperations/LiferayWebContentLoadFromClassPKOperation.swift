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


public class LiferayWebContentLoadFromClassPKOperation: LiferayWebContentLoadBaseOperation {

	public var classPK: Int64?


	//MARK: ServerOperation

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if classPK ?? 0 == 0 {
				return ValidationError("webcontentdisplay-screenlet", "undefined-classpk")
			}
		}

		return error
	}

	override internal func doGetJournalArticleWithTemplate(
			templateId: Int64,
			session: LRSession) -> String? {
		let result: String?
		let service = LRScreensjournalarticleService_v62(session: session)

		do {
			result = try service.getJournalArticleContentWithClassPK(classPK!,
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
			result = try service.getJournalArticleContentWithClassPK(classPK!,
				locale: NSLocale.currentLocaleString)
		}
		catch let error as NSError {
			lastError = error
			result = nil
		}

		return result
	}
	
}
