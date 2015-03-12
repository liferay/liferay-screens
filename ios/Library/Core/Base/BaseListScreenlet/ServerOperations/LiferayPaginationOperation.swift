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


public class LiferayPaginationOperation: ServerOperation {

	public let page: Int

	public var result: (pageContent: [[String:AnyObject]], rowCount: Int?)?

	internal override var hudLoadingMessage: HUDMessage? {
		return (page == 0)
				? (LocalizedString("base", "base-list-loading-message", self),
						details: LocalizedString("base", "base-list-loading-details", self))
				: nil
	}
	internal override var hudFailureMessage: HUDMessage? {
		return (page == 0) 
				? (LocalizedString("base", "base-list-loading-error", self), details: nil)
				: nil
	}


	private var computeRowCount: Bool
	

	internal init(screenlet: BaseScreenlet, page: Int, computeRowCount: Bool) {
		self.page = page
		self.computeRowCount = computeRowCount

		super.init(screenlet: screenlet)
	}


	//MARK: ServerOperation

	override internal func doRun(#session: LRSession) {
		let batchSession = LRBatchSession(session: session)

		doGetPageRowsOperation(session: batchSession, page: page)

		if batchSession.commands.count < 1 {
			lastError = createError(cause: .AbortedDueToPreconditions, userInfo: nil)

			return
		}

		if computeRowCount {
			doGetRowCountOperation(session: batchSession)
		}

		result = nil
		lastError = nil

		let responses = batchSession.invoke(&lastError)

		if lastError == nil {
			if let entriesResponse = responses[0] as? [[String:AnyObject]] {
				let serverPageContent = entriesResponse
				var serverRowCount: Int?

				if responses.count > 1 {
					if let countResponse = responses[1] as? NSNumber {
						serverRowCount = countResponse.integerValue
					}
				}

				result = (serverPageContent, serverRowCount)
			}
			else {
				lastError = createError(cause: .InvalidServerResponse, userInfo: nil)
			}
		}
	}

	internal func doGetPageRowsOperation(#session: LRBatchSession, page: Int) {
		assertionFailure("doGetPageRowsOperation must be overriden")
	}

	internal func doGetRowCountOperation(#session: LRBatchSession) {
		assertionFailure("doGetRowCountOperation must be overriden")
	}

}
