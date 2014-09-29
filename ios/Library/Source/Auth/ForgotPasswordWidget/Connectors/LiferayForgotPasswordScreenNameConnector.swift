//
//  LiferayLoginEmailConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit


class LiferayForgotPasswordScreenNameConnector: LiferayForgotPasswordBaseConnector {

	//MARK: LiferayForgotPasswordBaseConnector

	override func sendForgotPasswordRequest(
			#service: LRMobilewidgetsuserService_v62,
			error: NSErrorPointer)
			-> Bool? {

		return service.sendPasswordByScreenNameWithCompanyId(
				(LiferayServerContext.instance.companyId as NSNumber).longLongValue,
				screenName: (widget.widgetView as ForgotPasswordView).userName!,
				error: error)
	}


	//MARK: NSCopying

	override internal func copyWithZone(zone: NSZone) -> AnyObject {
		let result = LiferayForgotPasswordScreenNameConnector(widget: self.widget)

		result.onComplete = self.onComplete

		return result
	}

}
