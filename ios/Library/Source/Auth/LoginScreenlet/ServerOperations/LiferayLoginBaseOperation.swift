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

public class LiferayLoginBaseOperation: ServerOperation {

	public var resultUserAttributes: [String:AnyObject]?

	internal override var hudLoadingMessage: HUDMessage? {
		return (LocalizedString("login-screenlet", "loading-message", self),
				details: LocalizedString("login-screenlet", "loading-details", self))
	}
	internal override var hudFailureMessage: HUDMessage? {
		return (LocalizedString("login-screenlet", "loading-error", self), details: nil)
	}

	internal var loginData: LoginData {
		return screenlet.screenletView as LoginData
	}


	//MARK: ServerOperation

	override internal func validateData() -> Bool {
		if loginData.userName == nil || loginData.password == nil {
			showValidationHUD(
					message: LocalizedString("login-screenlet", "validation", self))

			return false
		}

		return true
	}

	override internal func preRun() -> Bool {
		SessionContext.createSession(
				username: loginData.userName!,
				password: loginData.password!,
				userAttributes: [:])

		return true
	}

	override internal func postRun() {
		if lastError == nil {
			SessionContext.createSession(
					username: SessionContext.currentUserName!,
					password: SessionContext.currentPassword!,
					userAttributes: resultUserAttributes!)
		}
		else {
			SessionContext.clearSession()
		}
	}

	override internal func doRun(#session: LRSession) {
		var outError: NSError?

		resultUserAttributes = nil

		let result = sendGetUserRequest(
				service: LRUserService_v62(session: session),
				error: &outError)

		if outError != nil {
			lastError = outError
			resultUserAttributes = nil
		}
		else if result?["userId"] == nil {
			lastError = createError(cause: .InvalidServerResponse, userInfo: nil)
			resultUserAttributes = nil
		}
		else {
			lastError = nil
			resultUserAttributes = result as? [String:AnyObject]
		}
	}


	// MARK: Template methods

	internal func sendGetUserRequest(
			#service: LRUserService_v62,
			error: NSErrorPointer)
			-> NSDictionary? {

		assertionFailure("sendGetUserRequest must be overriden")

		return nil
	}

   
}
