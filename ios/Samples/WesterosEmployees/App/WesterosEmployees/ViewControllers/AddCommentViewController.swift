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

class AddCommentViewController: CardViewController, KeyboardListener, CommentAddScreenletDelegate {

	var onCommentAdded: (Comment -> ())?
	var onCommentUpdated: (Comment -> ())?

	
	//MARK: Outlets

	@IBOutlet weak var commentAddScreenlet: CommentAddScreenlet? {
		didSet {
			self.commentAddScreenlet?.delegate = self
		}
	}


	//MARK: Init methods

	convenience init() {
		self.init(nibName: "AddCommentViewController", bundle: nil)
	}


	//MARK: Public methods

	func load(className className: String, classPK: Int64) {
		self.commentAddScreenlet?.className = className
		self.commentAddScreenlet?.classPK = classPK
	}

	
	//MARK: CardViewController

	override func cardWillAppear() {
		registerKeyboardListener(self)
	}

	override func cardWillDisappear() {
		self.cardView?.changeButtonText("Add Comment")
		(self.commentAddScreenlet?.viewModel as? CommentAddView_westeros)?.body = ""
		unregisterKeyboardListener(self)
		self.view.endEditing(true)
	}


	//MARK: KeyboardListener

	func showKeyboard(notif: NSNotification) {
		cardView?.changeToState(.Maximized)
	}

	func hideKeyboard(notif: NSNotification) {
		cardView?.changeToState(.Normal)
	}


	//MARK: CommentAddScreenletDelegate

	func screenlet(screenlet: CommentAddScreenlet, onCommentAdded comment: Comment) {
		onCommentAdded?(comment)
	}

	func screenlet(screenlet: CommentAddScreenlet, onCommentUpdated comment: Comment) {
		onCommentUpdated?(comment)
	}
}
