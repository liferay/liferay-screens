//
//  DDLListViewController.swift
//  Liferay-iOS-Screenlets-Swift-Sample
//
//  Created by jmWork on 19/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit

class DDListViewController: UIViewController, DDLListScreenletDelegate {

	@IBOutlet var screenlet: DDLListScreenlet?

    override func viewDidLoad() {
        super.viewDidLoad()

		screenlet!.delegate = self
    }

	@IBAction func loadAction(sender: AnyObject) {
		SessionContext.createSession(
				username: "jose.navarro@liferay.com",
				password: "jm",
				userAttributes: ["userId": 10198])

		screenlet!.loadList()
	}

	func onDDLListResponse(records: [DDLRecord]) {
		println("Loaded \(records.count) records")
		for e in records {
			let v = e["Title"]?.currentValueAsString
			println("      \(v)")
		}

	}

	func onDDLListError(error: NSError) {
		println("Load DDL List error")
	}

	func onDDLLRecordSelected(record: DDLRecord) {
		println("Selected DDL Record -> \(record.attributes)")
	}

}
