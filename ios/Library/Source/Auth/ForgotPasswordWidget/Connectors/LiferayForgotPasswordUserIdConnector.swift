//
//  LiferayLoginEmailConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit


class LiferayForgotPasswordUserIdConnector: LiferayForgotPasswordBaseConnector {

	override func sendForgotPasswordRequest(
			#service: LRMobilewidgetsuserService_v62,
			error: NSErrorPointer)
			-> Bool? {

		let userId = (userName!.toInt()! as NSNumber).longLongValue

		return service.sendPasswordByUserIdWithUserId(userId, error: error)
	}

}
