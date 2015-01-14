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

class DDLFormScreenletViewController: UIViewController, DDLFormScreenletDelegate {

	@IBOutlet var screenlet: DDLFormScreenlet?
	@IBOutlet var recordIdField: UITextField?

	@IBAction func loadForm(sender: AnyObject) {
		if let recordId = recordIdField?.text.toInt() {
			screenlet?.recordId = Int64(recordId)
			recordIdField?.resignFirstResponder()
			screenlet?.loadRecord()
		}
		else {
			screenlet?.loadForm()
		}
	}

	override func viewDidLoad() {
		super.viewDidLoad()

		self.screenlet?.delegate = self
	}

	func onFormLoaded(record: DDLRecord) {
		println("DELEGATE: onFormLoaded called -> \(record)")
	}

	func onFormLoadError(error: NSError) {
		println("DELEGATE: onDocumentUploadError called -> \(error)")
	}

	func onFormSubmitted(record: DDLRecord) {
		println("DELEGATE: onFormSubmitted called -> \(record)")
	}

	func onFormSubmitError(error: NSError) {
		println("DELEGATE: onDocumentUploadError called -> \(error)")
	}

	func onRecordLoaded(record: DDLRecord) {
		println("DELEGATE: onRecordLoaded called -> \(record)")
	}

	func onRecordLoadError(error: NSError) {
		println("DELEGATE: onDocumentUploadError called -> \(error)")
	}

	func onDocumentUploadStarted(field: DDLFieldDocument) {
		println("DELEGATE: onDocumentUploadError called. field -> \(field)")
	}

	func onDocumentUploadError(field: DDLFieldDocument, error: NSError) {
		println("DELEGATE: onDocumentUploadError called. field -> \(field) error -> \(error)")
	}

	func onDocumentUploadCompleted(field: DDLFieldDocument, result: [String : AnyObject]) {
		println("DELEGATE: onDocumentUploadCompleted called. field -> \(field) result -> \(result)")
	}

	func onDocumentUploadedBytes(field: DDLFieldDocument, bytes: UInt, sent: Int64, total: Int64) {
		println("DELEGATE: onDocumentUploadedBytes called. field -> \(field) \(bytes),\(sent),\(total)")
	}

}
