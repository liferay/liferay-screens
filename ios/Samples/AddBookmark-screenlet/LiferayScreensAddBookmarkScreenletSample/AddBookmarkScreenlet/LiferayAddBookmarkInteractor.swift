//
//  GetSiteTitleInteractor.swift
//  LiferayScreensAddBookmarkScreenletSample
//
//  Created by jmWork on 18/05/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit
import LiferayScreens
import LRMobileSDK

public class LiferayAddBookmarkInteractor: Interactor, LRCallback {

	public var resultBookmarkInfo: [String:AnyObject]?

	override public func start() -> Bool {
		let viewModel = self.screenlet.screenletView as! AddBookmarkViewModel

		if let URL = viewModel.URL {
			let session = SessionContext.createSessionFromCurrentSession()
			session?.callback = self

			let service = LRBookmarksEntryService_v62(session: session)

			var error: NSError? = nil

			service.addEntryWithGroupId(LiferayServerContext.groupId,
					folderId: 0,
					name: viewModel.title,
					url: viewModel.URL,
					description: "Added from Liferay Screens",
					serviceContext: nil,
					error: &error)

			return (error == nil)
		}

		return false
	}

    public func onFailure(error: NSError!) {
		self.onFailure?(error)
	}

    public func onSuccess(result: AnyObject!) {
		resultBookmarkInfo = (result as! [String:AnyObject])
		self.onSuccess?()
	}

}
