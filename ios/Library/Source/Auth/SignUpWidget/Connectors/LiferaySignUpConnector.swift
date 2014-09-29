//
//  LiferayLoginBaseConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit

class LiferaySignUpConnector: BaseConnector {

	var createdUserAttributes: [String:AnyObject]?

	var anonymousApiUserName: String?
	var anonymousApiPassword: String?

	var emailAddress: String?
	var password: String?
	var firstName: String?
	var lastName: String?

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

	override func doRun() {
		let session = LRSession(
				server: LiferayServerContext.instance.server,
				username: anonymousApiUserName!,
				password: anonymousApiPassword!)

		let service = LRUserService_v62(session: session)

		var outError: NSError?

		// user name
		let autoPassword = (password == "")

		// screen name
		let screenName = "";
		let autoScreenName = true

		// names
		let middleName = ""

		let emptyDict = []

		let result = service.addUserWithCompanyId(
				(LiferayServerContext.instance.companyId as NSNumber).longLongValue,
				autoPassword: autoPassword,
				password1: password,
				password2: password,
				autoScreenName: autoScreenName,
				screenName: screenName,
				emailAddress: emailAddress,
				facebookId: 0,
				openId: "",
				locale: NSLocale.currentLocaleString(),
				firstName: firstName,
				middleName: middleName,
				lastName: lastName,
				prefixId: 0,
				suffixId: 0,
				male: true,
				birthdayMonth: 1,
				birthdayDay: 1,
				birthdayYear: 1970,
				jobTitle: "",
				groupIds: [LiferayServerContext.instance.groupId],
				organizationIds: emptyDict,
				roleIds: emptyDict,
				userGroupIds: emptyDict,
				sendEmail: true,
				serviceContext: nil,
				error: &outError)

		if outError != nil || result?["userId"] == nil {
			lastError = outError!
			createdUserAttributes = nil
		}
		else {
			lastError = nil
			createdUserAttributes = result as? [String:AnyObject]
		}
	}

}
