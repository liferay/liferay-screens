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
import KeychainAccess

open class OAuth2CredentialsStoreKeyChain: BaseCredentialsStoreKeyChain {

	enum KeyChainKeys: String {
		case accessToken
		case refreshToken
		case scope
		case accessTokenExpirationDate
		case clientId
		case clientSecret
	}

	override open func storeAuth(keychain: Keychain, auth: LRAuthentication) {
		let oauth2 = auth as! LROAuth2Authentication

		let expirationDateString = "\(oauth2.accessTokenExpirationDate.timeIntervalSince1970)"

		do {
			try keychain.set(StringFromAuthType(AuthType.oauth2UsernameAndPassword), key: "auth_type")
			try keychain.set(oauth2.accessToken, key: KeyChainKeys.accessToken.rawValue)
			try keychain.set(oauth2.refreshToken, key: KeyChainKeys.refreshToken.rawValue)
			try keychain.set(oauth2.scope, key: KeyChainKeys.scope.rawValue)
			try keychain.set(expirationDateString, key: KeyChainKeys.accessTokenExpirationDate.rawValue)
			try keychain.set(oauth2.clientId, key: KeyChainKeys.clientId.rawValue)

			if let clientSecret = oauth2.clientSecret {
				try keychain.set(clientSecret, key: KeyChainKeys.clientSecret.rawValue)
			}

		}
		catch {
		}
	}

	override open func loadAuth(keychain: Keychain) -> LRAuthentication? {

		do {
			let accessToken = try keychain.get(KeyChainKeys.accessToken.rawValue)
			let refreshToken = try keychain.get(KeyChainKeys.refreshToken.rawValue)
			let scope = try keychain.get(KeyChainKeys.scope.rawValue)
			let expirationDateString = try keychain.get(KeyChainKeys.accessTokenExpirationDate.rawValue) ?? ""
			let clientId = try keychain.get(KeyChainKeys.clientId.rawValue)
			let clientSecret = try keychain.get(KeyChainKeys.clientSecret.rawValue)

			if let accessToken = accessToken,
				let refreshToken = refreshToken,
				let scope = scope,
				let expirationDateMs = TimeInterval(expirationDateString),
				let clientId = clientId {

				return LROAuth2Authentication(accessToken: accessToken, refreshToken: refreshToken, scope: scope,
					accessTokenExpirationDate: Date(timeIntervalSince1970: expirationDateMs), clientId: clientId,
					clientSecret: clientSecret)
			}
			else {
				return nil
			}
		}
		catch {
			return nil
		}
	}

}
