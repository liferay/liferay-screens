//
//  LiferayLoginEmailConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit


class LiferayLoginEmailConnector: LiferayLoginBaseConnector {

	//MARK: LiferayLoginBaseConnector

	override func sendGetUserRequest(
			#service: LRUserService_v62,
			error: NSErrorPointer)
			-> NSDictionary? {

		return service.getUserByEmailAddressWithCompanyId(
				LiferayServerContext.companyId,
				emailAddress: (widget.widgetView as LoginView).userName!,
				error: error)
	}


	//MARK: NSCopying

	override internal func copyWithZone(zone: NSZone) -> AnyObject {
		let result = LiferayLoginEmailConnector(widget: self.widget)

		result.onComplete = self.onComplete

		return result
	}

}
