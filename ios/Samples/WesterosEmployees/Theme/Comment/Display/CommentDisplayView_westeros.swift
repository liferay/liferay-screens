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


public class CommentDisplayView_westeros: CommentDisplayView_default {

	public override func createProgressPresenter() -> ProgressPresenter {
		return WesterosCardProgressPresenter(screenlet: self.screenlet)
	}

	override public class func defaultAttributedTextAttributes() -> [String: NSObject] {
		let paragrahpStyle = NSMutableParagraphStyle()
		paragrahpStyle.lineBreakMode = .ByWordWrapping

		var attributes: [String: NSObject] = [NSParagraphStyleAttributeName: paragrahpStyle]

		let font = UIFont(name: "HelveticaNeue", size: 17)
 
		if let font = font {
			attributes[NSForegroundColorAttributeName] = UIColor.whiteColor()
			attributes[NSFontAttributeName] = font
		}

		return attributes
	}
}
