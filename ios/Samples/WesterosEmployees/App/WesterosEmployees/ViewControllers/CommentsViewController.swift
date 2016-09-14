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

class CommentsViewController: CardViewController, CardDeckDelegate, CardDeckDataSource {

	@IBOutlet weak var commentListScreenlet: CommentListScreenlet?

	@IBOutlet weak var cardDeck: CardDeckView? {
		didSet {
			cardDeck?.delegate = self
			cardDeck?.dataSource = self
		}
	}

	var className: String?
	var classPK: Int64?


	//MARK: Card ViewControllers

	var addCommentViewController: AddCommentViewController?


	//MARK: Init methods

	convenience init() {
		self.init(nibName: "CommentsViewController", bundle: nil)
	}

	func load () {
		commentListScreenlet?.className = self.className!
		commentListScreenlet?.classPK = self.classPK!
		commentListScreenlet?.loadList()
	}

	//MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		self.cardDeck?.layer.zPosition = 0

		addCommentViewController = AddCommentViewController()
	}


	//MARK: CardDeckDataSource

	func numberOfCardsIn(cardDeck: CardDeckView) -> Int {
		return 1
	}

	func cardDeck(cardDeck: CardDeckView, controllerForCard position: CardPosition)
		-> CardViewController? {
			return addCommentViewController
	}


	//MARK: CardDeckDelegate

	func cardDeck(cardDeck: CardDeckView, customizeCard card: CardView, atIndex index: Int) {
		if let firstCardDeck = self.cardView?.superview {
			card.normalHeight = firstCardDeck.frame.height * 0.6
		}
	}

	func cardDeck(cardDeck: CardDeckView, buttonImageForCardIndex index: Int) -> UIImage? {
		return UIImage(named: "icon_DOWN")
	}

	func cardDeck(cardDeck: CardDeckView, titleForCard position: CardPosition) -> String? {
		return "Add Comment"
	}

}
