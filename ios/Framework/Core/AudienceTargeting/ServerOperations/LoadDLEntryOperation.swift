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

#if LIFERAY_SCREENS_FRAMEWORK
	import LRMobileSDK
#endif


public class LoadDLEntryOperation: ServerOperation {

	public var fileEntryId: Int64?

	public var resultGroupId: Int64?
	public var resultFolderId: Int64?
	public var resultName: String?
	public var resultUUID: String?
	public var resultMimeType: String?


	//MARK: ServerOperation

	override func validateData() -> Bool {
		var valid = super.validateData()

		valid = valid && (fileEntryId != nil)

		return valid
	}

	override internal func doRun(#session: LRSession) {
		let service = LRDLFileEntryService_v62(session: session)

		lastError = nil

		let result = service.getFileEntryWithFileEntryId(fileEntryId!,
				error: &lastError)

		if lastError == nil {
			if let groupId = ((result["groupId"] as? Int).map { Int64($0) }),
				folderId = ((result["folderId"] as? Int).map { Int64($0) }),
				name = result["name"] as? String,
				uuid = result["uuid"] as? String,
				mimeType = result["mimeType"] as? String {

				resultGroupId = groupId
				resultFolderId = folderId
				resultName = name
				resultUUID = uuid
				resultMimeType = mimeType
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse)

				resultGroupId = nil
				resultFolderId = nil
				resultName = nil
				resultUUID = nil
				resultMimeType = nil
			}
		}
	}

}
