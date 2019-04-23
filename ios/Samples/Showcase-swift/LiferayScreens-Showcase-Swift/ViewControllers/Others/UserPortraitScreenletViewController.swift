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

	// MARK: Outlets

	@IBOutlet weak var userIdField: UITextField? {
		didSet {
			if let userId = SessionContext.currentContext?.user.userId.description {
				userIdField?.text = userId
			}
		}
	}

	@IBOutlet weak var screenlet: UserPortraitScreenlet?

	@IBOutlet weak var screenletWithDelegate: UserPortraitScreenlet? {
		didSet {
			screenletWithDelegate?.delegate = self
		}
	}

	@IBOutlet weak var editableScreenlet: UserPortraitScreenlet? {
		didSet {
			editableScreenlet?.presentingViewController = self
		}
	}

	@IBOutlet weak var initialsScreenlet: UserPortraitScreenlet? {
		didSet {
			initialsScreenlet?.themeName = "initials"
		}
	}

	@IBOutlet weak var loadButton: UIButton? {
		didSet {
			loadButton?.replaceAttributedTitle(NSLocalizedString("load-button", comment: "LOAD"),
			                                   forState: .normal)
		}
	}

	// MARK: Actions

	@IBAction func loadPortrait(_ sender: AnyObject) {
		if let id = userIdField?.text, let userId = Int64(id) {
			screenlet?.load(userId: userId)
			screenletWithDelegate?.load(userId: userId)
			editableScreenlet?.load(userId: userId)
			initialsScreenlet?.load(userId: userId)
		}
		else if let text = userIdField?.text, text != "" {
			let company = LiferayServerContext.companyId

			if text.firstIndex(of: "@") != nil {
				screenlet?.load(companyId: company, emailAddress: text)
				screenletWithDelegate?.load(companyId: company, emailAddress: text)
				editableScreenlet?.load(companyId: company, emailAddress: text)
			}
			else {
				screenlet?.load(companyId: company, screenName: text)
				screenletWithDelegate?.load(companyId: company, screenName: text)
				editableScreenlet?.load(companyId: company, screenName: text)
			}
		}
		else {
			screenlet?.loadLoggedUserPortrait()
			screenletWithDelegate?.loadLoggedUserPortrait()
			editableScreenlet?.loadLoggedUserPortrait()
		}
	}

	// MARK: UserPortraitScreenletDelegate

	func screenlet(_ screenlet: UserPortraitScreenlet,
	               onUserPortraitResponseImage image: UIImage) -> UIImage {

		LiferayLogger.logDelegateMessage(args: image)

		return image.grayScaleImage()
	}

	func screenlet(_ screenlet: UserPortraitScreenlet, onUserPortraitError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(_ screenlet: UserPortraitScreenlet, onUserPortraitUploadError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}

	func screenlet(_ screenlet: UserPortraitScreenlet,
	               onUserPortraitUploaded attributes: [String: AnyObject]) {

		LiferayLogger.logDelegateMessage(args: attributes as AnyObject?)
	}
}
