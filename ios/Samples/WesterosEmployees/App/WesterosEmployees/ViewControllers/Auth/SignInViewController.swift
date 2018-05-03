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

class SignInViewController: CardViewController, LoginScreenletDelegate, KeyboardListener {

	// MARK: Outlets
	@IBOutlet weak var loginScreenlet: LoginScreenlet?

	// MARK: Initializers

	convenience init() {
		self.init(nibName: "SignInViewController", bundle: nil)
	}

	// MARK: Actions

	@IBAction func forgotPasswordAction() {
		cardView?.moveRight()
	}

	// MARK: UIViewController

	override func viewDidLoad() {
		self.loginScreenlet?.delegate = self
        self.loginScreenlet?.loginMode = "cookie"
	}

	// MARK: LoginScreenletDelegate

	func screenlet(_ screenlet: BaseScreenlet,
			onLoginResponseUserAttributes attributes: [String: AnyObject]) {
		onDone?()
	}

	// MARK: CardViewController

	override func pageWillAppear() {
		registerKeyboardListener(self)
	}

	override func pageWillDisappear() {
		self.view.endEditing(true)
		unregisterKeyboardListener(self)
	}

	// MARK: KeyboardListener

	func showKeyboard(_ notif: Notification) {
		if cardView?.currentState == .normal {
			cardView?.changeToState(.maximized)
		}
	}

	func hideKeyboard(_ notif: Notification) {
		if cardView?.currentState == .maximized {
			cardView?.changeToState(.normal)
		}
	}

}
