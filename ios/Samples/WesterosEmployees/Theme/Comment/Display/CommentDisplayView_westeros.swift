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


open class CommentDisplayView_westeros: CommentDisplayView_default {

	open override func createProgressPresenter() -> ProgressPresenter {
		return WesterosCardProgressPresenter(screenlet: self.screenlet)
	}

	override open class func defaultAttributedTextAttributes() -> [NSAttributedStringKey: NSObject] {
		let paragrahpStyle = NSMutableParagraphStyle()
		paragrahpStyle.lineBreakMode = .byWordWrapping

		var attributes: [NSAttributedStringKey: NSObject] = [.paragraphStyle: paragrahpStyle]

		let font = UIFont(name: "HelveticaNeue", size: 17)
 
		if let font = font {
			attributes[.foregroundColor] = UIColor.white
			attributes[.font] = font
		}

		return attributes
	}
}
