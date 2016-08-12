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


public class CommentDisplayView_default: BaseScreenletView, CommentDisplayViewModel {

	private enum CommentDisplayState {
		case Normal
		case Deleting
		case Editing
	}

	@IBOutlet weak var userPortraitScreenlet: UserPortraitScreenlet?
	@IBOutlet weak var userNameLabel: UILabel?
	@IBOutlet weak var createdDateLabel: UILabel?
	@IBOutlet weak var editedLabel: UILabel?
	@IBOutlet weak var bodyTextView: UITextView?
	@IBOutlet weak var bodyTextViewBottomMarginConstraint: NSLayoutConstraint?
	@IBOutlet weak var normalStateButtonsContainer: UIView?
	@IBOutlet weak var deletingStateButtonsContainer: UIView?
	@IBOutlet weak var editingStateButtonsContainer: UIView?
	@IBOutlet weak var deleteButton: UIButton?
	@IBOutlet weak var editButton: UIButton?

	private var state: CommentDisplayState = .Normal

	public override var editable: Bool {
		didSet {
			normalStateButtonsContainer?.hidden = !editable
		}
	}

	public var comment: Comment? {
		didSet {
			changeState(.Normal)

			if let comment = comment {
				//TODO change to attributed text
				bodyTextView?.text = comment.plainBody

				deleteButton?.enabled = comment.canDelete
				
				editButton?.enabled = comment.canEdit

				let loadedUserId = userPortraitScreenlet?.userId
				if loadedUserId == nil || loadedUserId != comment.userId {
					userPortraitScreenlet?.load(userId: comment.userId)
				}
				userNameLabel?.text = comment.userName
				createdDateLabel?.text = comment.createDate.timeAgo
				editedLabel?.hidden = comment.createDate.equalToDate(comment.modifiedDate)
			}
		}
	}

	public var computedHeight: CGFloat {
		if let label = bodyTextView {
			label.sizeToFit()
			let height = label.frame.height
			let y = label.frame.origin.y
			let margin: CGFloat = bodyTextViewBottomMarginConstraint?.constant ?? 0
			return  height + y + margin
		}

		return 0
	}

	//MARK: View actions

	@IBAction func deleteButtonClicked() {
		changeState(.Deleting)
	}

	@IBAction func editButtonClicked() {
		if let viewController = self.presentingViewController, editedComment = self.comment
				where editedComment.isStyled {
			let alertController = UIAlertController(
				title: LocalizedString("default", key: "comment-display-warning", obj: self),
				message: LocalizedString("default", key: "comment-display-styled", obj: self),
				preferredStyle: UIAlertControllerStyle.Alert)

			let dismissAction = UIAlertAction(
					title: LocalizedString("default", key: "comment-display-dismiss", obj: self),
					style: UIAlertActionStyle.Default) { _ in
				self.changeState(.Editing)
			}
			alertController.addAction(dismissAction)

			viewController.presentViewController(alertController, animated: true, completion: nil)
		} else {
			changeState(.Editing)
		}
	}

	@IBAction func cancelButtonClicked() {
		changeState(.Normal)
	}

	@IBAction func confirmButtonClicked() {
		if self.state == .Deleting {
			userAction(name: CommentDisplayScreenlet.DeleteAction)
		} else if self.state == .Editing {
			userAction(name: CommentDisplayScreenlet.UpdateAction,
			                         sender: bodyTextView?.text)
		}

		changeState(.Normal)
	}

	//MARK: BaseScreenletView

	override public func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}

	public override var progressMessages: [String : ProgressMessages] {
		return [
			CommentDisplayScreenlet.DeleteAction: [.Working: NoProgressMessage],
			CommentDisplayScreenlet.UpdateAction: [.Working: NoProgressMessage]
		]
	}

	//MARK: UITextFieldDelegate

	public override func textFieldShouldReturn(textField: UITextField) -> Bool {
		confirmButtonClicked()
		return super.textFieldShouldReturn(textField)
	}

	//MARK: Private methods

	private func changeState(state: CommentDisplayState) {
		self.state = state
		normalStateButtonsContainer?.hidden = state != .Normal || !editable
		deletingStateButtonsContainer?.hidden = state != .Deleting || !editable

		if state == .Editing {
			editingStateButtonsContainer?.hidden = false
			bodyTextView?.editable = true
			bodyTextView?.becomeFirstResponder()
		} else {
			editingStateButtonsContainer?.hidden = true
			bodyTextView?.editable = false
		}
	}

	private func stringToAttributedText(string: String) -> NSAttributedString {
		let encodedData = string.dataUsingEncoding(NSUTF8StringEncoding)!

		var attributes : [String: AnyObject] = [
			NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType,
			NSCharacterEncodingDocumentAttribute: NSUTF8StringEncoding,
		]

		return try! NSAttributedString(data: encodedData, options: attributes, documentAttributes: nil)
	}
}
