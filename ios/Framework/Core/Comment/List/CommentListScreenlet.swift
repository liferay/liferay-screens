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

	/// Called when the screenlet receives the comments.
	///
	/// - Parameters:
	///   - screenlet
	///   - comments: asset's comments.
	@objc optional func screenlet(_ screenlet: CommentListScreenlet,
			onListResponseComments comments: [Comment])

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error while retrieving comments.
	@objc optional func screenlet(_ screenlet: CommentListScreenlet,
			onCommentListError error: NSError)

	/// Called when a comment is selected.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: selected comment.
	@objc optional func screenlet(_ screenlet: CommentListScreenlet,
			onSelectedComment comment: Comment)

	/// Called when a comment is deleted.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: deleted comment.
	@objc optional func screenlet(_ screenlet: CommentListScreenlet,
			onDeletedComment comment: Comment)

	/// Called when the screenlet prepares a comment for deletion.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: deleted comment.
	///   - error: error while deleting comment.
	@objc optional func screenlet(_ screenlet: CommentListScreenlet,
			onCommentDelete comment: Comment,
			onError error: NSError)

	/// Called when a comment is updated.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: updated comment.
	@objc optional func screenlet(_ screenlet: CommentListScreenlet,
			onUpdatedComment comment: Comment)

	/// Called when the screenlet prepares a comment for update.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: updated comment.
	///   - error: error while updating comment.
	@objc optional func screenlet(_ screenlet: CommentListScreenlet,
			onCommentUpdate comment: Comment,
			onError error: NSError)

}


open class CommentListScreenlet: BaseListScreenlet,
		CommentDisplayScreenletDelegate {


	//MARK: Inspectables

	@IBInspectable open var className: String = ""

	@IBInspectable open var classPK: Int64 = 0

	@IBInspectable open var offlinePolicy: String? = CacheStrategyType.remoteFirst.rawValue

	@IBInspectable open var editable: Bool = true {
		didSet {
			screenletView?.editable = self.editable
		}
	}

	open var viewModel: CommentListViewModel? {
		return screenletView as? CommentListViewModel
	}

	open var commentListDelegate: CommentListScreenletDelegate? {
		return delegate as? CommentListScreenletDelegate
	}


	//MARK: Public methods

	/// Call this method to add a new asset comment.
	///
	/// - Parameter comment: asset comment.
	open func addComment(_ comment: Comment) {
		viewModel?.addComment(comment)
	}

	/// Call this method to delete an asset comment.
	///
	/// - Parameter comment: asset comment.
	open func deleteComment(_ comment: Comment) {
		viewModel?.deleteComment(comment)
	}

	/// Call this method to update an asset comment.
	///
	/// - Parameter comment: asset comment.
	open func updateComment(_ comment: Comment) {
		viewModel?.updateComment(comment)
	}


	//MARK: BaseListScreenlet

	override open func onCreated() {
		super.onCreated()
		screenletView?.editable = self.editable
	}

	override open func createPageLoadInteractor(
			page: Int,
			computeRowCount: Bool) -> BaseListPageLoadInteractor {
		let interactor = CommentListPageLoadInteractor(
				screenlet: self,
				page: page,
				computeRowCount: computeRowCount,
				className: className,
				classPK: classPK)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .remoteFirst

		return interactor
	}

	override open func onLoadPageError(page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		commentListDelegate?.screenlet?(self, onCommentListError: error)
	}

	override open func onLoadPageResult(page: Int, rows: [AnyObject], rowCount: Int) {
		super.onLoadPageResult(page: page, rows: rows, rowCount: rowCount)

		commentListDelegate?.screenlet?(self,
				onListResponseComments: rows as! [Comment])
	}

	override open func onSelectedRow(_ row: AnyObject) {
		commentListDelegate?.screenlet?(self,
				onSelectedComment: row as! Comment)
	}


	//MARK: CommentDisplayScreenletDelegate

	open func screenlet(
			_ screenlet: CommentDisplayScreenlet,
			onCommentDeleted comment: Comment) {
		deleteComment(comment)
		commentListDelegate?.screenlet?(self, onDeletedComment: comment)
	}

	open func screenlet(
			_ screenlet: CommentDisplayScreenlet,
			onDeleteComment comment: Comment,
			onError error: NSError) {
		commentListDelegate?.screenlet?(self, onCommentDelete: comment, onError: error)
	}

	open func screenlet(
			_ screenlet: CommentDisplayScreenlet,
			onCommentUpdated comment: Comment) {
		updateComment(comment)
		commentListDelegate?.screenlet?(self, onUpdatedComment: comment)
	}

	open func screenlet(
			_ screenlet: CommentDisplayScreenlet,
			onUpdateComment comment: Comment,
			onError error: NSError) {
		commentListDelegate?.screenlet?(self, onCommentUpdate: comment, onError: error)
	}
	
}
