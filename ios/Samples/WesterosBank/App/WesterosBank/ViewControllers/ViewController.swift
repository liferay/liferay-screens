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

class ViewController: UIViewController {

	@IBOutlet var cardDeck: CardDeckView!
	@IBOutlet weak var signInCard: CardView!
	@IBOutlet weak var signUpCard: CardView!

	var signInController: SignInViewController?
	var signUpController: SignUpViewController?

	override func viewDidLoad() {
		super.viewDidLoad()

		let onDone: () -> Void = {
			self.dismiss(animated: true, completion: nil)
		}

		signInCard.frame.size.height = signInCard.normalHeight - signInCard.minimizedHeight

		signInController = SignInViewController(card: signInCard)
		signInController!.onDone = onDone

		cardDeck.topCard = signInCard

		signUpCard.normalHeight = cardDeck.frame.size.height - 50

		signUpCard.frame.size.height = signUpCard.normalHeight - signUpCard.minimizedHeight

		signUpController = SignUpViewController(card: signUpCard)
		signUpController!.onDone = onDone

		cardDeck.bottomCard = signUpCard

		signUpCard.currentState = .minimized

		signInCard.currentState = .hidden
		signInCard.resetToCurrentState()
	}

	override func viewWillAppear(_ animated: Bool) {
		self.signInCard.nextState = .minimized
		self.signInCard.changeToNextState(nil, delay: 0.5)
	}

}
