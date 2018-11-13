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

class DDLListScreenletViewController: UIViewController, DDLListScreenletDelegate, UIPickerViewDataSource, UIPickerViewDelegate {

	// MARK: Outlets

	@IBOutlet weak var pickerView: UIPickerView? {
		didSet {
			pickerView?.delegate = self
			pickerView?.dataSource = self
		}
	}
	@IBOutlet weak var screenlet: DDLListScreenlet? {
		didSet {
			screenlet?.delegate = self
		}
	}
	@IBOutlet weak var recordSetIdTextField: UITextField? {
		didSet {
			recordSetIdTextField?.text =
				String(LiferayServerContext.longPropertyForKey("ddlRecordSetId"))
		}
	}
	@IBOutlet weak var labelFieldsTextField: UITextField? {
		didSet {
			labelFieldsTextField?.text = LiferayServerContext.stringPropertyForKey("ddlLabelField")
		}
	}
	@IBOutlet weak var loadButton: UIButton? {
		didSet {
			loadButton?.replaceAttributedTitle(NSLocalizedString("load-button", comment: "LOAD"),
			                                   forState: .normal)
		}
	}

	// MARK: Actions

	@IBAction func loadList(_ sender: AnyObject) {
		if let id = recordSetIdTextField?.text, let recordSetId = Int64(id) {
			screenlet?.recordSetId = recordSetId
			screenlet?.labelFields = labelFieldsTextField?.text
			screenlet?.obcClassName = selectedObcClassName
			screenlet?.loadList()
		}
	}

	let pickerData : [(name: String, className: String)] = [
		(name: "No order", className:""),
		(name: "Id", className: "com.liferay.dynamic.data.lists.util.comparator.DDLRecordIdComparator"),
		(name: "Create Date", className: "com.liferay.dynamic.data.lists.util.comparator.DDLRecordCreateDateComparator"),
		(name: "Modified Date", className: "com.liferay.dynamic.data.lists.util.comparator.DDLRecordModifiedDateComparator")
	]

	var selectedObcClassName = ""

	// MARK: DDLListScreenletDelegate

	func screenlet(_ screenlet: DDLListScreenlet, onDDLListError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(_ screenlet: DDLListScreenlet, onDDLSelectedRecord record: DDLRecord) {
		LiferayLogger.logDelegateMessage(args: record)
	}

	func screenlet(_ screenlet: DDLListScreenlet, onDDLListResponseRecords records: [DDLRecord]) {
		LiferayLogger.logDelegateMessage(args: records as AnyObject?)
	}

	// MARK: UIPickerViewDataSource

	func numberOfComponents(in pickerView: UIPickerView) -> Int {
		return 1
	}

	func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
		return pickerData.count
	}

	// MARK: UIPickerViewDelegate

	func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
		selectedObcClassName = pickerData[row].className
	}

	func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
		let attrs = [NSAttributedString.Key.font : UIFont.systemFont(ofSize: 3)]

		return NSAttributedString(string: pickerData[row].name, attributes: attrs)
	}
}
