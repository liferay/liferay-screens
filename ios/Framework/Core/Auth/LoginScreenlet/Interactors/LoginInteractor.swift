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


class LoginInteractor: ServerConnectorInteractor {

	var resultUserAttributes: [String:AnyObject]?

	override func createConnector() -> GetUserBaseLiferayConnector {
		let screenlet = self.screenlet as! LoginScreenlet

		let companyId = (screenlet.companyId != 0)
				? screenlet.companyId : LiferayServerContext.companyId

		var connector: GetUserBaseLiferayConnector?

		switch BasicAuthMethod.create(screenlet.basicAuthMethod) {
			case .ScreenName:
				connector = LiferayServerContext.connectorFactory.createLoginByScreenNameConnector(
						companyId: companyId,
						screenName: screenlet.viewModel.userName ?? "")
			case .UserId:
				connector = LiferayServerContext.connectorFactory.createLoginByUserIdConnector(
						userId: screenlet.viewModel.userName?.asNumber?.longLongValue ?? 0)
			default:
				connector = LiferayServerContext.connectorFactory.createLoginByEmailConnector(
					companyId: companyId,
					emailAddress: screenlet.viewModel.userName ?? "")
		}

		connector?.userName = screenlet.viewModel.userName
		connector?.password = screenlet.viewModel.password

		return connector!
	}

	override func completedConnector(op: ServerConnector) {
		self.resultUserAttributes = (op as! GetUserBaseLiferayConnector).resultUserAttributes
	}

}
