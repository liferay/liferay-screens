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

class AuthViewController: UIViewController, LoginScreenletDelegate {

	@IBOutlet var cardDeck: CardDeckView!

	var signInController: SignInViewController?
	var signUpController: SignUpViewController?

	override func viewDidLoad() {
		super.viewDidLoad()

		let onDone: () -> () = {
			self.dismissViewControllerAnimated(true, completion: nil)
		}

		cardDeck.addCards(["Sign In", "Sign Up"])

		signInController = SignInViewController(card: cardDeck.cards[0])
		signInController!.onDone = onDone

		signUpController = SignUpViewController(card: cardDeck.cards[1])
		signUpController!.onDone = onDone

		self.cardDeck.layoutIfNeeded()
	}

	override func viewWillAppear(animated: Bool) {
		super.viewWillAppear(animated)
		self.cardDeck.cards[0].currentState = .Hidden
		self.cardDeck.cards[0].resetToCurrentState()
		self.cardDeck.cards[0].nextState = .Minimized
		self.cardDeck.cards[0].changeToNextState(delay: 0.5)
	}
}
