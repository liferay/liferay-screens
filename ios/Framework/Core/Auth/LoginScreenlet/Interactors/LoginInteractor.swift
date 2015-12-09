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


class LoginInteractor: ServerOperationInteractor {

	var resultUserAttributes: [String:AnyObject]?

	override func createOperation() -> GetUserBaseOperation {
		let screenlet = self.screenlet as! LoginScreenlet

		let companyId = (screenlet.companyId != 0)
				? screenlet.companyId : LiferayServerContext.companyId

		var operation: GetUserBaseOperation?

		switch BasicAuthMethod.create(screenlet.basicAuthMethod) {
			case .ScreenName:
				operation = LiferayLoginByScreenNameOperation(
						companyId: companyId,
						screenName: screenlet.viewModel.userName ?? "")
			case .UserId:
				operation = LiferayLoginByUserIdOperation(
						userId: Int64(Int(screenlet.viewModel.userName!) ?? 0))
			default:
				operation = LiferayLoginByEmailOperation(
						companyId: companyId,
						emailAddress: screenlet.viewModel.userName ?? "")
		}

		operation?.userName = screenlet.viewModel.userName
		operation?.password = screenlet.viewModel.password

		return operation!
	}

	override func completedOperation(op: ServerOperation) {
		self.resultUserAttributes = (op as! GetUserBaseOperation).resultUserAttributes
	}

}
