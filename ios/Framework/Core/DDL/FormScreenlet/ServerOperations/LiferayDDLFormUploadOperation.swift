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



public class LiferayDDLFormUploadOperation: ServerOperation, LRCallback, LRFileProgressDelegate {

	public typealias OnProgress = (DDLFieldDocument, UInt64, UInt64) -> Void

	var document: DDLFieldDocument?
	var filePrefix: String?

	var repositoryId: Int64?
	var folderId: Int64?

	var onUploadedBytes: OnProgress?

	var uploadResult: [String:AnyObject]?

	private var requestSemaphore: dispatch_semaphore_t?


	//MARK: ServerOperation

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if document?.currentValue == nil {
				return ValidationError("ddlform-screenlet", "undefined-current-value")
			}

			if (filePrefix ?? "") == "" {
				return ValidationError("ddlform-screenlet", "undefined-fileprefix")
			}

			if repositoryId == nil {
				return ValidationError("ddlform-screenlet", "undefined-repository")
			}

			if folderId == nil {
				return ValidationError("ddlform-screenlet", "undefined-folder")
			}
		}

		return error
	}

	override public func doRun(session session: LRSession) {
		session.callback = self

		let fileName = "\(filePrefix!)\(NSUUID().UUIDString)"
		var size:Int64 = 0
		let stream = document!.getStream(&size)
		let uploadData = LRUploadData(
				inputStream: stream,
				length: size,
				fileName: fileName,
				mimeType: document!.mimeType,
				progressDelegate: self)
		uploadData.progressDelegate = self

		let service = LRDLAppService_v62(session: session)

		requestSemaphore = dispatch_semaphore_create(0)

		do {
			try service.addFileEntryWithRepositoryId(repositoryId!,
				folderId: folderId!,
				sourceFileName: fileName,
				mimeType: document!.mimeType,
				title: fileName,
				description: LocalizedString("ddlform-screenlet", key: "upload-metadata-description", obj: self),
				changeLog: LocalizedString("ddlform-screenlet", key: "upload-metadata-changelog", obj: self),
				file: uploadData,
				serviceContext: nil)
		}
		catch let error as NSError {
			lastError = error
		}

		dispatch_semaphore_wait(requestSemaphore!, DISPATCH_TIME_FOREVER)
	}


	//MARK: LRProgressDelegate

	public func onProgress(data: NSData!, totalBytes: Int64) {
		let sent = UInt64(data.length)
		let total = UInt64(totalBytes)
		document!.uploadStatus = .Uploading(sent, total)
		onUploadedBytes?(document!, sent, total)
	}


	//MARK: LRCallback

	public func onFailure(error: NSError!) {
		lastError = error
		uploadResult = nil

		dispatch_semaphore_signal(requestSemaphore!)
	}

	public func onSuccess(result: AnyObject!) {
		lastError = nil
		uploadResult = result as? [String:AnyObject]

		dispatch_semaphore_signal(requestSemaphore!)
	}

}
