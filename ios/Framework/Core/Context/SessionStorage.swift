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


class SessionStorage {

	typealias LoadResult = (session: LRSession, userAttributes: [String:AnyObject])

	private let credentialStorage: CredentialStorage
	private let keyChainStorage: KeyChainStorage

	init(credentialStorage: CredentialStorage, keyChainStorage: KeyChainStorage) {
		self.credentialStorage = credentialStorage
		self.keyChainStorage = keyChainStorage
	}

	func store(#session: LRSession?, userAttributes: [String:AnyObject]) -> Bool {
		func storeUserAttributes(userAttributes: [String:AnyObject]) -> Bool {
			let encodedData = NSKeyedArchiver.archivedDataWithRootObject(userAttributes)

			return keyChainStorage.setData(encodedData, forKey: "userAttributes")
		}

		if userAttributes.isEmpty {
			return false
		}

		if let auth = session?.authentication as? LRBasicAuthentication {
			let credential = credentialStorage.storeCredentialForServer(session?.server,
					username: auth.username,
					password: auth.password)

			if credential != nil {
				return storeUserAttributes(userAttributes)
			}
		}

		return false
	}

	func remove() -> Bool {
		credentialStorage.removeCredential()
		return keyChainStorage.removeItemForKey("userAttributes")
	}

	func load() -> LoadResult? {
		func loadUserAttributesFromStore() -> [String:AnyObject]? {
			if let encodedData = keyChainStorage.dataForKey("userAttributes") {
				let dictionary:AnyObject? = NSKeyedUnarchiver.unarchiveObjectWithData(encodedData)

				return dictionary as? [String:AnyObject]
			}

			return nil
		}

		if let loadedSession = credentialStorage.getSession() {
			if let loadedUserAttributes = loadUserAttributesFromStore() {
				return (loadedSession, loadedUserAttributes)
			}
		}

		return nil
	}

}
