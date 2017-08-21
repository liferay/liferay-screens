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

class DocumentationViewController: CardViewController, PortletDisplayScreenletDelegate {

	var selectedFileEntry: String?

	var loaded: Bool = false

	//MARK: Outlets
    
    @IBOutlet weak var portletDisplayScreenlet: PortletDisplayScreenlet!

	//MARK: Init methods

	convenience init() {
		self.init(nibName: "DocumentationViewController", bundle: nil)
	}

    func loadPortletScreenlet() {
        let portletConfiguration = PortletConfiguration.Builder(portletUrl: "/web/westeros-hybrid/documents").addCss(localFile: "docs").addJs(localFile: "docs").load()
        portletDisplayScreenlet.configuration = portletConfiguration
        portletDisplayScreenlet.load()
        portletDisplayScreenlet.delegate = self
    }
    
    //MARK: CardViewController
    override func pageWillAppear() {
        if !loaded {
            loadPortletScreenlet()
            loaded = true
        }
    }

    //MARK: PortletScreenletDelegate
    func screenlet(_ screenlet: PortletDisplayScreenlet,
                   onScriptMessageNamespace namespace: String,
                   onScriptMessage message: String) {
        selectedFileEntry = message
        cardView?.moveRight()
    }
    
}
