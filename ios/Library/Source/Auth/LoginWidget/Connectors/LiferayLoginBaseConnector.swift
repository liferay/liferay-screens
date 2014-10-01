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

class LiferayLoginBaseConnector: BaseConnector, NSCopying {

	var loggedUserAttributes: [String:AnyObject]?

	private var loginView: LoginView {
		return widget.widgetView as LoginView
	}


	//MARK: BaseConnector

	override func validateView() -> Bool {
		var result = super.validateView()

		if result {
			if loginView.userName == nil || loginView.password == nil {
				showValidationHUD(message: "Please, enter the user name and password")
				result = false
			}
		}

		return result
	}

	override func preRun() -> Bool {
		var result = super.preRun()

		if result {
			showHUD(message: "Sending sign in...", details:"Wait few seconds...")

			SessionContext.createSession(
					username: loginView.userName!,
					password: loginView.password!,
					userAttributes: [:])
		}

		return result
	}

	override func postRun() {
		if lastError == nil {
			SessionContext.createSession(
					username: SessionContext.currentUserName!,
					password: SessionContext.currentPassword!,
					userAttributes: loggedUserAttributes!)

			hideHUD()
		}
		else {
			SessionContext.clearSession()

			hideHUD(error: lastError!, message: "Error signing in!")
		}
	}

	override func doRun(#session: LRSession) {
		var outError: NSError?

		let result = sendGetUserRequest(
				service: LRUserService_v62(session: session),
				error: &outError)

		if outError != nil {
			lastError = outError
			loggedUserAttributes = nil
		}
		else if result?["userId"] == nil {
			lastError = createError(cause: .InvalidServerResponse, userInfo: nil)
			loggedUserAttributes = nil
		}
		else {
			lastError = nil
			loggedUserAttributes = result as? [String:AnyObject]
		}
	}


	//MARK: NSCopying

	internal func copyWithZone(zone: NSZone) -> AnyObject {
		assertionFailure("copyWithZone must be overriden")
		return self
	}


	// MARK: Internal methods

	internal func sendGetUserRequest(
			#service: LRUserService_v62,
			error: NSErrorPointer)
			-> NSDictionary? {

		return nil
	}

   
}
