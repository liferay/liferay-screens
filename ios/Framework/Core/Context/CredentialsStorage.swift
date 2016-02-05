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


@objc public class CredentialsStorage: NSObject {

	public typealias LoadResult = (session: LRSession, userAttributes: [String:AnyObject])

	public let credentialsStore: CredentialsStore

	public init(store: CredentialsStore) {
		credentialsStore = store
		super.init()
	}

	public static func createFromStoredAuthType() -> CredentialsStorage? {
		guard let authType = BaseCredentialsStoreKeyChain.storedAuthType() else {
			return nil
		}

		let store = LiferayServerContext.factory.createCredentialsStore(authType)

		return CredentialsStorage(store: store)
	}

	public func store(session session: LRSession?, userAttributes: [String:AnyObject]) -> Bool {
		if session == nil || userAttributes.isEmpty {
			return false
		}

		return credentialsStore.storeCredentials(session,
				userAttributes: userAttributes)
	}

	public func remove() -> Bool {
		return credentialsStore.removeStoredCredentials()
	}

	public func load() -> LoadResult? {
		guard credentialsStore.loadStoredCredentials() else {
			return nil
		}
		guard let loadedAuth = credentialsStore.authentication else {
			return nil
		}
		guard let loadedUserAttributes = credentialsStore.userAttributes else {
			return nil
		}

		let loadedSession = LRSession(
				server: LiferayServerContext.server,
				authentication: loadedAuth)

		return (loadedSession, loadedUserAttributes)
	}

}
