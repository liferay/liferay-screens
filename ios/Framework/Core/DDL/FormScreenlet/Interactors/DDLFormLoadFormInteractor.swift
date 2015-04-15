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


class DDLFormLoadFormInteractor: ServerOperationInteractor {

	var resultRecord: DDLRecord?
	var resultUserId: Int64?

	override func createOperation() -> LiferayDDLFormLoadOperation {
		let screenlet = self.screenlet as! DDLFormScreenlet

		let operation = LiferayDDLFormLoadOperation(screenlet: screenlet)

		operation.structureId = screenlet.structureId

		return operation
	}

	override func completedOperation(op: ServerOperation) {
		if let loadOp = op as? LiferayDDLFormLoadOperation {
			self.resultRecord = loadOp.resultRecord
			self.resultUserId = loadOp.resultUserId
		}
	}

}
