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


@objc protocol SignUpWidgetDelegate {

	optional func onSignUpResponse(attributes: [String:AnyObject])
	optional func onSignUpError(error: NSError)

}

@IBDesignable public class SignUpWidget: BaseWidget {

	@IBInspectable var anonymousApiUserName: String?
	@IBInspectable var anonymousApiPassword: String?

	@IBInspectable var autologin: Bool = true

	@IBOutlet var delegate: SignUpWidgetDelegate?

	public var authType: AuthType = AuthType.Email


	//MARK: BaseWidget METHODS

	override internal func onCustomAction(actionName: String?, sender: AnyObject?) {
		sendSignUpWithEmailAddress(signUpView().getEmailAddress(),
				password:signUpView().getPassword(),
				firstName:signUpView().getFirstName(),
				lastName:signUpView().getLastName())
	}

	override internal func onServerError(error: NSError) {
		delegate?.onSignUpError?(error)

		finishOperationWithError(error, message:"Error signing up!")
	}

	override internal func onServerResult(result: [String:AnyObject]) {
		delegate?.onSignUpResponse?(result)

		if autologin && creatingPassword != nil {
			LiferayContext.instance.clearSession()
			LRSession.removeStoredCredential()

			LiferayContext.instance.createSession(creatingUsername!, password: creatingPassword!)
		}

		finishOperationWithMessage("Sign up completed")
	}


	private func signUpView() -> SignUpView {
		return widgetView as SignUpView
	}

	private func sendSignUpWithEmailAddress(
			emailAddress:String, password:String, firstName:String, lastName:String) {

		if anonymousApiUserName == nil || anonymousApiPassword == nil {
			println(
				"ERROR: The credentials to use for anonymous API calls must be set in order to use " +
				"SignUpWidget")

			return
		}

		startOperationWithMessage("Sending sign up...", details:"Wait few seconds...")

		let session = LiferayContext.instance.createSession(anonymousApiUserName!,
				password: anonymousApiPassword!)
		session.callback = self

		let service = LRUserService_v62(session: session)

		var outError: NSError?

		// user name
		switch authType {
		case AuthType.Email:
			creatingUsername = signUpView().getEmailAddress()
		case AuthType.ScreenName:
			creatingUsername = signUpView().getScreenName()
		case AuthType.UserId:
			println("ERROR: sign Up with User id is not supported")
		default: ()
		}

		// password
		creatingPassword = signUpView().getPassword();
		let autoPassword = (creatingPassword == "")

		// screen name
		let screenName = signUpView().getScreenName();
		let autoScreenName = (screenName == "")

		// names
		let firstName = signUpView().getFirstName();
		let middleName = signUpView().getMiddleName();
		let lastName = signUpView().getLastName();

		let emptyDict = []

		service.addUserWithCompanyId((LiferayContext.instance.companyId as NSNumber).longLongValue,
			autoPassword: autoPassword, password1: password, password2: password,
			autoScreenName: autoScreenName, screenName: screenName,
			emailAddress: signUpView().getEmailAddress(),
			facebookId: 0, openId: "",
			locale: NSLocale.currentLocaleString(),
			firstName: firstName, middleName: middleName, lastName: lastName,
			prefixId: 0, suffixId: 0,
			male: true,
			birthdayMonth: 1, birthdayDay: 1, birthdayYear: 1970,
			jobTitle: signUpView().getJobTitle(),
			groupIds: [LiferayContext.instance.groupId],
			organizationIds: emptyDict,
			roleIds: emptyDict,
			userGroupIds: emptyDict,
			sendEmail: true,
			serviceContext: nil, error: &outError)

		if let error = outError {
			onFailure(error)
		}
	}

	private var creatingUsername: String?
	private var creatingPassword: String?

}
