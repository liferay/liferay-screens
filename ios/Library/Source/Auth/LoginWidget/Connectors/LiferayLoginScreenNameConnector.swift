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


class LiferayLoginScreenNameConnector: LiferayLoginBaseConnector {

	//MARK: LiferayLoginBaseConnector

	override func sendGetUserRequest(
			#service: LRUserService_v62,
			error: NSErrorPointer)
			-> NSDictionary? {

		return service.getUserByScreenNameWithCompanyId(
				LiferayServerContext.companyId,
				screenName: (widget.widgetView as LoginView).userName!,
				error: error)
	}


	//MARK: NSCopying

	override internal func copyWithZone(zone: NSZone) -> AnyObject {
		let result = LiferayLoginScreenNameConnector(widget: self.widget)

		result.onComplete = self.onComplete

		return result
	}

}
