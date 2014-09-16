//
//  SignUpViewController.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 18/07/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import Foundation


public class FormViewController: UIViewController, DDLFormWidgetDelegate {

	@IBOutlet var widget: DDLFormWidget?

	override public func viewDidLoad() {
		widget!.delegate = self
	}

	public func onFormLoaded(elements: [DDLElement]) {
		widget!.becomeFirstResponder()
	}

	public func onFormLoadError(error: NSError) {
		println("Error with form -> " + error.description)
	}

	@IBAction internal func buttonClick(sender: AnyObject) {
		LiferayContext.instance().createSession("jose.navarro@liferay.com", password: "jm")

		if widget!.recordId == 0 {
			widget!.loadForm()
		}
		else {
			widget!.loadRecord()
		}
	}

	@IBAction func button2Click(sender: AnyObject) {
		widget!.submitForm()
	}

}
