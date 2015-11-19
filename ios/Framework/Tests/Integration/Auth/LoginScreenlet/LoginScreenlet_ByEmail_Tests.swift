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

	override func setUp() {
		SessionContext.sessionStorage = SessionStorage(
				credentialStore: CredentialStoreMock())

		super.setUp()
	}

	override func tearDown() {
		super.tearDown()

		SessionContext.sessionStorage = SessionStorage(
			credentialStore: CredentialStoreMock())
	}

	func test_Successful() {
		scenario("LoginScreenlet by email should work") {
			given("a configured login screenlet") {
				with("auth method set to email") {
					self.screenlet!.basicAuthMethod = BasicAuthMethod.Email.rawValue
				}
				and("email and password entered by the user") {
					self.screenlet!.viewModel.userName = "test@liferay.com"
					self.screenlet!.viewModel.password = "test"
				}
				and("server returns ok") {
					mockServer.stubService("get-user-by-email-address",
							withResult:mockServer.loginOK())
				}
			}
			when("the request is sent and the response is received") {
				self.screenlet!.delegate = TestLoginScreenletDelegate() { result in
					done("login response received", withResult: result)
				}
				self.screenlet!.performDefaultAction()
			}
			eventually("the state of the screenlet should be consistent", code: {result in
				assertThat("the error should be nil") {
					XCTAssertFalse(result is NSError)
				}
				assertThat("the attributes should be populated") {
					XCTAssertTrue(result is [String:AnyObject])

					let attrs = result as! [String:AnyObject]

					XCTAssertTrue(attrs.count > 0)
					XCTAssertNotNil(attrs["emailAddress"])
					XCTAssertEqual("test@liferay.com", attrs["emailAddress"] as! String)
				}
				assertThat("the session should be established") {
					XCTAssertTrue(SessionContext.hasSession)
				}
				assertThat("the current user name should be the email address") {
					XCTAssertNotNil(SessionContext.currentBasicUserName)
					XCTAssertEqual("test@liferay.com", SessionContext.currentBasicUserName!)
				}
				assertThat("the current password should be available") {
					XCTAssertNotNil(SessionContext.currentBasicPassword)
					XCTAssertEqual("test", SessionContext.currentBasicPassword!)
				}
			},
			action: .TestAndWaitFor("login response received", self))
		}
	}

	func test_StoreCredentials() {
		scenario("LoginScreenlet by email store credentials") {
			given("a configured login screenlet") {
				with("auth method set to email") {
					self.screenlet!.basicAuthMethod = BasicAuthMethod.Email.rawValue
				}
				and("email and password entered by the user") {
					self.screenlet!.viewModel.userName = "test@liferay.com"
					self.screenlet!.viewModel.password = "test"
				}
				and("store credentials flag enabled") {
					self.screenlet!.saveCredentials = true
				}
				and("server returns ok") {
					mockServer.stubService("get-user-by-email-address",
							withResult:mockServer.loginOK())
				}
			}
			when("the request is sent and the response is received") {
				// we need to complete the test when the credentials are saved.
				// This happens *after* the loginResponse is received
				let loginDelegate = TestLoginScreenletDelegate()
				loginDelegate.onCredentialsStored = {
					done("login response received", withResult: nil)
				}
				self.screenlet!.delegate = loginDelegate

				self.screenlet!.performDefaultAction()

			}
			eventually("the credentials should be stored", code: {result in
				assertThat("the session context can load the credentials") {
					XCTAssertTrue(SessionContext.loadSessionFromStore())
				}
				assertThat("onCredentialsSaved delegate is called") {
					XCTAssertTrue((self.screenlet!.delegate as! TestLoginScreenletDelegate).credentialsSavedCalled)
				}
			},
			action: .TestAndWaitFor("login response received", self))
		}
	}

	func test_Failed_WrongCredentials() {
		scenario("LoginScreenlet by email should fail when credentials are wrong") {
			given("a configured login screenlet") {
				with("auth method set to email") {
					self.screenlet!.basicAuthMethod = BasicAuthMethod.Email.rawValue
				}
				and("email and password entered by the user") {
					self.screenlet!.viewModel.userName = "test@liferay.com"
					self.screenlet!.viewModel.password = "test"
				}
				and("server returns failure") {
					mockServer.stubService("get-user-by-email-address",
							withResult:mockServer.loginFailedAuthentication())
				}
			}
			when("the request is sent and the response is received") {
				self.screenlet!.delegate = TestLoginScreenletDelegate() { result in
					done("login response received", withResult: result)
				}
				self.screenlet!.performDefaultAction()
			}
			eventually("the state of the screenlet should be consistent", code: {result in
				assertThat("the error should be nil") {
					XCTAssertTrue(result is NSError)

					let error = result as! NSError

					XCTAssertEqual("Authenticated access required", error.localizedDescription)

				}
				assertThat("the session should not be established") {
					XCTAssertFalse(SessionContext.hasSession)
				}
				assertThat("the current user name should be empty") {
					XCTAssertNil(SessionContext.currentBasicUserName)
				}
				assertThat("the current password should be empty") {
					XCTAssertNil(SessionContext.currentBasicPassword)
				}
			},
			action: .TestAndWaitFor("login response received", self))
		}
	}

}
