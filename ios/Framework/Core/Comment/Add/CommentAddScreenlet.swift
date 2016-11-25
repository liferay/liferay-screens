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


@objc public protocol CommentAddScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: CommentAddScreenlet,
			onCommentAdded comment: Comment)

	optional func screenlet(screenlet: CommentAddScreenlet,
			onAddCommentError error: NSError)

	optional func screenlet(screenlet: CommentAddScreenlet,
			onCommentUpdated comment: Comment)

	optional func screenlet(screenlet: CommentAddScreenlet,
			onUpdateCommentError error: NSError)

}


public class CommentAddScreenlet: BaseScreenlet {

	@IBInspectable public var className: String = ""
	@IBInspectable public var classPK: Int64 = 0

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue

	public var commentAddDelegate: CommentAddScreenletDelegate? {
		return delegate as? CommentAddScreenletDelegate
	}

	public var viewModel: CommentAddViewModel {
		return screenletView as! CommentAddViewModel
	}

	public var comment: Comment? {
		didSet {
			if let comment = self.comment {
				viewModel.body = comment.plainBody
			}
		}
	}


	//MARK: BaseScreenlet

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		if comment != nil {
			return createUpdateCommentInteractor()
		}
		return createAddCommentInteractor()
	}


	//MARK: Interactor methods

	func createAddCommentInteractor() -> Interactor {
		let interactor = CommentAddInteractor(screenlet: self, body: self.viewModel.body)

		interactor.cacheStrategy = CacheStrategyType(rawValue: offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			if let resultComment = interactor.resultComment {
				self.commentAddDelegate?.screenlet?(self, onCommentAdded: resultComment)
				self.viewModel.body = ""
			}
			else {
				self.commentAddDelegate?.screenlet?(self, onAddCommentError: NSError.errorWithCause(
						.InvalidServerResponse, message: "Could not add comment."))
			}
		}

		interactor.onFailure = {
			self.commentAddDelegate?.screenlet?(self, onAddCommentError: $0)
		}
		
		return interactor
	}

	func createUpdateCommentInteractor() -> Interactor {
		let interactor = CommentUpdateInteractor(
			commentId: comment!.commentId,
			body: self.viewModel.body)

		interactor.onSuccess = {
			if let resultComment = interactor.resultComment {
				self.commentAddDelegate?.screenlet?(self, onCommentUpdated: resultComment)
				self.viewModel.body = ""
			}
			else {
				self.commentAddDelegate?.screenlet?(self,
				                                    onUpdateCommentError: NSError.errorWithCause(
														.InvalidServerResponse,
														message: "Could not update comment."))
			}
		}

		interactor.onFailure = {
			self.commentAddDelegate?.screenlet?(self, onUpdateCommentError: $0)
		}

		return interactor
	}
}
