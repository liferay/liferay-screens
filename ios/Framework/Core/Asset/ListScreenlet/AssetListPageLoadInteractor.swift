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


public class AssetListPageLoadInteractor : BaseListPageLoadInteractor {

	public var customEntryQuery: [String:AnyObject]?

	private let groupId: Int64
	private let classNameId: Int64
	private let portletItemName: String?

	init(screenlet: BaseListScreenlet,
			page: Int,
			computeRowCount: Bool,
			groupId: Int64,
			classNameId: Int64,
			portletItemName: String?) {

		self.groupId = groupId
		self.classNameId = classNameId
		self.portletItemName = portletItemName

		super.init(screenlet: screenlet, page: page, computeRowCount: computeRowCount)
	}

	override public func createConnector() -> AssetListPageLiferayConnector {
		let pager = (self.screenlet as! BaseListScreenlet).firstRowForPage

		let connector = LiferayServerContext.connectorFactory.createAssetListPageConnector(
				startRow: pager(self.page),
				endRow: pager(self.page + 1),
				computeRowCount: self.computeRowCount)

		connector.groupId = (self.groupId != 0)
				? self.groupId : LiferayServerContext.groupId
		connector.classNameId = self.classNameId

		connector.portletItemName = self.portletItemName
		connector.customEntryQuery = self.customEntryQuery

		return connector;
	}

	override public func convertResult(serverResult: [String:AnyObject]) -> AnyObject {
		guard let className = serverResult["className"] as? String else {
			return Asset(attributes: serverResult)
		}

		if WebContent.isWebContentClassName(className) {
			return WebContent(attributes: serverResult)
		}
		else {
			return Asset(attributes: serverResult)
		}
	}

	override public func cacheKey(op: PaginationLiferayConnector) -> String {
		return "\((groupId != 0) ? groupId : LiferayServerContext.groupId)-\(classNameId)"
	}

}
