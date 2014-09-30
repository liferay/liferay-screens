//
//  LiferayLoginBaseConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit


class LiferayForgotPasswordBaseConnector: BaseConnector, NSCopying {

	var newPasswordSent: Bool?


	//MARK BaseConnector

	override func preRun() -> Bool {
		let view = widget.widgetView as ForgotPasswordView
		assert(view.userName != nil, "User name is required to forgot password request")

		showHUD(message: "Sending password request...", details: "Wait few seconds...")

		return true
	}

	override func postRun() {
		if lastError != nil {
			hideHUD(error: lastError!, message:"Error requesting password!")
		}
		else if newPasswordSent == nil {
			hideHUD(errorMessage: "An error happened requesting the password")
		}
		else {
			let userMessage = newPasswordSent!
					? "New password generated"
					: "New password reset link sent"

			hideHUD(message: userMessage, details: "Check your email inbox")
		}
	}

	override func doRun(#session: LRSession) {
		var outError: NSError?

		let result = sendForgotPasswordRequest(
				service: LRMobilewidgetsuserService_v62(session: session),
				error: &outError)

		if outError != nil {
			lastError = outError!
			newPasswordSent = nil
		}
		else {
			lastError = nil
			newPasswordSent = result
		}
	}


	//MARK: NSCopying

	internal func copyWithZone(zone: NSZone) -> AnyObject {
		assertionFailure("copyWithZone must be overriden")
		return self
	}


	func sendForgotPasswordRequest(
			#service: LRMobilewidgetsuserService_v62,
			error: NSErrorPointer)
			-> Bool? {

		return nil
	}

}
