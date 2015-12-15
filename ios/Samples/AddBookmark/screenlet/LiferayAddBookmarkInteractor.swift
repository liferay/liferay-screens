//
//  GetSiteTitleInteractor.swift
//  LiferayScreensAddBookmarkScreenletSample
//
//  Created by jmWork on 18/05/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit
import LiferayScreens


public class LiferayAddBookmarkInteractor: ServerOperationInteractor {

	public var resultBookmarkInfo: [NSObject:AnyObject]?

	public override func createOperation() -> ServerOperation? {
		let viewModel = self.screenlet!.screenletView as! AddBookmarkViewModel

		let op = LiferayAddBookmarkOperation(
			groupId: LiferayServerContext.groupId,
			title: viewModel.title!,
			url: viewModel.URL!)

		op.folderId = 20622 // this bookmark folder is writable by test user

		return op
	}

	override public func completedOperation(op: ServerOperation) {
		self.resultBookmarkInfo = (op as! LiferayAddBookmarkOperation).resultBookmarkInfo
	}

}
