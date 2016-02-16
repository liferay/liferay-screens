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

	func test_StoreSession_ShouldReturnFalse_WhenUserAttributesAreEmpty() {
		SessionContext.loginWithBasic(
				username: "username",
				password: "password",
				userAttributes: [:])

		withCredentialsStoreMockedSession { mock in
			XCTAssertFalse(SessionContext.currentContext!.storeCredentials())
			XCTAssertFalse(mock.calledStoreCredential)
		}
	}

	func test_StoreSession_ShouldReturnTrue_WhenSessionIsCreated() {
		SessionContext.loginWithBasic(
				username: "username",
				password: "password",
				userAttributes: ["k":"v"])

		withCredentialsStoreMockedSession { mock in
			XCTAssertTrue(SessionContext.currentContext!.storeCredentials())
			XCTAssertTrue(mock.calledStoreCredential)
		}
	}

	func test_LoadSessionFromStore_ShouldReturnFalse_WhenSessionIsNotStored() {
		SessionContext.loginWithBasic(
				username: "username123",
				password: "password456",
				userAttributes: ["k":"v"])

		withCredentialsStoreMockedSession { mock in
			let storage = CredentialsStorage(store: mock)

			mock.hasData = false

			XCTAssertFalse(SessionContext.loadStoredCredentials(storage))
			XCTAssertTrue(mock.calledLoadCredential)

			// current session is not cleared
			XCTAssertTrue(SessionContext.currentContext!.basicAuthUsername == "username123")
			XCTAssertTrue(SessionContext.currentContext!.basicAuthPassword == "password456")
		}
	}

	func test_RemoveStoredSession_ShouldEmptyTheStoredSession() {
		SessionContext.loginWithBasic(
			username: "username",
			password: "password",
			userAttributes: ["k":"v"])

		withCredentialsStoreMockedSession { mock in
			SessionContext.currentContext!.removeStoredCredentials()
			XCTAssertTrue(mock.calledRemoveCredential)
		}
	}

}
