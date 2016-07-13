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


class UserPortraitScreenletViewController: UIViewController, UserPortraitScreenletDelegate {

	@IBOutlet weak var screenlet: UserPortraitScreenlet!
	@IBOutlet weak var screenletWithDelegate: UserPortraitScreenlet!
	@IBOutlet weak var userIdField: UITextField!
	@IBOutlet weak var editableScreenletContainer: UIView!

	var editableScreenlet: UserPortraitScreenlet?

	@IBAction func loadPortrait(sender: AnyObject) {
		if let userId = Int(userIdField.text!) {
			print("[PORTRAIT] Loading by id '\(userId)'")
			screenlet.load(userId: Int64(userId))
			screenletWithDelegate.load(userId: Int64(userId))
			editableScreenlet?.load(userId: Int64(userId))
		}
		else if let text = userIdField.text where text != "" {
			let company = LiferayServerContext.companyId

			if text.characters.indexOf("@") != nil {
				print("[PORTRAIT] Loading by email '\(text)'")
				screenlet.load(companyId: company, emailAddress: text)
				screenletWithDelegate.load(companyId: company, emailAddress: text)
				editableScreenlet?.load(companyId: company, emailAddress: text)
			}
			else  {
				print("[PORTRAIT] Loading by screenName '\(text)'")
				screenlet.load(companyId: company, screenName: text)
				screenletWithDelegate.load(companyId: company, screenName: text)
				editableScreenlet?.load(companyId: company, screenName: text)
			}
		}
		else {
			print("[PORTRAIT] Loading by logged user")
			screenlet.loadLoggedUserPortrait()
			screenletWithDelegate.loadLoggedUserPortrait()
			editableScreenlet?.loadLoggedUserPortrait()
		}
	}

    override func viewDidLoad() {
        super.viewDidLoad()

		if let userId = SessionContext.currentContext?.userAttribute("userId")?.description {
			userIdField.text = userId
		}

		screenletWithDelegate?.delegate = self

		editableScreenlet = UserPortraitScreenlet(frame: CGRect(x: 0, y: 0, width: 60, height: 60), themeName: nil) {
			if let s = $0 as? UserPortraitScreenlet {
				s.presentingViewController = self
				s.editable = true
			}
		}

		editableScreenletContainer.addSubview(editableScreenlet!)
    }

	func screenlet(screenlet: UserPortraitScreenlet,
			onUserPortraitResponseImage image: UIImage) -> UIImage {
		print("DELEGATE: onUserPortraitResponse -> \(image.size)\n")

		return image.grayScaleImage()
	}

	func screenlet(screenlet: UserPortraitScreenlet,
			onUserPortraitError error: NSError) {
		print("DELEGATE: onUserPortraitError -> \(error)\n")
	}

	func screenlet(screenlet: UserPortraitScreenlet,
			onUserPortraitUploaded attributes: [String:AnyObject]) {
		print("DELEGATE: onUserPortraitUploaded -> \(attributes)\n")
	}

}
