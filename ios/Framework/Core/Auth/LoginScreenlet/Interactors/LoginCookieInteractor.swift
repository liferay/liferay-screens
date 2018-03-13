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

open class LoginCookieInteractor: ServerConnectorInteractor {

	open let emailAddress: String
	open let password: String
	open let shouldHandleCookieExpiration: Bool
	open let cookieExpirationTime: Double

	open var cookieSession: LRSession?
	open var resultUserAttributes: [String: AnyObject]?

	// MARK: Initializers

	public init(screenlet: BaseScreenlet?, emailAddress: String, password: String,
				shouldHandleCookieExpiration: Bool, cookieExpirationTime: Double) {

		self.emailAddress = emailAddress
		self.password = password
		self.shouldHandleCookieExpiration = shouldHandleCookieExpiration
		self.cookieExpirationTime = cookieExpirationTime

		super.init(screenlet: screenlet)
	}

	open override func createConnector() -> ServerConnector? {

		return LiferayServerContext.connectorFactory.createLoginByCookieConnector(
			emailAddress: emailAddress,
			password: password,
			shouldHandleCookieExpiration: shouldHandleCookieExpiration,
			cookieExpirationTime: cookieExpirationTime)
	}

	open override func completedConnector(_ c: ServerConnector) {
		self.resultUserAttributes = (c as! LoginByCookieConnector).resultUserAttributes
	}
}
