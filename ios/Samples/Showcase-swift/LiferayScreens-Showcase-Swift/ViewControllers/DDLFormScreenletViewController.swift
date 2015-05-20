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

	func screenlet(screenlet: DDLFormScreenlet,
			onFormLoaded record: DDLRecord) {
		println("DELEGATE: onFormLoaded called -> \(record)")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onFormLoadError error: NSError) {
		println("DELEGATE: onDocumentUploadError called -> \(error)")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onRecordLoaded record: DDLRecord) {
		println("DELEGATE: onRecordLoaded called -> \(record)")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onRecordLoadError error: NSError) {
		println("DELEGATE: onDocumentUploadError called -> \(error)")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onFormSubmitted record: DDLRecord) {
		println("DELEGATE: onFormSubmitted called -> \(record)")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onFormSubmitError error: NSError) {
		println("DELEGATE: onDocumentUploadError called -> \(error)")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onDocumentFieldUploadStarted field: DDLFieldDocument) {
		println("DELEGATE: onDocumentUploadError called. field -> \(field)")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onDocumentField field: DDLFieldDocument,
			uploadedBytes bytes: UInt,
			sentBytes sent: Int64,
			totalBytes total: Int64) {
		println("DELEGATE: onDocumentUploadedBytes called. field -> \(field) \(bytes),\(sent),\(total)")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onDocumentField field: DDLFieldDocument,
			uploadResult result: [String:AnyObject]) {
		println("DELEGATE: onDocumentUploadCompleted called. field -> \(field) result -> \(result)")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onDocumentField field: DDLFieldDocument,
			uploadError error: NSError) {
		println("DELEGATE: onDocumentUploadError called. field -> \(field) error -> \(error)")
	}

}
