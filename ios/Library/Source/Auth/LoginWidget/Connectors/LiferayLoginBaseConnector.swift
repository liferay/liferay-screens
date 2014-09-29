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

	init(widget: BaseWidget) {
		super.init(widget: widget, session: nil)
	}

	override func main() {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.startOperationWithMessage("Sending sign in...",
					details:"Wait few seconds...")
		}

		SessionContext.clearSession()

		session = LRSession(
				server: LiferayServerContext.instance.server,
				username: userName,
				password: password)

		var outError: NSError?

		let result = sendGetUserRequest(
				service: LRUserService_v62(session: session),
				error: &outError)

		if outError != nil || result?["userId"] == nil {
			SessionContext.removeStoredSession()
			lastError = outError
			loggedUserAttributes = nil

			dispatch_async(dispatch_get_main_queue()) {
				self.widget.finishOperationWithError(self.lastError!, message:"Error signing in!")
			}
		}
		else {
			lastError = nil
			loggedUserAttributes = result as? [String:AnyObject]

			dispatch_async(dispatch_get_main_queue()) {
				self.widget.finishOperation()
			}
		}
	}

	func sendGetUserRequest(#service: LRUserService_v62, error: NSErrorPointer) -> NSDictionary? {
		return nil
	}

   
}
