//
//  DDLListViewController.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 19/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit

class DDListViewController: UIViewController, DDLListWidgetDelegate {

	@IBOutlet var widget: DDLListWidget?

    override func viewDidLoad() {
        super.viewDidLoad()

		widget!.delegate = self

		//FIXME
		// Inspectable properties on IB are broken in XCode 6.0.1!
		widget!.userId = 10198
		widget!.recordSetId = 13006
		widget!.labelField = "Title"
    }

	@IBAction func loadAction(sender: AnyObject) {
		SessionContext.createSession(
				username: "jose.navarro@liferay.com",
				password: "jm",
				userAttributes: ["userId": 10198])

		widget!.loadList()
	}

	func onDDLListResponse(records: [DDLRecord]) {
		println("Loaded \(records.count) records")
	}

	func onDDLListError(error: NSError) {
		println("Load DDL List error")
	}

	func onDDLLRecordSelected(record: DDLRecord) {
		println("Selected DDL Record -> \(record.attributes)")
	}

}
