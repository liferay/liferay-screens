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


@objc public protocol WebContentListScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: WebContentListScreenlet,
			onWebContentListResponse contents: [WebContent])

	optional func screenlet(screenlet: WebContentListScreenlet,
			onWebContentListError error: NSError)

	optional func screenlet(screenlet: WebContentListScreenlet,
			onWebContentSelected content: WebContent)

}

@IBDesignable public class WebContentListScreenlet: BaseListScreenlet {

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var folderId: Int64 = 0
	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue


	public var webContentListDelegate: WebContentListScreenletDelegate? {
		return delegate as? WebContentListScreenletDelegate
	}


	//MARK: BaseListScreenlet

	override public func createPageLoadInteractor(
			page page: Int,
			computeRowCount: Bool)
			-> BaseListPageLoadInteractor {

		let interactor = WebContentListPageLoadInteractor(
			screenlet: self,
			page: page,
			computeRowCount: computeRowCount,
			groupId: self.groupId,
			folderId: self.folderId)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		return interactor
	}

	override public func onLoadPageError(page page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		webContentListDelegate?.screenlet?(self, onWebContentListError: error)
	}

	override public func onLoadPageResult(page page: Int, rows: [AnyObject], rowCount: Int) {
		super.onLoadPageResult(page: page, rows: rows, rowCount: rowCount)

		let webContentEntries = rows as! [WebContent]

		webContentListDelegate?.screenlet?(self, onWebContentListResponse: webContentEntries)
	}

	override public func onSelectedRow(row: AnyObject) {
		webContentListDelegate?.screenlet?(self, onWebContentSelected: row as! WebContent)
	}

}
