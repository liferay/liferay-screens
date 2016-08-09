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


class LoadFileEntryInteractor: ServerReadConnectorInteractor {

	let fileEntry: FileEntry

	var resultUrl: NSURL?

	init(screenlet: BaseScreenlet?, fileEntry: FileEntry) {
		self.fileEntry = fileEntry

		super.init(screenlet: screenlet)
	}

	override func createConnector() -> ServerConnector? {
		guard let url = NSURL(string: LiferayServerContext.server + fileEntry.url) else {
			return nil
		}

		return HttpDownloadConnector(url: url)
	}

	override func completedConnector(op: ServerConnector) {
		resultUrl = (op as? HttpDownloadConnector)?.resultUrl
	}
}
