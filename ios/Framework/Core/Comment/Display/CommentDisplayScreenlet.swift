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

	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onCommentLoaded comment: Comment)

	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onLoadCommentError error: NSError)

	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onCommentDeleted comment: Comment)

	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onDeleteComment comment: Comment,
			onError error: NSError)

	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onCommentUpdated comment: Comment)

	optional func screenlet(screenlet: CommentDisplayScreenlet,
			onUpdateComment comment: Comment,
			onError error: NSError)

}


@IBDesignable public class CommentDisplayScreenlet: BaseScreenlet {

	public static let DeleteAction = "deleteAction"
	public static let UpdateAction = "updateAction"

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var commentId: Int64 = 0
	@IBInspectable public var className: String = ""
	@IBInspectable public var classPK: Int64 = 0
	@IBInspectable public var autoLoad: Bool = true
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

	public func load() {
		performDefaultAction()
	}

	public func deleteComment() {
		performAction(name: CommentDisplayScreenlet.DeleteAction)
	}

	public func editComment() {
		viewModel.editComment()
	}


	//MARK: BaseScreenlet

	public override func onCreated() {
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
			return createCommentUpdateInteractor(sender as? String)
		default:
			return nil
		}
	}


	//MARK: Private methods

	private func createCommentLoadInteractor() -> Interactor {
		let interactor = CommentLoadInteractor(
			screenlet: self,
			groupId: self.groupId,
			commentId: self.commentId)

		interactor.onSuccess = {
			if let resultComment = interactor.resultComment {
				self.comment = resultComment
				self.viewModel.comment = resultComment
				self.commentDisplayDelegate?.screenlet?(self, onCommentLoaded: resultComment)
			}
			else {
				self.commentDisplayDelegate?.screenlet?(self,
					onLoadCommentError: NSError.errorWithCause(.InvalidServerResponse))
			}
		}

		interactor.onFailure = {
			self.commentDisplayDelegate?.screenlet?(self, onLoadCommentError: $0)
		}

		return interactor
	}

	private func createCommentDeleteInteractor() -> Interactor {
		let interactor = CommentDeleteInteractor(
			screenlet: self,
			commentId: self.commentId)

		interactor.onSuccess = {
			self.commentDisplayDelegate?.screenlet?(self, onCommentDeleted: self.comment!)
		}

		interactor.onFailure = {
			self.commentDisplayDelegate?.screenlet?(self, onDeleteComment: self.comment!, onError: $0)
		}

		return interactor
	}

	private func createCommentUpdateInteractor(body: String?) -> Interactor {
		let interactor = CommentUpdateInteractor(
			screenlet: self,
			groupId: groupId,
			className: className,
			classPK: classPK,
			commentId: commentId,
			body: body)

		interactor.onSuccess = {
			if let resultComment = interactor.resultComment {
				self.comment = resultComment
				self.viewModel.comment = resultComment
				self.commentDisplayDelegate?.screenlet?(self, onCommentUpdated: resultComment)
			}
			else {
				self.commentDisplayDelegate?.screenlet?(self,
					onUpdateComment: self.comment!,
					onError: NSError.errorWithCause(.InvalidServerResponse))
			}
		}

		interactor.onFailure = {
			self.commentDisplayDelegate?.screenlet?(self, onLoadCommentError: $0)
		}

		return interactor

	}
}
