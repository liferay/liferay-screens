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
import LRMobileSDK


public class LiferayAddBookmarkOperation: ServerOperation {

	private var groupId: Int64
	private var title: String
	private var url: String

	public var folderId: Int64 = 0
	public var bookmarkDescription = "Added from Liferay Screens"

	public var resultBookmarkInfo: [NSObject:AnyObject]?

	public init(groupId: Int64, title: String, url: String) {
		self.groupId = groupId
		self.title = title
		self.url = url

		super.init()
	}


	//MARK: ServerOperation

	override public func doRun(session session: LRSession) {
		let service = LRBookmarksEntryService_v62(session: session)

		do {
			lastError = nil

			resultBookmarkInfo = try service.addEntryWithGroupId(groupId,
				folderId: folderId,
				name: title,
				url: url,
				description: bookmarkDescription,
				serviceContext: nil)
		}
		catch let error as NSError {
			lastError = error
			resultBookmarkInfo = nil
		}
	}

}
