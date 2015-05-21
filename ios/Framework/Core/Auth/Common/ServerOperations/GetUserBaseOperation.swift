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

public class GetUserBaseOperation: ServerOperation {

	public var resultUserAttributes: [String:AnyObject]?

	public var userName: String?
	public var password: String?

	//MARK: ServerOperation

	override internal func validateData() -> Bool {
		var valid = super.validateData()
		
		if !SessionContext.hasSession {
			valid = valid && (userName != nil)
			valid = valid && (password != nil)
		}

		return valid
	}

	override func createSession() -> LRSession? {
		if SessionContext.hasSession {
			return SessionContext.createSessionFromCurrentSession()
		}

		return LRSession(
				server: LiferayServerContext.server,
				authentication: LRBasicAuthentication(
						username: userName!,
						password: password!))
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
			lastError = NSError.errorWithCause(.InvalidServerResponse)
			resultUserAttributes = nil
		}
		else {
			lastError = nil
			resultUserAttributes = result as? [String:AnyObject]
		}
	}

	internal func setResultAsSessionContext() -> Bool {
		if let userAttributesValue = resultUserAttributes {
			SessionContext.createSession(
					username: self.userName!,
					password: self.password!,
					userAttributes: userAttributesValue)

			return true
		}

		return false
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
