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

#if LIFERAY_SCREENS_FRAMEWORK
	import LRMobileSDK
	import KeychainAccess
#endif


public class BaseCredentialsStoreKeyChain : CredentialsStore {

	public var authentication: LRAuthentication?
	public var userAttributes: [String:AnyObject]?

	public func storeCredentials(
			session: LRSession?,
			userAttributes: [String:AnyObject]?) -> Bool {

		if session == nil { return false }
		if session?.authentication == nil { return false }
		if userAttributes == nil { return false }
		if userAttributes!.isEmpty { return false }

		let keychain = BaseCredentialsStoreKeyChain.keychain()

		do {
			try keychain.set(String(LiferayServerContext.companyId),
				key: "companyId")
			try keychain.set(String(LiferayServerContext.groupId),
				key: "groupId")

			let userData = try? NSJSONSerialization.dataWithJSONObject(userAttributes!,
				options: NSJSONWritingOptions())
			if let userData = userData {
				try keychain.set(userData, key: "user_attributes")

				storeAuth(keychain: keychain, auth: session!.authentication!)

				return true
			}
		}
		catch {
			do {
				try keychain.remove("companyId")
				try keychain.remove("groupId")
				try keychain.remove("user_attributes")
			} catch {
			}
		}

		return false
	}

	public func removeStoredCredentials() -> Bool {
		let keychain = BaseCredentialsStoreKeyChain.keychain()

		do {
			try keychain.removeAll()
		}
		catch {
			return false
		}

		return true
	}

	public func loadStoredCredentials() -> Bool {
		let keychain = BaseCredentialsStoreKeyChain.keychain()

		let companyId = try? keychain.get("companyId")
					.flatMap { Int($0) }
					.flatMap { Int64($0) }
		let groupId = try? keychain.get("groupId")
					.flatMap { Int($0) }
					.flatMap { Int64($0) }

		if (companyId ?? 0) != LiferayServerContext.companyId
				|| (groupId ?? 0) != LiferayServerContext.groupId {
			return false
		}

		guard let userData = try? keychain.getData("user_attributes")
		else {
			return false
		}

		if let userData = userData {
			let json = try? NSJSONSerialization.JSONObjectWithData(userData,
					options: [])

			userAttributes = json as? [String:AnyObject]
			authentication = loadAuth(keychain: keychain)

			return (authentication != nil && userAttributes != nil)
		}

		return false
	}

	public func storeAuth(keychain keychain: Keychain, auth: LRAuthentication) {
		fatalError("This method must be overriden")
	}

	public func loadAuth(keychain keychain: Keychain) -> LRAuthentication? {
		fatalError("This method must be overriden")
	}

	public class func storedAuthType() -> AuthType? {
		guard let value = try? keychain().get("auth_type")
		else {
			return nil
		}

		return AuthType(rawValue: value ?? "")
	}

	public class func keychain() -> Keychain {
		let url = NSURL(string: LiferayServerContext.server)!
		return Keychain(server: url, protocolType: .HTTPS)
	}

}
