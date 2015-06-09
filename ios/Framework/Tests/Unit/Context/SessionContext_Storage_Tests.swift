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
import XCTest


class SessionContext_Storage_Tests: XCTestCase {

	var credentialStorage = CredentialStorageMock()
	var keyChainStorage = KeyChainStorageMock()

	override func setUp() {
		super.setUp()

		self.continueAfterFailure = false
		self.credentialStorage = CredentialStorageMock()
		self.keyChainStorage = KeyChainStorageMock()

		SessionContext.sessionStorage = SessionStorage(
				credentialStorage: self.credentialStorage,
				keyChainStorage: self.keyChainStorage)

		SessionContext.clearSession()
	}

	override func tearDown() {
		SessionContext.clearSession()
		SessionContext.sessionStorage = SessionStorage(
			credentialStorage: CredentialStorageMobileSDK(),
			keyChainStorage: KeyChainStorageImpl())

		super.tearDown()
	}

	func test_StoreSession_ShouldReturnFalse_WhenSessionIsNotCreated() {
		XCTAssertFalse(SessionContext.storeSession())

		XCTAssertFalse(credentialStorage.calledStoreCredentialForServer)
		XCTAssertFalse(keyChainStorage.calledSetData)
	}

	func test_StoreSession_ShouldReturnFalse_WhenUserAttributesAreEmpty() {
		let session = SessionContext.createSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertFalse(SessionContext.storeSession())

		XCTAssertFalse(credentialStorage.calledStoreCredentialForServer)
		XCTAssertFalse(keyChainStorage.calledSetData)
	}

	func test_StoreSession_ShouldReturnTrue_WhenSessionIsCreated() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		XCTAssertTrue(SessionContext.storeSession())

		XCTAssertTrue(credentialStorage.calledStoreCredentialForServer)
		XCTAssertTrue(keyChainStorage.calledSetData)
	}

	func test_LoadSessionFromStore_ShouldReturnFalse_WhenSessionIsNotStored() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		credentialStorage.hasData = false

		XCTAssertFalse(SessionContext.loadSessionFromStore())

		XCTAssertTrue(credentialStorage.calledGetSession)
		XCTAssertFalse(keyChainStorage.calledDataForKey)

		XCTAssertTrue(SessionContext.currentUserName == nil)
		XCTAssertTrue(SessionContext.currentPassword == nil)
	}

	func test_LoadSessionFromStore_ShouldReturnTrue_WhenSessionIsStored() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		XCTAssertTrue(SessionContext.loadSessionFromStore())

		XCTAssertTrue(credentialStorage.calledGetSession)
		XCTAssertTrue(keyChainStorage.calledDataForKey)
	}

	func test_LoadSessionFromStore_ShouldRetrieveData_WhenSessionIsStored() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		SessionContext.loadSessionFromStore()

		XCTAssertTrue(SessionContext.currentUserName != nil)
		XCTAssertTrue(SessionContext.currentPassword != nil)
		XCTAssertTrue(SessionContext.userAttribute("k") != nil)

		XCTAssertEqual("username", SessionContext.currentUserName!)
		XCTAssertEqual("password", SessionContext.currentPassword!)
		XCTAssertEqual("v", SessionContext.userAttribute("k") as! String)
	}

	func test_RemoveStoredSession_ShouldEmptyTheStoredSession() {
		SessionContext.removeStoredSession()

		XCTAssertTrue(credentialStorage.calledRemoveCredential)
		XCTAssertTrue(keyChainStorage.calledRemoveItemForKey)
	}

}
