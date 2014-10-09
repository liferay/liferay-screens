//
//  WebContentViewController.swift
//  Liferay-iOS-Screenlets-Swift-Sample
//
//  Created by jmWork on 08/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit


public class WebContentViewController: UIViewController, WebContentDisplayScreenletDelegate {

	@IBOutlet private weak var screenlet: WebContentDisplayScreenlet?

	override public func viewDidLoad() {
		screenlet!.delegate = self
	}

	@IBAction func loadButtonAction(sender: AnyObject) {
		SessionContext.createSession(
				username: "jose.navarro@liferay.com",
				password: "jm",
				userAttributes: ["userId": 10198])
		screenlet!.loadWebContent()
	}

}
