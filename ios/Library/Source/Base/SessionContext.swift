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

	public var hasSession: Bool {
		return currentSession != nil
	}

	public var currentUserName: String? {
		return currentSession?.username
	}

	public var currentPassword: String? {
		return currentSession?.password
	}

	private var currentSession:LRSession?
	private var userAttributes: [String:AnyObject] = [:]


	//MARK: Singleton

	class var instance: SessionContext {
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

	public func userAttribute(key: String) -> AnyObject? {
		return userAttributes[key]
	}

	public func createSession(
			#username:String,
			password:String,
			userAttributes: [String:AnyObject])
			-> LRSession {

		currentSession = LRSession(
				server:LiferayContext.instance.server,
				username:username,
				password:password)

		self.userAttributes = userAttributes

		return currentSession!
	}

	public func createSessionFromCurrentSession() -> LRSession? {
		if let currentSessionValue = currentSession {
			return LRSession(session: currentSessionValue)
		}

		return nil
	}

	public func createBatchSessionFromCurrentSession() -> LRBatchSession? {
		if let currentSessionValue = currentSession {
			return LRBatchSession(session: currentSessionValue)
		}

		return nil
	}

	public func clearSession() {
		currentSession = nil
		userAttributes = [:]
	}

	public func storeSession() -> Bool {
		return (hasSession && currentSession!.storeCredential() && storeUserAttributes())
	}

	public class func removeStoredSession() {
		LRSession.removeStoredCredential()
		UICKeyChainStore.removeItemForKey("userAttributes")
	}

	public func loadSessionFromStore() -> Bool {
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