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


protocol CredentialStorage {

	func getCredential() -> NSURLCredential!
	func getServer() -> String!
	func getSession() -> LRSession!
	func removeCredential()
	func storeCredentialForServer(server: String!,
			username: String!,
			password: String!)
			-> NSURLCredential!

}


class CredentialStorageMobileSDK : CredentialStorage {

	func getCredential() -> NSURLCredential! {
		return LRCredentialStorage.getCredential()
	}

	func getServer() -> String! {
		return LRCredentialStorage.getServer()
	}

	func getSession() -> LRSession! {
		return LRCredentialStorage.getSession()
	}

	func removeCredential() {
		LRCredentialStorage.removeCredential()
	}

	func storeCredentialForServer(server: String!,
			username: String!,
			password: String!)
			-> NSURLCredential! {

		return LRCredentialStorage.storeCredentialForServer(server,
				username: username,
				password: password)
	}

}
