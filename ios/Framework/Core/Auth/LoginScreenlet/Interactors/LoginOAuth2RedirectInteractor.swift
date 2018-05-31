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

open class LoginOAuth2RedirectInteractor: Interactor, LoginResult {

	open let redirectURL: String
	open let scopes: [String]
	open let clientId: String

	open var resultUserAttributes: [String: AnyObject]?

	// Initializers

	public init(redirectURL: String, scopes: [String] = [], clientId: String) {

		self.redirectURL = redirectURL
		self.scopes = scopes
		self.clientId = clientId

		super.init(screenlet: nil)
	}

	open override func start() -> Bool {
		guard let redirectURL = URL(string: redirectURL) else {
			callOnFailure(ValidationError("Provide a valid redirectURL"))
			return false
		}

		let session = LRSession(server: LiferayServerContext.server)

		SessionContext.oauth2AuthorizationFlow = LROAuth2SignIn.signIn(withRedirectURL: redirectURL,
			session: session, clientId: clientId, scopes: scopes) { [unowned self] session, error in

			if let session = session {
				self.getUserAttributes(session: session)
			}
			else {
				self.callOnFailure(error! as NSError)
			}
		}

		return true
	}

	open func getUserAttributes(session: LRSession) {
		if let getCurrentUserConnector = LiferayServerContext.connectorFactory
			.createGetCurrentUserConnector(session: session) {

			getCurrentUserConnector.validateAndEnqueue { [unowned self] c in
				guard c.lastError == nil else {
					self.callOnFailure(c.lastError!)
					return
				}
				self.resultUserAttributes = (c as! GetCurrentUserConnector).resultUserAttributes
				self.login(with: session)
				self.callOnSuccess()
			}
		}
		else {
			self.callOnFailure(NSError.errorWithCause(.abortedDueToPreconditions,
				message: "An error ocurred when creating the connector."))
		}
	}

	open func login(with session: LRSession) {
		SessionContext.currentContext?.removeStoredCredentials()

		let oauth2Authentication = session.authentication as! LROAuth2Authentication
		SessionContext.loginWithOAuth2(authentication: oauth2Authentication,
									   userAttributes: resultUserAttributes ?? [:])
	}
}
