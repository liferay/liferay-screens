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

open class LoginCookieInteractor: ServerConnectorInteractor, LoginResult {

	open let username: String
	open let password: String
	open let shouldHandleCookieExpiration: Bool
	open let cookieExpirationTime: Double

	open var cookieSession: LRSession?
	open var resultUserAttributes: [String: AnyObject]?

	// MARK: Initializers

	public init(screenlet: BaseScreenlet?, username: String, password: String,
				shouldHandleCookieExpiration: Bool, cookieExpirationTime: Double) {

		self.username = username
		self.password = password
		self.shouldHandleCookieExpiration = shouldHandleCookieExpiration
		self.cookieExpirationTime = cookieExpirationTime

		super.init(screenlet: screenlet)
	}

	open override func createConnector() -> ServerConnector? {

		let cookieConnector = LiferayServerContext.connectorFactory.createLoginByCookieConnector(
			username: username,
			password: password,
			shouldHandleCookieExpiration: shouldHandleCookieExpiration,
			cookieExpirationTime: cookieExpirationTime)

		let chain = ServerConnectorChain(head: cookieConnector)

		chain.onNextStep = { c, _ in
			guard let c = c as? LoginByCookieConnector,
				c.lastError == nil else {
					return nil
			}

			return LiferayServerContext.connectorFactory.createGetCurrentUserConnector(session: c.cookieSession!)
		}

		return chain
	}

	open override func completedConnector(_ c: ServerConnector) {
		if let c = (c as? ServerConnectorChain)?.currentConnector as? GetCurrentUserConnector {
			self.resultUserAttributes = c.resultUserAttributes
			self.cookieSession = c.session
			self.loginWithResult()
		}
	}

	open func loginWithResult() {
		SessionContext.currentContext?.removeStoredCredentials()
		let cookieAuthentication = self.cookieSession?.authentication as! LRCookieAuthentication
		SessionContext.loginWithCookie(authentication: cookieAuthentication,
				userAttributes: resultUserAttributes ?? [:])
	}
}
