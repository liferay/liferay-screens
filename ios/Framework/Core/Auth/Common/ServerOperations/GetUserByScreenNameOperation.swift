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


public class GetUserByScreenNameOperation: GetUserBaseOperation {

	public var companyId: Int64
	public var screenName: String

	public init(companyId: Int64, screenName: String) {
		self.companyId = companyId
		self.screenName = screenName

		super.init()
	}

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if screenName == "" {
				return ValidationError("login-screenlet", "validation-screenname")
			}
		}

		return error
	}

	//MARK: LiferayLoginBaseOperation

	override internal func sendGetUserRequest(
			service service: LRUserService_v62)
			throws -> NSDictionary? {

		let companyId = (self.companyId != 0) ? self.companyId : LiferayServerContext.companyId

		return try service.getUserByScreenNameWithCompanyId(companyId,
				screenName: screenName)
	}

}
