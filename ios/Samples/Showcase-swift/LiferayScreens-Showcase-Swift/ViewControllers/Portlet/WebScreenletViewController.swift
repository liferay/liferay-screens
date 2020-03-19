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

class WebScreenletViewController: UIViewController, WebScreenletDelegate {

	// MARK: Outlets

	@IBOutlet var screenlet: WebScreenlet? {
		didSet {
			screenlet?.delegate = self
			screenlet?.presentingViewController = self
		}
	}

	// MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		let url = "/web/guest"

		let configuration = WebScreenletConfigurationBuilder(url: url)
				.load()

		screenlet?.configuration = configuration

		screenlet?.load()
	}

	// MARK: WebScreenletDelegate

    func onWebLoad(_ screenlet: WebScreenlet, url: String) {

		
    }

	func screenlet(_ screenlet: WebScreenlet, onError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(_ screenlet: WebScreenlet, onScriptMessageNamespace namespace: String, onScriptMessage message: String) {

		//We can check what is the name of the message handler responsible for the action
		performSegue(withIdentifier: "detail", sender: message)
	}

	override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
		let vc = segue.destination as? WebDetailViewController
		vc?.url = "http://" + (sender as! String)
	}
}
