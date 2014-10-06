//
//  SignUpViewController.swift
//  Liferay-iOS-Screenlets-Swift-Sample
//
//  Created by jmWork on 18/07/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import Foundation


public class FormViewController: UIViewController, DDLFormScreenletDelegate {

	@IBOutlet var screenlet: DDLFormScreenlet?

	override public func viewDidLoad() {
		screenlet!.delegate = self
	}

	public func onFormLoaded(record: DDLRecord) {
		screenlet!.becomeFirstResponder()
	}

	public func onFormLoadError(error: NSError) {
		println("Error with form -> " + error.description)
	}

	public func onRecordLoaded(record: DDLRecord) {
	}

	public func onRecordLoadError(error: NSError) {
		println("Error with record -> " + error.description)
	}

	public func onFormSubmitted(record: DDLRecord) {
	}

	public func onFormSubmitError(error: NSError) {
		println("Error with submit -> " + error.description)
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
	/*
		SessionContext.createSession(
				username: "jose.navarro@liferay.com",
				password: "jm",
				userAttributes: ["userId": 10198])
*/
		if true || screenlet!.recordId == 0 {
			screenlet!.loadForm()
		}
		else {
			screenlet!.loadRecord()
		}
	}

	@IBAction func button2Click(sender: AnyObject) {
		screenlet!.submitForm()
	}

}
