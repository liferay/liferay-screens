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

	public let store: CredentialsStore

	public init(store: CredentialsStore) {
		self.store = store
		super.init()
	}

	public static func createFromStoredAuthType() -> CredentialsStorage? {
		guard let authType = BaseCredentialsStoreKeyChain.storedAuthType() else {
			return nil
		}

		let store: CredentialsStore

		switch authType {
		case .Basic:
			store = BasicCredentialsStoreKeyChain()
		case .OAuth:
			store = OAuthCredentialsStoreKeyChain()
		}

		return CredentialsStorage(store: store)
	}

	func store(session session: LRSession?, userAttributes: [String:AnyObject]) -> Bool {
		if session == nil || userAttributes.isEmpty {
			return false
		}

		return store.storeCredentials(session,
				userAttributes: userAttributes)
	}

	public func remove() -> Bool {
		return store.removeStoredCredentials()
	}

	public func load() -> LoadResult? {
		guard store.loadStoredCredentials() else {
			return nil
		}
		guard let loadedAuth = store.authentication else {
			return nil
		}
		guard let loadedUserAttributes = store.userAttributes else {
			return nil
		}

		let loadedSession = LRSession(
				server: LiferayServerContext.server,
				authentication: loadedAuth)

		return (loadedSession, loadedUserAttributes)
	}

}
