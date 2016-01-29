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

	internal(set) static var currentContext: SessionContext?


	internal(set) var currentUserSession: LRSession
	internal(set) var currentUserAttributes: [String:AnyObject]

	internal(set) var cacheManager: CacheManager
	internal(set) var credentialsStorage: CredentialsStorage


	//MARK: Private init

	private init(session: LRSession, attributes: [String: AnyObject], store: CredentialsStore) {
		self.currentUserSession = session
		self.currentUserAttributes = attributes

		cacheManager = CacheManager(session: session)

		credentialsStorage = CredentialsStorage(store: store)

		super.init()
	}


	//MARK: Public properties

	public class var isLoggedIn: Bool {
		return currentContext?.currentUserSession != nil
	}

	public var currentBasicUserName: String? {
		let authentication = currentUserSession.authentication
			as? LRBasicAuthentication

		return authentication?.username
	}

	public var currentBasicPassword: String? {
		let authentication = currentUserSession.authentication
			as? LRBasicAuthentication

		return authentication?.password
	}

	public var currentUserId: Int64? {
		return currentUserAttributes["userId"]
				.map { $0 as! NSNumber }
				.map { $0.longLongValue }
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

	public class func createBasicSessionContext(
			username username: String,
			password: String,
			userAttributes: [String:AnyObject]) -> LRSession {

		let authentication = LRBasicAuthentication(
			username: username,
			password: password)

		let session = LRSession(
			server: LiferayServerContext.server,
			authentication: authentication)

		SessionContext.currentContext = SessionContext(
			session: session,
			attributes: userAttributes,
			store: BasicCredentialsStoreKeyChain())

		return session
	}

	public class func createOAuthSession(
			authentication authentication: LROAuth,
			userAttributes: [String:AnyObject]) -> LRSession {

		let session = LRSession(
			server: LiferayServerContext.server,
			authentication: authentication)

		SessionContext.currentContext = SessionContext(
			session: session,
			attributes: userAttributes,
			store: OAuthCredentialsStoreKeyChain())

		return session
	}

	public func userAttribute(key: String) -> AnyObject? {
		return currentUserAttributes[key]
	}

	public func createSessionFromCurrentSession() -> LRSession? {
		return LRSession(session: currentUserSession)
	}

	public func createBatchSessionFromCurrentSession() -> LRBatchSession? {
		return LRBatchSession(session: currentUserSession)
	}

	public class func logout() {
		SessionContext.currentContext = nil
	}

	public func storeCredentials() -> Bool {
		return credentialsStorage.store(
				session: currentUserSession,
				userAttributes: currentUserAttributes)
	}

	public func removeStoredCredentials() -> Bool {
		return credentialsStorage.remove()
	}

	public class func loadStoredCredentials() -> Bool {
		guard let storage = CredentialsStorage.createFromStoredAuthType() else {
			return false
		}
		guard let result = storage.load() else {
			return false
		}
		guard result.session.server != nil else {
			return false
		}

		SessionContext.currentContext = SessionContext(
			session: result.session,
			attributes: result.userAttributes,
			store: storage.store)

		return true
	}


}