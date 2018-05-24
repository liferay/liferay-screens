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

class AccountSettingsViewController: UIViewController,
		UserPortraitScreenletDelegate,
		SignUpScreenletDelegate,
		KeyboardListener {

	@IBOutlet weak var portraitScreenlet: UserPortraitScreenlet?
	@IBOutlet weak var signUpScreenlet: SignUpScreenlet?

	fileprivate var initialSignUpPosition: CGFloat?

	@IBAction func closeAction(_ sender: AnyObject) {
		self.dismiss(animated: true, completion: nil)
	}

	override func viewDidLoad() {
		portraitScreenlet?.presentingViewController = self
		portraitScreenlet?.delegate = self

		signUpScreenlet?.delegate = self

		initialSignUpPosition = signUpScreenlet?.frame.origin.y
	}

	override func viewWillAppear(_ animated: Bool) {
		portraitScreenlet?.loadLoggedUserPortrait()
		signUpScreenlet?.loadCurrentUser()

		registerKeyboardListener(self)
	}

	override func viewWillDisappear(_ animated: Bool) {
		unregisterKeyboardListener(self)
	}

	func showKeyboard(_ notif: Notification) {
		UIView.animate(withDuration: 1.0) {
			self.signUpScreenlet?.frame = CGRectMake(
				self.signUpScreenlet!.frame.origin.x,
				y: 0,
				size: self.signUpScreenlet!.frame.size)
		}
	}

	func hideKeyboard(_ notif: Notification) {
		UIView.animate(withDuration: 1.0) {
			self.signUpScreenlet?.frame = CGRectMake(
				self.signUpScreenlet!.frame.origin.x,
				y: self.initialSignUpPosition!,
				size: self.signUpScreenlet!.frame.size)
		}
	}

	func screenlet(_ screenlet: SignUpScreenlet, onSignUpResponseUserAttributes attributes: [String: AnyObject]) {
		closeAction(screenlet)
	}

}
