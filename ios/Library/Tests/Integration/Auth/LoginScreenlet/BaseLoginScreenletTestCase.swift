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


class BaseLoginScreenletTestCase: IntegrationTestCase {

	var screenlet: LoginScreenlet?

	internal class Delegate : LoginScreenletDelegate {
		let completed: AnyObject -> Void

		var credentialsSavedCalled = false
		var credentialsLoadedCalled = false

		init(completed: AnyObject -> Void) {
			self.completed = completed
		}

		func onLoginResponse(attributes: [String:AnyObject]) {
			completed(attributes)
		}

		func onLoginError(error: NSError) {
			completed(error)
		}

		func onCredentialsSaved() {
			credentialsSavedCalled = true
		}

		func onCredentialsLoaded() {
			credentialsLoadedCalled = true
		}

	}


	override func setUp() {
		super.setUp()

		screenlet = LoginScreenlet(frame: CGRectZero)
		screenlet!.screenletView = screenlet!.loadScreenletView()

		SessionContext.clearSession()
		SessionContext.removeStoredSession()
	}

	internal let loginOKJSON =
		"[{\"agreedToTermsOfUse\":true,\"comments\":\"From stub!\",\"companyId\":10154," +
		"\"contactId\":10202,\"createDate\":1413408478000,\"defaultUser\":false," +
		"\"emailAddress\":\"test@liferay.com\",\"emailAddressVerified\":true,\"facebookId\":0," +
		"\"failedLoginAttempts\":0,\"firstName\":\"Test\",\"graceLoginCount\":0," +
		"\"greeting\":\"Welcome Test Test!\",\"jobTitle\":\"\",\"languageId\":\"en_US\"," +
		"\"lastFailedLoginDate\":null,\"lastLoginDate\":1427299352000,\"lastLoginIP\":" +
		"\"127.0.0.1\",\"lastName\":\"Test\",\"ldapServerId\":-1,\"lockout\":false," +
		"\"lockoutDate\":null,\"loginDate\":1427372894000,\"loginIP\":\"127.0.0.1\"," +
		"\"middleName\":\"\",\"modifiedDate\":1413408478000,\"openId\":\"\",\"portraitId\":14809," +
		"\"reminderQueryAnswer\":\"test\"," +
		"\"reminderQueryQuestion\":\"what-is-your-father's-middle-name\",\"screenName\":\"test\"," +
		"\"status\":0,\"timeZoneId\":\"UTC\",\"userId\":10201," +
		"\"uuid\":\"a16f9d4a-9012-4eee-a42b-269c8a8263c7\"}]";

	internal let loginFailedAuthentication =
		"{\"exception\":\"Authenticated access required\"}"

}
