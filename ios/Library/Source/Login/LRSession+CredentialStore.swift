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

	//MARK: CLASS METHODS
	
	public class func removeStoredCredential() {
		let credentialTuple = credentialForServer(LiferayContext.instance.server)

		if let credential = credentialTuple.0 {
			NSURLCredentialStorage.sharedCredentialStorage().removeCredential(
				credential, forProtectionSpace:credentialTuple.1)
		}
	}

	public class func sessionFromStoredCredential() -> LRSession? {
		let credentialTuple = credentialForServer(LiferayContext.instance.server)

		if let credential = credentialTuple.0 {
			return LRSession(server:LiferayContext.instance.server, username:credential.user, password:credential.password)
		}

		return nil
	}


	//MARK: INSTANCE METHODS

	public func storeCredential() -> Bool {
		var success = false

		if self.username != nil && self.password != nil {
			let protectionSpace = LRSession.protectionSpaceForServer(LiferayContext.instance.server)

			let credential = NSURLCredential(
								user:self.username!,
								password:self.password!,
								persistence: NSURLCredentialPersistence.Permanent)

			NSURLCredentialStorage.sharedCredentialStorage().setCredential(credential,
								forProtectionSpace:protectionSpace)

			success = true
		}

		return success
	}


	//MARK: PRIVATE METHODS

	private class func credentialForServer(server:String) -> (NSURLCredential?, NSURLProtectionSpace) {
		let protectionSpace = protectionSpaceForServer(server)

		let credentialDict =
			NSURLCredentialStorage.sharedCredentialStorage().credentialsForProtectionSpace(protectionSpace)
				as NSDictionary?
		
		if let credentialDictValue = credentialDict {
			let username = credentialDictValue.keyEnumerator().nextObject() as NSString
			
			return (credentialDictValue.objectForKey(username) as? NSURLCredential, protectionSpace)
		}
		else {
			return (nil, protectionSpace)
		}
	}

	private class func protectionSpaceForServer(server:String) -> NSURLProtectionSpace {
		let url = NSURL(string: server)

		return NSURLProtectionSpace(
				host:url.host!, port:url.port!.integerValue,
				`protocol`:url.scheme, realm:nil,
				authenticationMethod:NSURLAuthenticationMethodHTTPDigest)
	}

}
