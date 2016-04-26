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


class LoginBasicInteractor: ServerConnectorInteractor {

	let companyId: Int64
	let screenName: String?
	let emailAddress: String?
	let userId: Int64?
	let password: String

	let authMethod: BasicAuthMethod

	var resultUserAttributes: [String:AnyObject]?


	init(loginScreenlet: LoginScreenlet) {
		companyId = (loginScreenlet.companyId ?? 0) != 0
			? loginScreenlet.companyId : LiferayServerContext.companyId

		authMethod = BasicAuthMethod.create(loginScreenlet.basicAuthMethod)

		switch authMethod {
		case .ScreenName:
			screenName = loginScreenlet.viewModel.userName ?? ""
			emailAddress = nil
			userId = nil
		case .UserId:
			userId = loginScreenlet.viewModel.userName?.asNumber?.longLongValue ?? 0
			emailAddress = nil
			screenName = nil
		default:
			emailAddress = loginScreenlet.viewModel.userName ?? ""
			userId = nil
			screenName = nil
		}

		password = loginScreenlet.viewModel.password ?? ""

		super.init(screenlet: loginScreenlet)
	}

	override func createConnector() -> GetUserBaseLiferayConnector? {
		let connector: GetUserBaseLiferayConnector?

		if let screenName = self.screenName {
			connector = LiferayServerContext.connectorFactory.createLoginByScreenNameConnector(
				companyId: companyId,
				screenName: screenName,
				password: password)
		}
		else if let userId = self.userId {
			connector = LiferayServerContext.connectorFactory.createLoginByUserIdConnector(
				userId: userId,
				password: password)
		}
		else if let emailAddress = self.emailAddress {
			connector = LiferayServerContext.connectorFactory.createLoginByEmailConnector(
				companyId: companyId,
				emailAddress: emailAddress,
				password: password)
		}
		else {
			connector = nil
		}

		return connector
	}

	override func completedConnector(op: ServerConnector) {
		self.resultUserAttributes = (op as! GetUserBaseLiferayConnector).resultUserAttributes
	}

}
