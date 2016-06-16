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
		SessionContext.logout()

		super.tearDown()
	}

	func test_LoginWithBasic_ShouldReturnTheSession() {
		let session = SessionContext.loginWithBasic(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertTrue(session.authentication is LRBasicAuthentication)

		if let auth = session.authentication as? LRBasicAuthentication {
			XCTAssertEqual("username", auth.username)
			XCTAssertEqual("password", auth.password)
		}
	}

	func test_LoginWithBasic_ShouldStoreUserAttributes() {
		SessionContext.loginWithBasic(
				username: "username",
				password: "password",
				userAttributes: ["key":"value"])

		XCTAssertEqual("value", (SessionContext.currentContext?.userAttribute("key") ?? "") as? String)
	}

	func test_BasicAuthUsername_ShouldReturnTheUserName_WhenSessionIsCreated() {
		SessionContext.loginWithBasic(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertNotNil(SessionContext.currentContext?.basicAuthUsername)
		XCTAssertEqual("username", SessionContext.currentContext?.basicAuthUsername!)
	}

	func test_BasicAuthPassword_ShouldReturnThePassword_WhenSessionIsCreated() {
		SessionContext.loginWithBasic(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertNotNil(SessionContext.currentContext?.basicAuthPassword)
		XCTAssertEqual("password", SessionContext.currentContext?.basicAuthPassword!)
	}

	func test_IsLoggedIn_ShouldReturnTrue_WhenSessionIsCreated() {
		SessionContext.loginWithBasic(
				username: "username",
				password: "password",
				userAttributes: [:])

		XCTAssertTrue(SessionContext.isLoggedIn)
	}

	func test_IsLoggedIn_ShouldReturnFalse_WhenSessionIsNotCreated() {
		SessionContext.logout()
		XCTAssertFalse(SessionContext.isLoggedIn)
	}

	func test_RequestSession_ShouldReturnNewSession_WhenSessionIsCreated() {
		let session = SessionContext.loginWithBasic(
				username: "username",
				password: "password",
				userAttributes: [:])

		let createdSession = SessionContext.currentContext!.createRequestSession()

		XCTAssertNotNil(createdSession)
		XCTAssertFalse(session === createdSession)
	}

	func test_Logout_ShouldEmptyTheSession() {
		SessionContext.loginWithBasic(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		SessionContext.logout()

		XCTAssertNil(SessionContext.currentContext?.basicAuthUsername)
		XCTAssertNil(SessionContext.currentContext?.basicAuthPassword)
		XCTAssertNil(SessionContext.currentContext?.userAttribute("k"))
	}

}
