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


	//MARK: BaseConnector

	override func preRun() -> Bool {
		showHUD(message: "Sending sign up...", details: "Wait few seconds...")

		return true

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

		let view = widget.widgetView as SignUpView

		// user name
		let autoPassword = (view.password == "")

		// screen name
		let screenName = "";
		let autoScreenName = true

		let emptyDict = []

		let result = service.addUserWithCompanyId(
				(LiferayServerContext.instance.companyId as NSNumber).longLongValue,
				autoPassword: autoPassword,
				password1: view.password,
				password2: view.password,
				autoScreenName: autoScreenName,
				screenName: screenName,
				emailAddress: view.emailAddress,
				facebookId: 0,
				openId: "",
				locale: NSLocale.currentLocaleString(),
				firstName: view.firstName,
				middleName: emptyIfNull(view.middleName),
				lastName: view.lastName,
				prefixId: 0,
				suffixId: 0,
				male: true,
				birthdayMonth: 1,
				birthdayDay: 1,
				birthdayYear: 1970,
				jobTitle: emptyIfNull(view.jobTitle),
				groupIds: [LiferayServerContext.instance.groupId],
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
