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

		let onDone: () -> () = {
			self.dismissViewControllerAnimated(true, completion: nil)
		}

		signInController = SignInViewController(card: signInCard)
		signInController!.onDone = onDone

		cardDeck.topCard = signInCard

		signUpController = SignUpViewController(card: signUpCard)
		signUpController!.onDone = onDone

		cardDeck.bottomCard = signUpCard

		signUpCard.normalHeight = cardDeck.frame.size.height - 50

		signUpCard.currentState = .Minimized

		signInCard.currentState = .Hidden
		signInCard.resetToCurrentState()
	}

	override func viewWillAppear(animated: Bool) {
		self.signInCard.nextState = .Minimized
		self.signInCard.changeToNextState(time: nil, delay: 0.5)
	}

}
