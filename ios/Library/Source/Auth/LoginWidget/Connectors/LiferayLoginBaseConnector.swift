//
//  LiferayLoginBaseConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit

class LiferayLoginBaseConnector: BaseConnector {

	var loggedUserAttributes: [String:AnyObject]?

	var userName: String?
	var password: String?


	override func preRun() -> Bool {
		if userName == nil || password == nil {
			return false
		}

		showHUD(message: "Sending sign in...", details:"Wait few seconds...")

		SessionContext.clearSession()

		return true
	}

	override func postRun() {
		if lastError == nil {
			hideHUD()
		}
		else {
			SessionContext.removeStoredSession()
			hideHUD(error: lastError!, message: "Error signing in!")
		}
	}

	override func doRun() {
		let session = LRSession(
				server: LiferayServerContext.instance.server,
				username: userName,
				password: password)

		var outError: NSError?

		let result = sendGetUserRequest(
				service: LRUserService_v62(session: session),
				error: &outError)

		if outError != nil || result?["userId"] == nil {
			lastError = outError
			loggedUserAttributes = nil
		}
		else {
			lastError = nil
			loggedUserAttributes = result as? [String:AnyObject]
		}
	}

	func sendGetUserRequest(#service: LRUserService_v62, error: NSErrorPointer) -> NSDictionary? {
		return nil
	}

   
}
