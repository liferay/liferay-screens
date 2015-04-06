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
import XCTest
import Nocilla


let mockServer = Liferay62MockServer(host: "test-host:9090")


protocol StubResponses {
	func loginOK() -> String
	func loginFailedAuthentication() -> String
}

class MockServer  {

	private let host: String

	init(host: String) {
		self.host = host
	}

	func stubService(service: NSString, withResult jsonResult: String) {
		stubRequest("POST", "http://\(host)/api/jsonws/invoke")
				.withBody(service.regex())
				.andReturn(200)
				.withBody(jsonResult)
	}

}

class Liferay62MockServer : MockServer, StubResponses {
	func loginOK() -> String {
		return
			"[{\"agreedToTermsOfUse\":true,\"comments\":\"From stub!\",\"companyId\":10154," +
			"\"contactId\":10202,\"createDate\":1413408478000,\"defaultUser\":false," +
			"\"emailAddress\":\"test@liferay.com\",\"emailAddressVerified\":true," +
			"\"facebookId\":0,\"failedLoginAttempts\":0,\"firstName\":\"Test\"," +
			"\"graceLoginCount\":0,\"greeting\":\"Welcome Test Test!\",\"jobTitle\":\"\"," +
			"\"languageId\":\"en_US\",\"lastFailedLoginDate\":null," +
			"\"lastLoginDate\":1427299352000,\"lastLoginIP\":\"127.0.0.1\",\"lastName\":\"Test\"," +
			"\"ldapServerId\":-1,\"lockout\":false,\"lockoutDate\":null," +
			"\"loginDate\":1427372894000,\"loginIP\":\"127.0.0.1\",\"middleName\":\"\"," +
			"\"modifiedDate\":1413408478000,\"openId\":\"\",\"portraitId\":14809," +
			"\"reminderQueryAnswer\":\"test\"," +
			"\"reminderQueryQuestion\":\"what-is-your-father's-middle-name\"," +
			"\"screenName\":\"test\",\"status\":0,\"timeZoneId\":\"UTC\",\"userId\":123456," +
			"\"uuid\":\"a16f9d4a-9012-4eee-a42b-269c8a8263c7\"}]";
	}

	func loginFailedAuthentication() -> String {
		return "{\"exception\":\"Authenticated access required\"}"
	}

}


class KeyChainStorageMock : KeyChainStorage {

	var calledSetData = false
	var calledRemoveItemForKey = false
	var calledDataForKey = false

	var hasData: Bool = true

	func setData(data: NSData, forKey key: String) -> Bool {
		calledSetData = true
		return true
	}

	func dataForKey(key: String) -> NSData! {
		calledDataForKey = true

		let encodedData = NSKeyedArchiver.archivedDataWithRootObject(["k":"v"])
		return hasData ? encodedData : nil
	}

	func removeItemForKey(key: String) -> Bool {
		calledRemoveItemForKey = true
		return true
	}

}


class CredentialStorageMock : CredentialStorage {

	var calledGetCredential = false
	var calledGetServer = false
	var calledGetSession = false
	var calledRemoveCredential = false
	var calledStoreCredentialForServer = false

	var hasData: Bool = true

	let credential = NSURLCredential(
			user: "username",
			password: "password",
			persistence: NSURLCredentialPersistence.None)

	func getCredential() -> NSURLCredential! {
		calledGetCredential = true
		return hasData ? self.credential : nil
	}

	func getServer() -> String! {
		calledGetServer = true
		return hasData ? LiferayServerContext.server : nil
	}

	func getSession() -> LRSession! {
		calledGetSession = true

		if hasData {
			return LRSession(
					server: LiferayServerContext.server,
					authentication: LRBasicAuthentication(
						username: credential.user,
						password: credential.password))
		}

		return nil
	}

	func removeCredential() {
		calledRemoveCredential = true
	}

	func storeCredentialForServer(server: String!,
			username: String!,
			password: String!)
			-> NSURLCredential! {

		calledStoreCredentialForServer = true
		return hasData ? self.credential : nil
	}

}
