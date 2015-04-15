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


class DDLFormUploadDocumentInteractor: ServerOperationInteractor {

	typealias OnProgress = LiferayDDLFormUploadOperation.OnProgress


	let document: DDLFieldDocument
	let onProgressClosure: OnProgress

	var resultResponse: [String:AnyObject]?


	init(screenlet: BaseScreenlet, document: DDLFieldDocument, onProgressClosure: OnProgress) {
		self.document = document
		self.onProgressClosure = onProgressClosure

		super.init(screenlet: screenlet)
	}

	override func createOperation() -> LiferayDDLFormUploadOperation {
		let screenlet = self.screenlet as! DDLFormScreenlet

		let operation = LiferayDDLFormUploadOperation(screenlet: screenlet)

		operation.document = self.document
		operation.onUploadedBytes = self.onProgressClosure

		operation.filePrefix = screenlet.filePrefix
		operation.folderId = screenlet.folderId

		operation.repositoryId = (screenlet.repositoryId != 0)
				? screenlet.repositoryId
				: (screenlet.groupId != 0) ? screenlet.groupId : LiferayServerContext.groupId

		return operation
	}

	override func completedOperation(op: ServerOperation) {
		if let lastErrorValue = op.lastError {
			document.uploadStatus = .Failed(lastErrorValue)
		}
		else if let uploadOp = op as? LiferayDDLFormUploadOperation {
			self.resultResponse = uploadOp.uploadResult
			document.uploadStatus = .Uploaded(uploadOp.uploadResult!)
		}
	}

}
