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

open class LoginOAuth2UsernamePasswordInteractor: ServerConnectorInteractor, LoginResult {

	open let username: String
	open let password: String
	open let scopes: [String]
	open let clientId: String
	open let clientSecret: String

	open var oauth2Session: LRSession?
	open var resultUserAttributes: [String: AnyObject]?

	// Initializers

	public init(screenlet: BaseScreenlet?, username: String, password: String, scopes: [String] = [],
		clientId: String, clientSecret: String) {

		self.username = username
		self.password = password
		self.scopes = scopes
		self.clientId = clientId
		self.clientSecret = clientSecret

		super.init(screenlet: screenlet)
	}

	open override func createConnector() -> ServerConnector? {
		guard let oauth2Connector =  LiferayServerContext.connectorFactory.createLoginByOAuth2PasswordConnector(
			username: username,
			password: password,
			scopes: scopes,
			clientId: clientId,
			clientSecret: clientSecret) else {
				return nil
		}

		let chain = ServerConnectorChain(head: oauth2Connector)

		chain.onNextStep = { c, _ in
			guard let c = c as? LoginByOAuth2UsernamePasswordConnector,
				c.lastError == nil else {
				return nil
			}

			return LiferayServerContext.connectorFactory.createGetCurrentUserConnector(session: c.oauth2Session!)
		}

		return chain
	}

	open override func completedConnector(_ c: ServerConnector) {
		if let c = (c as? ServerConnectorChain)?.currentConnector as? GetCurrentUserConnector {
			self.resultUserAttributes = c.resultUserAttributes
			self.oauth2Session = c.session
			loginWithResult()
		}
	}

	open func loginWithResult() {
		SessionContext.currentContext?.removeStoredCredentials()

		let oauth2Authentication = self.oauth2Session?.authentication as! LROAuth2Authentication
		SessionContext.loginWithOAuth2(authentication: oauth2Authentication,
			userAttributes: resultUserAttributes ?? [:])
	}
}
