//
//  LiferayLoginEmailConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit


class LiferayLoginUserIdConnector: LiferayLoginBaseConnector {

	//MARK: LiferayLoginBaseConnector

	override func sendGetUserRequest(
			#service: LRUserService_v62,
			error: NSErrorPointer)
			-> NSDictionary? {

		let view = widget.widgetView as LoginView

		let userId = (view.userName!.toInt()! as NSNumber).longLongValue

		return service.getUserByIdWithUserId(userId, error: error)
	}


	//MARK: NSCopying

	override internal func copyWithZone(zone: NSZone) -> AnyObject {
		let result = LiferayLoginUserIdConnector(widget: self.widget)

		result.onComplete = self.onComplete

		return result
	}

}
