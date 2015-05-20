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


class DDLFormSubmitFormInteractor: ServerOperationInteractor {

	var resultRecordId: Int64?
	var resultAttributes: NSDictionary?

	override func createOperation() -> LiferayDDLFormSubmitOperation {
		let screenlet = self.screenlet as! DDLFormScreenlet

		let operation = LiferayDDLFormSubmitOperation(screenlet: screenlet)

		operation.groupId = (screenlet.groupId != 0)
				? screenlet.groupId : LiferayServerContext.groupId

		operation.userId = (screenlet.userId != 0)
				? screenlet.userId
				: SessionContext.currentUserId

		operation.recordId = (screenlet.recordId != 0) ? screenlet.recordId : nil
		operation.recordSetId = screenlet.recordSetId

		operation.autoscrollOnValidation = screenlet.autoscrollOnValidation

		return operation
	}

	override func completedOperation(op: ServerOperation) {
		if let loadOp = op as? LiferayDDLFormSubmitOperation {
			self.resultRecordId = loadOp.resultRecordId
			self.resultAttributes = loadOp.resultAttributes
		}
	}

}
