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

public class ImageGalleryUploadConnector : UploadFileConnector<String> {

	private let repositoryId: Int64
	private let folderId: Int64
	private let sourceFileName: String
	private let title: String
	private let descrip: String
	private let changeLog: String

	public init(repositoryId: Int64,
			folderId: Int64,
			sourceFileName: String,
			mimeType: String,
			title: String,
			descrip: String,
			changeLog: String,
			image: UIImage,
			onUploadBytes: OnProgress?) {

		self.repositoryId = repositoryId
		self.folderId = folderId
		self.sourceFileName = sourceFileName
		self.title = title
		self.descrip = descrip
		self.changeLog = changeLog

		super.init(
			image: image,
			fileName: sourceFileName,
			mimeType: mimeType,
			parameter: title,
			onUploadedBytes: onUploadBytes)
	}

	public override func validateData() -> ValidationError? {
		var error = super.validateData()

		if error == nil {
			if repositoryId < 0 {
				error = ValidationError("imagegallery-screenlet","undefined-repositoryid")
			}
			else if folderId < 0 {
				error = ValidationError("imagegallery-screenlet","undefined-folderid")
			}
		}

		return error
	}

	public override func doSendFile(session: LRSession, data: LRUploadData) throws {
		let service = LRDLAppService_v7(session: session)

		let serviceContext = LRJSONObjectWrapper(JSONObject: ["addGuestPermissions":true])
		try service.addFileEntryWithRepositoryId(repositoryId,
				folderId: folderId,
			 	sourceFileName: fileName,
			 	mimeType: mimeType,
			 	title: title,
			 	description: descrip,
			 	changeLog: changeLog,
			 	file: data,
			 	serviceContext: serviceContext)
	}
	
}
