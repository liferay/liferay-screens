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
		screenlet!.themeName = "test"
		screenlet!.screenletView = screenlet!.loadScreenletView()

		SessionContext.clearSession()
	}

}
