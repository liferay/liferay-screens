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


class UserPortraitLoadLoggedUserInteractor: UserPortraitBaseInteractor {

	override internal func start() -> Bool {
		resultURL = nil

		if let portraitId = SessionContext.userAttribute("portraitId") as? NSNumber {
			if let uuid = SessionContext.userAttribute("uuid") as? String {
				resultURL = URLForAttributes(
						portraitId: portraitId.longLongValue,
						uuid: uuid,
						male: true)
				resultUserId = SessionContext.currentUserId
			}
		}

		if resultURL == nil {
			callOnFailure(NSError.errorWithCause(.AbortedDueToPreconditions))
		}
		else {
			callOnSuccess()
		}

		return (resultURL != nil)
	}

}
