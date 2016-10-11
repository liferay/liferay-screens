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

class CommentDisplayScreenletViewController: UIViewController, CommentDisplayScreenletDelegate {

	@IBOutlet weak var screenlet: CommentDisplayScreenlet?
	@IBOutlet weak var commentIdText: UITextField?

	override func viewDidLoad() {
		self.screenlet?.delegate = self
	}

	@IBAction func loadComment(sender: AnyObject) {
		if let commentId = Int(commentIdText?.text ?? "") {
			self.screenlet?.commentId = Int64(commentId)
			self.screenlet?.load()
		}
	}

	@IBAction func deleteComment(sender: AnyObject) {
		self.screenlet?.deleteComment()
	}

	func screenlet(
			screenlet: CommentDisplayScreenlet,
			onCommentLoaded comment: Comment) {
		print("DELEGATE: onCommentLoaded called -> \(comment)\n");
	}

	func screenlet(
			screenlet: CommentDisplayScreenlet,
			onLoadCommentError error: NSError) {
		print("DELEGATE: onLoadCommentError called -> \(error)\n");
	}

	func screenlet(
			screenlet: CommentDisplayScreenlet,
			onCommentDeleted comment: Comment) {
		print("DELEGATE: onCommentDeleted called -> \(comment)\n");
	}

	func screenlet(
			screenlet: CommentDisplayScreenlet,
			onDeleteComment comment: Comment,
			onError error: NSError) {
		print("DELEGATE: onDeleteComment onError called -> \(comment), \(error)\n");
	}

	func screenlet(
			screenlet: CommentDisplayScreenlet,
			onCommentUpdated comment: Comment) {
		print("DELEGATE: onCommentUpdated called -> \(comment)\n");
	}
	
	func screenlet(
			screenlet: CommentDisplayScreenlet,
			onUpdateComment comment: Comment,
			onError error: NSError) {
		print("DELEGATE: onUpdateComment onError called -> \(comment), \(error)\n");
	}

}
