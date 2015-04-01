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
import LRMobileSDK


public class LiferaySignUpOperation: ServerOperation {

	public var companyId: Int64 = 0

	public var resultUserAttributes: [String:AnyObject]?

	internal override var hudLoadingMessage: HUDMessage? {
		return (LocalizedString("signup-screenlet", "loading-message", self),
				details: LocalizedString("signup-screenlet", "loading-details", self))
	}
	internal override var hudFailureMessage: HUDMessage? {
		return (LocalizedString("signup-screenlet", "loading-error", self), details: nil)
	}

	private var viewModel: SignUpViewModel {
		return screenlet.screenletView as SignUpViewModel
	}

	//MARK: ServerOperation

	override func validateData() -> Bool {
		if viewModel.emailAddress == nil {
			showValidationHUD(message: LocalizedString("signup-screenlet", "validation", self))

			return false
		}

		return true
	}

	override func doRun(#session: LRSession) {
		let service = LRUserService_v62(session: session)

		var outError: NSError?

		let emptyDict = []

		let password = viewModel.password ?? ""

		let companyId = (self.companyId != 0)
				? self.companyId : LiferayServerContext.companyId

		let result = service.addUserWithCompanyId(companyId,
				autoPassword: (password == ""),
				password1: password,
				password2: password,
				autoScreenName: true,
				screenName: viewModel.screenName ?? "",
				emailAddress: viewModel.emailAddress,
				facebookId: 0,
				openId: "",
				locale: NSLocale.currentLocaleString,
				firstName: viewModel.firstName ?? "",
				middleName: viewModel.middleName ?? "",
				lastName: viewModel.lastName ?? "",
				prefixId: 0,
				suffixId: 0,
				male: true,
				birthdayMonth: 1,
				birthdayDay: 1,
				birthdayYear: 1970,
				jobTitle: viewModel.jobTitle ?? "",
				groupIds: [NSNumber(longLong: LiferayServerContext.groupId)],
				organizationIds: emptyDict,
				roleIds: emptyDict,
				userGroupIds: emptyDict,
				sendEmail: true,
				serviceContext: nil,
				error: &outError)

		if outError != nil {
			lastError = outError!
			resultUserAttributes = nil
		}
		else if result?["userId"] == nil {
			lastError = createError(cause: .InvalidServerResponse, userInfo: nil)
			resultUserAttributes = nil
		}
		else {
			lastError = nil
			resultUserAttributes = result as? [String:AnyObject]
		}
	}

}
