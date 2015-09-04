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


@objc public class SessionContext {

	//MARK: Singleton type

	private struct StaticInstance {
		static var currentSession: LRSession?
		static var userAttributes: [String:AnyObject] = [:]

		static var chacheManager: SessionCacheManager?

		static var sessionStorage = SessionStorage(
			credentialStore: BasicCredentialsStoreKeyChain())
	}


	//MARK: Public properties

	public class var hasSession: Bool {
		return StaticInstance.currentSession != nil
	}

	public class var currentBasicUserName: String? {
		var authentication = StaticInstance.currentSession?.authentication
			as? LRBasicAuthentication

		return authentication?.username
	}

	public class var currentBasicPassword: String? {
		var authentication = StaticInstance.currentSession?.authentication
			as? LRBasicAuthentication

		return authentication?.password
	}

	public class var currentUserId: Int64? {
		return StaticInstance.userAttributes["userId"]
				.map { $0 as! NSNumber }
				.map { $0.longLongValue }
	}

	public class var currentCacheManager: SessionCacheManager? {
		return StaticInstance.chacheManager
	}

	internal class var sessionStorage: SessionStorage {
		get {
			return StaticInstance.sessionStorage
		}
		set {
			StaticInstance.sessionStorage = newValue
		}
	}

	//MARK Public methods

	public class func userAttribute(key: String) -> AnyObject? {
		return StaticInstance.userAttributes[key]
	}

	public class func createAnonymousBasicSession(userName: String, _ password: String) -> LRSession {
		return LRSession(
			server: LiferayServerContext.server,
			authentication: LRBasicAuthentication(
				username: userName,
				password: password))
	}

	public class func createBasicSession(
			#username: String,
			password: String,
			userAttributes: [String:AnyObject])
			-> LRSession {

		sessionStorage = SessionStorage(
			credentialStore: BasicCredentialsStoreKeyChain())

		let authentication = LRBasicAuthentication(
				username: username,
				password: password)

		return createSession(
				authentication: authentication,
				userAttributes: userAttributes)
	}

	public class func createOAuthSession(
			#authentication: LROAuth,
			userAttributes: [String:AnyObject])
			-> LRSession {

		sessionStorage = SessionStorage(
			credentialStore: OAuthCredentialsStoreKeyChain())

		return createSession(
				server: LiferayServerContext.server,
				authentication: authentication,
				userAttributes: userAttributes)
	}


	private class func createSession(
			#authentication: LRAuthentication,
			userAttributes: [String:AnyObject])
			-> LRSession {

		return createSession(
				server: LiferayServerContext.server,
				authentication: authentication,
				userAttributes: userAttributes)
	}

	public class func createSessionFromCurrentSession() -> LRSession? {
		if let currentSessionValue = StaticInstance.currentSession {
			return LRSession(session: currentSessionValue)
		}

		return nil
	}

	public class func createBatchSessionFromCurrentSession() -> LRBatchSession? {
		if let currentSessionValue = StaticInstance.currentSession {
			return LRBatchSession(session: currentSessionValue)
		}

		return nil
	}

	public class func clearSession() {
		StaticInstance.currentSession = nil
		StaticInstance.userAttributes = [:]
		StaticInstance.chacheManager = nil
	}

	public class func storeSession() -> Bool {
		return sessionStorage.store(
				session: StaticInstance.currentSession,
				userAttributes: StaticInstance.userAttributes)
	}

	public class func removeStoredSession() -> Bool {
		return sessionStorage.remove()
	}

	public class func loadSessionFromStore() -> Bool {
		if let sessionStorage = SessionStorage() {
			if let result = sessionStorage.load()
					where result.session.server != nil {
				StaticInstance.currentSession = result.session
				StaticInstance.userAttributes = result.userAttributes

				StaticInstance.chacheManager = SessionCacheManager(
					session: result.session,
					cacheManager: CacheManager(
						name: result.session.serverName!))

				return true
			}

			clearSession()
		}

		return false
	}


	//MARK Private methods

	private class func createSession(
			#server: String,
			authentication: LRAuthentication,
			userAttributes: [String:AnyObject])
			-> LRSession {

		let session = LRSession(server: server, authentication: authentication)

		StaticInstance.currentSession = session
		StaticInstance.userAttributes = userAttributes

		StaticInstance.chacheManager = SessionCacheManager(
			session: session,
			cacheManager: CacheManager(name: server))

		return session
	}

}