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

public enum CommentDisplayState_default {
	case Normal
	case Deleting
	case Editing
}

public class CommentDisplayView_default: BaseScreenletView, CommentDisplayViewModel {

	//Left/right UILabel padding
	private static let LabelPadding: CGFloat = 16

	//This fixed height equals the sum of UserPortraitScreenlet height, plus UILabel insets,
	//plus margin between user portrait - text view, plus one pixel for rounding
	private static let FixedHeight: CGFloat = 50 + 8 + 8 + 8 + 1

	//Top/bottom UILabel insets
	private static let LabelInsets: CGFloat = 16

	public class func heightForText(text: String?, width: CGFloat) -> CGFloat {
		let realWidth = width - LabelPadding

		let attributedText = text?.toHtmlTextWithAttributes(attributedTextAttributes)

		if let attributedText = attributedText {
			let rect = attributedText.boundingRectWithSize(
				CGSizeMake(realWidth, CGFloat.max),
				options: [.UsesLineFragmentOrigin, .UsesFontLeading],
				context: nil)

			return rect.height + FixedHeight + LabelInsets
		}

		return 110
	}

	public static var attributedTextAttributes: [String: NSObject] {
		let paragrahpStyle = NSMutableParagraphStyle()
		paragrahpStyle.lineBreakMode = .ByWordWrapping

		var attributes: [String: NSObject] = [NSParagraphStyleAttributeName: paragrahpStyle]

		let font = UIFont(name: "HelveticaNeue", size: 17)

		if let font = font {
			attributes[NSFontAttributeName] = font
		}

		return attributes
	}

	public func editComment() {
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

	@IBOutlet weak var userPortraitScreenlet: UserPortraitScreenlet?
	@IBOutlet weak var userNameLabel: UILabel?
	@IBOutlet weak var createdDateLabel: UILabel?
	@IBOutlet weak var editedLabel: UILabel?
	@IBOutlet weak var bodyLabel: UILabel?
	@IBOutlet weak var bodyLabelBottomMarginConstraint: NSLayoutConstraint?
	@IBOutlet weak var normalStateButtonsContainer: UIView?
	@IBOutlet weak var deletingStateButtonsContainer: UIView?
	@IBOutlet weak var deleteButton: UIButton?
	@IBOutlet weak var editButton: UIButton?

	var editViewController: CommentEditViewController_default?

	private var state: CommentDisplayState_default = .Normal

	public override var editable: Bool {
		didSet {
			normalStateButtonsContainer?.hidden = !editable
		}
	}

	public var comment: Comment? {
		didSet {
			changeState(.Normal)

			if let comment = comment {
				bodyLabel?.attributedText = comment.htmlBody.toHtmlTextWithAttributes(
					CommentDisplayView_default.attributedTextAttributes)

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

	//MARK: Public methods

	public func changeState(state: CommentDisplayState_default) {
		self.state = state
		normalStateButtonsContainer?.hidden = state != .Normal || !editable
		deletingStateButtonsContainer?.hidden = state != .Deleting || !editable

		if state == .Editing {
			editViewController = CommentEditViewController_default(body: comment?.plainBody)
			editViewController?.modalPresentationStyle = .OverCurrentContext
			editViewController!.confirmBodyClosure = confirmBodyClosure

			if let vc = self.presentingViewController {
				vc.presentViewController(editViewController!, animated: true, completion: {})
			}
			else {
				print("ERROR: You neet to set the presentingViewController before editing comments")
			}
		}
	}

	public func confirmBodyClosure(body: String?) {
		editViewController?.dismissViewControllerAnimated(true, completion: nil)

		if let updatedBody = body where updatedBody != comment?.plainBody {
			userAction(name: CommentDisplayScreenlet.UpdateAction, sender: updatedBody)
		}
	}

	//MARK: View actions

	@IBAction func deleteButtonClicked() {
		changeState(.Deleting)
	}

	@IBAction func editButtonClicked() {
		editComment()
	}

	@IBAction func cancelDeletionButtonClicked() {
		changeState(.Normal)
	}

	@IBAction func confirmDeletionButtonClicked() {
		userAction(name: CommentDisplayScreenlet.DeleteAction)
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
}
