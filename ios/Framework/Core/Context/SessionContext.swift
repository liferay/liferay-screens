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
			userId: userAttributes["userId"]?.longLongValue ?? 0)

		credentialsStorage = CredentialsStorage(store: store)

		super.init()
	}


	//MARK: Public properties

	public class var isLoggedIn: Bool {
		return currentContext?.session != nil
	}

	public var basicAuthUsername: String? {
		let authentication = session.authentication
			as? LRBasicAuthentication

		return authentication?.username
	}

	public var basicAuthPassword: String? {
		let authentication = session.authentication
			as? LRBasicAuthentication

		return authentication?.password
	}

	public var userId: Int64? {
		return userAttributes["userId"]?.longLongValue
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