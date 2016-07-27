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

public class ImageGalleryUploadInteractor : ServerWriteConnectorInteractor {

	public typealias OnProgress = ImageGalleryUploadConnector.OnProgress

	let imageUpload: ImageEntryUpload
	let repositoryId: Int64
	let folderId: Int64
	let onUploadedBytes: OnProgress?

	var result: [String : AnyObject]?

	init(screenlet: ImageGalleryScreenlet, imageUpload: ImageEntryUpload, repositoryId: Int64, folderId: Int64, onUploadedBytes: OnProgress? ) {

		self.imageUpload = imageUpload
		self.repositoryId = repositoryId
		self.folderId = folderId
		self.onUploadedBytes = onUploadedBytes

		super.init(screenlet: screenlet)
	}

	public override func createConnector() -> ServerConnector? {
		return ImageGalleryUploadConnector(repositoryId: repositoryId, folderId: folderId, sourceFileName: imageUpload.title, mimeType: "image/png", title: imageUpload.title, descrip: "", changeLog: "", image: imageUpload.image, onUploadBytes:  onUploadedBytes)
	}

	public override func completedConnector(op: ServerConnector) {
		if let op = op as? ImageGalleryUploadConnector {
			result = op.uploadResult
		}
	}

}