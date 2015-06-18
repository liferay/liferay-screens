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
import DeviceGuru


class AudienceTargetingLoadPlaceholderInteractor: ServerOperationInteractor {

	var resultContent: String?
	var resultClassName: String?

	override func createOperation() -> AudienceTargetingLoadPlaceholderOperation {
		let screenlet = self.screenlet as! AudienceTargetingDisplayScreenlet
		let operation = AudienceTargetingLoadPlaceholderOperation(screenlet: self.screenlet)

		operation.groupId = (screenlet.groupId != 0)
				? screenlet.groupId : LiferayServerContext.groupId

		operation.appId = screenlet.appId
		operation.placeholderId = screenlet.placeholderId

		operation.userContext = computeUserContext()

		return operation
	}

	override func completedOperation(op: ServerOperation) {
		let loadOp = (op as! AudienceTargetingLoadPlaceholderOperation)

		if let result = loadOp.results?.first {
			self.resultContent = result.classPK?.description ?? result.customContent
			self.resultClassName = result.className
		}
	}

	func computeUserContext() -> [String:String] {
		var result = [String:String]()

		if SessionContext.hasSession {
			result["userId"] = (SessionContext.userAttribute("userId") as! Int).description
		}

		// device
		result["os-name"] = "ios"
		result["os-version"] = NSProcessInfo.processInfo().operatingSystemVersionString
		result["device"] = hardwareDescription()

		result["locale"] = NSLocale.currentLocaleString

		// more...

		return result
	}

}
