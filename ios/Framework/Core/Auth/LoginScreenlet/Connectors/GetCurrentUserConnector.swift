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

open class GetCurrentUserConnector: ServerConnector {

	open let session: LRSession

	open var resultUserAttributes: [String: AnyObject]?

	public init(session: LRSession) {
		self.session = session

		super.init()
	}

	override open func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if session.server.isEmpty {
				return ValidationError("Server must not be nil in session")
			}
			if session.authentication == nil {
				return ValidationError("Server must have an authentication")
			}
		}

		return error
	}

	override open func createSession() -> LRSession? {
		return session
	}

	open override func doRun(session: LRSession) {
		do {
			resultUserAttributes = try getCurrentUser(session: session)
		}
		catch let error as NSError {
			lastError = error
		}
	}

	internal func getCurrentUser(session: LRSession) throws -> [String: AnyObject] {
		fatalError("getCurrentUser method must be overwritten")
	}
}

open class Liferay62GetCurrentUserConnector: GetCurrentUserConnector {

	// MARK: GetCurrentUserConnector

	override func getCurrentUser(session: LRSession) throws -> [String: AnyObject] {
		let service = LRScreensuserService_v62(session: session)

		return try service.getCurrentUser() as! [String: AnyObject]
	}
}

open class Liferay70GetCurrentUserConnector: GetCurrentUserConnector {

	// MARK: GetCurrentUserConnector

	override func getCurrentUser(session: LRSession) throws -> [String: AnyObject] {
		let service = LRUserService_v7(session: session)

		return try service.getCurrentUser() as! [String: AnyObject]
	}
}
