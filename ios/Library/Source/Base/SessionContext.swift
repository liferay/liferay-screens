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


public class SessionContext {

	public class var hasSession: Bool {
		return instance.currentSession != nil
	}

	public class var currentUserName: String? {
		return instance.currentSession?.username
	}

	public class var currentPassword: String? {
		return instance.currentSession?.password
	}

	private var currentSession:LRSession?
	private var userAttributes: [String:AnyObject] = [:]


	//MARK: Singleton

	private class var instance: SessionContext {
		struct Singleton {
			static var instance: SessionContext? = nil
			static var onceToken: dispatch_once_t = 0
		}

		dispatch_once(&Singleton.onceToken) {
			Singleton.instance = self()
		}

		return Singleton.instance!
	}

	public required init() {
	}


	//MARK Public methods

	public class func userAttribute(key: String) -> AnyObject? {
		return instance.userAttributes[key]
	}

	public class func createSession(
			#username:String,
			password:String,
			userAttributes: [String:AnyObject])
			-> LRSession {

		instance.currentSession = LRSession(
				server:LiferayServerContext.instance.server,
				username:username,
				password:password)

		instance.userAttributes = userAttributes

		return instance.currentSession!
	}

	public class func createSessionFromCurrentSession() -> LRSession? {
		if let currentSessionValue = instance.currentSession {
			return LRSession(session: currentSessionValue)
		}

		return nil
	}

	public class func createBatchSessionFromCurrentSession() -> LRBatchSession? {
		if let currentSessionValue = instance.currentSession {
			return LRBatchSession(session: currentSessionValue)
		}

		return nil
	}

	public class func clearSession() {
		instance.currentSession = nil
		instance.userAttributes = [:]
	}

	public class func storeSession() -> Bool {
		if !hasSession {
			return false
		}

		let success = instance.currentSession!.storeCredential()

		if !success {
			return false
		}

		return instance.storeUserAttributes()
	}

	public class func removeStoredSession() {
		LRSession.removeStoredCredential()
		UICKeyChainStore.removeItemForKey("userAttributes")
	}

	public class func loadSessionFromStore() -> Bool {
		if let storedSession = LRSession.sessionFromStoredCredential() {
			if let userAttributes = instance.loadUserAttributesFromStore() {
				createSession(
						username: storedSession.username,
						password: storedSession.password,
						userAttributes: userAttributes)

				return true
			}
		}

		return false
	}


	private func storeUserAttributes() -> Bool {
		if userAttributes.isEmpty {
			return false
		}

		let encodedData = NSKeyedArchiver.archivedDataWithRootObject(userAttributes)

		return UICKeyChainStore.setData(encodedData, forKey: "userAttributes")
	}

	private func loadUserAttributesFromStore() -> [String:AnyObject]? {
		if let encodedData = UICKeyChainStore.dataForKey("userAttributes") {
			let dictionary:AnyObject? = NSKeyedUnarchiver.unarchiveObjectWithData(encodedData)

			return dictionary as? [String:AnyObject]
		}

		return nil
	}

}