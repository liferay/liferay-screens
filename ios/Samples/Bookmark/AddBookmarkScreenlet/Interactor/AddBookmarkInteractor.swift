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


public class AddBookmarkInteractor: Interactor, LRCallback {
	
	public var resultBookmarkInfo: [String:AnyObject]?


	//MARK: Interactor

	override public func start() -> Bool {
		let screenlet = self.screenlet as! AddBookmarkScreenlet
		let view = screenlet.screenletView as! AddBookmarkView

		if let url = view.URL {
			let session = SessionContext.createSessionFromCurrentSession()
			session?.callback = self

			let service = LRBookmarksEntryService_v7(session: session)

			do {
				try service.addEntryWithGroupId(LiferayServerContext.groupId,
						folderId: screenlet.folderId,
						name: view.title,
						url: url,
				    	description: "Added from Liferay Screens",
				    	serviceContext: nil)

				return true
			}
			catch {
				return false
			}
		}

		return false
	}


	//MARK: LRCallback

	public func onFailure(error: NSError!) {
		self.callOnFailure(error)
	}

	public func onSuccess(result: AnyObject!) {
		//Save result bookmark info
		resultBookmarkInfo = (result as! [String:AnyObject])

		self.callOnSuccess()
	}

}
