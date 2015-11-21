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


public class LiferaySignUpOperation: ServerOperation {

	public var companyId: Int64 = 0

	public var resultUserAttributes: [String:AnyObject]?

	private let viewModel: SignUpViewModel
	private let anonymousUsername: String
	private let anonymousPassword: String


	public init(viewModel: SignUpViewModel, anonymousUsername: String, anonymousPassword: String) {
		self.viewModel = viewModel
		self.anonymousUsername = anonymousUsername
		self.anonymousPassword = anonymousPassword

		super.init()
	}


	//MARK: ServerOperation

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if viewModel.emailAddress == nil {
				return ValidationError("signup-screenlet", "validation-email")
			}
		}

		return error
	}

	override public func doRun(session session: LRSession) {
		let result: [NSObject:AnyObject]?
		let service = LRUserService_v62(session: session)
		let emptyDict = [AnyObject]()
		let password = viewModel.password ?? ""
		let companyId = (self.companyId != 0)
				? self.companyId
				: LiferayServerContext.companyId

		do {
			result = try service.addUserWithCompanyId(companyId,
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
				groupIds: [
					NSNumber(longLong: LiferayServerContext.groupId)
				],
				organizationIds: emptyDict,
				roleIds: emptyDict,
				userGroupIds: emptyDict,
				addresses: emptyDict,
				emailAddresses: emptyDict,
				phones: emptyDict,
				websites: emptyDict,
				announcementsDelivers: emptyDict,
				sendEmail: true,
				serviceContext: nil)

			if result?["userId"] == nil {
				lastError = NSError.errorWithCause(.InvalidServerResponse, userInfo: nil)
				resultUserAttributes = nil
			}
			else {
				lastError = nil
				resultUserAttributes = result as? [String:AnyObject]
			}
		}
		catch let error as NSError {
			lastError = error
			resultUserAttributes = nil
		}
	}

	override public func createSession() -> LRSession? {
		return SessionContext.createAnonymousBasicSession(anonymousUsername, anonymousPassword)
	}

}
