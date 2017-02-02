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
	optional func screenlet(screenlet: CommentListScreenlet,
			onListResponseComments comments: [Comment])

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error while retrieving comments.
	optional func screenlet(screenlet: CommentListScreenlet,
			onCommentListError error: NSError)

	/// Called when a comment is selected.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: selected comment.
	optional func screenlet(screenlet: CommentListScreenlet,
			onSelectedComment comment: Comment)

	/// Called when a comment is deleted.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: deleted comment.
	optional func screenlet(screenlet: CommentListScreenlet,
			onDeletedComment comment: Comment)

	/// Called when the screenlet prepares a comment for deletion.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: deleted comment.
	///   - error: error while deleting comment.
	optional func screenlet(screenlet: CommentListScreenlet,
			onCommentDelete comment: Comment,
			onError error: NSError)

	/// Called when a comment is updated.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: updated comment.
	optional func screenlet(screenlet: CommentListScreenlet,
			onUpdatedComment comment: Comment)

	/// Called when the screenlet prepares a comment for update.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: updated comment.
	///   - error: error while updating comment.
	optional func screenlet(screenlet: CommentListScreenlet,
			onCommentUpdate comment: Comment,
			onError error: NSError)

}


public class CommentListScreenlet: BaseListScreenlet,
		CommentDisplayScreenletDelegate {


	//MARK: Inspectables

	@IBInspectable public var className: String = ""

	@IBInspectable public var classPK: Int64 = 0

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue

	@IBInspectable public var editable: Bool = true {
		didSet {
			screenletView?.editable = self.editable
		}
	}

	public var viewModel: CommentListViewModel? {
		return screenletView as? CommentListViewModel
	}

	public var commentListDelegate: CommentListScreenletDelegate? {
		return delegate as? CommentListScreenletDelegate
	}


	//MARK: Public methods

	/// Call this method to add a new asset comment.
	///
	/// - Parameter comment: asset comment.
	public func addComment(comment: Comment) {
		viewModel?.addComment(comment)
	}

	/// Call this method to delete an asset comment.
	///
	/// - Parameter comment: asset comment.
	public func deleteComment(comment: Comment) {
		viewModel?.deleteComment(comment)
	}

	/// Call this method to update an asset comment.
	///
	/// - Parameter comment: asset comment.
	public func updateComment(comment: Comment) {
		viewModel?.updateComment(comment)
	}


	//MARK: BaseListScreenlet

	override public func onCreated() {
		super.onCreated()
		screenletView?.editable = self.editable
	}

	override public func createPageLoadInteractor(
			page page: Int,
			computeRowCount: Bool) -> BaseListPageLoadInteractor {
		let interactor = CommentListPageLoadInteractor(
				screenlet: self,
				page: page,
				computeRowCount: computeRowCount,
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

	public func screenlet(
			screenlet: CommentDisplayScreenlet,
			onCommentDeleted comment: Comment) {
		deleteComment(comment)
		commentListDelegate?.screenlet?(self, onDeletedComment: comment)
	}

	public func screenlet(
			screenlet: CommentDisplayScreenlet,
			onDeleteComment comment: Comment,
			onError error: NSError) {
		commentListDelegate?.screenlet?(self, onCommentDelete: comment, onError: error)
	}

	public func screenlet(
			screenlet: CommentDisplayScreenlet,
			onCommentUpdated comment: Comment) {
		updateComment(comment)
		commentListDelegate?.screenlet?(self, onUpdatedComment: comment)
	}

	public func screenlet(
			screenlet: CommentDisplayScreenlet,
			onUpdateComment comment: Comment,
			onError error: NSError) {
		commentListDelegate?.screenlet?(self, onCommentUpdate: comment, onError: error)
	}
	
}
