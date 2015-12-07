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


class DDLListScreenletViewController: UIViewController, DDLListScreenletDelegate {

	@IBOutlet weak var screenlet: DDLListScreenlet?
	@IBOutlet weak var recordSetIdTextField: UITextField?
	@IBOutlet weak var labelFieldsTextField: UITextField?

	@IBAction func loadList(sender: AnyObject) {
		if let recordSetId = Int(recordSetIdTextField!.text!) {
			screenlet!.recordSetId = Int64(recordSetId)
			screenlet!.labelFields = labelFieldsTextField!.text

			screenlet!.loadList()
		}
	}

	override func viewDidLoad() {
		super.viewDidLoad()

		self.screenlet?.delegate = self
	}

	func screenlet(screenlet: DDLListScreenlet,
			onDDLListResponseRecords records: [DDLRecord]) {
		print("DELEGATE: onDDLListResponse called -> \(records)\n");
	}

	func screenlet(screenlet: DDLListScreenlet,
			onDDLListError error: NSError) {
		print("DELEGATE: onDDLListError called -> \(error)\n");
	}

	func screenlet(screenlet: DDLListScreenlet,
			onDDLSelectedRecord record: DDLRecord) {
		print("DELEGATE: onDDLRecordSelected called -> \(record)\n");
	}

}
