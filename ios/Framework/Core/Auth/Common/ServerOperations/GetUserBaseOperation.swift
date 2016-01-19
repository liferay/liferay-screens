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

	override public func validateData() -> ValidationError? {
		let error = super.validateData()
		
		if !SessionContext.isLoggedIn {
			if userName == nil {
				return ValidationError("login-screenlet", "undefined-username")
			}

			if password == nil {
				return ValidationError("login-screenlet", "undefined-password")
			}
		}

		return error
	}

	override public func createSession() -> LRSession? {
		if SessionContext.isLoggedIn {
			return SessionContext.createSessionFromCurrentSession()
		}

		return LRSession(
				server: LiferayServerContext.server,
				authentication: LRBasicAuthentication(
						username: userName!,
						password: password!))
	}

	override public func doRun(session session: LRSession) {
		do {
			let result = try sendGetUserRequest(
				service: LRUserService_v62(session: session))

			if result?["userId"] == nil {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
				resultUserAttributes = nil
			}
			else {
				lastError = nil
				resultUserAttributes = extractUserAttributes(result)
			}
		}
		catch let error as NSError {
			lastError = error
			resultUserAttributes = nil
		}
	}

	internal func setResultAsSessionContext() -> Bool {
		if let userAttributesValue = resultUserAttributes {
			SessionContext.createBasicSession(
					username: self.userName!,
					password: self.password!,
					userAttributes: userAttributesValue)

			return true
		}

		return false
	}

	internal func extractUserAttributes(result: NSDictionary?) -> [String: AnyObject]? {
		guard var userAttributes = result as? [String: AnyObject] else {
			return nil
		}

		// LSR-745: Liferay 7 sends all fields as string.
		// Some were numbers in Liferay 6.2

		let stringFields = ["userId", "companyId", "portraitId", "contactId"]

		stringFields.forEach {
			if let userId = userAttributes[$0] as? String {
				userAttributes[$0] = userId.asNumber()!
			}
		}

		return userAttributes
	}


	// MARK: Template methods

	internal func sendGetUserRequest(
			service service: LRUserService_v62)
			throws -> NSDictionary? {

		fatalError("sendGetUserRequest must be overriden")
	}

   
}
