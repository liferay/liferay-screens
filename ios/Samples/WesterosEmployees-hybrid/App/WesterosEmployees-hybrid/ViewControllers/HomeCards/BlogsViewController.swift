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

class BlogsViewController: CardViewController, PortletDisplayScreenletDelegate {

	var selectedBlogEntry:String?

	var loaded: Bool = false
	
    @IBOutlet weak var portletDisplayScreenlet: PortletDisplayScreenlet!

	//MARK: Init methods

	convenience init() {
		self.init(nibName: "BlogsViewController", bundle: nil)
	}
    
    func portletScreenlet() {
        let portletConfiguration = PortletConfiguration.Builder(portletUrl: "/web/guest/companynews").addCss(localFile: "blogs").addJs(localFile: "blogs").load()
        portletDisplayScreenlet.configuration = portletConfiguration
        portletDisplayScreenlet.load()
        portletDisplayScreenlet.delegate = self
    }


	//MARK: CardViewController
	override func pageWillAppear() {
		if !loaded {
			portletScreenlet()
			loaded = true
		}
	}


	//MARK: PortletScreenletDelegate
    func screenlet(_ screenlet: PortletDisplayScreenlet,
                   onScriptMessageHandler key: String,
                   onScriptMessageBody body: Any) {
        selectedBlogEntry = body as? String
        cardView?.moveRight()
	}

}
