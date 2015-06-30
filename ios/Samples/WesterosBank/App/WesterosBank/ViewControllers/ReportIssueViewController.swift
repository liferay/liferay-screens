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

class ReportIssueViewController: CardViewController, DDLFormScreenletDelegate {

	@IBOutlet weak var screenlet: DDLFormScreenlet!
	@IBOutlet weak var saveButton: UIButton!

	var issueRecord : DDLRecord?
	var editable: Bool = true

	override init(card: CardView, nibName: String) {
		super.init(card: card, nibName: nibName)
	}

	convenience init(card: CardView) {
		self.init(card: card, nibName:"ReportIssueViewController")
	}

	required init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

	@IBAction func saveAction(sender: AnyObject) {
		screenlet.submitForm()
	}

	override func viewDidLoad() {
		screenlet.delegate = self
	}

	override func cardWillAppear() {
		screenlet.editable = editable
		saveButton.hidden = !editable

		saveButton.frame.origin.y = saveButton.superview!.frame.size.height - saveButton.frame.size.height - 25

		if let recordValue = issueRecord {
			screenlet.recordId = recordValue.recordId
			screenlet.loadRecord()
		}
		else {
			screenlet.recordId = 0

			if screenlet.isFormLoaded {
				screenlet.clearForm()
			}
			else {
				screenlet.loadForm()
			}
		}

		UIView.animateWithDuration(0.5) {
			self.screenlet.alpha = 1.0
		}
	}

	override func cardWillDisappear() {
		UIView.animateWithDuration(0.5) {
			self.screenlet.alpha = 0.0
		}
	}


	func screenlet(screenlet: DDLFormScreenlet,
			onFormSubmitted record: DDLRecord) {
		onDone?()
	}

}
