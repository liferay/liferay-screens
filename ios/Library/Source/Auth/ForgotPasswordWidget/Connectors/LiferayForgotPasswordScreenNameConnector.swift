//
//  LiferayLoginEmailConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit


class LiferayForgotPasswordScreenNameConnector: LiferayForgotPasswordBaseConnector {

	override func sendForgotPasswordRequest(
			#service: LRMobilewidgetsuserService_v62,
			error: NSErrorPointer)
			-> Bool? {

		return service.sendPasswordByScreenNameWithCompanyId(
				(LiferayServerContext.instance.companyId as NSNumber).longLongValue,
				screenName: (widget.widgetView as ForgotPasswordView).userName!,
				error: error)
	}

}
