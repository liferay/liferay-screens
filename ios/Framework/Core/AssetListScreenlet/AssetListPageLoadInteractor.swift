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


class AssetListPageLoadInteractor : BaseListPageLoadInteractor {

	let groupId: Int64
	let classNameId: Int

	init(screenlet: BaseListScreenlet,
			page: Int,
			computeRowCount: Bool,
			groupId: Int64,
			classNameId: Int) {

		self.groupId = groupId
		self.classNameId = classNameId

		super.init(screenlet: screenlet, page: page, computeRowCount: computeRowCount)
	}

	override func createOperation() -> LiferayAssetListPageOperation {
		let operation = LiferayAssetListPageOperation(
				screenlet: self.screenlet,
				page: self.page,
				computeRowCount: self.computeRowCount)

		operation.groupId = (self.groupId != 0)
				? self.groupId : LiferayServerContext.groupId
		operation.classNameId = self.classNameId

		return operation;
	}

	override func convertResult(serverResult: [String:AnyObject]) -> AnyObject {
		return AssetListScreenletEntry(attributes: serverResult)
	}

}
