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
