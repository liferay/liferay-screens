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


class UserPortraitAttributesLoadInteractor: UserPortraitBaseInteractor {

	let portraitId: Int64
	let uuid: String
	let male: Bool

	init(screenlet: BaseScreenlet, portraitId: Int64, uuid: String, male: Bool) {
		self.portraitId = portraitId
		self.uuid = uuid
		self.male = male

		super.init(screenlet: screenlet)
	}

	override internal func start() -> Bool {
		resultURL = (portraitId == 0)
				? nil : URLForAttributes(portraitId: portraitId, uuid: uuid, male: male)

		callOnSuccess()

		return (resultURL != nil)
	}

}
