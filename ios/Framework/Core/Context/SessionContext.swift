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

#if LIFERAY_SCREENS_FRAMEWORK
	import LRMobileSDK
	import LROAuth
#endif


@objc public class SessionContext: NSObject {

	public static var currentContext: SessionContext?

	public let session: LRSession
	public let userAttributes: [String:AnyObject]

	public let cacheManager: CacheManager
	public var credentialsStorage: CredentialsStorage


	public init(session: LRSession, attributes: [String: AnyObject], store: CredentialsStore) {
		self.session = session
		self.userAttributes = attributes

		cacheManager = LiferayServerContext.factory.createCacheManager(
			session: session,
			userId: userAttributes["userId"]?.description?.asLong ?? 0)

		credentialsStorage = CredentialsStorage(store: store)

		super.init()
	}


	//MARK: Public properties

	public class var isLoggedIn: Bool {
		return currentContext?.session != nil
	}

	public var basicAuthUsername: String? {
		guard let auth = session.authentication as? LRBasicAuthentication else {
			return nil
		}

		return auth.username
	}

	public var basicAuthPassword: String? {
		guard let auth = session.authentication as? LRBasicAuthentication else {
			return nil
		}

		return auth.password
	}

	public var userId: Int64? {
		return userAttributes["userId"]?.description?.asLong
	}


	//MARK Public methods

	public class func createEphemeralBasicSession(
			userName: String,
			_ password: String) -> LRSession {
		return LRSession(
			server: LiferayServerContext.server,
			authentication: LRBasicAuthentication(
				username: userName,
				password: password))
	}

	public class func loginWithBasic(
			username username: String,
			password: String,
			userAttributes: [String:AnyObject]) -> LRSession {

		let authentication = LRBasicAuthentication(
			username: username,
			password: password)

		let session = LRSession(
			server: LiferayServerContext.server,
			authentication: authentication)

		let store = LiferayServerContext.factory.createCredentialsStore(AuthType.Basic)

		SessionContext.currentContext =
			LiferayServerContext.factory.createSessionContext(
				session: session,
				attributes: userAttributes,
				store: store)

		return session
	}

	public class func loginWithOAuth(
			authentication authentication: LROAuth,
			userAttributes: [String:AnyObject]) -> LRSession {

		let session = LRSession(
			server: LiferayServerContext.server,
			authentication: authentication)

		let store = LiferayServerContext.factory.createCredentialsStore(AuthType.OAuth)

		SessionContext.currentContext =
			LiferayServerContext.factory.createSessionContext(
				session: session,
				attributes: userAttributes,
				store: store)

		return session
	}

	public func userAttribute(key: String) -> AnyObject? {
		return userAttributes[key]
	}

	public func createRequestSession() -> LRSession {
		return LRSession(session: session)
	}

	public func relogin(completed: ([String:AnyObject]? -> ())?) -> Bool {
		if session.authentication is LRBasicAuthentication {
			return reloginBasic(completed)
		}
		else if session.authentication is LROAuth {
			return reloginOAuth(completed)
		}

		return false
	}

	public func reloginBasic(completed: ([String:AnyObject]? -> ())?) -> Bool {
		guard let userName = self.basicAuthUsername,
				password = self.basicAuthPassword else {
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

	public func reloginOAuth(completed: ([String:AnyObject]? -> ())?) -> Bool {
		guard let auth = self.session.authentication as? LROAuth else {
			completed?(nil)
			return false
		}

		return refreshUserAttributes { attributes in
			if let attributes = attributes {
				SessionContext.loginWithOAuth(authentication: auth, userAttributes: attributes)
			}
			else {
				SessionContext.logout()
			}

			completed?(attributes)
		}
	}

	public func refreshUserAttributes(completed: ([String:AnyObject]? -> ())?) -> Bool {
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
			failure: { err in
				completed?(nil)
		})

		switch LiferayServerContext.serverVersion {
		case .v62:
			let srv = LRScreensuserService_v62(session: session)

			_ = try? srv.getCurrentUser()

		case .v70:
			let srv = LRUserService_v7(session: session)

			_ = try? srv.getCurrentUser()
		}
		
		return true
	}

	public class func logout() {
		SessionContext.currentContext = nil
	}

	public func storeCredentials() -> Bool {
		return credentialsStorage.store(
				session: session,
				userAttributes: userAttributes)
	}

	public func removeStoredCredentials() -> Bool {
		return credentialsStorage.remove()
	}

	public class func loadStoredCredentials() -> Bool {
		guard let storage = CredentialsStorage.createFromStoredAuthType() else {
			return false
		}

		return loadStoredCredentials(storage)
	}

	public class func loadStoredCredentials(storage: CredentialsStorage) -> Bool {
		guard let result = storage.load() else {
			return false
		}
		guard result.session.server != nil else {
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
	public class func createSessionFromCurrentSession() -> LRSession? {
		return SessionContext.currentContext?.createRequestSession()
	}

}