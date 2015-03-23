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
	}

	//MARK: Public properties

	public class var hasSession: Bool {
		return StaticInstance.currentSession != nil
	}

	public class var currentUserName: String? {
		var authentication = StaticInstance.currentSession?.authentication
			as LRBasicAuthentication?

		return authentication?.username
	}

	public class var currentPassword: String? {
		var authentication = StaticInstance.currentSession?.authentication
			as LRBasicAuthentication?

		return authentication?.password
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
		if !hasSession {
			return false
		}

		let success = StaticInstance.currentSession!.storeCredential()

		if !success {
			return false
		}

		return storeUserAttributes()
	}

	public class func removeStoredSession() {
		LRSession.removeStoredCredential()
		UICKeyChainStore.removeItemForKey("userAttributes")
	}

	public class func loadSessionFromStore() -> Bool {
		if let storedSession = LRSession.sessionFromStoredCredential() {
			if let userAttributes = loadUserAttributesFromStore() {
				createSession(
						authentication:storedSession.authentication,
						userAttributes: userAttributes)

				return true
			}
		}

		return false
	}

	private class func storeUserAttributes() -> Bool {
		if StaticInstance.userAttributes.isEmpty {
			return false
		}

		let encodedData = NSKeyedArchiver.archivedDataWithRootObject(StaticInstance.userAttributes)

		return UICKeyChainStore.setData(encodedData, forKey: "userAttributes")
	}

	private class func loadUserAttributesFromStore() -> [String:AnyObject]? {
		if let encodedData = UICKeyChainStore.dataForKey("userAttributes") {
			let dictionary:AnyObject? = NSKeyedUnarchiver.unarchiveObjectWithData(encodedData)

			return dictionary as? [String:AnyObject]
		}

		return nil
	}

	private class func createSession(
			#authentication: LRAuthentication,
			userAttributes: [String:AnyObject])
			-> LRSession {

		let session = LRSession(server: LiferayServerContext.server, authentication: authentication)

		StaticInstance.currentSession = session

		StaticInstance.userAttributes = userAttributes

		return session
	}

}