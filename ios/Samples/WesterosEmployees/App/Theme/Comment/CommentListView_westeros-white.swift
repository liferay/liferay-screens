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


public class CommentListView_westeros_white: CommentListView_westeros {

	//MARK: BaseListTableView

	override public func doRegisterCellNibs() {
		let nib = NSBundle.nibInBundles(
			name: "CommentTableViewCell_westeros-white", currentClass: self.dynamicType)

		if let commentNib = nib {
			tableView?.registerNib(commentNib, forCellReuseIdentifier: CommentCellId)
		}
	}
}
