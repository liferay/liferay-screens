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


public class AddBookmarkInteractor: ServerWriteConnectorInteractor {
	
	public var resultBookmarkInfo: [String:AnyObject]?

	public let folderId: Int64
	public let title: String
	public let url: String


	//MARK: Initializer

	public init(screenlet: BaseScreenlet, folderId: Int64, title: String, url: String) {
		self.folderId = folderId
		self.title = title
		self.url = url
		super.init(screenlet: screenlet)
	}


	//MARK: ServerConnectorInteractor

	public override func createConnector() -> ServerConnector? {
		return AddBookmarkLiferayConnector(folderId: folderId, title: title, url: url)
	}

	override public func completedConnector(c: ServerConnector) {
		if let addCon = (c as? AddBookmarkLiferayConnector),
			bookmarkInfo = addCon.resultBookmarkInfo {
			self.resultBookmarkInfo = bookmarkInfo
		}
	}

}
