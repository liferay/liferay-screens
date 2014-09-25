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

		SessionContext.instance.clearSession()
	}

	override func tearDown() {
		SessionContext.removeStoredSession()
		SessionContext.instance.clearSession()

		super.tearDown()
	}

	func test_StoreSession_ShouldReturnFalse_WhenSessionIsNotCreated() {
		XCTAssertFalse(SessionContext.instance.storeSession())
	}

	func test_StoreSession_ShouldReturnFalse_WhenUserAttributesAreEmpty() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertFalse(SessionContext.instance.storeSession())
	}

	func test_StoreSession_ShouldReturnTrue_WhenSessionIsCreated() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		XCTAssertTrue(SessionContext.instance.storeSession())
	}

	func test_LoadSessionFromStore_ShouldReturnFalse_WhenSessionIsNotStored() {
		SessionContext.removeStoredSession()
		XCTAssertFalse(SessionContext.instance.loadSessionFromStore())
	}

	func test_LoadSessionFromStore_ShouldReturnTrue_WhenSessionIsStored() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		SessionContext.instance.storeSession()

		XCTAssertTrue(SessionContext.instance.loadSessionFromStore())
	}

	func test_LoadSessionFromStore_ShouldSetCurrentSession_WhenSessionIsStored() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])
		SessionContext.instance.storeSession()
		SessionContext.instance.clearSession()
		SessionContext.instance.loadSessionFromStore()

		XCTAssertTrue(SessionContext.instance.hasSession)

		XCTAssertOptional(SessionContext.instance.currentUserName)
		XCTAssertOptional(SessionContext.instance.currentPassword)

		XCTAssertEqual("username", SessionContext.instance.currentUserName!)
		XCTAssertEqual("password", SessionContext.instance.currentPassword!)
	}

	func test_LoadSessionFromStore_ShouldSetUserAttributes_WhenSessionIsStored() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: ["k1": "v1", "k2": "v2"])
		SessionContext.instance.storeSession()
		SessionContext.instance.clearSession()
		SessionContext.instance.loadSessionFromStore()

		XCTAssertEqual("v1", (SessionContext.instance.userAttribute("k1") ?? "") as String)
		XCTAssertEqual("v2", (SessionContext.instance.userAttribute("k2") ?? "") as String)
	}

	func test_RemoveStoredSession_ShouldEmptyTheStoredSession() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: ["k1": "v1", "k2": "v2"])
		SessionContext.instance.storeSession()
		SessionContext.instance.clearSession()

		SessionContext.removeStoredSession()
		XCTAssertFalse(SessionContext.instance.loadSessionFromStore())
	}



}
