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


class SessionContext_Basic_Tests: XCTestCase {

	override func tearDown() {
		SessionContext.clearSession()

		super.tearDown()
	}

	func test_CreateSession_ShouldReturnTheSession() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertTrue(session.authentication is LRBasicAuthentication)

		if let auth = session.authentication as? LRBasicAuthentication {
			XCTAssertEqual("username", auth.username)
			XCTAssertEqual("password", auth.password)
		}
	}

	func test_CreateSession_ShouldStoreUserAttributes() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: ["key":"value"])

		XCTAssertEqual("value", (SessionContext.userAttribute("key") ?? "") as! String)
	}

	func test_CurrentUserName_ShouldReturnTheUserName_WhenSessionIsCreated() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertNotNil(SessionContext.currentBasicUserName)
		XCTAssertEqual("username", SessionContext.currentBasicUserName!)
	}

	func test_CurrentPassword_ShouldReturnThePassword_WhenSessionIsCreated() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertNotNil(SessionContext.currentBasicPassword)
		XCTAssertEqual("password", SessionContext.currentBasicPassword!)
	}

	func test_CurrentUserName_ShouldReturnNil_WhenSessionIsNotCreated() {
		SessionContext.clearSession()
		XCTAssertNil(SessionContext.currentBasicUserName)
	}

	func test_CurrentPassword_ShouldReturnNil_WhenSessionIsNotCreated() {
		SessionContext.clearSession()
		XCTAssertNil(SessionContext.currentBasicPassword)
	}

	func test_HasSession_ShouldReturnTrue_WhenSessionIsCreated() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertTrue(SessionContext.hasSession)
	}

	func test_HasSession_ShouldReturnFalse_WhenSessionIsNotCreated() {
		SessionContext.clearSession()
		XCTAssertFalse(SessionContext.hasSession)
	}

	func test_CreateSessionFromCurrentSession_ShouldReturnNil_WhenSessionIsNotCreated() {
		SessionContext.clearSession()
		XCTAssertNil(SessionContext.createSessionFromCurrentSession())
	}

	func test_CreateSessionFromCurrentSession_ShouldReturnNewSession_WhenSessionIsCreated() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		let createdSession = SessionContext.createSessionFromCurrentSession()

		XCTAssertNotNil(createdSession)
		XCTAssertFalse(session == createdSession!)
	}

	func test_CreateBatchSessionFromCurrentSession_ShouldReturnNewSession_WhenSessionIsCreated() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		let createdSession = SessionContext.createBatchSessionFromCurrentSession()

		XCTAssertNotNil(createdSession)
		XCTAssertFalse(session == createdSession!)
	}

	func test_CreateBatchSessionFromCurrentSession_ShouldReturnNil_WhenSessionIsNotCreated() {
		SessionContext.clearSession()
		XCTAssertNil(SessionContext.createBatchSessionFromCurrentSession())
	}

	func test_ClearSession_ShouldEmptyTheSession() {
		let session = SessionContext.createBasicSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		SessionContext.clearSession()

		XCTAssertNil(SessionContext.currentBasicUserName)
		XCTAssertNil(SessionContext.currentBasicPassword)
		XCTAssertNil(SessionContext.userAttribute("k"))
		XCTAssertFalse(SessionContext.hasSession)
	}



}
