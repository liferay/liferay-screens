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


public struct SessionContext {

	public static var hasSession: Bool {
		return currentSession != nil
	}

	public static var currentUserName: String? {
		return currentSession?.username
	}

	public static var currentPassword: String? {
		return currentSession?.password
	}

	private static var currentSession:LRSession?
	private static var userAttributes: [String:AnyObject] = [:]


	//MARK Public methods

	public static func userAttribute(key: String) -> AnyObject? {
		return userAttributes[key]
	}

	public static func createSession(
			#username:String,
			password:String,
			userAttributes: [String:AnyObject])
			-> LRSession {

		currentSession = LRSession(
				server:LiferayServerContext.server,
				username:username,
				password:password)

		self.userAttributes = userAttributes

		return currentSession!
	}

	public static func createSessionFromCurrentSession() -> LRSession? {
		if let currentSessionValue = currentSession {
			return LRSession(session: currentSessionValue)
		}

		return nil
	}

	public static func createBatchSessionFromCurrentSession() -> LRBatchSession? {
		if let currentSessionValue = currentSession {
			return LRBatchSession(session: currentSessionValue)
		}

		return nil
	}

	public static func clearSession() {
		currentSession = nil
		userAttributes = [:]
	}

	public static func storeSession() -> Bool {
		if !hasSession {
			return false
		}

		let success = currentSession!.storeCredential()

		if !success {
			return false
		}

		return storeUserAttributes()
	}

	public static func removeStoredSession() {
		LRSession.removeStoredCredential()
		UICKeyChainStore.removeItemForKey("userAttributes")
	}

	public static func loadSessionFromStore() -> Bool {
		if let storedSession = LRSession.sessionFromStoredCredential() {
			if let userAttributes = loadUserAttributesFromStore() {
				createSession(
						username: storedSession.username,
						password: storedSession.password,
						userAttributes: userAttributes)

				return true
			}
		}

		return false
	}


	private static func storeUserAttributes() -> Bool {
		if userAttributes.isEmpty {
			return false
		}

		let encodedData = NSKeyedArchiver.archivedDataWithRootObject(userAttributes)

		return UICKeyChainStore.setData(encodedData, forKey: "userAttributes")
	}

	private static func loadUserAttributesFromStore() -> [String:AnyObject]? {
		if let encodedData = UICKeyChainStore.dataForKey("userAttributes") {
			let dictionary:AnyObject? = NSKeyedUnarchiver.unarchiveObjectWithData(encodedData)

			return dictionary as? [String:AnyObject]
		}

		return nil
	}

}