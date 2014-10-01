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

public class LiferaySignUpOperation: ServerOperation, NSCopying {

	internal(set) var createdUserAttributes: [String:AnyObject]?

	internal override var hudLoadingMessage: HUDMessage? {
		return ("Sending sign up...", details: "Wait few seconds...")
	}
	internal override var hudFailureMessage: HUDMessage? {
		return ("Error signing up!", details: nil)
	}

	private var signUpView: SignUpView {
		return widget.widgetView as SignUpView
	}

	//MARK: ServerOperation

	override func validateView() -> Bool {
		if signUpView.emailAddress == nil {
			showValidationHUD(message: "Please, enter your email address at least")

			return false
		}

		return true
	}

	override func doRun(#session: LRSession) {
		let service = LRUserService_v62(session: session)

		var outError: NSError?

		let emptyDict = []

		let password = emptyIfNull(signUpView.password)

		let result = service.addUserWithCompanyId(
				LiferayServerContext.companyId,
				autoPassword: (password == ""),
				password1: password,
				password2: password,
				autoScreenName: true,
				screenName: "",
				emailAddress: signUpView.emailAddress,
				facebookId: 0,
				openId: "",
				locale: NSLocale.currentLocaleString(),
				firstName: emptyIfNull(signUpView.firstName),
				middleName: emptyIfNull(signUpView.middleName),
				lastName: emptyIfNull(signUpView.lastName),
				prefixId: 0,
				suffixId: 0,
				male: true,
				birthdayMonth: 1,
				birthdayDay: 1,
				birthdayYear: 1970,
				jobTitle: emptyIfNull(signUpView.jobTitle),
				groupIds: [NSNumber(longLong: LiferayServerContext.groupId)],
				organizationIds: emptyDict,
				roleIds: emptyDict,
				userGroupIds: emptyDict,
				sendEmail: true,
				serviceContext: nil,
				error: &outError)

		if outError != nil {
			lastError = outError!
			createdUserAttributes = nil
		}
		else if result?["userId"] == nil {
			lastError = createError(cause: .InvalidServerResponse, userInfo: nil)
			createdUserAttributes = nil
		}
		else {
			lastError = nil
			createdUserAttributes = result as? [String:AnyObject]
		}
	}


	//MARK: NSCopying

	public func copyWithZone(zone: NSZone) -> AnyObject {
		let result = LiferaySignUpOperation(widget: self.widget)

		result.onComplete = self.onComplete

		return result
	}

}
