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

class DetailViewController: CardViewController, CardDeckDelegate, WebScreenletDelegate {

	// MARK: Outlets

	@IBOutlet weak var webScreenlet: WebScreenlet?

	@IBOutlet weak var cardDeck: CardDeckView? {
		didSet {
			cardDeck?.delegate = self
			cardDeck?.layer.zPosition = 0
		}
	}

	@IBOutlet weak var goBackButton: UIButton? {
		didSet {
			goBackButton?.titleEdgeInsets = UIEdgeInsets(top: 0, left: 70, bottom: 0, right: 70)
		}
	}
	@IBOutlet weak var arrowImageView: UIImageView?

	// MARK: View methods

	@IBAction func goBackButtonClicked() {
		dismiss(animated: true, completion: nil)
	}

	func load(file: String, id: String) {
		let webScreenletConfiguration = WebScreenletConfigurationBuilder(url: "/web/westeros-hybrid/detail?id=\(id)")
			.addCss(localFile: file)
			.addJs(localFile: file)
			.load()

		webScreenlet?.backgroundColor = .clear
		webScreenlet?.presentingViewController = self
		webScreenlet?.configuration = webScreenletConfiguration
		webScreenlet?.delegate = self
		webScreenlet?.load()
	}

	// MARK: CardViewController

	override func pageWillDisappear() {
		//Hide comment card
		self.cardDeck?.cards[safe: 0]?.changeToState(.minimized)
	}

	// MARK: Init methods

	convenience init(nibName: String) {
		self.init(nibName: nibName, bundle: nil)
	}

	// MARK: CardDeckDataSource

	func numberOfCardsIn(_ cardDeck: CardDeckView) -> Int {
		return 1
	}

	func doCreateCard(_ cardDeck: CardDeckView, index: Int) -> CardView? {
		return WesterosCardView.newAutoLayout()
	}

	// MARK: CardDeckDelegate

	func cardDeck(_ cardDeck: CardDeckView, customizeCard card: CardView, atIndex index: Int) {
		if let cardView = self.cardView {
			card.normalHeight = cardView.frame.height * 0.95
		}
		else {
			card.normalHeight = self.view.frame.height * 0.85
		}
	}
}
