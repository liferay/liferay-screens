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

open class LoginByOAuth2UsernamePasswordConnector: ServerConnector {

	open let username: String
	open let password: String
	open let scopes: [String]
	open let clientId: String
	open let clientSecret: String

	open var oauth2Session: LRSession?

	public init(username: String, password: String, scopes: [String] = [], clientId: String, clientSecret: String) {

		self.username = username
		self.password = password
		self.scopes = scopes
		self.clientId = clientId
		self.clientSecret = clientSecret

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
			if clientId.isEmpty {
				return ValidationError("login-screenlet", "undefined-clientId")
			}
			if clientSecret.isEmpty {
				return ValidationError("login-screenlet", "undefined-clientSecret")
			}
		}

		return error
	}

	override open func createSession() -> LRSession? {
		return LRSession(server: LiferayServerContext.server)
	}

	open override func doRun(session: LRSession) {
		do {
			oauth2Session = try LROAuth2SignIn.signIn(withUsername: username,
				password: password,
				session: LRSession(server: LiferayServerContext.server),
				clientId: clientId,
				clientSecret: clientSecret,
				scopes: scopes,
				callback: nil)
		}
		catch let error as NSError {
			lastError = error
		}
	}
}
