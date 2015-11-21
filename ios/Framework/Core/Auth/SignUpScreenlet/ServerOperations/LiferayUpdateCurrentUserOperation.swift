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


public class LiferayUpdateCurrentUserOperation: ServerOperation {

	public var resultUserAttributes: [String:AnyObject]?

	private let viewModel: SignUpViewModel


	public init(viewModel: SignUpViewModel) {
		self.viewModel = viewModel

		super.init()
	}


	//MARK: ServerOperation

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if viewModel.emailAddress == nil {
				return ValidationError("signup-screenlet", "validation-email")
			}

			if viewModel.password == SessionContext.currentBasicPassword {
				return ValidationError("signup-screenlet", "validation-change-password")
			}
		}

		return error
	}

	override public func doRun(session session: LRSession) {
		func attributeAsString(key: String) -> String {
			return SessionContext.userAttribute(key) as! String
		}

		func attributeAsId(key: String) -> Int64 {
			return Int64(SessionContext.userAttribute(key) as! Int)
		}


		let service = LRUserService_v62(session: session)

		do {
			//FIXME
			// Values marked with (!!) will be overwritten in the server
			// The JSON WS API isn't able to handle this scenario correctly
			let result = try service.updateUserWithUserId(attributeAsId("userId"),
				oldPassword: SessionContext.currentBasicPassword,
				newPassword1: viewModel.password ?? "",
				newPassword2: viewModel.password ?? "",
				passwordReset: false,
				reminderQueryQuestion: attributeAsString("reminderQueryQuestion"),
				reminderQueryAnswer: "", // (!!)
				screenName: attributeAsString("screenName"),
				emailAddress: viewModel.emailAddress,
				facebookId: attributeAsId("facebookId"),
				openId: attributeAsString("openId"),
				languageId: attributeAsString("languageId"),
				timeZoneId: attributeAsString("timeZoneId"),
				greeting: attributeAsString("greeting"),
				comments: attributeAsString("comments"),
				firstName: viewModel.firstName ?? "",
				middleName: viewModel.middleName ?? "",
				lastName: viewModel.lastName ?? "",
				prefixId: 0, 		// (!!)
				suffixId: 0, 		// (!!)
				male: true, 		// (!!)
				birthdayMonth: 1, 	// (!!)
				birthdayDay: 1, 	// (!!)
				birthdayYear: 1970, // (!!)
				smsSn: "", 			// (!!)
				aimSn: "", 			// (!!)
				facebookSn: "", 	// (!!)
				icqSn: "", 			// (!!)
				jabberSn: "", 		// (!!)
				msnSn: "", 			// (!!)
				mySpaceSn: "", 		// (!!)
				skypeSn: "", 		// (!!)
				twitterSn: "", 		// (!!)
				ymSn: "", 			// (!!)
				jobTitle: viewModel.jobTitle ?? "",
				groupIds: [NSNumber(longLong: LiferayServerContext.groupId)],
				organizationIds: [AnyObject](),
				roleIds: [AnyObject](),
				userGroupRoles: [AnyObject](),
				userGroupIds: [AnyObject](),
				serviceContext: nil)

			if result["userId"] == nil {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
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

}
