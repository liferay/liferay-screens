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

	@IBOutlet weak var listScreenlet: CommentListScreenlet?

	var editViewController: CommentEditViewController_default?

	override func viewDidLoad() {
		super.viewDidLoad()

		self.definesPresentationContext = true

		self.listScreenlet?.delegate = self
		self.listScreenlet?.presentingViewController = self
		self.listScreenlet?.loadList()
	}

	@IBAction func insertButtonPressed(sender: AnyObject) {
		if editViewController == nil {
			editViewController = CommentEditViewController_default(body: "")
			editViewController!.modalPresentationStyle = .OverCurrentContext
			editViewController!.confirmButton?.titleLabel?.text = "Add comment"
			editViewController!.confirmBodyClosure = addComment
			presentViewController(editViewController!, animated: true, completion: nil)
		}
	}

	//MARK: CommentListScreenletDelegate

	func screenlet(screenlet: CommentListScreenlet, onCommentListError error: NSError) {
		print("DELEGATE: onCommentListError called -> \(error)\n")
	}

	func screenlet(screenlet: CommentListScreenlet, onSelectedComment comment: Comment) {
		print("DELEGATE: onCommentSelected called -> \(comment)\n")
	}

	func screenlet(screenlet: CommentListScreenlet, onListResponseComments comments: [Comment]) {
		print("DELEGATE: onCommentListResponse called -> \(comments)\n")
	}

	func screenlet(screenlet: CommentListScreenlet, onDeletedComment comment: Comment) {
		print("DELEGATE: onDeletedComment called -> \(comment)\n")
	}

	func screenlet(screenlet: CommentListScreenlet, onCommentDelete comment: Comment,
			onError error: NSError) {
		print("DELEGATE: onCommentDelete onError called -> \(comment) \(error)\n")
	}

	func screenlet(screenlet: CommentListScreenlet, onUpdatedComment comment: Comment) {
		print("DELEGATE: onUpdatedComment called -> \(comment)\n")
	}

	func screenlet(screenlet: CommentListScreenlet, onCommentUpdate comment: Comment,
			onError error: NSError) {
		print("DELEGATE: onCommentUpdate onError called -> \(comment) \(error)\n")
	}

	//MARK: Private methods

	private func addComment(body: String?) {
		editViewController?.dismissViewControllerAnimated(true) {
			self.editViewController = nil
		}

		if let newCommentBody = body, screenlet = listScreenlet {
			let interactor = CommentAddInteractor(
				screenlet: nil,
				groupId: screenlet.groupId,
				className: screenlet.className,
				classPK: screenlet.classPK,
				body: newCommentBody)

			interactor.onSuccess = {
				if let comment = interactor.resultComment {
					screenlet.addComment(comment)
				}
			}

			interactor.onFailure = {
				print("ERROR: adding comment \($0)")
			}

			interactor.start()
		}
	}

}
