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

class IssuesViewController: CardViewController,
		DDLListScreenletDelegate, DDLFormScreenletDelegate {

	@IBOutlet weak var listScreenlet: DDLListScreenlet!
	@IBOutlet weak var formScreenlet: DDLFormScreenlet!
	@IBOutlet weak var scroll: UIScrollView!

	var onEditIssue: (DDLRecord -> Void)? {
		didSet {
			let view = listScreenlet.viewModel as! DDLListView_westeros

			view.onEditAction = onEditIssue
		}
	}

	var onViewIssue: (DDLRecord -> Void)? {
		didSet {
			let view = listScreenlet.viewModel as! DDLListView_westeros

			view.onViewAction = onViewIssue
		}
	}

	override init(card: CardView, nibName: String) {
		super.init(card: card, nibName: nibName)
	}

	convenience init(card: CardView) {
		self.init(card: card, nibName:"IssuesViewController")
	}

	required init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

	override func viewDidLoad() {
		listScreenlet.delegate = self
		formScreenlet.delegate = self

		scroll.contentSize = CGSizeMake(scroll.frame.size.width * 2, scroll.frame.size.height)
	}

	override func viewWillAppear(animated: Bool) {
		if SessionContext.hasSession {
			listScreenlet.loadList()
		}
	}

	func onDDLRecordSelected(record: DDLRecord) {
		onEditIssue?(record)
	}

	func scrollToShowList() {
		let newRect = CGRectMake(0, 0, scroll.frame.size)
		scroll.scrollRectToVisible(newRect, animated: true)
	}

	func scrollToShowRecord(record: DDLRecord) {
		formScreenlet.recordId = record.recordId
		formScreenlet.loadRecord()

		let newRect = CGRectMake(scroll.frame.size.width, 0, scroll.frame.size)
		scroll.scrollRectToVisible(newRect, animated: true)
	}


}
