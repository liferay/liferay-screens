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

class UserPortraitScreenletViewController: UIViewController, UserPortraitScreenletDelegate {

	@IBOutlet weak var screenlet: UserPortraitScreenlet!
	@IBOutlet weak var screenletWithDelegate: UserPortraitScreenlet!
	@IBOutlet weak var userIdField: UITextField!

	@IBAction func loadPortrait(sender: AnyObject) {
		if let userId = userIdField.text.toInt() {
			screenlet.load(userId: Int64(userId))
			screenletWithDelegate.load(userId: Int64(userId))
		}
	}

    override func viewDidLoad() {
        super.viewDidLoad()

		if SessionContext.hasSession {
			userIdField.text = SessionContext.userAttribute("userId")?.description
		}

		screenletWithDelegate?.delegate = self
    }

	func onUserPortraitResponse(image: UIImage) -> UIImage {
		println("DELEGATE: onUserPortraitResponse -> \(image.size)")

		return image.getGrayScale() ?? image
	}

	func onUserPortraitError(error: NSError) {
		println("DELEGATE: onUserPortraitError -> \(error)")
	}

}
