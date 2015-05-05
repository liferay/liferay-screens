//
//  AccountSettingsViewController.swift
//  WesterosBank
//
//  Created by jmWork on 26/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit
import LiferayScreens


class AccountSettingsViewController: UIViewController, UserPortraitScreenletDelegate, SignUpScreenletDelegate {

	@IBOutlet weak var portraitScreenlet: UserPortraitScreenlet!
	@IBOutlet weak var signUpScreenlet: SignUpScreenlet!

	private var initialSignUpPosition: CGFloat?

	@IBAction func saveAction(sender: AnyObject) {
		self.dismissViewControllerAnimated(true, completion: nil)
	}

	override func viewDidLoad() {
		portraitScreenlet.delegate = self
		signUpScreenlet.delegate = self

		initialSignUpPosition = signUpScreenlet.frame.origin.y
	}

	override func viewWillAppear(animated: Bool) {
		signUpScreenlet.loadCurrentUser()

		NSNotificationCenter.defaultCenter().addObserver(self,
				selector: "showKeyboard:",
				name: UIKeyboardWillChangeFrameNotification,
				object: nil)
		NSNotificationCenter.defaultCenter().addObserver(self,
				selector: "hideKeyboard:",
				name: UIKeyboardWillHideNotification,
				object: nil)
	}

	override func viewWillDisappear(animated: Bool) {
		NSNotificationCenter.defaultCenter().removeObserver(self,
				name: UIKeyboardWillHideNotification,
				object: nil)
		NSNotificationCenter.defaultCenter().removeObserver(self,
				name: UIKeyboardWillChangeFrameNotification,
				object: nil)
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

}
