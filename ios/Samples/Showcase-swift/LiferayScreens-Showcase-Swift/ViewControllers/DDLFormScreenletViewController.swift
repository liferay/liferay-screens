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
		if let recordId = Int(recordIdField!.text!) {
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
		print("DELEGATE: onFormLoaded called -> \(record.debugDescription)\n")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onFormLoadError error: NSError) {
		print("DELEGATE: onFormLoadError called -> \(error)\n")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onRecordLoaded record: DDLRecord) {
		print("DELEGATE: onRecordLoaded called -> \(record.debugDescription)\n")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onRecordLoadError error: NSError) {
		print("DELEGATE: onRecordLoadError called -> \(error)\n")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onFormSubmitted record: DDLRecord) {
		print("DELEGATE: onFormSubmitted called -> \(record.debugDescription)\n")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onFormSubmitError error: NSError) {
		print("DELEGATE: onFormSubmitError called -> \(error)\n")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onDocumentFieldUploadStarted field: DDMFieldDocument) {
		print("DELEGATE: onDocumentFieldUploadStarted called. field -> \(field.debugDescription)\n")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onDocumentField field: DDMFieldDocument,
			uploadedBytes bytes: UInt,
			sentBytes sent: Int64,
			totalBytes total: Int64) {
		print("DELEGATE: onDocumentUploadedBytes called. field -> \(field.debugDescription) \(bytes),\(sent),\(total)\n")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onDocumentField field: DDMFieldDocument,
			uploadResult result: [String:AnyObject]) {
		print("DELEGATE: onDocumentUploadCompleted called. field -> \(field.debugDescription) result -> \(result)\n")
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onDocumentField field: DDMFieldDocument,
			uploadError error: NSError) {
		print("DELEGATE: onDocumentUploadError called. field -> \(field.debugDescription) error -> \(error)\n")
	}

}
