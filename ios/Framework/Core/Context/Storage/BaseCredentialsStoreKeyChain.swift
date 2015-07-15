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

		keychain.set(String(LiferayServerContext.companyId),
				key: "companyId")
		keychain.set(String(LiferayServerContext.groupId),
				key: "groupId")

		var outError: NSError?
		let userData = NSJSONSerialization.dataWithJSONObject(userAttributes!,
				options: NSJSONWritingOptions.allZeros,
				error: &outError)

		let bundleId = NSBundle.mainBundle().bundleIdentifier ?? "liferay-screens"
		keychain.set(userData!, key: "\(bundleId)-user")

		storeAuth(keychain: keychain, auth: session!.authentication!)

		return true
	}

	public func removeStoredCredentials() -> Bool {
		let keychain = BaseCredentialsStoreKeyChain.keychain()

		let error1 = keychain.removeAll()

		let userKeychain = Keychain(service: NSBundle.mainBundle().bundleIdentifier!)

		let error2 = keychain.removeAll()

		return (error1 == nil && error2 == nil)
	}

	public func loadStoredCredentials() -> Bool {
		let keychain = BaseCredentialsStoreKeyChain.keychain()

		let companyId = keychain.get("companyId")
					.map { $0.toInt() }
					.map { Int64($0!) }
		let groupId = keychain.get("groupId")
					.map { $0.toInt() }
					.map { Int64($0!) }

		if companyId != LiferayServerContext.companyId
				|| groupId != LiferayServerContext.groupId {
			return false
		}

		let bundleId = NSBundle.mainBundle().bundleIdentifier ?? "liferay-screens"
		if let userData = keychain.getData("\(bundleId)-user") {
			var outError: NSError?
			let json: AnyObject? =
					NSJSONSerialization.JSONObjectWithData(userData,
						options: NSJSONReadingOptions.allZeros,
						error: &outError)

			userAttributes = json as? [String:AnyObject]
		}
		else {
			userAttributes = nil
		}

		authentication = loadAuth(keychain: keychain)

		return (authentication != nil && userAttributes != nil)
	}

	public func storeAuth(#keychain: Keychain, auth: LRAuthentication) {
		assertionFailure("This method must be overriden")
	}

	public func loadAuth(#keychain: Keychain) -> LRAuthentication? {
		assertionFailure("This method must be overriden")
		return nil
	}

	public class func storedAuthType() -> AuthType? {
		if let value = keychain().get("auth_type") {
			return AuthType(rawValue: value)
		}

		return nil
	}

	public class func keychain() -> Keychain {
		let url = NSURL(string: LiferayServerContext.server)!
		return Keychain(server: url, protocolType: .HTTPS)
	}

}
