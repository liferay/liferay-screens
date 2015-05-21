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
		if isUserLogged() {
			return startLoggedUser()
		}

		return startLoadUserOperation()
	}

	func createLoadUserOperation() -> GetUserBaseOperation? {
		return nil
	}

	func isUserLogged() -> Bool {
		return false
	}

	private func startLoggedUser() -> Bool {
		let loggedUserInteractor = UserPortraitLoadLoggedUserInteractor(screenlet: screenlet)

		loggedUserInteractor.onSuccess = {
			self.resultURL = loggedUserInteractor.resultURL
			self.resultUserId = loggedUserInteractor.resultUserId
			self.callOnSuccess()
		}

		loggedUserInteractor.onFailure = {
			self.resultURL = nil
			self.callOnFailure($0)
		}

		return loggedUserInteractor.start()
	}

	private func startLoadUserOperation() -> Bool {
		var result = false

		if let operation = createLoadUserOperation() {
			result = operation.validateAndEnqueue(onComplete: onUserLoaded)

			if result {
				self.screenlet.screenletView?.onStartOperation()
			}
			else {
				resultURL = nil
			}
		}

		return result
	}

	private func onUserLoaded(operation: ServerOperation) {
		let userOperation = operation as! GetUserBaseOperation

		if let userAttributes = userOperation.resultUserAttributes {
			self.resultUserId = (userAttributes["userId"] as! NSNumber).longLongValue

			let attributesInteractor = UserPortraitAttributesLoadInteractor(
					screenlet: screenlet,
					portraitId: (userAttributes["portraitId"] as! NSNumber).longLongValue,
					uuid: userAttributes["uuid"] as! String,
					male: true)

			attributesInteractor.onSuccess = {
				self.resultURL = attributesInteractor.resultURL
				self.callOnSuccess()
			}

			attributesInteractor.onFailure = {
				self.resultURL = nil
				self.callOnFailure($0)
			}

			attributesInteractor.start()
		}
		else {
			resultURL = nil
			callOnFailure(NSError.errorWithCause(.InvalidServerResponse))
		}
	}

}
