//
//  WebContentViewController.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 08/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit


public class WebContentViewController: UIViewController, WebContentWidgetDelegate {

	@IBOutlet private weak var widget: WebContentWidget?

	override public func viewDidLoad() {
		widget!.delegate = self
	}

	@IBAction func loadButtonAction(sender: AnyObject) {
		SessionContext.createSession(
				username: "jose.navarro@liferay.com",
				password: "jm",
				userAttributes: ["userId": 10198])
		widget!.loadWebContent()
	}

}
