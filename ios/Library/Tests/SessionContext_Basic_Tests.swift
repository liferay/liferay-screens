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
		SessionContext.instance.clearSession()

		super.tearDown()
	}

	func test_CreateSession_ShouldReturnTheSession() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertEqual("username", session.username)
		XCTAssertEqual("password", session.password)
	}

	func test_CreateSession_ShouldStoreUserAttributes() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: ["key":"value"])

		XCTAssertEqual("value", (SessionContext.instance.userAttribute("key") ?? "") as String)
	}

	func test_CurrentUserName_ShouldReturnTheUserName_WhenSessionIsCreated() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertNotNil(SessionContext.instance.currentUserName)
		XCTAssertEqual("username", SessionContext.instance.currentUserName!)
	}

	func test_CurrentPassword_ShouldReturnThePassword_WhenSessionIsCreated() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertNotNil(SessionContext.instance.currentPassword)
		XCTAssertEqual("password", SessionContext.instance.currentPassword!)
	}

	func test_CurrentUserName_ShouldReturnNil_WhenSessionIsNotCreated() {
		SessionContext.instance.clearSession()
		XCTAssertNil(SessionContext.instance.currentUserName)
	}

	func test_CurrentPassword_ShouldReturnNil_WhenSessionIsNotCreated() {
		SessionContext.instance.clearSession()
		XCTAssertNil(SessionContext.instance.currentPassword)
	}

	func test_HasSession_ShouldReturnTrue_WhenSessionIsCreated() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertTrue(SessionContext.instance.hasSession)
	}

	func test_HasSession_ShouldReturnFalse_WhenSessionIsNotCreated() {
		SessionContext.instance.clearSession()
		XCTAssertFalse(SessionContext.instance.hasSession)
	}

	func test_CreateSessionFromCurrentSession_ShouldReturnNil_WhenSessionIsNotCreated() {
		SessionContext.instance.clearSession()
		XCTAssertNil(SessionContext.instance.createSessionFromCurrentSession())
	}

	func test_CreateSessionFromCurrentSession_ShouldReturnNewSession_WhenSessionIsCreated() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		let createdSession = SessionContext.instance.createSessionFromCurrentSession()
		XCTAssertNotNil(createdSession)
		XCTAssertFalse(session == createdSession!)

		createdSession!.username = "modified-username"
		createdSession!.password = "modified-password"

		XCTAssertEqual("modified-username", createdSession!.username)
		XCTAssertEqual("modified-password", createdSession!.password)

		XCTAssertEqual("username", SessionContext.instance.currentUserName!)
		XCTAssertEqual("password", SessionContext.instance.currentPassword!)
	}

	func test_CreateBatchSessionFromCurrentSession_ShouldReturnNewSession_WhenSessionIsCreated() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: [:])

		let createdSession = SessionContext.instance.createBatchSessionFromCurrentSession()
		XCTAssertNotNil(createdSession)
		XCTAssertFalse(session == createdSession!)

		createdSession!.username = "modified-username"
		createdSession!.password = "modified-password"

		XCTAssertEqual("modified-username", createdSession!.username)
		XCTAssertEqual("modified-password", createdSession!.password)

		XCTAssertEqual("username", SessionContext.instance.currentUserName!)
		XCTAssertEqual("password", SessionContext.instance.currentPassword!)
	}

	func test_CreateBatchSessionFromCurrentSession_ShouldReturnNil_WhenSessionIsNotCreated() {
		SessionContext.instance.clearSession()
		XCTAssertNil(SessionContext.instance.createBatchSessionFromCurrentSession())
	}

	func test_ClearSession_ShouldEmptyTheSession() {
		let session = SessionContext.instance.createSession(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		SessionContext.instance.clearSession()

		XCTAssertNil(SessionContext.instance.currentUserName)
		XCTAssertNil(SessionContext.instance.currentPassword)
		XCTAssertNil(SessionContext.instance.userAttribute("k"))
		XCTAssertFalse(SessionContext.instance.hasSession)
	}



}
