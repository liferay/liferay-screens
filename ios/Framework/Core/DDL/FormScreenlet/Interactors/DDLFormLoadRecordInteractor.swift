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


class DDLFormLoadRecordInteractor: ServerOperationInteractor {

	var resultRecordData: [String:AnyObject]?
	var resultRecordId: Int64?

	var resultFormRecord: DDLRecord?
	var resultFormUserId: Int64?


	override func createOperation() -> LiferayDDLFormRecordLoadOperation? {
		let screenlet = self.screenlet as! DDLFormScreenlet

		let loadRecordOperation = LiferayDDLFormRecordLoadOperation(screenlet: screenlet)

		loadRecordOperation.recordId = screenlet.recordId

		if screenlet.formView.isRecordEmpty {
			let loadFormOperation = LiferayDDLFormLoadOperation(screenlet: screenlet)

			loadFormOperation.structureId = screenlet.structureId

			loadRecordOperation.addDependency(loadFormOperation)

			let result = loadFormOperation.validateAndEnqueue() {
				if let loadFormOp = $0 as? LiferayDDLFormLoadOperation {
					self.resultFormRecord = loadFormOp.resultRecord
					self.resultFormUserId = loadFormOp.resultUserId
				}
			}

			if !result {
				return nil
			}
		}

		return loadRecordOperation
	}

	override func completedOperation(op: ServerOperation) {
		if let recordOp = op as? LiferayDDLFormRecordLoadOperation {
			self.resultRecordData = recordOp.resultRecord
			self.resultRecordId = recordOp.resultRecordId

			if let resultRecordValue = recordOp.resultRecord {
				self.resultFormRecord?.updateCurrentValues(resultRecordValue)
			}
		}
	}

}
