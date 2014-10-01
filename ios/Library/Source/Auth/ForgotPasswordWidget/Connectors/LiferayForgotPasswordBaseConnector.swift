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
import UIKit


class LiferayForgotPasswordBaseConnector: BaseConnector, NSCopying {

	var newPasswordSent: Bool?

	private var forgotPasswordView: ForgotPasswordView {
		return widget.widgetView as ForgotPasswordView
	}


	//MARK BaseConnector

	override func validateView() -> Bool {
		if !super.validateView() {
			return false
		}

		if forgotPasswordView.userName == nil {
			showValidationHUD(message: "Please, enter the user name")

			return false
		}

		return true
	}

	override func preRun() -> Bool {
		if !super.preRun() {
			return false
		}

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


	//MARK: Template Methods
	
	internal func sendForgotPasswordRequest(
			#service: LRMobilewidgetsuserService_v62,
			error: NSErrorPointer)
			-> Bool? {

		assertionFailure("sendForgotPasswordRequest must be overriden")

		return nil
	}

}
