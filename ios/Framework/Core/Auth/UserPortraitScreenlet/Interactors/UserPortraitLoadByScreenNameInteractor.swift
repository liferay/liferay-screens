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


class UserPortraitLoadByScreenNameInteractor: UserPortraitBaseLoadUserInteractor {

	let companyId: Int64
	let screenName: String

	init(screenlet: BaseScreenlet, companyId: Int64, screenName: String) {
		self.companyId = companyId
		self.screenName = screenName

		super.init(screenlet: screenlet)
	}

	override func createLoadUserOperation() -> GetUserBaseOperation? {
		return GetUserByScreenNameOperation(
				screenlet: screenlet,
				companyId: companyId,
				screenName: screenName)
	}

	override func isUserLogged() -> Bool {
		if let companyIdValue = SessionContext.userAttribute("companyId") as? Int {
			if let screenNameValue = SessionContext.userAttribute("screenName") as? String {
				return (companyId == Int64(companyIdValue) && screenName == screenNameValue)
			}
		}

		return false
	}

}
