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
import Foundation
import LRMobileSDK


public class UploadFileConnector<T> : ServerConnector, LRCallback, LRFileProgressDelegate {

	public typealias OnProgress = (T?, UInt64, UInt64) -> Void

	var inputStream: NSInputStream?
	var bytesToSend: Int64?
	var image: UIImage?
	let fileName: String
	let mimeType: String
	let parameter: T?
	let onUploadedBytes: OnProgress?

	var requestSemaphore: dispatch_semaphore_t?

	var uploadResult: [String:AnyObject]?


	//MARK: Initializers

	public init(
		inputStream: NSInputStream,
		bytesToSend: Int64,
		fileName: String,
		mimeType: String,
		parameter: T? = nil,
		onUploadedBytes: OnProgress?) {

		self.inputStream = inputStream
		self.bytesToSend = bytesToSend
		self.fileName = fileName
		self.mimeType = mimeType
		self.parameter = parameter
		self.onUploadedBytes = onUploadedBytes

		super.init()
	}

	public init(
		image: UIImage,
		fileName: String,
		mimeType: String,
		parameter: T? = nil,
		onUploadedBytes: OnProgress?) {

		self.image = image
		self.fileName = fileName
		self.mimeType = mimeType
		self.parameter = parameter
		self.onUploadedBytes = onUploadedBytes

		super.init()
	}


	//MARK: ServerConnector

	override public func doRun(session session: LRSession) {
		if inputStream == nil {
			if let imageData = UIImagePNGRepresentation(image!) {
				bytesToSend = Int64(imageData.length)
				inputStream = NSInputStream(data: imageData)
			}
			else {
				print("ERROR: could not create inputstream from image")
			}
		}

		session.callback = self
		let uploadData = LRUploadData(
			inputStream: inputStream,
			length: bytesToSend!,
			fileName: fileName,
			mimeType: mimeType,
			progressDelegate: self)
		uploadData.progressDelegate = self

		requestSemaphore = dispatch_semaphore_create(0)

		do {
			try doSendFile(session, data: uploadData)
		} catch {

		}

		dispatch_semaphore_wait(requestSemaphore!, DISPATCH_TIME_FOREVER)
	}


	//MARK: Public methods

	public func onProgress(data: NSData!, totalBytes: Int64) {
		let totalBytesSent = UInt64(totalBytes)
		let totalBytesToSend = UInt64(self.bytesToSend ?? 0)

		onUploadedBytes?(parameter, totalBytesSent, totalBytesToSend)
	}

	public func onFailure(error: NSError!) {
		lastError = error
		dispatch_semaphore_signal(requestSemaphore!)
	}

	public func doSendFile(session: LRSession, data: LRUploadData) throws {
		fatalError("Override doSendFile method")
	}

	public func onSuccess(result: AnyObject!) {
		lastError = nil

		uploadResult = result as? [String:AnyObject]
		dispatch_semaphore_signal(requestSemaphore!)
	}

}
