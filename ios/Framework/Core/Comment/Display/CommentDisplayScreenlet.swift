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


@objc public protocol CommentDisplayScreenletDelegate : BaseScreenletDelegate {

	/// Called when the screenlet loads the comment.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: loaded comment.
	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onCommentLoaded comment: Comment)

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error while loading the comment.
	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onLoadCommentError error: NSError)

	/// Called when the screenlet prepares the comment for deletion.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: comment to be deleted.
	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onCommentDeleted comment: Comment)

	///  Called when a comment is deleted.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: deleted comment.
	///   - error: error while deleting the comment.
	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onDeleteComment comment: Comment,
			onError error: NSError)

	/// Called when the screenlet prepares the comment for update.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: comment to be updated.
	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onCommentUpdated comment: Comment)

	/// Called when a comment is updated.
	///
	/// - Parameters:
	///   - screenlet
	///   - comment: updated comment.
	///   - error: error while updating the comment.
	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onUpdateComment comment: Comment,
			onError error: NSError)

}


public class CommentDisplayScreenlet: BaseScreenlet {

	public static let DeleteAction = "deleteAction"
	public static let UpdateAction = "updateAction"


	//MARK: Inspectables

	@IBInspectable public var commentId: Int64 = 0

	@IBInspectable public var autoLoad: Bool = true

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue

	@IBInspectable public var editable: Bool = false {
		didSet {
			screenletView?.editable = self.editable
		}
	}

	public var commentDisplayDelegate: CommentDisplayScreenletDelegate? {
		return delegate as? CommentDisplayScreenletDelegate
	}

	public var viewModel: CommentDisplayViewModel {
		return screenletView as! CommentDisplayViewModel
	}

	public var comment: Comment? {
		didSet {
			if let comment = comment {
				commentId = comment.commentId
			}
			viewModel.comment = self.comment
		}
	}


	//MARK: Public methods

	/// Loads the comment in the screenlet.
	public func load() {
		performDefaultAction()
	}

	/// Call this method to delete one comment.
	public func deleteComment() {
		performAction(name: CommentDisplayScreenlet.DeleteAction)
	}

	/// Call this method to edit the comment.
	public func editComment() {
		viewModel.editComment()
	}


	//MARK: BaseScreenlet

	override public func onCreated() {
		super.onCreated()
		screenletView?.editable = self.editable
	}

	override public func onShow() {
		if autoLoad {
			load()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		switch name {
			case BaseScreenlet.DefaultAction:
				return createCommentLoadInteractor()
			case CommentDisplayScreenlet.DeleteAction:
				return createCommentDeleteInteractor()
			case CommentDisplayScreenlet.UpdateAction:
				guard let body = sender as? String else {
					return nil
				}
				return createCommentUpdateInteractor(body)
			default:
				return nil
		}
	}


	//MARK: Private methods

	private func createCommentLoadInteractor() -> Interactor {
		let interactor = CommentLoadInteractor(screenlet: self)

		interactor.cacheStrategy = CacheStrategyType(rawValue: offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			if let resultComment = interactor.resultComment {
				self.comment = resultComment
				self.viewModel.comment = resultComment
				self.commentDisplayDelegate?.screenlet?(self, onCommentLoaded: resultComment)
			}
			else {
				self.commentDisplayDelegate?.screenlet?(self,
					onLoadCommentError: NSError.errorWithCause(.InvalidServerResponse,
							message: "Could not load comment."))
			}
		}

		interactor.onFailure = {
			self.commentDisplayDelegate?.screenlet?(self, onLoadCommentError: $0)
		}

		return interactor
	}

	private func createCommentDeleteInteractor() -> Interactor {
		let interactor = CommentDeleteInteractor(screenlet: self)

		interactor.onSuccess = {
			self.commentDisplayDelegate?.screenlet?(self, onCommentDeleted: self.comment!)
		}

		interactor.onFailure = {
			self.commentDisplayDelegate?.screenlet?(self, onDeleteComment: self.comment!, onError: $0)
		}

		return interactor
	}

	private func createCommentUpdateInteractor(body: String) -> Interactor {
		let interactor = CommentUpdateInteractor(screenlet: self, body: body)

		interactor.onSuccess = {
			if let resultComment = interactor.resultComment {
				self.comment = resultComment
				self.viewModel.comment = resultComment
				self.commentDisplayDelegate?.screenlet?(self, onCommentUpdated: resultComment)
			}
			else {
				self.commentDisplayDelegate?.screenlet?(self,
					onUpdateComment: self.comment!,
					onError: NSError.errorWithCause(.InvalidServerResponse,
							message: "Could not update comment."))
			}
		}

		interactor.onFailure = {
			self.commentDisplayDelegate?.screenlet?(self, onLoadCommentError: $0)
		}

		return interactor

	}
}
