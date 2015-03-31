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


class LoginScreenlet_ByEmail_Tests: BaseLoginScreenletTestCase {

	func test_Successful() {
		scenario("LoginScreenlet by email should work") {
			given("a configured login screenlet", {
				with("auth method set to email") {
					self.screenlet!.authMethod = AuthMethod.Email.rawValue
				}
				and("email and password entered by the user") {
					self.screenlet!.viewModel.userName = "test@liferay.com"
					self.screenlet!.viewModel.password = "test"
				}
			},
			when: "the screenlet sends the request to the server and the response is received", {
				mockServer.stubService("get-user-by-email-address", withResult:mockServer.loginOK())

				self.screenlet!.delegate = Delegate() { result in
					done("login response received", withResult: result)
				}
				self.screenlet!.performDefaultAction()

			},
			eventually: "the state of the screenlet should be consistent", {result in
				assertThat("the error should be nil") {
					XCTAssertFalse(result is NSError)
				}
				assertThat("the attributes should be populated") {
					XCTAssertTrue(result is [String:AnyObject])

					let attrs = result as [String:AnyObject]

					XCTAssertTrue(attrs.count > 0)
					XCTAssertNotNil(attrs["emailAddress"])
					XCTAssertEqual("test@liferay.com", attrs["emailAddress"] as String)
				}
				assertThat("the session should be established") {
					XCTAssertTrue(SessionContext.hasSession)
				}
				assertThat("the current user name should be the email address") {
					XCTAssertNotNil(SessionContext.currentUserName)
					XCTAssertEqual("test@liferay.com", SessionContext.currentUserName!)
				}
				assertThat("the current password should be available") {
					XCTAssertNotNil(SessionContext.currentPassword)
					XCTAssertEqual("test", SessionContext.currentPassword!)
				}
			},
			.TestAndWaitFor("login response received", self))
		}
	}

	func test_StoreCredentials() {
		scenario("LoginScreenlet by email store credentials") {
			given("a configured login screenlet", {
				with("auth method set to email") {
					self.screenlet!.authMethod = AuthMethod.Email.rawValue
				}
				and("email and password entered by the user") {
					self.screenlet!.viewModel.userName = "test@liferay.com"
					self.screenlet!.viewModel.password = "test"
				}
				and("store credentials flag enabled") {
					self.screenlet!.saveCredentials = true
				}
			},
			when: "the screenlet sends the request to the server and the response is received", {
				mockServer.stubService("get-user-by-email-address", withResult:mockServer.loginOK())

				self.screenlet!.delegate = Delegate() {
					done("login response received", withResult: $0)
				}
				self.screenlet!.performDefaultAction()

			},
			eventually: "the credentials should be stored", {result in
				assertThat("the session context can load the credentials") {
					XCTAssertTrue(SessionContext.loadSessionFromStore())
				}
				assertThat("onCredentialsSaved delegate is called") {
					XCTAssertTrue((self.screenlet!.delegate as Delegate).credentialsSavedCalled)
				}
			},
			.TestAndWaitFor("login response received", self))
		}
	}

	func test_Failed_WrongCredentials() {
		scenario("LoginScreenlet by email should fail when credentials are wrong") {
			given("a configured login screenlet", {
				with("auth method set to email") {
					self.screenlet!.authMethod = AuthMethod.Email.rawValue
				}
				and("email and password entered by the user") {
					self.screenlet!.viewModel.userName = "test@liferay.com"
					self.screenlet!.viewModel.password = "test"
				}
			},
			when: "the screenlet sends the request to the server and the response is received", {
				mockServer.stubService("get-user-by-email-address",
						withResult:mockServer.loginFailedAuthentication())

				self.screenlet!.delegate = Delegate() { result in
					done("login response received", withResult: result)
				}
				self.screenlet!.performDefaultAction()

			},
			eventually: "the state of the screenlet should be consistent", {result in
				assertThat("the error should be nil") {
					XCTAssertTrue(result is NSError)

					let error = result as NSError

					XCTAssertEqual("Authenticated access required", error.localizedDescription)

				}
				assertThat("the session should not be established") {
					XCTAssertFalse(SessionContext.hasSession)
				}
				assertThat("the current user name should be empty") {
					XCTAssertNil(SessionContext.currentUserName)
				}
				assertThat("the current password should be empty") {
					XCTAssertNil(SessionContext.currentPassword)
				}
			},
			.TestAndWaitFor("login response received", self))
		}
	}


}
