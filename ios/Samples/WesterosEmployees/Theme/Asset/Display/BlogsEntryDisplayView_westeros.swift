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

public class BlogsEntryDisplayView_westeros: BlogsEntryDisplayView_default {

	@IBOutlet weak var ratingScreenlet: RatingScreenlet?
	
	public override func loadBlog() {
		super.loadBlog()
		ratingScreenlet?.classPK = blogsEntry!.classPK
		ratingScreenlet?.className = AssetClasses.getClassName(AssetClassNameKey_BlogsEntry)!
		ratingScreenlet?.loadRatings()
	}

	public override class func defaultAttributedTextAttributes() -> [String: NSObject] {
		let paragrahpStyle = NSMutableParagraphStyle()
		paragrahpStyle.lineBreakMode = .ByWordWrapping

		var attributes: [String: NSObject] = [NSParagraphStyleAttributeName: paragrahpStyle]

		let font = UIFont(name: "HelveticaNeue", size: 17)

		if let font = font {
			attributes[NSFontAttributeName] = font
			attributes[NSForegroundColorAttributeName] = UIColor.whiteColor()
		}

		return attributes
	}
}
