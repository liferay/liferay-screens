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


public class LiferayWebContentLoadBaseOperation: ServerOperation {

	public var groupId: Int64?
	public var templateId: Int64?

	public var resultHTML: String?


	//MARK: ServerOperation

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if groupId ?? 0 == 0 {
				return ValidationError("webcontentdisplay-screenlet", "undefined-group")
			}
		}

		return error
	}

	override public func doRun(session session: LRSession) {
		resultHTML = nil

		var result: String?

		if templateId ?? 0 != 0 {
			result = doGetJournalArticleWithTemplate(templateId!, session: session)
		}
		else {
			result = doGetJournalArticle(session)
		}

		if lastError == nil {
			if let result = result {
				resultHTML = result
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
			}
		}
	}

	internal func doGetJournalArticleWithTemplate(
			templateId: Int64,
			session: LRSession) -> String? {
		fatalError("doGetJournalArticleWithTemplate method must be overwritten")
	}

	internal func doGetJournalArticle(session: LRSession) -> String? {
		fatalError("doGetJournalArticle method must be overwritten")
	}

}
