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



public class LiferayDDLFormUploadOperation: ServerOperation, LRCallback, LRProgressDelegate {

	public typealias OnProgress = (DDLFieldDocument, UInt, Int64, Int64) -> Void

	var document: DDLFieldDocument?
	var filePrefix: String?

	var repositoryId: Int64?
	var folderId: Int64?

	var onUploadedBytes: OnProgress?

	var uploadResult: [String:AnyObject]?

	override public var hudFailureMessage: HUDMessage? {
		return (LocalizedString("ddlform-screenlet", "uploading-error", self), details: nil)
	}

	private var requestSemaphore: dispatch_semaphore_t?


	//MARK: ServerOperation

	override func validateData() -> Bool {
		var valid = super.validateData()

		valid = valid && (document != nil && document?.currentValue != nil)
		valid = valid && (filePrefix != nil)
		valid = valid && (repositoryId != nil)
		valid = valid && (folderId != nil)

		return valid
	}

	override internal func doRun(#session: LRSession) {
		session.callback = self

		let fileName = "\(filePrefix!)\(NSUUID().UUIDString)"
		var size:Int64 = 0
		let stream = document!.getStream(&size)
		let uploadData = LRUploadData(
				inputStream: stream,
				length: size,
				fileName: fileName,
				mimeType: document!.mimeType)
		uploadData.progressDelegate = self

		let service = LRDLAppService_v62(session: session)

		requestSemaphore = dispatch_semaphore_create(0)

		service.addFileEntryWithRepositoryId(repositoryId!,
				folderId: folderId!,
				sourceFileName: fileName,
				mimeType: document!.mimeType,
				title: fileName,
				description: LocalizedString("ddlform-screenlet", "upload-metadata-description", self),
				changeLog: LocalizedString("ddlform-screenlet", "upload-metadata-changelog", self),
				file: uploadData,
				serviceContext: nil,
				error: &lastError)

		dispatch_semaphore_wait(requestSemaphore!, DISPATCH_TIME_FOREVER)
	}


	//MARK: LRProgressDelegate

	public func onProgressBytes(bytes: UInt, sent: Int64, total: Int64) {
		document!.uploadStatus = .Uploading(UInt(sent), UInt(total))
		onUploadedBytes?(document!, bytes, sent, total)
	}


	//MARK: LRCallback

	public func onFailure(error: NSError?) {
		lastError = error
		uploadResult = nil

		dispatch_semaphore_signal(requestSemaphore!)
	}

	public func onSuccess(result: AnyObject?) {
		lastError = nil
		uploadResult = result as? [String:AnyObject]

		dispatch_semaphore_signal(requestSemaphore!)
	}

}
