//
//  AssetListViewController.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 11/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit


public class AssetListViewController: UIViewController, AssetListWidgetDelegate {

	@IBOutlet weak var widget: AssetListWidget?

    override public func viewDidLoad() {
        super.viewDidLoad()

        widget!.delegate = self
    }

	@IBAction func loadAction(sender: AnyObject) {
		SessionContext.createSession(
				username: "jose.navarro@liferay.com",
				password: "jm",
				userAttributes: ["userId": 10198])
		widget!.loadList()
	}

	public func onAssetListResponse(entries:[AssetListWidget.Entry]) {
		println("Loaded \(entries.count) entries")
		for e in entries {
			println("      \(e.title)")
		}
	}

	public func onAssetListError(error: NSError) {
	}

	public func onAssetSelected(entry: AssetListWidget.Entry) {
		println("selected \(entry.title)")
	}

}
