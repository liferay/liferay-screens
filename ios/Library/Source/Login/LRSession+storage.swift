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

extension LRSession {

	class func emptyStore() {
		if let server = NSUserDefaults.standardUserDefaults().stringForKey("server") {
			NSUserDefaults.standardUserDefaults().removeObjectForKey("server")
			NSUserDefaults.standardUserDefaults().synchronize()

			let credentialInfo = credentialsForServer(server)
			if let credential = credentialInfo.0 {
				NSURLCredentialStorage.sharedCredentialStorage().removeCredential(
					credential, forProtectionSpace:credentialInfo.1)
			}
		}
	}

	func store() -> Bool {
		var result = false
		// WORKAROUND!
		// Compiler crash with compound if statement: if self.server && self.username && self.password {
		// "While emitting IR SIL function @_TFCSo9LRSession5storefS_FT_T_ for 'store' at LRSession+storage.swift:36:2"
		if self.server {
			if self.username {
				if self.password {
					NSUserDefaults.standardUserDefaults().setObject(self.server, forKey:"server")
					NSUserDefaults.standardUserDefaults().synchronize()

					let protectionSpace = LRSession.protectionSpaceForServer(self.server!)

					let credential = NSURLCredential(user:self.username!, password:self.password!,
						persistence: NSURLCredentialPersistence.Permanent)

					NSURLCredentialStorage.sharedCredentialStorage().setCredential(
						credential, forProtectionSpace:protectionSpace)

					result = true
				}
			}
		}

		return result
	}

	class func storedSession() -> LRSession? {
		if let server = NSUserDefaults.standardUserDefaults().stringForKey("server") {
			let credentialInfo = credentialsForServer(server)
			if let credential = credentialInfo.0 {
				return LRSession(server, username:credential.user, password:credential.password)
			}
		}

		return nil
	}


	// PRIVATE METHODS


	class func credentialsForServer(server:String) -> (NSURLCredential?, NSURLProtectionSpace) {
		let protectionSpace = protectionSpaceForServer(server)

		let credentialDict =
		NSURLCredentialStorage.sharedCredentialStorage().credentialsForProtectionSpace(protectionSpace)

		let username = credentialDict.keyEnumerator().nextObject() as NSString

		return (credentialDict[username] as? NSURLCredential, protectionSpace)
	}

	class func protectionSpaceForServer(server:String) -> NSURLProtectionSpace {
		let url = NSURL(string: server)

		return NSURLProtectionSpace(host:url.host, port:url.port.integerValue, `protocol`:url.scheme, realm:nil,
			authenticationMethod:NSURLAuthenticationMethodHTTPDigest)
	}

}