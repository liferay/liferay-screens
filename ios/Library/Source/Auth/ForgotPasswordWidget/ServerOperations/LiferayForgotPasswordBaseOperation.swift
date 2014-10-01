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


public class LiferayForgotPasswordBaseOperation: ServerOperation, NSCopying {

	internal(set) var newPasswordSent: Bool?

	internal override var hudLoadingMessage: HUDMessage? {
		return ("Sending password request...", details: "Wait few seconds...")
	}
	internal override var hudFailureMessage: HUDMessage? {
		return ("An error happened requesting the password", details: nil)
	}
	internal override var hudSuccessMessage: HUDMessage? {
		return (successMessage, details: "Check your email inbox")
	}

	private var successMessage = ""

	private let newPasswordSuccessMessage = "New password generated"
	private let resetPasswordSuccessMessage = "New password reset link sent"

	private var forgotPasswordView: ForgotPasswordView {
		return widget.widgetView as ForgotPasswordView
	}


	//MARK ServerOperation

	override func validateView() -> Bool {
		if forgotPasswordView.userName == nil {
			showValidationHUD(message: "Please, enter the user name")

			return false
		}

		return true
	}

	override func postRun() {
		if lastError != nil {
			successMessage = newPasswordSent!
					? newPasswordSuccessMessage
					: resetPasswordSuccessMessage
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
		else if result != nil {
			lastError = nil
			newPasswordSent = result
		}
		else {
			lastError = createError(cause: .InvalidServerResponse, userInfo: nil)
			newPasswordSent = nil
		}
	}


	//MARK: NSCopying

	public func copyWithZone(zone: NSZone) -> AnyObject {
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
