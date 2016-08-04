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

}


@IBDesignable public class CommentAddScreenlet: BaseScreenlet {

	@IBInspectable public var groupId: Int64 = 0

	@IBInspectable public var className: String = ""

	@IBInspectable public var classPK: Int64 = 0

	public var commentAddDelegate: CommentAddScreenletDelegate? {
		return delegate as? CommentAddScreenletDelegate
	}

	public var viewModel: CommentAddViewModel? {
		return screenletView as? CommentAddViewModel
	}
	
}
