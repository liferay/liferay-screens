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

class CommentsViewController: CardViewController, CardDeckDelegate, CardDeckDataSource, CommentListScreenletDelegate {

	var addCommentViewController: AddCommentViewController? {
		didSet {
			self.addChildViewController(addCommentViewController!)
			self.addCommentViewController?.onCommentAdded = { comment in
				self.cardDeck?.cards[safe: 0]?.changeToState(.minimized)
				self.commentListScreenlet?.addComment(comment)
			}
			self.addCommentViewController?.onCommentUpdated = { comment in
				self.cardDeck?.cards[safe: 0]?.changeToState(.minimized)
				self.commentListScreenlet?.updateComment(comment)
			}
		}
	}

	// MARK: Outlets

	@IBOutlet weak var commentListScreenlet: CommentListScreenlet? {
		didSet {
			self.commentListScreenlet?.delegate = self
		}
	}

	@IBOutlet weak var cardDeck: CardDeckView? {
		didSet {
			cardDeck?.delegate = self
			cardDeck?.dataSource = self
		}
	}

	// MARK: Public methods

	func hideAddCommentCard() {
		self.cardDeck?.cards[safe: 0]?.changeToState(.minimized)
	}

	// MARK: CardViewController

	override func pageWillAppear() {
		commentListScreenlet?.loadList()
	}

	// MARK: Initializers

	convenience init() {
		self.init(nibName: "CommentsViewController", bundle: nil)
	}

	func load(className: String, classPK: Int64) {
		commentListScreenlet?.className = className
		commentListScreenlet?.classPK = classPK

		addCommentViewController?.load(className: className, classPK: classPK)

		//Change color depending on asset
		if let card = cardView as? WesterosCardView {
			//If loading blog...
			if className == AssetClasses.getClassName(AssetClassNameKey_BlogsEntry)! {
				card.backgroundColor = DefaultResources.EvenColorBackground
				card.button.setTitleColor(DefaultResources.OddColorBackground, for: .normal)
				card.arrow.image = UIImage(named: "icon_DOWN")
				card.activityIndicator.color = DefaultResources.OddColorBackground
				commentListScreenlet?.themeName = "westeros-white"
			}
			else {
				card.backgroundColor = DefaultResources.OddColorBackground
				card.button.setTitleColor(DefaultResources.EvenColorBackground, for: .normal)
				card.arrow.image = UIImage(named: "icon_DOWN_W")
				card.activityIndicator.color = DefaultResources.EvenColorBackground
				commentListScreenlet?.themeName = "westeros"
			}
		}
	}

	// MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		self.cardDeck?.layer.zPosition = 0

		addCommentViewController = AddCommentViewController()
	}

	// MARK: CardDeckDataSource

	func numberOfCardsIn(_ cardDeck: CardDeckView) -> Int {
		return 1
	}

	func cardDeck(_ cardDeck: CardDeckView, controllerForCard position: CardPosition)
		-> CardViewController? {
			return addCommentViewController
	}

	// MARK: CardDeckDelegate

	func cardDeck(_ cardDeck: CardDeckView, customizeCard card: CardView, atIndex index: Int) {
		card.normalHeight = UIScreen.main.bounds.height * 0.7
	}

	func cardDeck(_ cardDeck: CardDeckView, titleForCard position: CardPosition) -> String? {
		return "Add Comment"
	}

	// MARK: CommentListScreenletDelegate
	func screenlet(_ screenlet: BaseScreenlet, customInteractorForAction action: String, withSender sender: AnyObject?) -> Interactor? {
		if action == "edit-comment" {
			if let comment = sender as? Comment {
				addCommentViewController?.editComment(comment)
			}
		}

		return nil
	}

}
