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


@objc public class SessionContext {

	//MARK: Singleton type

	private struct StaticInstance {
		static var currentSession:LRSession?
		static var userAttributes: [String:AnyObject] = [:]

		static var sessionStorage = SessionStorage(
			credentialStorage: CredentialStorageMobileSDK(),
			keyChainStorage: KeyChainStorageImpl())
	}


	//MARK: Public properties

	public class var hasSession: Bool {
		return StaticInstance.currentSession != nil
	}

	public class var currentAuthMethod: AuthMethod? {
		if let userName = currentUserName {
			return AuthMethod.fromUserName(userName)
		}

		return nil
	}

	public class var currentUserName: String? {
		var authentication = StaticInstance.currentSession?.authentication
			as? LRBasicAuthentication

		return authentication?.username
	}

	public class var currentPassword: String? {
		var authentication = StaticInstance.currentSession?.authentication
			as? LRBasicAuthentication

		return authentication?.password
	}

	public class var currentUserId: Int64? {
		return StaticInstance.userAttributes["userId"]
				.map { $0 as! Int }
				.map { Int64($0) }
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

	public class func createSession(
			#username: String,
			password: String,
			userAttributes: [String:AnyObject])
			-> LRSession {

		let authentication = LRBasicAuthentication(
				username: username,
				password: password)

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
		if let result = sessionStorage.load() {
			StaticInstance.currentSession = result.session
			StaticInstance.userAttributes = result.userAttributes

			return true
		}

		clearSession()

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

		return session
	}

}