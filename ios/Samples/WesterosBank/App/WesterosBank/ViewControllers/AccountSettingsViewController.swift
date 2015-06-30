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
		BaseScreenletDelegate,
		SignUpScreenletDelegate,
		KeyboardListener {

	@IBOutlet weak var portraitScreenlet: UserPortraitScreenlet!
	@IBOutlet weak var signUpScreenlet: SignUpScreenlet!

	private var initialSignUpPosition: CGFloat?

	@IBAction func closeAction(sender: AnyObject) {
		self.dismissViewControllerAnimated(true, completion: nil)
	}

	override func viewDidLoad() {
		portraitScreenlet.presentingViewController = self
		portraitScreenlet.delegate = self

		signUpScreenlet.delegate = self

		initialSignUpPosition = signUpScreenlet.frame.origin.y
	}

	override func viewWillAppear(animated: Bool) {
		portraitScreenlet.loadLoggedUserPortrait()
		signUpScreenlet.loadCurrentUser()

		registerKeyboardListener(self)
	}

	override func viewWillDisappear(animated: Bool) {
		unregisterKeyboardListener(self)
	}

	func showKeyboard(notif: NSNotification) {
		UIView.animateWithDuration(1.0) {
			self.signUpScreenlet.frame = CGRectMake(
					self.signUpScreenlet.frame.origin.x, 0,
					self.signUpScreenlet.frame.size)
		}
	}

	func hideKeyboard(notif: NSNotification) {
		UIView.animateWithDuration(1.0) {
			self.signUpScreenlet.frame = CGRectMake(
					self.signUpScreenlet.frame.origin.x, self.initialSignUpPosition!,
					self.signUpScreenlet.frame.size)
		}
	}

	func screenlet(screenlet: SignUpScreenlet,
			onSignUpResponseUserAttributes attributes: [String:AnyObject]) {
		closeAction(screenlet)
	}

	func screenlet(screenlet: BaseScreenlet,
			willLoadViewForTheme theme: String) -> String {

		var result = theme

		if audienceTargeting().hasContentCached(placeholderId: "portrait-theme") {
			// when the content is cached, the method is sync
			audienceTargeting().content(
					placeholderId: "portrait-theme",
					result: { (themeName, error) -> Void in
						result = themeName!
					})
		}

		return result
	}

}
