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
		CommentListScreenletDelegate {

	// MARK: Outlets

	@IBOutlet weak var listScreenlet: CommentListScreenlet? {
		didSet {
			listScreenlet?.delegate = self
			listScreenlet?.presentingViewController = self
			listScreenlet?.className =
				LiferayServerContext.stringPropertyForKey("commentScreenletClassName")
			listScreenlet?.classPK =
				LiferayServerContext.longPropertyForKey("commentScreenletClassPK")
		}
	}

	var editViewController: CommentEditViewController_default?

	// MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		self.definesPresentationContext = true
	}

	// MARK: Actions

	@IBAction func insertButtonPressed(_ sender: AnyObject) {
		if editViewController == nil {
			editViewController = CommentEditViewController_default(body: "")
			editViewController!.modalPresentationStyle = .overCurrentContext
			editViewController!.confirmButton?.titleLabel?.text = "Add comment"
			editViewController!.confirmBodyClosure = addComment
			present(editViewController!, animated: true, completion: nil)
		}
	}

	// MARK: CommentListScreenletDelegate

	func screenlet(_ screenlet: CommentListScreenlet, onCommentListError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(_ screenlet: CommentListScreenlet, onSelectedComment comment: Comment) {
		LiferayLogger.logDelegateMessage(args: comment)
	}

	func screenlet(_ screenlet: CommentListScreenlet, onListResponseComments comments: [Comment]) {
		LiferayLogger.logDelegateMessage(args: comments as AnyObject?)
	}

	func screenlet(_ screenlet: CommentListScreenlet, onDeletedComment comment: Comment) {
		LiferayLogger.logDelegateMessage(args: comment)
	}

	func screenlet(_ screenlet: CommentListScreenlet, onCommentDelete comment: Comment,
	               onError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(_ screenlet: CommentListScreenlet, onUpdatedComment comment: Comment) {
		LiferayLogger.logDelegateMessage(args: comment)
	}

	func screenlet(_ screenlet: CommentListScreenlet, onCommentUpdate comment: Comment,
	               onError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	// MARK: Private methods

	fileprivate func addComment(_ body: String?) {
		editViewController?.dismiss(animated: true) {
			self.editViewController = nil
		}

		if let newCommentBody = body, let screenlet = listScreenlet {
			let interactor = CommentAddInteractor(
				className: screenlet.className,
				classPK: screenlet.classPK,
				body: newCommentBody)

			interactor.onSuccess = {
				if let comment = interactor.resultComment {
					screenlet.addComment(comment)
				}
			}

			interactor.onFailure = {
				LiferayLogger.logDelegateMessage(args: $0)
			}

			_ = interactor.start()
		}
	}

}
