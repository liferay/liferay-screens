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
import UIKit
import LRMobileSDK
import UICKeyChainStore


typealias LoadResult = (session: LRSession, userAttributes: [String:AnyObject])

protocol SessionStorage {

	func store(#session: LRSession?, userAttributes: [String:AnyObject]) -> Bool

	func remove() -> Bool

	func load() -> LoadResult?

}

class SessionStorageImpl : SessionStorage {

	func store(#session: LRSession?, userAttributes: [String:AnyObject]) -> Bool {
		func storeUserAttributes(userAttributes: [String:AnyObject]) -> Bool {
			if userAttributes.isEmpty {
				return false
			}

			let encodedData = NSKeyedArchiver.archivedDataWithRootObject(userAttributes)

			return UICKeyChainStore.setData(encodedData, forKey: "userAttributes")
		}

		let server = session?.server

		if let auth = session?.authentication as? LRBasicAuthentication {
			let credential = LRCredentialStorage.storeCredentialForServer(server,
					username: auth.username,
					password: auth.password)

			if credential != nil {
				return storeUserAttributes(userAttributes)
			}
		}

		return false
	}

	func remove() -> Bool {
		LRCredentialStorage.removeCredential()
		return UICKeyChainStore.removeItemForKey("userAttributes")
	}

	func load() -> LoadResult? {
		func loadUserAttributesFromStore() -> [String:AnyObject]? {
			if let encodedData = UICKeyChainStore.dataForKey("userAttributes") {
				let dictionary:AnyObject? = NSKeyedUnarchiver.unarchiveObjectWithData(encodedData)

				return dictionary as? [String:AnyObject]
			}

			return nil
		}

		if let loadedSession = LRCredentialStorage.getSession() {
			if let loadedUserAttributes = loadUserAttributesFromStore() {
				return (loadedSession, loadedUserAttributes)
			}
		}

		return nil
	}



}
