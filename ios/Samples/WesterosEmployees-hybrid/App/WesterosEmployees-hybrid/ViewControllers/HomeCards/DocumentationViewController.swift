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

class DocumentationViewController: CardViewController, WebScreenletDelegate {

	// MARK: Outlets

	@IBOutlet weak var webScreenlet: WebScreenlet? {
		didSet {
			let webScreenletConfiguration = WebScreenletConfigurationBuilder(url: "/web/westeros-hybrid/documents")
				.addCss(localFile: "docs")
				.addJs(localFile: "docs")
				.load()

			webScreenlet?.backgroundColor = .clear
			webScreenlet?.presentingViewController = self
			webScreenlet?.configuration = webScreenletConfiguration
			webScreenlet?.delegate = self
		}
	}
	
	// MARK: Variables
	
	var selectedFileEntry: String?
	
	var loaded: Bool = false

	// MARK: Initializers

	convenience init() {
		self.init(nibName: "DocumentationViewController", bundle: nil)
	}

	// MARK: CardViewController

	override func pageWillAppear() {
		if !loaded {
			webScreenlet?.load()
			loaded = true
		}
	}

	// MARK: WebScreenletDelegate

	func screenlet(_ screenlet: WebScreenlet,
				   onScriptMessageNamespace namespace: String,
				   onScriptMessage message: String) {
		selectedFileEntry = message
		cardView?.moveRight()
	}
	
	func screenlet(_ screenlet: WebScreenlet, onError error: NSError) {
		print("WebScreenlet error (DocumentationViewController): \(error.debugDescription)")
	}
}
