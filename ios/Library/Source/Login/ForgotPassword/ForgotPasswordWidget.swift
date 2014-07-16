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

	@optional func onForgotPasswordResponse()
	@optional func onForgotPasswordError(error: NSError)

}

class ForgotPasswordWidget: BaseWidget {

	@IBOutlet var delegate: ForgotPasswordWidgetDelegate?
    
	typealias ResetClosureType = (String, LRMobilewidgetsService_v6201, (NSError)->()) -> (Void)

	let resetClosures = [
		AuthType.Email.toRaw(): resetPasswordWithEmail,
		AuthType.ScreenName.toRaw(): resetPasswordWithScreenName]

	var resetClosure: ResetClosureType?

	
	// PUBLIC METHODS
	
	
	func setAuthType(authType:AuthType) {
		forgotPasswordView().setAuthType(authType.toRaw())

		resetClosure = resetClosures[authType.toRaw()]
	}


    // BaseWidget METHODS


	override func onCreate() {
		setAuthType(AuthType.Email)

		forgotPasswordView().usernameField.text = LiferayContext.instance.currentSession?.username
	}

	override func onCustomAction(actionName: String?, sender: UIControl) {
		sendForgotPasswordRequest(forgotPasswordView().usernameField.text)
	}

	override func onServerError(error: NSError) {
		//TODO get server error message
		delegate?.onForgotPasswordError?(error)

		hideHUDWithMessage("Error requesting password!", details: nil)
	}

	override func onServerResult(result: [String:AnyObject!]) {
		let success = result["result"]

		if let successValue = success as? Bool {
			if successValue {
				delegate?.onForgotPasswordResponse?()
			}
			else {
				onServerError(NSError(domain: "LiferayWidgets", code: -1, userInfo: nil))

				return
			}
		}

		hideHUDWithMessage("New password request sent!", details: nil)
    }


	// PRIVATE METHODS


	func forgotPasswordView() -> ForgotPasswordView {
		return widgetView as ForgotPasswordView
	}

	func sendForgotPasswordRequest(username:String) {
		showHUDWithMessage("Sending password request...", details:"Wait few seconds...")

		let session = LiferayContext.instance.createSession("test", password: "test")
		session.callback = self

		let service = LRMobilewidgetsService_v6201(session: session)

		let companyId: CLongLong = (LiferayContext.instance.companyId as NSNumber).longLongValue

		resetClosure!(username, LRMobilewidgetsService_v6201(session: session)) {error in
			self.onFailure(error)
		}
	}
}

func resetPasswordWithEmail(email:String, service:LRMobilewidgetsService_v6201, onError:(NSError)->()) {
	let companyId = (LiferayContext.instance.companyId as NSNumber).longLongValue

	var outError: NSError?

	service.resetPasswordByEmailAddressWithCompanyId(companyId, emailAddress: email, error: &outError)

	if let error = outError {
		onError(error)
	}
}

func resetPasswordWithScreenName(screenName:String, service:LRMobilewidgetsService_v6201, onError:(NSError)->()) {
	let companyId = (LiferayContext.instance.companyId as NSNumber).longLongValue

	var outError: NSError?

	service.resetPasswordByScreenNameWithCompanyId(companyId, screenName: screenName, error: &outError)

	if let error = outError {
		onError(error)
	}
}