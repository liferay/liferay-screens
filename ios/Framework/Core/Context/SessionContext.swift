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
import Foundation

@objc(SessionContext)
@objcMembers
open class SessionContext: NSObject {

	open static var currentContext: SessionContext?
	open static var challengeResolver: ChallengeBlock? {
		didSet {
			guard let challengeResolver = challengeResolver else { return }
			LRCookieSignIn.registerAuthenticationChallenge(challengeResolver, forServer: LiferayServerContext.server)
		}
	}
	open static var oauth2AuthorizationFlow: LROAuth2AuthorizationFlow?

	open let session: LRSession
	open let user: User

	open let cacheManager: CacheManager
	open var credentialsStorage: CredentialsStorage

	@available(*, deprecated: 2.0.1, message: "Use public property user")
	open var userId: Int64 {
		return user.userId
	}

	@available(*, deprecated: 2.0.1, message: "Access it throught user.attributes")
	open var userAttributes: [String: AnyObject] {
		return user.attributes
	}

	public init(session: LRSession, attributes: [String: AnyObject], store: CredentialsStore) {
		self.session = session
		self.user = User(attributes: attributes)

		let userId = user.userId

		cacheManager = LiferayServerContext.factory.createCacheManager(
			session: session,
			userId: userId)

		credentialsStorage = CredentialsStorage(store: store)

		super.init()
	}

	// MARK: Public properties

	open class var isLoggedIn: Bool {
		return currentContext?.session != nil
	}

	open var basicAuthUsername: String? {
		if let auth = session.authentication as? LRBasicAuthentication {
			return auth.username
		}

		if let auth = session.authentication as? LRCookieAuthentication {
			return auth.username
		}

		return nil
	}

	open var basicAuthPassword: String? {
		if let auth = session.authentication as? LRBasicAuthentication {
			return auth.password
		}

		if let auth = session.authentication as? LRCookieAuthentication {
			return auth.password
		}

		return nil
	}

	// MARK: Public methods

	open class func createEphemeralBasicSession(
			_ userName: String,
			_ password: String) -> LRSession {
		return LRSession(
			server: LiferayServerContext.server,
			authentication: LRBasicAuthentication(
				username: userName,
				password: password))
	}

	@discardableResult
	open class func loginWithBasic(
			username: String,
			password: String,
			userAttributes: [String: AnyObject]) -> LRSession {

		let authentication = LRBasicAuthentication(
			username: username,
			password: password)

		let session = LRSession(
			server: LiferayServerContext.server,
			authentication: authentication)

		let store = LiferayServerContext.factory.createCredentialsStore(AuthType.basic)

		SessionContext.currentContext =
			LiferayServerContext.factory.createSessionContext(
				session: session,
				attributes: userAttributes,
				store: store)

		return session
	}

	@discardableResult
	open class func loginWithCookie(
		authentication: LRCookieAuthentication,
		userAttributes: [String: AnyObject]) -> LRSession {

		let session = LRSession(
			server: LiferayServerContext.server,
			authentication: authentication)

		let store = LiferayServerContext.factory.createCredentialsStore(AuthType.cookie)

		SessionContext.currentContext =
			LiferayServerContext.factory.createSessionContext(
				session: session,
				attributes: userAttributes,
				store: store)

		return session
	}

	@discardableResult
	open class func loginWithOAuth2(
		authentication: LROAuth2Authentication,
		userAttributes: [String: AnyObject]) -> LRSession {

		let session = LRSession(
			server: LiferayServerContext.server,
			authentication: authentication)

		let store = LiferayServerContext.factory.createCredentialsStore(AuthType.oauth2UsernameAndPassword)

		SessionContext.currentContext =
			LiferayServerContext.factory.createSessionContext(
				session: session,
				attributes: userAttributes,
				store: store)

		return session
	}

	open class func reloadCookieAuth(session: LRSession? = SessionContext.currentContext?.createRequestSession(),
									 callback: LRBlockCookieCallback) {

		let currentAttrs = SessionContext.currentContext?.user.attributes ?? [:]

		_ = try? LRCookieSignIn.signIn(with: session!, callback: LRBlockCookieCallback.init(success: { session in
			SessionContext.loginWithCookie(authentication: session.authentication as! LRCookieAuthentication,
				userAttributes: currentAttrs)
			callback.onSuccess(session)
		}, failure: { (error) in
			callback.onFailure(error)
		}))
	}

	open func createRequestSession() -> LRSession {
		return LRSession(session: session)
	}

	open func relogin(_ completed: (([String: AnyObject]?) -> Void)?) -> Bool {
		if session.authentication is LRBasicAuthentication {
			return reloginBasic(completed)
		}
		else if session.authentication is LRCookieAuthentication {
			return reloginCookie(completed)
		}
		else if session.authentication is LROAuth2Authentication {
			return reloginOAuth2(completed)
		}

		return false
	}

