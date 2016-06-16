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


public class ForgotPasswordScreenNameLiferay62Connector: ForgotPasswordBaseLiferayConnector {

	override public func sendForgotPasswordRequest(session: LRSession) throws -> Bool {

		let service = LRScreensuserService_v62(session: session)

		try service.sendPasswordByScreenNameWithCompanyId(companyId,
			screenName: viewModel.userName!)

		return true
	}

}


public class ForgotPasswordScreenNameLiferay70Connector: ForgotPasswordBaseLiferayConnector {

	override public func sendForgotPasswordRequest(session: LRSession) throws -> Bool {

		let service = LRUserService_v7(session: session)

		try service.sendPasswordByScreenNameWithCompanyId(companyId,
			screenName: viewModel.userName!)

		return true
	}
	
}
