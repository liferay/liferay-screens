


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

class LRSession_Storage_Tests: XCTestCase {

	override func setUp() {
		super.setUp()
	}

	override func tearDown() {
		LRSession.emptyStore()

		super.tearDown()
	}

	func testStoredSessionShouldReturnNilWhenEmptyStore() {
		let storedSession = LRSession.storedSession()

		XCTAssertNil(storedSession, "'LRSession.storedSession' result should be nil when loaded from empty store")
	}

	func testStoredSessionShouldReturnSessionWhenValidSessionWasStored() {
		let session = LRSession(LiferayContext.instance.server, username:"user", password:"pass")

		XCTAssertTrue(session.store(), "'LRSession.store' result should be 'true' when store was ok")

		if let storedSession = LRSession.storedSession() {
			XCTAssertEqual(session.server!, storedSession.server!)
			XCTAssertEqual(session.username!, storedSession.username!)
			XCTAssertEqual(session.password!, storedSession.password!)
		}
		else {
			XCTFail("'LRSession.storedSession' result should not be nil when loaded from valid store")
		}
	}

	func testStoreShouldReturnFalseWhenUsernameIsMissing() {
		let session = LRSession(LiferayContext.instance.server, username:nil, password:"pass")

		XCTAssertFalse(session.store(), "'LRSession.store' result should be 'false' when 'username' is missing")
	}

	func testStoreShouldReturnFalseWhenPasswordIsMissing() {
		let session = LRSession(LiferayContext.instance.server, username:"user", password:nil)

		XCTAssertFalse(session.store(), "'LRSession.store' result should be 'false' when 'password' is missing")
	}

	func testEmptyStoreShouldRemoveAllStoredCredentials() {
		let session = LRSession(LiferayContext.instance.server, username:"user", password:"pass")
		session.store()

		LRSession.emptyStore()

		if let storedSession = LRSession.storedSession() {
			XCTFail("'LRSession.storedSession' result should be nil when store is just emptied")
		}
	}
	
}
