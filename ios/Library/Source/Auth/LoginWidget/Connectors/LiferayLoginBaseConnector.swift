//
//  LiferayLoginBaseConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit

class LiferayLoginBaseConnector: BaseConnector, NSCopying {

	var loggedUserAttributes: [String:AnyObject]?


	//MARK: BaseConnector

	override func preRun() -> Bool {
		let view = widget.widgetView as LoginView
		assert(view.userName != nil, "User name is required to log in")
		assert(view.password != nil, "Password is required to log in")

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

	override func doRun(#session: LRSession) {
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


	//MARK: NSCopying

	internal func copyWithZone(zone: NSZone) -> AnyObject {
		return self
	}


	// MARK: Internal methods

	internal func sendGetUserRequest(#service: LRUserService_v62, error: NSErrorPointer) -> NSDictionary? {
		return nil
	}

   
}
