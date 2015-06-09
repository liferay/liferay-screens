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

		let url = NSURL(string: LiferayServerContext.server)!
		let serverKeychain = Keychain(server: url, protocolType: .HTTPS)

		serverKeychain.set(String(LiferayServerContext.companyId),
				key: "companyId")
		serverKeychain.set(String(LiferayServerContext.groupId),
				key: "groupId")

		let userKeychain = Keychain(service: NSBundle.mainBundle().bundleIdentifier!)

		var outError: NSError?
		let userData = NSJSONSerialization.dataWithJSONObject(userAttributes!,
				options: NSJSONWritingOptions.allZeros,
				error: &outError)

		userKeychain.set(userData!, key: "\(NSBundle.mainBundle().bundleIdentifier!)-user")

		storeAuth(keychain: serverKeychain, auth: authentication!)

		return true
	}

	public func removeStoredCredentials() -> Bool {
		let url = NSURL(string: LiferayServerContext.server)!
		let serverKeychain = Keychain(server: url, protocolType: .HTTPS)

		let error1 = serverKeychain.removeAll()

		let userKeychain = Keychain(service: NSBundle.mainBundle().bundleIdentifier!)

		let error2 = userKeychain.removeAll()

		return (error1 == nil && error2 == nil)
	}

	public func loadStoredCredentials() -> Bool {
		let url = NSURL(string: LiferayServerContext.server)!
		let serverKeychain = Keychain(server: url, protocolType: .HTTPS)

		let companyId = serverKeychain.get("companyId")
					.map { $0.toInt() }
					.map { Int64($0!) }
		let groupId = serverKeychain.get("groupId")
					.map { $0.toInt() }
					.map { Int64($0!) }

		if companyId != LiferayServerContext.companyId
				|| groupId != LiferayServerContext.groupId {
			return false
		}

		let userKeychain = Keychain(service: NSBundle.mainBundle().bundleIdentifier!)

		let userData = userKeychain.getData("\(NSBundle.mainBundle().bundleIdentifier!)-user")

		var outError: NSError?
		let json: AnyObject? = NSJSONSerialization.JSONObjectWithData(userData!,
				options: NSJSONReadingOptions.allZeros,
				error: &outError)

		userAttributes = json as? [String:AnyObject]

		loadAuth(keychain: serverKeychain)

		return (userAttributes != nil)
	}

	public func storeAuth(#keychain: Keychain, auth: LRAuthentication) {
		assertionFailure("This method must be overriden")
	}

	public func loadAuth(#keychain: Keychain) -> LRAuthentication? {
		assertionFailure("This method must be overriden")
		return nil
	}

	public class func storedAuthType() -> AuthType? {
		let url = NSURL(string: LiferayServerContext.server)!
		let serverKeychain = Keychain(server: url, protocolType: .HTTPS)

		if let value = serverKeychain.get("auth_type") {
			return AuthType(rawValue: value)
		}

		return nil
	}

}
