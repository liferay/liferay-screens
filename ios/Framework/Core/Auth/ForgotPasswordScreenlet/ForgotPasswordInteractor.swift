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


class ForgotPasswordInteractor: ServerOperationInteractor {

	var resultPasswordSent: Bool?

	override func createOperation() -> LiferayForgotPasswordBaseOperation {
		let screenlet = self.screenlet as! ForgotPasswordScreenlet

		var operation: LiferayForgotPasswordBaseOperation?

		switch AuthMethod.create(screenlet.authMethod) {
			case .ScreenName:
				operation = LiferayForgotPasswordScreenNameOperation(screenlet: screenlet)
			case .UserId:
				operation = LiferayForgotPasswordUserIdOperation(screenlet: screenlet)
			case .Email:
				operation = LiferayForgotPasswordEmailOperation(screenlet: screenlet)
		}

		operation!.companyId = screenlet.companyId

		return operation!
	}

	override func completedOperation(op: ServerOperation) {
		self.resultPasswordSent = (op as! LiferayForgotPasswordBaseOperation).resultPasswordSent
	}

}
