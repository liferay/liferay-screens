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


class ForgotPasswordViewController: CardViewController,
		ForgotPasswordScreenletDelegate,
		KeyboardListener {

	//MARK: Outlets
	
	@IBOutlet weak var forgotPasswordScreenlet: ForgotPasswordScreenlet?


	//MARK: Init methods

	override init(card: CardView, nibName: String) {
		super.init(card: card, nibName: nibName)
	}

	convenience init(card: CardView) {
		self.init(card: card, nibName: "ForgotPasswordViewController")
	}

	required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}


	//MARK: UIViewController


	override func viewDidLoad() {
		self.forgotPasswordScreenlet?.delegate = self
		self.forgotPasswordScreenlet?.anonymousApiUserName =
				LiferayServerContext.propertyForKey("anonymousUsername") as? String
		self.forgotPasswordScreenlet?.anonymousApiPassword =
				LiferayServerContext.propertyForKey("anonymousPassword") as? String
	}


	//MARK: ForgotPasswordScreenletDelegate

	func screenlet(screenlet: ForgotPasswordScreenlet,
			onForgotPasswordSent passwordSent: Bool) {
		//TODO: go back to login view
	}


	//MARK: CardViewController

	override func cardWillAppear() {
		registerKeyboardListener(self)
	}

	override func cardWillDisappear() {
		unregisterKeyboardListener(self)
		self.view.endEditing(true)
	}


	//MARK: KeyboardListener

	func showKeyboard(notif: NSNotification) {
		if cardView?.currentState == .Normal {
			cardView?.nextState = .Maximized
			cardView?.changeToNextState()
		}
	}

	func hideKeyboard(notif: NSNotification) {
		if cardView?.currentState == .Maximized {
			cardView?.nextState = .Normal
			cardView?.changeToNextState()
		}
	}

}
