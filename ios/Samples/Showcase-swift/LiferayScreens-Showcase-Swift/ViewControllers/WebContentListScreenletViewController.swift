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

	@IBOutlet weak var screenlet: WebContentListScreenlet!

    override func viewDidLoad() {
        super.viewDidLoad()

		self.screenlet.delegate = self
    }

	func screenlet(screenlet: WebContentListScreenlet,
			onWebContentListResponse entries: [WebContent]) {
		print("onWebContentListResponse (\(entries.count)):")
		for e in entries {
			print("    -> \(e.debugDescription)")
		}
	}

	func screenlet(screenlet: WebContentListScreenlet,
		   onWebContentListError error: NSError) {
		print("onWebContentListError: \(error)")
	}

	func screenlet(screenlet: WebContentListScreenlet,
		   onWebContentSelected entry: WebContent) {
		print("onWebContentSelected \(entry.debugDescription)")
	}

}
