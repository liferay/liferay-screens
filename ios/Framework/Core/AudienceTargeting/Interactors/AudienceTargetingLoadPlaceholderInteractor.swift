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


class AudienceTargetingLoadPlaceholderInteractor: Interactor {

	var resultClassPK: String?
	var resultClassName: String?
	var resultCustomContent: String?

	var resultContent: AnyObject?
	var resultMimeType: String?


	override func start() -> Bool {
		let result = createAudienceTargetingOperation().validateAndEnqueue()

		if !result {
			self.callOnFailure(NSError.errorWithCause(.AbortedDueToPreconditions))
		}

		return result
	}

	func createAudienceTargetingOperation() -> AudienceTargetingLoadPlaceholderOperation {
		let screenlet = self.screenlet as! AudienceTargetingDisplayScreenlet
		let operation = AudienceTargetingLoadPlaceholderOperation(screenlet: self.screenlet)

		operation.groupId = (screenlet.groupId != 0)
				? screenlet.groupId : LiferayServerContext.groupId

		operation.appId = screenlet.appId
		operation.placeholderId = screenlet.placeholderId

		operation.userContext = AudienceTargetingLoader.computeUserContext()

		// TODO retain-cycle on operation?
		operation.onComplete = {
			if let error = $0.lastError {
				self.callOnFailure(error)
			}
			else if let loadOp = ($0 as? AudienceTargetingLoadPlaceholderOperation),
					result = loadOp.results?.first {

				if let customContent = result.customContent {
					self.resultCustomContent = customContent
					self.resultClassName = nil
					self.resultClassPK = nil

					self.callOnSuccess()
				}
				else {
					if let className = result.className,
							classPK = result.classPK?.description {
						self.resultClassName = className
						self.resultClassPK = classPK

						self.startContentOperation(
								className: className,
								classPK: classPK);
					}
					else {
						self.callOnFailure(NSError.errorWithCause(.InvalidServerResponse))
					}
				}
			}
		}

		return operation
	}

	func startContentOperation(#className: String, classPK: String) {
		if let op = createContentOperation(className: className, classPK: classPK) {
			if !op.validateAndEnqueue() {
				self.callOnFailure(NSError.errorWithCause(.InvalidServerResponse))
			}
		}
		else {
			self.callOnFailure(NSError.errorWithCause(.InvalidServerResponse))
		}
	}

	func createContentOperation(#className: String, classPK: String) -> ServerOperation? {
		var operation: ServerOperation? = nil

		if className == "com.liferay.portlet.documentlibrary.model.DLFileEntry" {
			let op = LoadDLEntryOperation(screenlet: self.screenlet)

			if let fileEntryId = classPK.toInt() {
				op.fileEntryId = Int64(fileEntryId)

				// TODO retain-cycle on operation?
				op.onComplete = {
					if let error = $0.lastError {
						self.callOnFailure(error)
					}
					else if let loadOp = ($0 as? LoadDLEntryOperation),
							resultGroupId = loadOp.resultGroupId,
							resultFolderId = loadOp.resultFolderId,
							resultName = loadOp.resultName,
							resultUUID = loadOp.resultUUID,
							resultMimeType = loadOp.resultMimeType {

						self.resultContent = "\(LiferayServerContext.server)/documents/" +
								"\(resultGroupId)/\(resultFolderId)/" +
								"\(resultName)/\(resultUUID)"
						self.resultMimeType = resultMimeType

						self.callOnSuccess()
					}
					else {
						self.callOnFailure(NSError.errorWithCause(.InvalidServerResponse))
					}
				}

				operation = op
			}
		}

		return operation
	}

}
