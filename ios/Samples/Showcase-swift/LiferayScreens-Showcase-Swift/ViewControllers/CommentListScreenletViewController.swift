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


class CommentListScreenletViewController: UIViewController,
	CommentListScreenletDelegate, CommentAddScreenletDelegate,
	CommentDisplayScreenletDelegate {

	@IBOutlet weak var listScreenlet: CommentListScreenlet?
	@IBOutlet weak var addScreenlet: CommentAddScreenlet?
	@IBOutlet weak var displayScreenlet: CommentDisplayScreenlet?

	@IBOutlet weak var displayScreenletHeightConstraint: NSLayoutConstraint?

	override func viewDidLoad() {
		super.viewDidLoad()

		self.listScreenlet?.delegate = self
		self.addScreenlet?.delegate = self
		self.displayScreenlet?.delegate = self

		self.displayScreenlet?.layer.cornerRadius = 4
	}

	override func viewDidLayoutSubviews() {
		super.viewDidLayoutSubviews()
		recalculateDisplayScreenletHeightConstraint()
	}
	
	//MARK: CommentAddScreenletDelegate

	func screenlet(screenlet: CommentAddScreenlet, onCommentAdded comment: Comment) {
		print("DELEGATE: onCommentAdded called -> \(comment)\n")
		self.listScreenlet?.addComment(comment)
	}

	func screenlet(screenlet: CommentAddScreenlet, onAddCommentError error: NSError) {
		print("DELEGATE: onAddCommentError called -> \(error)\n")
	}

	//MARK: CommentListScreenletDelegate

	func screenlet(screenlet: CommentListScreenlet, onCommentListError error: NSError) {
		print("DELEGATE: onCommentListError called -> \(error)\n")
	}

	func screenlet(screenlet: CommentListScreenlet, onSelectedComment comment: Comment) {
		print("DELEGATE: onCommentSelected called -> \(comment)\n")
		self.displayScreenletHeightConstraint?.constant = 115
		self.displayScreenlet?.commentId = comment.commentId
		self.displayScreenlet?.load()
	}

	func screenlet(screenlet: CommentListScreenlet, onListResponseComments comments: [Comment]) {
		print("DELEGATE: onCommentListResponse called -> \(comments)\n")
	}

	//MARK: CommentDisplayScreenletDelegate

	func screenlet(screenlet: CommentDisplayScreenlet, onCommentLoaded comment: Comment) {
		print("DELEGATE: onCommentLoaded called -> \(comment)\n")
	}

	func screenlet(screenlet: CommentDisplayScreenlet, onLoadCommentError error: NSError) {
		print("DELEGATE: onLoadCommentError called -> \(error)\n")
	}

	func screenlet(screenlet: CommentDisplayScreenlet, onCommentDeleted comment: Comment?) {
		print("DELEGATE: onCommentDeleted called -> \(comment)\n")
		if let comment = comment {
			self.listScreenlet?.deleteComment(comment)
		}
	}

	func screenlet(screenlet: CommentDisplayScreenlet, onDeleteComment comment: Comment?,
	               onError error: NSError) {
		print("DELEGATE: onDeleteComment onError called -> \(comment) \(error)\n")
	}

	func screenlet(screenlet: CommentDisplayScreenlet, onCommentUpdated comment: Comment?) {
		print("DELEGATE: onCommentUpdated called -> \(comment)\n")
		if let comment = comment {
			self.listScreenlet?.updateComment(comment)
		}
	}

	func screenlet(screenlet: CommentDisplayScreenlet, onUpdateComment comment: Comment?, onError error: NSError) {
		print("DELEGATE: onUpdateComment onError called -> \(comment) \(error)\n")
	}

	//MARK: Private methods

	private func recalculateDisplayScreenletHeightConstraint() {
		let computedHeight = self.displayScreenlet?.computedHeight
		self.displayScreenletHeightConstraint?.constant = computedHeight ?? 115
	}

}
