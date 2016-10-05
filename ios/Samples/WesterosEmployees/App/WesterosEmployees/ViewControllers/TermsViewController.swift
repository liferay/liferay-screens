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

class TermsViewController: CardViewController, AssetDisplayScreenletDelegate {


	//MARK: Outlets

	@IBOutlet weak var assetDisplayScreenlet: AssetDisplayScreenlet? {
		didSet {
			assetDisplayScreenlet?.delegate = self
		}
	}

	//MARK: Init methods

	convenience init() {
		self.init(nibName: "TermsViewController", bundle: nil)
	}


	//MARK: UIViewController

	override func viewWillAppear(animated: Bool) {
		func prop(key: String) -> AnyObject? {
			return LiferayServerContext.propertyForKey(key)
		}

		//Load terms and conditions from a web article
		if let entryId = prop("termsAndConditionsAssetEntryId") as? NSNumber,
			   anonymUsername = prop("anonymousUsername") as? String,
			   anonymPassword = prop("anonymousPassword") as? String {

			//Login anonymous user
			SessionContext.loginWithBasic(
				username: anonymUsername, password: anonymPassword, userAttributes: [:])

			//Load article data into screenlet
			self.assetDisplayScreenlet?.assetEntryId = entryId.longLongValue
			self.assetDisplayScreenlet?.load()
		} else {
			print("Some of the options needed for this web content are not set. " +
				"Review your server-context list.")
		}
	}


	//MARK: AssetDisplayScreenletDelegate

	func screenlet(screenlet: AssetDisplayScreenlet, onAssetError error: NSError) {
		print("Couldn't load terms and conditions. Error: \(error)")
	}
}
