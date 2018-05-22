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

open class UserDisplayViewController: UIViewController, AssetDisplayScreenletDelegate {

	// MARK: Outlets

	@IBOutlet var screenlet: AssetDisplayScreenlet? {
		didSet {
			self.screenlet?.delegate = self

			self.screenlet?.className = AssetClasses.getClassName(AssetClassNameKey_User)!
			self.screenlet?.classPK = (SessionContext.currentContext?.user.userId)!
		}
	}

	// MARK: UIViewController

	open override func viewDidLoad() {
		super.viewDidLoad()
		self.screenlet?.load()
	}

	// MARK: AssetDisplayScreenletDelegate

	open func screenlet(_ screenlet: AssetDisplayScreenlet, onAsset asset: Asset) -> UIView? {
		if let type = asset.attributes["object"]?.allKeys.first as? String {
			if type == "user" {
				let view = Bundle.main.loadNibNamed("UserProfileView", owner: self, options: nil)![safe: 0] as? UserProfileView

				let object = asset.attributes["object"] as! [String: AnyObject]

				view?.user = User(attributes: object["user"] as! [String: AnyObject])
				view?.goBackButtonClicked = {
					self.dismiss(animated: true, completion: nil)
				}
				view?.signOutButtonClicked = {
					SessionContext.currentContext?.removeStoredCredentials()
					SessionContext.logout()
					self.dismiss(animated: true, completion: nil)
				}
				return view
			}
		}
		return nil
	}
}
