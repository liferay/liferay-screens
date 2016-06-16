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


public class LoginBasicInteractor: ServerConnectorInteractor {

	public let companyId: Int64
	public let screenName: String?
	public let emailAddress: String?
	public let userId: Int64?
	public let password: String

	public let authMethod: BasicAuthMethod

	public var resultUserAttributes: [String:AnyObject]?


	public init(loginScreenlet: LoginScreenlet) {
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

	public init(companyId: Int64, screenName: String, password: String) {
		self.authMethod = BasicAuthMethod.ScreenName
		self.companyId = (companyId != 0) ? companyId : LiferayServerContext.companyId
		self.screenName = screenName
		self.password = password
		self.emailAddress = nil
		self.userId = nil

		super.init(screenlet: nil)
	}

	public init(companyId: Int64, emailAddress: String, password: String) {
		self.authMethod = BasicAuthMethod.Email
		self.companyId = (companyId != 0) ? companyId : LiferayServerContext.companyId
		self.emailAddress = emailAddress
		self.password = password
		self.screenName = nil
		self.userId = nil

		super.init(screenlet: nil)
	}

	public init(userId: Int64, password: String) {
		self.authMethod = BasicAuthMethod.UserId
		self.companyId = 0
		self.userId = userId
		self.password = password
		self.emailAddress = nil
		self.screenName = nil

		super.init(screenlet: nil)
	}

	override public func createConnector() -> GetUserBaseLiferayConnector? {
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

	override public func completedConnector(op: ServerConnector) {
		self.resultUserAttributes = (op as! GetUserBaseLiferayConnector).resultUserAttributes
	}

}
