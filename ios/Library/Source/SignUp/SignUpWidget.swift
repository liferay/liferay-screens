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

@IBDesignable class SignUpWidget: BaseWidget {

	@IBInspectable var creatorUsername: String?
	@IBInspectable var creatorPassword: String?
	@IBInspectable var autologin: Bool = true

	@IBOutlet var delegate: SignUpWidgetDelegate?

	private var creatingUsername: String?
	private var creatingPassword: String?

	public var authType: AuthType = AuthType.Email


	// BaseWidget METHODS


	override func onCustomAction(actionName: String?, sender: UIControl) {
		sendSignUpWithEmailAddress(signUpView().emailAddressField!.text, password:signUpView().passwordField!.text, firstName:signUpView().firstNameField!.text, lastName:signUpView().lastNameField!.text)
	}

	override func onServerError(error: NSError) {
		delegate?.onSignUpError?(error)

		hideHUDWithMessage("Error signing up!", details: nil)

		signUpView().signUpButton!.enabled = true
	}

	override func onServerResult(result: [String:AnyObject]) {
		delegate?.onSignUpResponse?(result)

		if autologin {
			LiferayContext.instance.clearSession()
			LRSession.removeStoredCredential()

			LiferayContext.instance.createSession(creatingUsername!, password: creatingPassword!)
		}

		hideHUDWithMessage("Sign up completed", details: nil)

		signUpView().signUpButton!.enabled = true
	}


	private func signUpView() -> SignUpView {
		return widgetView as SignUpView
	}

	private func sendSignUpWithEmailAddress(emailAddress:String, password:String, firstName:String, lastName:String) {

		if !creatorUsername || !creatorPassword {
			println("ERROR: Creator username and password must be set for SignUpWidget in Interface Builder")
			return
		}

		showHUDWithMessage("Sending sign up...", details:"Wait few seconds...")

		let session = LiferayContext.instance.createSession(creatorUsername!, password: creatorPassword!)
		session.callback = self

		let service = LRUserService_v62(session: session)

		var outError: NSError?

		let companyId: Int64 = (LiferayContext.instance.companyId as NSNumber).longLongValue

		// user name
		switch authType {
		case AuthType.Email:
			creatingUsername = signUpView().emailAddressField!.text
		case AuthType.Screenname:
			creatingUsername = signUpView().screenNameField!.text
		case AuthType.UserId:
			println("ERROR: sign Up with User id is not supported")
		default:
			break;
		}

		// password
		creatingPassword = _optionalFieldValue(signUpView().passwordField);
		let autoPassword = (creatingPassword == "")

		// screen name
		let screenName = _optionalFieldValue(signUpView().screenNameField);
		let autoScreenName = (screenName == "")

		// names
		let firstName = _optionalFieldValue(signUpView().firstNameField);
		let middleName = _optionalFieldValue(signUpView().middleNameField);
		let lastName = _optionalFieldValue(signUpView().lastNameField);

		let emptyDict = []

		service.addUserWithCompanyId(companyId,
			autoPassword: autoPassword, password1: password, password2: password,
			autoScreenName: autoScreenName, screenName: screenName,
			emailAddress: signUpView().emailAddressField!.text,
			facebookId: 0, openId: "",
			locale: NSLocale.currentLocaleString(),
			firstName: firstName, middleName: middleName, lastName: lastName,
			prefixId: 0, suffixId: 0,
			male: true,
			birthdayMonth: 1, birthdayDay: 1, birthdayYear: 1970,
			jobTitle: _optionalFieldValue(signUpView().jobTitleField),
			groupIds: [LiferayContext.instance.groupId],
			organizationIds: emptyDict,
			roleIds: emptyDict,
			userGroupIds: emptyDict,
			sendEmail: true,
			serviceContext: nil, error: &outError)

		if let error = outError {
			onFailure(error)
		}
		else {
			signUpView().signUpButton!.enabled = false
		}
	}

	private func _optionalFieldValue(textField: UITextField?) -> String {
		if textField && textField!.text != "" {
			return textField!.text
		}

		return ""
	}

}