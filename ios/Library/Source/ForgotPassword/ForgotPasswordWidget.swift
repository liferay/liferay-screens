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

@objc protocol ForgotPasswordWidgetDelegate {

	optional func onForgotPasswordResponse(newPasswordSent:Bool)
	optional func onForgotPasswordError(error: NSError)

}

@IBDesignable public class ForgotPasswordWidget: BaseWidget {

	@IBInspectable var anonymousApiUserName: String?
	@IBInspectable var anonymousApiPassword: String?

	@IBOutlet var delegate: ForgotPasswordWidgetDelegate?


	private typealias ResetClosureType = (String, LRMwuserService_v6201, (NSError)->()) -> (Void)

	private let resetClosures = [
		AuthType.Email.toRaw(): resetPasswordWithEmail,
		AuthType.ScreenName.toRaw(): resetPasswordWithScreenName,
		AuthType.UserId.toRaw(): resetPasswordWithUserId]

	private var resetClosure: ResetClosureType?

	
	public func setAuthType(authType:String) {
		forgotPasswordView().setAuthType(authType)

		resetClosure = resetClosures[authType]
	}

    // MARK: BaseWidget METHODS

	override public func onCreate() {
		setAuthType(AuthType.Email.toRaw())

		if let userName = LiferayContext.instance.currentSession?.username {
			forgotPasswordView().setUserName(userName)
		}
	}

	override public func onCustomAction(actionName: String?, sender: UIControl) {
		sendForgotPasswordRequest(forgotPasswordView().getUserName())
	}

	override public func onServerError(error: NSError) {
		delegate?.onForgotPasswordError?(error)

		finishOperationWithMessage("Error requesting password!", details: error.localizedDescription)
	}

	override public func onServerResult(result: [String:AnyObject]) {
		if let resultValue:AnyObject = result["result"] {
			let newPasswordSent = resultValue as Bool

			delegate?.onForgotPasswordResponse?(newPasswordSent)

			let userMessage = newPasswordSent ? "New password generated" : "New password reset link sent"

			finishOperationWithMessage(userMessage, details: "Check your email inbox")
		}
		else {
			var errorMsg:String? = result["error"]?.description

			if errorMsg == nil {
				errorMsg = result["exception.localizedMessage"]?.description
			}

			finishOperationWithMessage("An error happened", details: errorMsg)
		}
    }


	private func forgotPasswordView() -> ForgotPasswordView {
		return widgetView as ForgotPasswordView
	}

	private func sendForgotPasswordRequest(username:String) {
		if anonymousApiUserName == nil || anonymousApiPassword == nil {
			println(
				"ERROR: The credentials to use for anonymous API calls must be set in order to use " +
					"ForgotPasswordWidget")

			return
		}

		startOperationWithMessage("Sending password request...", details:"Wait few seconds...")

		let session = LiferayContext.instance.createSession(anonymousApiUserName!, password: anonymousApiPassword!)

		session.callback = self

		let companyId: CLongLong = (LiferayContext.instance.companyId as NSNumber).longLongValue

		resetClosure!(username, LRMwuserService_v6201(session: session)) {error in
			self.onFailure(error)
		}
	}
}

func resetPasswordWithEmail(email:String, service:LRMwuserService_v6201, onError:(NSError)->()) {
	let companyId = (LiferayContext.instance.companyId as NSNumber).longLongValue

	var outError: NSError?

	service.sendPasswordByEmailAddressWithCompanyId(companyId, emailAddress: email, serviceContext:nil, error: &outError)

	if let error = outError {
		onError(error)
	}
}

func resetPasswordWithScreenName(screenName:String, service:LRMwuserService_v6201, onError:(NSError)->()) {
	let companyId = (LiferayContext.instance.companyId as NSNumber).longLongValue

	var outError: NSError?

	service.sendPasswordByScreenNameWithCompanyId(companyId, screenName: screenName, serviceContext:nil, error: &outError)

	if let error = outError {
		onError(error)
	}
}

func resetPasswordWithUserId(userId:String, service:LRMwuserService_v6201, onError:(NSError)->()) {
	let companyId = (LiferayContext.instance.companyId as NSNumber).longLongValue
	let userIdValue = (userId.toInt()! as NSNumber).longLongValue

	var outError: NSError?

	service.sendPasswordByUserIdWithCompanyId(companyId, userId: userIdValue, serviceContext: nil, error: &outError)

	if let error = outError {
		onError(error)
	}
}