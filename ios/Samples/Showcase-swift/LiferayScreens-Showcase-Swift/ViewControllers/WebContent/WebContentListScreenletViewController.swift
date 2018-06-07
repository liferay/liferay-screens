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

	// MARK: Outlets

	@IBOutlet weak var screenlet: WebContentListScreenlet! {
		didSet {
			screenlet.delegate = self
		}
	}

	var selectedArticleId = ""

	// MARK: WebContentListScreenletDelegate

	func screenlet(_ screenlet: WebContentListScreenlet,
			onWebContentListResponse entries: [WebContent]) {
		LiferayLogger.logDelegateMessage(args: entries as AnyObject?)
	}

	func screenlet(_ screenlet: WebContentListScreenlet, onWebContentListError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(_ screenlet: WebContentListScreenlet, onWebContentSelected entry: WebContent) {
		LiferayLogger.logDelegateMessage(args: entry)
		selectedArticleId = entry.attributes["articleId"] as! String
		performSegue(withIdentifier: "WebContentDisplay", sender: self)
	}

	// MARK: UIViewController

	override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
		if let vc = segue.destination as? WebContentDisplayScreenletViewController {
			vc.articleId = selectedArticleId
		}

	}
}
