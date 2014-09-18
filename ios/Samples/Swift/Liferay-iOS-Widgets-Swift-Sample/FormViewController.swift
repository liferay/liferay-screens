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

	public func onFormLoaded(record: DDLRecord) {
		widget!.becomeFirstResponder()
	}

	public func onFormLoadError(error: NSError) {
		println("Error with form -> " + error.description)
	}

	public func onRecordLoaded(record: DDLRecord) {
	}

	public func onRecordLoadError(error: NSError) {
	}

	public func onFormSubmitted(record: DDLRecord) {
	}

	public func onFormSubmitError(error: NSError) {
	}

	public func onDocumentUploadStarted(field:DDLFieldDocument) {
	}

	public func onDocumentUploadedBytes(field:DDLFieldDocument,
			bytes: UInt,
			sent: Int64,
			total: Int64) {
	}

	public func onDocumentUploadCompleted(field:DDLFieldDocument, result:[String:AnyObject]) {
	}

	public func onDocumentUploadError(field:DDLFieldDocument, error: NSError) {
	}


	@IBAction internal func buttonClick(sender: AnyObject) {
		LiferayContext.instance.createSession("jose.navarro@liferay.com", password: "jm")

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
