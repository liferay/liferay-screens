//
//  ViewController.swift
//  LiferayScreensAddBookmarkTestApp
//
//  Created by jmWork on 14/12/15.
//  Copyright © 2015 Liferay. All rights reserved.
//

import UIKit
import LiferayScreens

class ViewController: UIViewController {

	@IBOutlet weak var screenlet: AddBookmarkScreenlet!
	
	override func viewDidLoad() {
		super.viewDidLoad()

		SessionContext.createBasicSession(username: "test@liferay.com", password: "test", userAttributes: [:])
	}

}

