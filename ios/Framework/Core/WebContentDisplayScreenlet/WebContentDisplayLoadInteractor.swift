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


class WebContentDisplayLoadInteractor: ServerOperationInteractor {

	var resultHTML: String?

	override func createOperation() -> LiferayWebContentLoadOperation {
		let screenlet = self.screenlet as! WebContentDisplayScreenlet
		let operation = LiferayWebContentLoadOperation(screenlet: self.screenlet)

		operation.groupId = (screenlet.groupId != 0)
				? screenlet.groupId : LiferayServerContext.groupId

		operation.articleId = screenlet.articleId

		return operation
	}

	override func completedOperation(op: ServerOperation) {
		self.resultHTML = (op as! LiferayWebContentLoadOperation).resultHTML
	}

}
