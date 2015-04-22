//
//  ViewController.swift
//  WesterosBank
//
//  Created by jmWork on 21/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

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
			SessionContext.createSession(username: "test@liferay.com", password: "test", userAttributes: ["userId":1])
			self.dismissViewControllerAnimated(true, completion: nil)
		}

		signInController = SignInViewController(card: signInCard)
		signInController!.onDone = onDone

		cardDeck.topCard = signInCard

		signUpController = SignUpViewController(card: signUpCard)
		signUpController!.onDone = onDone

		cardDeck.bottomCard = signUpCard

		signUpCard.normalHeight = cardDeck.frame.size.height - 50
	}

}

