//
//  LiferayLoginEmailConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

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
