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

		cardDeck.addCard(signInCard, withController: SignInViewController())
		cardDeck.addCard(signUpCard, withController: SignUpViewController())

		signUpCard.normalHeight = cardDeck.frame.size.height - 50
	}

}

