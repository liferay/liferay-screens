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


@objc public protocol BookmarkListScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: BookmarkListScreenlet,
		onBookmarkListResponse bookmarks: [Bookmark])

	optional func screenlet(screenlet: BookmarkListScreenlet,
		onBookmarkListError error: NSError)

	optional func screenlet(screenlet: BookmarkListScreenlet,
		onBookmarkSelected bookmark: Bookmark)

}


@objc public class Bookmark : NSObject {

	public let attributes: [String:AnyObject]

	public var name: String {
		return attributes["name"] as! String
	}

	override public var description: String {
		return attributes["description"] as! String
	}

	public var url: String {
		return attributes["url"] as! String
	}

	//MARK: Init

	public init(attributes:[String:AnyObject]) {
		self.attributes = attributes
	}

}


@IBDesignable public class BookmarkListScreenlet: BaseListScreenlet {

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var folderId: Int64 = 0

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue


	public var bookmarkListDelegate: BookmarkListScreenletDelegate? {
		return delegate as? BookmarkListScreenletDelegate
	}


	//MARK: BaseListScreenlet

	override public func createPageLoadInteractor(
			page page: Int,
			computeRowCount: Bool) -> BaseListPageLoadInteractor {

		let interactor = BookmarkListPageLoadInteractor(
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

		bookmarkListDelegate?.screenlet?(self, onBookmarkListError: error)
	}

	override public func onLoadPageResult(page page: Int, rows: [AnyObject], rowCount: Int) {
		super.onLoadPageResult(page: page, rows: rows, rowCount: rowCount)

		bookmarkListDelegate?.screenlet?(self, onBookmarkListResponse: rows as! [Bookmark])
	}

	override public func onSelectedRow(row: AnyObject) {
		bookmarkListDelegate?.screenlet?(self, onBookmarkSelected: row as! Bookmark)
	}
	
}
