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


class SurveyViewController: UIViewController, DDLFormScreenletDelegate {

	@IBOutlet var screenlet: DDLFormScreenlet?

	var onFinish: (String -> Void)?

	var structureId: Int64 = 0
	var recordSetId: Int64 = 0

	required init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

	override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: NSBundle?) {
		super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
	}

	override func viewDidLoad() {
		screenlet?.delegate = self
	}

	override func viewWillAppear(animated: Bool) {
		screenlet?.structureId = structureId
		screenlet?.recordSetId = recordSetId
		screenlet?.loadForm()
	}

	func screenlet(screenlet: DDLFormScreenlet, onFormLoadError error: NSError) {
		println(error)
	}

	func screenlet(screenlet: DDLFormScreenlet,
			onFormSubmitted record: DDLRecord) {

		self.dismissViewControllerAnimated(true, completion: nil)

		let result = record.fields.map {
				"\($0.name)=\($0.currentValueAsString);"
			}
			.reduce("") {
				$0 + $1
			}

		onFinish?(result)
	}


	@IBAction func onSaveAction(sender: AnyObject) {
		screenlet?.submitForm()
	}
}
