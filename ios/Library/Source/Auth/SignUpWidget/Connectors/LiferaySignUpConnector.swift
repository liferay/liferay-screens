//
//  LiferayLoginBaseConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit

class LiferaySignUpConnector: BaseConnector, NSCopying {

	var createdUserAttributes: [String:AnyObject]?

	private var signUpView: SignUpView {
		return widget.widgetView as SignUpView
	}

	//MARK: BaseConnector

	override func validateView() -> Bool {
		var result = super.validateView()

		if result {
			if signUpView.emailAddress == nil {
				showValidationHUD(message: "Please, enter your email address at least")
				result = false
			}
		}

		return result
	}

	override func preRun() -> Bool {
		var result = super.preRun()

		if result {
			showHUD(message: "Sending sign up...", details: "Wait few seconds...")
		}

		return result
	}

	override func postRun() {
		if lastError != nil {
			hideHUD(error: lastError!, message:"Error signing up!")
		}
		else {
			hideHUD()
		}
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

	internal func copyWithZone(zone: NSZone) -> AnyObject {
		let result = LiferaySignUpConnector(widget: self.widget)

		result.onComplete = self.onComplete

		return result
	}

}
