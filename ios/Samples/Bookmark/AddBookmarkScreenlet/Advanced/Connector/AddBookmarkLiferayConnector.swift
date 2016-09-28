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
import LiferayScreens

public class AddBookmarkLiferayConnector: ServerConnector {

	public let folderId: Int64
	public let title: String
	public let url: String

	public var resultBookmarkInfo: [String:AnyObject]?

	public init(folderId: Int64, title: String, url: String) {
		self.folderId = folderId
		self.title = title
		self.url = url
		super.init()
	}

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {

			if folderId <= 0 {
				return ValidationError("Undefined folderId")
			}

			if title.isEmpty {
				return ValidationError("Title cannot be empty")
			}

			if url.isEmpty {
				return ValidationError("URL cannot be empty")
			}
		}

		return error
	}

	override public func doRun(session session: LRSession) {
		let service = LRBookmarksEntryService_v7(session: session)

		do {
			let result = try service.addEntryWithGroupId(LiferayServerContext.groupId,
			                                             folderId: folderId,
			                                             name: title,
			                                             url: url,
			                                             description: "Added from Liferay Screens",
			                                             serviceContext: nil)

			if let result = result as? [String: AnyObject] {
				resultBookmarkInfo = result
				lastError = nil
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
				resultBookmarkInfo = nil
			}
		}
		catch let error as NSError {
			lastError = error
			resultBookmarkInfo = nil
		}
		
	}
	
}