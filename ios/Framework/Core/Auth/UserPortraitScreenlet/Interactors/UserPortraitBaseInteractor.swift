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


class UserPortraitBaseInteractor: Interactor {

	var resultURL: NSURL?
	var resultUserId: Int64?

	func URLForAttributes(#portraitId: Int64, uuid: String, male: Bool) -> NSURL? {

		let result = LRPortraitUtil.getPortraitURL(SessionContext.createSessionFromCurrentSession(), male: male, portraitId: portraitId, uuid: uuid)

		return NSURL(string: result)
	}

}
