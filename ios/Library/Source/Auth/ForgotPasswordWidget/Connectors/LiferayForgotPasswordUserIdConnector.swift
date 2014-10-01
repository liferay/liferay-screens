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


class LiferayForgotPasswordUserIdConnector: LiferayForgotPasswordBaseConnector {

	//MARK: LiferayForgotPasswordBaseConnector

	override func sendForgotPasswordRequest(
			#service: LRMobilewidgetsuserService_v62,
			error: NSErrorPointer)
			-> Bool? {

		let view = widget.widgetView as ForgotPasswordView

		let userId = (view.userName!.toInt()! as NSNumber).longLongValue

		return service.sendPasswordByUserIdWithUserId(userId, error: error)
	}


	//MARK: NSCopying

	override internal func copyWithZone(zone: NSZone) -> AnyObject {
		let result = LiferayForgotPasswordUserIdConnector(widget: self.widget)

		result.onComplete = self.onComplete

		return result
	}

}