	open func reloginBasic(_ completed: (([String: AnyObject]?) -> Void)?) -> Bool {
		guard let userName = self.basicAuthUsername,
				let password = self.basicAuthPassword else {
			completed?(nil)
			return false
		}

		return refreshUserAttributes { attributes in
			if let attributes = attributes {
				SessionContext.loginWithBasic(
					username: userName,
					password: password,
					userAttributes: attributes)
			}

			completed?(attributes)
		}
	}

	open func reloginCookie(_ completed: (([String: AnyObject]?) -> Void)?) -> Bool {
		guard session.authentication is LRCookieAuthentication else {
			completed?(nil)
			return false
		}

		let callback = LRBlockCookieCallback(success: { session in
			let cookieAuth = session.authentication as! LRCookieAuthentication
			_ = SessionContext.currentContext?.refreshUserAttributes { attributes in
				if let attributes = attributes {
					SessionContext.loginWithCookie(authentication: cookieAuth, userAttributes: attributes)
				}
				else {
					SessionContext.logout()
				}

				completed?(attributes)
			}
		}, failure: { error in
			print("Error reloading the cookie auth\(error)")
			completed?(nil)
		})

		SessionContext.reloadCookieAuth(session: self.session, callback: callback)

		return true
	}

	open func reloginOAuth2(_ completed: (([String: AnyObject]?) -> Void)?) -> Bool {
		return refreshUserAttributes { [unowned self] attributes in
			if let attributes = attributes {
				let oauth2Authentication = self.session.authentication as! LROAuth2Authentication
				SessionContext.loginWithOAuth2(authentication: oauth2Authentication, userAttributes: attributes)
			}

			completed?(attributes)
		}
	}

	open func refreshUserAttributes(_ completed: (([String: AnyObject]?) -> Void)?) -> Bool {
		let session = self.createRequestSession()

		session.callback = LRBlockCallback(
			success: { obj in
				guard let attributes = obj as? [String:AnyObject] else {
					SessionContext.logout()
					completed?(nil)
					return
				}

				completed?(attributes)
			},
			failure: { _ in
				completed?(nil)
		})

		switch LiferayServerContext.serverVersion {
		case .v62:
			let srv = LRScreensuserService_v62(session: session)

			_ = try? srv.getCurrentUser()

		case .v70, .v71:
			let srv = LRUserService_v7(session: session)

			_ = try? srv.getCurrentUser()
		}

		return true
	}

	open class func logout() {
		if SessionContext.currentContext?.session.authentication as? LRCookieAuthentication != nil {
			HTTPCookieStorage.shared.removeCookies(since: .distantPast)
		}
		SessionContext.currentContext = nil
	}

	@discardableResult
	open func storeCredentials() -> Bool {
		return credentialsStorage.store(
				session: session,
				userAttributes: user.attributes)
	}

	@discardableResult
	open func removeStoredCredentials() -> Bool {
		return credentialsStorage.remove()
	}

	@discardableResult
	open class func loadStoredCredentials() -> Bool {
		return loadStoredCredentials(shouldLoadServer: false)
	}

	@discardableResult
	open class func loadStoredCredentialsAndServer() -> Bool {
		return loadStoredCredentials(shouldLoadServer: true)
	}

	private class func loadStoredCredentials(shouldLoadServer: Bool) -> Bool {
		guard let storage = CredentialsStorage.createFromStoredAuthType() else {
			return false
		}

		return loadStoredCredentials(storage, shouldLoadServer: shouldLoadServer)
	}

	@discardableResult
	open class func loadStoredCredentials(_ storage: CredentialsStorage, shouldLoadServer: Bool = false) -> Bool {
		guard let result = storage.load(shouldLoadServer: shouldLoadServer) else {
			return false
		}

		SessionContext.currentContext =
			LiferayServerContext.factory.createSessionContext(
				session: result.session,
				attributes: result.userAttributes,
				store: storage.credentialsStore)

		return true
	}

	// Deprecated. Will be removed in next version
	open class func createSessionFromCurrentSession() -> LRSession? {
		return SessionContext.currentContext?.createRequestSession()
	}

	open class func register(authenticationChallenge: @escaping ChallengeBlock, for server: String) {
		LRCookieSignIn.registerAuthenticationChallenge(authenticationChallenge, forServer: server)
	}

	open class func oauth2ResumeAuthorization(url: URL) -> Bool {
		return SessionContext.oauth2AuthorizationFlow?.resumeAuthorizationFlow(with: url) ?? false
	}

	open class func oauth2CancelAuthorization() {
		SessionContext.oauth2AuthorizationFlow?.cancel()
	}

}
