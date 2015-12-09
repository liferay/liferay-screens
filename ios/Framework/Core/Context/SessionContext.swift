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

	//MARK: Singleton type

	private struct StaticInstance {
		static var currentUserSession: LRSession?
		static var currentUserAttributes = [String:AnyObject]()

		static var chacheManager: CacheManager?

		static var credentialsStorage = CredentialsStorage(
			credentialStore: BasicCredentialsStoreKeyChain())
	}


	//MARK: Public properties

	public class var isLoggedIn: Bool {
		return StaticInstance.currentUserSession != nil
	}

	public class var currentBasicUserName: String? {
		let authentication = StaticInstance.currentUserSession?.authentication
			as? LRBasicAuthentication

		return authentication?.username
	}

	public class var currentBasicPassword: String? {
		let authentication = StaticInstance.currentUserSession?.authentication
			as? LRBasicAuthentication

		return authentication?.password
	}

	public class var currentUserId: Int64? {
		return StaticInstance.currentUserAttributes["userId"]
				.map { $0 as! NSNumber }
				.map { $0.longLongValue }
	}

	public class var currentCacheManager: CacheManager? {
		return StaticInstance.chacheManager
	}

	internal class var credentialsStorage: CredentialsStorage {
		get {
			return StaticInstance.credentialsStorage
		}
		set {
			StaticInstance.credentialsStorage = newValue
		}
	}

	//MARK Public methods

	public class func userAttribute(key: String) -> AnyObject? {
		return StaticInstance.currentUserAttributes[key]
	}

	public class func createAnonymousBasicSession(userName: String, _ password: String) -> LRSession {
		return LRSession(
			server: LiferayServerContext.server,
			authentication: LRBasicAuthentication(
				username: userName,
				password: password))
	}

	public class func createBasicSession(
			username username: String,
			password: String,
			userAttributes: [String:AnyObject])
			-> LRSession {

		credentialsStorage = CredentialsStorage(
			credentialStore: BasicCredentialsStoreKeyChain())

		let authentication = LRBasicAuthentication(
				username: username,
				password: password)

		return createSession(
				authentication: authentication,
				userAttributes: userAttributes)
	}

	public class func createOAuthSession(
			authentication authentication: LROAuth,
			userAttributes: [String:AnyObject])
			-> LRSession {

		credentialsStorage = CredentialsStorage(
			credentialStore: OAuthCredentialsStoreKeyChain())

		return createSession(
				server: LiferayServerContext.server,
				authentication: authentication,
				userAttributes: userAttributes)
	}


	private class func createSession(
			authentication authentication: LRAuthentication,
			userAttributes: [String:AnyObject])
			-> LRSession {

		return createSession(
				server: LiferayServerContext.server,
				authentication: authentication,
				userAttributes: userAttributes)
	}

	public class func createSessionFromCurrentSession() -> LRSession? {
		if let currentSessionValue = StaticInstance.currentUserSession {
			return LRSession(session: currentSessionValue)
		}

		return nil
	}

	public class func createBatchSessionFromCurrentSession() -> LRBatchSession? {
		if let currentSessionValue = StaticInstance.currentUserSession {
			return LRBatchSession(session: currentSessionValue)
		}

		return nil
	}

	public class func logout() {
		StaticInstance.currentUserSession = nil
		StaticInstance.currentUserAttributes = [:]
		StaticInstance.chacheManager = nil
	}

	public class func storeCredentials() -> Bool {
		return credentialsStorage.store(
				session: StaticInstance.currentUserSession,
				userAttributes: StaticInstance.currentUserAttributes)
	}

	public class func removeStoredCredentials() -> Bool {
		return credentialsStorage.remove()
	}

	public class func loadStoredCredentials() -> Bool {
		let credentialsStorage = CredentialsStorage()

		if credentialsStorage.hasCredentialsStored {
			if let result = credentialsStorage.load()
					where result.session.server != nil {

				StaticInstance.currentUserSession = result.session
				StaticInstance.currentUserAttributes = result.userAttributes
				StaticInstance.chacheManager = CacheManager(session: result.session)

				return true
			}
		}

		return false
	}


	//MARK Private methods

	private class func createSession(
			server server: String,
			authentication: LRAuthentication,
			userAttributes: [String:AnyObject])
			-> LRSession {

		let session = LRSession(server: server, authentication: authentication)

		StaticInstance.currentUserSession = session
		StaticInstance.currentUserAttributes = userAttributes
		StaticInstance.chacheManager = CacheManager(session: session)

		return session
	}

}