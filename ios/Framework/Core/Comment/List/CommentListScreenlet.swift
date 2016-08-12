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


@objc public protocol CommentListScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: CommentListScreenlet,
			onListResponseComments comments: [Comment])

	optional func screenlet(screenlet: CommentListScreenlet,
			onCommentListError error: NSError)

	optional func screenlet(screenlet: CommentListScreenlet,
			onSelectedComment comment: Comment)

	optional func screenlet(screenlet: CommentListScreenlet,
	        onDeletedComment comment: Comment)

	optional func screenlet(screenlet: CommentListScreenlet,
	        onCommentDelete comment: Comment,
	        onError error: NSError)
}


@IBDesignable public class CommentListScreenlet: BaseListScreenlet, CommentDisplayScreenletDelegate {

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var className: String = ""
	@IBInspectable public var classPK: Int64 = 0

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue

	public var viewModel: CommentListViewModel? {
		return screenletView as? CommentListViewModel
	}

	public var commentListDelegate: CommentListScreenletDelegate? {
		return delegate as? CommentListScreenletDelegate
	}

	@IBInspectable public var editable: Bool = false {
		didSet {
			screenletView?.editable = self.editable
		}
	}

	//MARK: Public methods

	public func addComment(comment: Comment) {
		viewModel?.addComment(comment)
	}

	public func deleteComment(comment: Comment) {
		viewModel?.deleteComment(comment)
	}

	public func updateComment(comment: Comment) {
		viewModel?.updateComment(comment)
	}

	public override func onCreated() {
		super.onCreated()
		screenletView?.editable = self.editable
	}

	//MARK: BaseListScreenlet

	override public func createPageLoadInteractor(
			page page: Int,
			computeRowCount: Bool)
			-> BaseListPageLoadInteractor {

		let interactor = CommentListPageLoadInteractor(
			screenlet: self,
			page: page,
			computeRowCount: computeRowCount,
			groupId: groupId,
			className: className,
			classPK: classPK)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		return interactor
	}

	override public func onLoadPageError(page page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		commentListDelegate?.screenlet?(self, onCommentListError: error)
	}

	override public func onLoadPageResult(page page: Int, rows: [AnyObject], rowCount: Int) {
		super.onLoadPageResult(page: page, rows: rows, rowCount: rowCount)

		commentListDelegate?.screenlet?(self,
				onListResponseComments: rows as! [Comment])
	}

	override public func onSelectedRow(row: AnyObject) {
		commentListDelegate?.screenlet?(self,
				onSelectedComment: row as! Comment)
	}

	//MARK: CommentDisplayScreenletDelegate

	public func screenlet(screenlet: CommentDisplayScreenlet, onCommentDeleted comment: Comment?) {
		if let deletedComment = comment {
			deleteComment(deletedComment)
			commentListDelegate?.screenlet?(self, onDeletedComment: deletedComment)
		}
	}

	public func screenlet(screenlet: CommentDisplayScreenlet, onDeleteComment comment: Comment?,
			onError error: NSError) {
		if let comment = comment {
			commentListDelegate?.screenlet?(self, onDeletedComment: comment)
		}
	}

}
