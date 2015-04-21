//
//  ViewController.swift
//  WesterosBank
//
//  Created by jmWork on 21/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit
import KNSemiModalViewController


class ViewController: UIViewController {

	@IBOutlet var cardDeck: CardDeckView!
	@IBOutlet weak var signInCard: CardView!
	@IBOutlet weak var signUpCard: CardView!

	override func viewDidLoad() {
		super.viewDidLoad()

		let signInController = SignInViewController()
		signInController.onDone = {
			self.dismissViewControllerAnimated(true, completion: nil)
		}

		cardDeck.addCard(signInCard, withController: signInController)

		let signUpController = SignUpViewController()
		signUpController.onDone = {
			self.dismissViewControllerAnimated(true, completion: nil)
		}

		cardDeck.addCard(signUpCard, withController: signUpController)

		signUpCard.normalHeight = cardDeck.frame.size.height - 50
	}

}

