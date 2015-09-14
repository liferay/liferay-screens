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

	let groupId: Int64
	let recordSetId: Int64
	let recordId: Int64?
	let userId: Int64?

	var resultRecordId: Int64?
	var resultAttributes: NSDictionary?

	override init(screenlet: BaseScreenlet?) {
		let formScreenlet = screenlet as! DDLFormScreenlet

		groupId = (formScreenlet.groupId != 0)
			? formScreenlet.groupId
			: LiferayServerContext.groupId

		userId = (formScreenlet.userId != 0)
			? formScreenlet.userId
			: SessionContext.currentUserId

		recordId = (formScreenlet.recordId != 0)
			? formScreenlet.recordId
			: nil

		recordSetId = formScreenlet.recordSetId

		super.init(screenlet: formScreenlet)
	}

	override func createOperation() -> LiferayDDLFormSubmitOperation {
		let screenlet = self.screenlet as! DDLFormScreenlet

		let operation = LiferayDDLFormSubmitOperation(viewModel: screenlet.viewModel)

		operation.groupId = groupId
		operation.userId = userId
		operation.recordId = recordId
		operation.recordSetId = recordSetId
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
