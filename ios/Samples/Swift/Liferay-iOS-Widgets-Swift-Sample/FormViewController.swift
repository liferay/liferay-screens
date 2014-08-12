//
//  SignUpViewController.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 18/07/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import Foundation

class FormViewController: UIViewController, DDLFormWidgetDelegate {

	@IBOutlet var widget: DDLFormWidget?

	override func viewDidLoad() {
		widget!.delegate = self
	}

	func onFormLoaded(elements: [DDLElement]) {
		widget!.becomeFirstResponder()
	}

	func onFormLoadError(error: NSError) {
		println("Error with form -> " + error.description)
	}

	@IBAction func buttonClick(sender: AnyObject) {
		LiferayContext.instance.createSession("jose.navarro@liferay.com", password: "jm")
		widget!.loadForm()
	}

	@IBAction func button2Click(sender: AnyObject) {
		widget!.submitForm()
	}
}
