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


class UserPortraitBaseLoadUserInteractor: UserPortraitBaseInteractor {

	override internal func start() -> Bool {
		return startWithLoggedUser() || startLoadUserOperation()
	}

	func createLoadUserOperation() -> GetUserBaseOperation? {
		return nil
	}

	func isUserLogged() -> Bool {
		return false
	}

	private func startWithLoggedUser() -> Bool {
		if !isUserLogged() {
			return false
		}

		if let portraitId = SessionContext.userAttribute("portraitId") as? NSNumber,
				uuid = SessionContext.userAttribute("uuid") as? String {

			resultUserId = SessionContext.currentUserId

			return startLoadImage(
				portraitId: portraitId.longLongValue,
				uuid: uuid,
				male: true)
		}

		return false
	}

	private func startLoadUserOperation() -> Bool {
		if let operation = createLoadUserOperation() {
			return (operation.validateAndEnqueue(onComplete: onUserLoaded) == nil)
		}

		return false
	}

	private func onUserLoaded(operation: ServerOperation) {
		let userOperation = operation as! GetUserBaseOperation

		if let userAttributes = userOperation.resultUserAttributes,
				resultUserId = userAttributes["userId"] as? NSNumber,
				portraitId = userAttributes["portraitId"] as? NSNumber,
				uuid = userAttributes["uuid"] as? String {

			self.resultUserId = resultUserId.longLongValue

			startLoadImage(
				portraitId: portraitId.longLongValue,
				uuid: uuid,
				male: true)
		}
		else {
			callOnFailure(NSError.errorWithCause(.InvalidServerResponse))
		}
	}

}
