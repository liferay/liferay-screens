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

	private var forgotPasswordView: ForgotPasswordView {
		return widget.widgetView as ForgotPasswordView
	}


	//MARK BaseConnector

	override func validateView() -> Bool {
		var result = super.validateView()

		if result {
			if forgotPasswordView.userName == nil {
				showValidationHUD(message: "Please, enter the user name")
				result = false
			}
		}

		return result
	}

	override func preRun() -> Bool {
		var result = super.preRun()

		if result {
			showHUD(message: "Sending password request...", details: "Wait few seconds...")
		}

		return result
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
