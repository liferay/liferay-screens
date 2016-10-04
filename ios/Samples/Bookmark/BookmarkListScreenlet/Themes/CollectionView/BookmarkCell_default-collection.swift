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

public class BookmarkCell_default_collection: UICollectionViewCell {

    
    //MARK: Outlets
    
	@IBOutlet weak var centerLabel: UILabel?

	@IBOutlet weak var urlLabel: UILabel?
    
    
    //MARK: Public properties
    
    public var bookmark: Bookmark? {
        didSet {
            if let bookmark = bookmark, url = NSURL(string: bookmark.url),
                    firstLetter = url.host?.remove("www.").characters.first {
                self.centerLabel?.text = String(firstLetter).uppercaseString
                
                self.urlLabel?.text = bookmark.url.remove("http://").remove("https://").remove("www.")
            }
        }
    }

    
    //MARK: UICollectionViewCell
    
	override public func prepareForReuse() {
		super.prepareForReuse()

		centerLabel?.text = "..."
		urlLabel?.text = "..."
	}
}
