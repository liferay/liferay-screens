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


public class GetUserByEmailLiferayConnector: GetUserBaseLiferayConnector {

	public var companyId: Int64
	public var emailAddress: String

	public init(companyId: Int64, emailAddress: String) {
		self.companyId = companyId
		self.emailAddress = emailAddress

		super.init()
	}

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if emailAddress == "" {
				return ValidationError("login-screenlet", "validation-email")
			}
		}

		return error
	}

}
