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

	override func setUp() {
		super.setUp()

		self.continueAfterFailure = false

		SessionContext.clearSession()
	}

	override func tearDown() {
		SessionContext.removeStoredSession()
		SessionContext.clearSession()

		super.tearDown()
	}

	func test_StoreSession_ShouldReturnFalse_WhenSessionIsNotCreated() {
		XCTAssertFalse(SessionContext.storeSession())
	}

	func test_StoreSession_ShouldReturnFalse_WhenUserAttributesAreEmpty() {
		let session = SessionContext.createSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertFalse(SessionContext.storeSession())
	}

	func test_StoreSession_ShouldReturnTrue_WhenSessionIsCreated() {
		let session = SessionContext.createSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		XCTAssertTrue(SessionContext.storeSession())
	}

	func test_LoadSessionFromStore_ShouldReturnFalse_WhenSessionIsNotStored() {
		SessionContext.removeStoredSession()
		XCTAssertFalse(SessionContext.loadSessionFromStore())
	}

	func test_LoadSessionFromStore_ShouldReturnTrue_WhenSessionIsStored() {
		let session = SessionContext.createSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		SessionContext.storeSession()

		XCTAssertTrue(SessionContext.loadSessionFromStore())
	}

	func test_LoadSessionFromStore_ShouldSetCurrentSession_WhenSessionIsStored() {
		let session = SessionContext.createSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])
		SessionContext.storeSession()
		SessionContext.clearSession()
		SessionContext.loadSessionFromStore()

		XCTAssertTrue(SessionContext.hasSession)

		XCTAssertOptional(SessionContext.currentUserName)
		XCTAssertOptional(SessionContext.currentPassword)

		XCTAssertEqual("username", SessionContext.currentUserName!)
		XCTAssertEqual("password", SessionContext.currentPassword!)
	}

	func test_LoadSessionFromStore_ShouldSetUserAttributes_WhenSessionIsStored() {
		let session = SessionContext.createSession(
				username: "username",
				password: "password",
				userAttributes: ["k1": "v1", "k2": "v2"])
		SessionContext.storeSession()
		SessionContext.clearSession()
		SessionContext.loadSessionFromStore()

		XCTAssertEqual("v1", (SessionContext.userAttribute("k1") ?? "") as String)
		XCTAssertEqual("v2", (SessionContext.userAttribute("k2") ?? "") as String)
	}

	func test_RemoveStoredSession_ShouldEmptyTheStoredSession() {
		let session = SessionContext.createSession(
				username: "username",
				password: "password",
				userAttributes: ["k1": "v1", "k2": "v2"])
		SessionContext.storeSession()
		SessionContext.clearSession()

		SessionContext.removeStoredSession()
		XCTAssertFalse(SessionContext.loadSessionFromStore())
	}



}
