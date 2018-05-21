//
//  AccountSettingsViewController.swift
//  WesterosBank
//
//  Created by jmWork on 26/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit
import LiferayScreens

class AccountSettingsViewController: UIViewController,
		UserPortraitScreenletDelegate,
		SignUpScreenletDelegate,
		KeyboardListener {

	@IBOutlet weak var portraitScreenlet: UserPortraitScreenlet!
	@IBOutlet weak var signUpScreenlet: SignUpScreenlet!

	fileprivate var initialSignUpPosition: CGFloat?

	@IBAction func closeAction(_ sender: AnyObject) {
		self.dismiss(animated: true, completion: nil)
	}

	override func viewDidLoad() {
		portraitScreenlet.presentingViewController = self
		portraitScreenlet.delegate = self

		signUpScreenlet.delegate = self

		initialSignUpPosition = signUpScreenlet.frame.origin.y
	}

	override func viewWillAppear(_ animated: Bool) {
		portraitScreenlet.loadLoggedUserPortrait()
		signUpScreenlet.loadCurrentUser()

		registerKeyboardListener(self)
	}

	override func viewWillDisappear(_ animated: Bool) {
		unregisterKeyboardListener(self)
	}

	func showKeyboard(_ notif: Notification) {
		UIView.animate(withDuration: 1.0) {
			self.signUpScreenlet.frame = CGRectMake(
				self.signUpScreenlet.frame.origin.x,
				y: 0,
				size: self.signUpScreenlet.frame.size)
		}
	}

	func hideKeyboard(_ notif: Notification) {
		UIView.animate(withDuration: 1.0) {
			self.signUpScreenlet.frame = CGRectMake(
				self.signUpScreenlet.frame.origin.x,
				y: self.initialSignUpPosition!,
				size: self.signUpScreenlet.frame.size)
		}
	}

	func screenlet(_ screenlet: SignUpScreenlet, onSignUpResponseUserAttributes attributes: [String: AnyObject]) {
		closeAction(screenlet)
	}

}
