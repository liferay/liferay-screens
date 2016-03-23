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


public class WebContentLoadStructuredBaseLiferayConnector: WebContentLoadBaseLiferayConnector {

	public let structureId: Int64

	public var resultRecord: DDLRecord?

	public init(groupId: Int64, structureId: Int64) {
		self.structureId = structureId
		super.init()
		self.groupId = groupId
	}


	//MARK: ServerConnector

	override public func doRun(session session: LRSession) {
		if let resultRecord = doGetJournalArticleStructure(session) {
			self.resultRecord = resultRecord
			lastError = nil
		}
		else {
			lastError = NSError.errorWithCause(.InvalidServerResponse)
			self.resultRecord = nil
		}
	}

	internal func doGetJournalArticleStructure(session: LRSession) -> DDLRecord? {
		fatalError("doGetJournalArticle method must be overwritten")
	}

}
