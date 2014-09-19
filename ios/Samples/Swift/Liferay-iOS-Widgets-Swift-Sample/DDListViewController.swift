//
//  DDLListViewController.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 19/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit

class DDListViewController: UIViewController, DDListWidgetDelegate {

	@IBOutlet var widget: DDListWidget?

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
		LiferayContext.instance.createSession("jose.navarro@liferay.com", password: "jm")

		widget!.loadList()
	}

	func onDDListResponse(records: [DDLRecord]) {
		println("Loaded \(records.count) records")
	}

	func onDDListError(error: NSError) {
		println("Load DDL List error")
	}

	func onDDLRecordSelected(record: DDLRecord) {
		println("Selected DDL Record -> \(record.attributes)")
	}

}
