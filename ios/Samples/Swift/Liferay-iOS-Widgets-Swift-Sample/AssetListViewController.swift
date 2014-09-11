//
//  AssetListViewController.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 11/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit

class AssetListViewController: UIViewController, AssetListWidgetDelegate {

	@IBOutlet weak var widget: AssetListWidget?

    override func viewDidLoad() {
        super.viewDidLoad()

        widget!.delegate = self
    }

	@IBAction func loadAction(sender: AnyObject) {
		LiferayContext.instance.createSession("jose.navarro@liferay.com", password: "jm")
		widget!.loadList()
	}

	func onAssetListResponse(entries:[AssetEntry]) {
		println("Loaded \(entries.count) entries")
	}

	func onAssetListError(error: NSError) {
	}

	func onAssetSelected(entry:AssetEntry) {
		println("selected \(entry.title)")
	}


}
