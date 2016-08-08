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

	@IBOutlet weak var userPortraitScreenlet: UserPortraitScreenlet?
	@IBOutlet weak var userNameLabel: UILabel?
	@IBOutlet weak var createdDateLabel: UILabel?
	@IBOutlet weak var editedLabel: UILabel?
	@IBOutlet weak var bodyLabel: UILabel?
	@IBOutlet weak var bodyLabelBottomMarginConstraint: NSLayoutConstraint?

	public var comment: Comment? {
		didSet {
			if let comment = comment {
				bodyLabel?.text = comment.body
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
		if let label = bodyLabel {
			label.sizeToFit()
			let height = label.frame.height
			let y = label.frame.origin.y
			let margin: CGFloat = bodyLabelBottomMarginConstraint?.constant ?? 0
			return  height + y + margin
		}

		return 0
	}

	//MARK: BaseScreenletView

	override public func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}
}
