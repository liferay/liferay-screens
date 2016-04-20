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
import LRMobileSDK


public class DDLFormUploadLiferayConnector: ServerConnector, LRCallback, LRFileProgressDelegate {

	public typealias OnProgress = (DDMFieldDocument, UInt64, UInt64) -> Void

	let document: DDMFieldDocument
	let filePrefix: String

	let repositoryId: Int64
	let folderId: Int64

	let onUploadedBytes: OnProgress?

	var uploadResult: [String:AnyObject]?

	private var requestSemaphore: dispatch_semaphore_t?

	private var bytesToSend: Int64 = 0

	public init(
			document: DDMFieldDocument,
			filePrefix: String,
			repositoryId: Int64,
			folderId: Int64,
			onProgress: OnProgress?) {
		self.document = document
		self.filePrefix = filePrefix
		self.repositoryId = repositoryId
		self.folderId = folderId
		self.onUploadedBytes = onProgress

		super.init()
	}


	//MARK: ServerConnector

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if document.currentValue == nil {
				return ValidationError("ddlform-screenlet", "undefined-current-value")
			}

			if (filePrefix ?? "") == "" {
				return ValidationError("ddlform-screenlet", "undefined-fileprefix")
			}
		}

		return error
	}

	override public func doRun(session session: LRSession) {
		session.callback = self

		let fileName = "\(filePrefix)\(NSUUID().UUIDString)"
		let stream = document.getStream(&bytesToSend)
		let uploadData = LRUploadData(
			inputStream: stream,
			length: bytesToSend,
			fileName: fileName,
			mimeType: document.mimeType,
			progressDelegate: self)
		uploadData.progressDelegate = self

		requestSemaphore = dispatch_semaphore_create(0)

		do {
			try doSendFile(session, name: fileName, data: uploadData)
		}
		catch {
			// ignore the error because this is an async call
			// (the ObjC-Swift bridge is not working well in this scenario)
		}

		dispatch_semaphore_wait(requestSemaphore!, DISPATCH_TIME_FOREVER)
	}

	public func doSendFile(session: LRSession, name: String, data: LRUploadData) throws {
		fatalError("Override doSendFile method")
	}


	//MARK: LRFileProgressDelegate

	public func onProgress(data: NSData!, totalBytes: Int64) {
		let totalBytesSent = UInt64(totalBytes)
		let totalBytesToSend = UInt64(self.bytesToSend)

		document.uploadStatus = .Uploading(totalBytesSent, totalBytesToSend)
		onUploadedBytes?(document, totalBytesSent, totalBytesToSend)
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


public class Liferay62DDLFormUploadConnector: DDLFormUploadLiferayConnector {

	override public func doSendFile(session: LRSession, name: String, data: LRUploadData) throws {
		let service = LRDLAppService_v62(session: session)

		try service.addFileEntryWithRepositoryId(repositoryId,
			folderId: folderId,
			sourceFileName: name,
			mimeType: document.mimeType,
			title: name,
			description: LocalizedString("ddlform-screenlet", key: "upload-metadata-description", obj: self),
			changeLog: LocalizedString("ddlform-screenlet", key: "upload-metadata-changelog", obj: self),
			file: data,
			serviceContext: nil)
	}

}


public class Liferay70DDLFormUploadConnector: DDLFormUploadLiferayConnector {

	override public func doSendFile(session: LRSession, name: String, data: LRUploadData) throws {
		let service = LRDLAppService_v7(session: session)

		try service.addFileEntryWithRepositoryId(repositoryId,
			folderId: folderId,
			sourceFileName: name,
			mimeType: document.mimeType,
			title: name,
			description: LocalizedString("ddlform-screenlet", key: "upload-metadata-description", obj: self),
			changeLog: LocalizedString("ddlform-screenlet", key: "upload-metadata-changelog", obj: self),
			file: data,
			serviceContext: nil)
	}

}
