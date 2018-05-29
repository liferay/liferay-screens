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
import KeychainAccess

open class CookieCredentialsStoreKeyChain: BaseCredentialsStoreKeyChain {

	enum KeyChainKeys: String {
		case cookieUsernameKey = "cookie_username"
		case cookiePasswordKey = "cookie_password"
		case cookieAuthTokenKey = "cookie_authToken"
		case cookieHeaderKey = "cookie_header"
		case lastCookieRefreshKey = "cookie_last_refresh"
		case cookieExpirationTimeKey = "cookie_expiration_time"
		case cookieShouldHandleExpirationKey = "cookie_shouldhandle"
	}

	override open func storeAuth(keychain: Keychain, auth: LRAuthentication) {
		let cookieAuth = auth as! LRCookieAuthentication

		do {
			try keychain.set(StringFromAuthType(AuthType.cookie), key: "auth_type")
			try keychain.set(cookieAuth.authToken, key: KeyChainKeys.cookieAuthTokenKey.rawValue)
			try keychain.set(cookieAuth.cookieHeader, key: KeyChainKeys.cookieHeaderKey.rawValue)
			try keychain.set(cookieAuth.username, key: KeyChainKeys.cookieUsernameKey.rawValue)
			try keychain.set(cookieAuth.password, key: KeyChainKeys.cookiePasswordKey.rawValue)
			try keychain.set("\(cookieAuth.shouldHandleExpiration)",
				key: KeyChainKeys.cookieShouldHandleExpirationKey.rawValue)
			try keychain.set("\(cookieAuth.cookieExpirationTime)", key: KeyChainKeys.cookieExpirationTimeKey.rawValue)
			try keychain.set("\(cookieAuth.lastCookieRefresh)", key: KeyChainKeys.lastCookieRefreshKey.rawValue)
		}
		catch {
		}
	}

	override open func loadAuth(keychain: Keychain) -> LRAuthentication? {
		do {
			let authToken = try keychain.get(KeyChainKeys.cookieAuthTokenKey.rawValue)
			let cookieHeader = try keychain.get(KeyChainKeys.cookieHeaderKey.rawValue)
			let username = try keychain.get(KeyChainKeys.cookieUsernameKey.rawValue)
			let password = try keychain.get(KeyChainKeys.cookiePasswordKey.rawValue)
			let shouldHandleExpiration = try keychain.get(KeyChainKeys.cookieShouldHandleExpirationKey.rawValue)
			let cookieExpirationTime = try keychain.get(KeyChainKeys.cookieExpirationTimeKey.rawValue)
			let lastCookieRefresh = try keychain.get(KeyChainKeys.lastCookieRefreshKey.rawValue)

			if let username = username,
				let password = password,
				let authToken = authToken,
				let cookieHeader = cookieHeader,
				let shouldHandleExpirationString = shouldHandleExpiration,
				let cookieExpirationTimeString = cookieExpirationTime,
				let lastCookieRefreshString = lastCookieRefresh,
				let shouldHandleExpiration = Bool(shouldHandleExpirationString),
				let cookieExpirationTime = Double(cookieExpirationTimeString),
				let lastCookieRefresh = Double(lastCookieRefreshString) {

				return LRCookieAuthentication(authToken: authToken,
					cookieHeader: cookieHeader,
					username: username,
					password: password,
					shouldHandleExpiration: shouldHandleExpiration,
					cookieExpirationTime: cookieExpirationTime,
					lastCookieRefresh: lastCookieRefresh)

			}

			return nil
		}
		catch {
			print("Error retrieving authentication from storage: \(error)")
			return nil
		}
	}
}
