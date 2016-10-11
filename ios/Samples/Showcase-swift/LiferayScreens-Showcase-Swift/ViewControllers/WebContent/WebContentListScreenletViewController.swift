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

class WebContentListScreenletViewController: UIViewController, WebContentListScreenletDelegate {

	
	//MARK: IBOutlet
	
	@IBOutlet weak var screenlet: WebContentListScreenlet! {
		didSet {
			screenlet.delegate = self
		}
	}
	
	var selectedArticleId = ""


	//MARK: WebContentListScreenletDelegate

	func screenlet(screenlet: WebContentListScreenlet,
			onWebContentListResponse entries: [WebContent]) {
		LiferayLogger.logDelegateMessage(args: entries)
	}

	func screenlet(screenlet: WebContentListScreenlet, onWebContentListError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(screenlet: WebContentListScreenlet, onWebContentSelected entry: WebContent) {
		LiferayLogger.logDelegateMessage(args: entry)
		selectedArticleId = entry.attributes["articleId"] as! String
		performSegueWithIdentifier("WebContentDisplay", sender: self)
	}
	
	
	//MARK: UIViewController
	
	override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
		if let vc = segue.destinationViewController as? WebContentDisplayScreenletViewController {
			vc.articleId = selectedArticleId
		}
		
	}

}
