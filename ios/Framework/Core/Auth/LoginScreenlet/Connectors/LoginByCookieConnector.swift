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

open class LoginByCookieConnector: ServerConnector {

	open let username: String
	open let password: String
	open let shouldHandleCookieExpiration: Bool
	open let cookieExpirationTime: Double

	open var cookieSession: LRSession?

	public init(username: String, password: String, shouldHandleCookieExpiration: Bool,
				cookieExpirationTime: Double) {

		self.username = username
		self.password = password
		self.shouldHandleCookieExpiration = shouldHandleCookieExpiration
		self.cookieExpirationTime = cookieExpirationTime

		super.init()
	}

	override open func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if username.isEmpty {
				return ValidationError("login-screenlet", "undefined-username")
			}
			if password.isEmpty {
				return ValidationError("login-screenlet", "undefined-password")
			}
			if cookieExpirationTime < 0 {
				return ValidationError("login-screenlet", "undefined-password")
			}
		}

		return error
	}

	override open func createSession() -> LRSession? {

		let authentication = LRCookieAuthentication(authToken: "", cookieHeader: "", username: username,
			password: password, shouldHandleExpiration: shouldHandleCookieExpiration,
			cookieExpirationTime: cookieExpirationTime, lastCookieRefresh: 0)

		return LRSession(server: LiferayServerContext.server, authentication: authentication)
	}

	open override func doRun(session: LRSession) {

		do {
			cookieSession = try LRCookieSignIn.signIn(with: session, callback: nil)
		}
		catch let error as NSError {
			lastError = error
		}
	}
}
