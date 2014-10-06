//
//  AssetListViewController.swift
//  Liferay-iOS-Screenlets-Swift-Sample
//
//  Created by jmWork on 11/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit


public class AssetListViewController: UIViewController, AssetListScreenletDelegate {

	@IBOutlet weak var screenlet: AssetListScreenlet?

    override public func viewDidLoad() {
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

	public func onAssetListResponse(entries:[AssetListScreenlet.Entry]) {
		println("Loaded \(entries.count) entries")
		for e in entries {
			println("      \(e.title)")
		}
	}

	public func onAssetListError(error: NSError) {
	}

	public func onAssetSelected(entry: AssetListScreenlet.Entry) {
		println("selected \(entry.title)")
	}

}
