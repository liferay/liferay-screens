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


public class BookmarkListView_default_custom: BookmarkListView_default {

	let BookmarkCellId = "bookmarkCell"


	//MARK: BaseListTableView

    public override func doRegisterCellNibs() {
        let nib = UINib(nibName: "BookmarkCell_default-custom", bundle: NSBundle.mainBundle())
        
        tableView?.registerNib(nib, forCellReuseIdentifier: BookmarkCellId)
    }
    
	override public func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
        if let bookmarkCell = cell as? BookmarkCell_default_custom, bookmark = object as? Bookmark {
            bookmarkCell.bookmark = bookmark
        }
    }

	override public func doGetCellId(row row: Int, object: AnyObject?) -> String {
		return BookmarkCellId
	}
    
    //MARK: UITableViewDelegate
    
    public func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        return 80
    }

}
