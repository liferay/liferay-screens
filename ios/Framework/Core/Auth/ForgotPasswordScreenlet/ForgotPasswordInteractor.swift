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

	override func createOperation() -> LiferayForgotPasswordBaseOperation? {
		let screenlet = self.screenlet as! ForgotPasswordScreenlet

		if screenlet.anonymousApiUserName == nil || screenlet.anonymousApiPassword == nil {
			print("[ERROR] Anonymous credentials are required for this interaction\n")
			return nil
		}

		let operation: LiferayForgotPasswordBaseOperation?

		switch BasicAuthMethod.create(screenlet.basicAuthMethod) {
			case .ScreenName:
				operation = LiferayForgotPasswordScreenNameOperation(
					viewModel: screenlet.viewModel,
					anonymousUsername: screenlet.anonymousApiUserName!,
					anonymousPassword: screenlet.anonymousApiPassword!)
			case .UserId:
				operation = LiferayForgotPasswordUserIdOperation(
					viewModel: screenlet.viewModel,
					anonymousUsername: screenlet.anonymousApiUserName!,
					anonymousPassword: screenlet.anonymousApiPassword!)
			case .Email:
				operation = LiferayForgotPasswordEmailOperation(
					viewModel: screenlet.viewModel,
					anonymousUsername: screenlet.anonymousApiUserName!,
					anonymousPassword: screenlet.anonymousApiPassword!)
		}

		operation!.companyId = screenlet.companyId

		return operation!
	}

	override func completedOperation(op: ServerOperation) {
		self.resultPasswordSent = (op as! LiferayForgotPasswordBaseOperation).resultPasswordSent
	}

	override func interactionResult() -> AnyObject? {
		return resultPasswordSent
	}

}
