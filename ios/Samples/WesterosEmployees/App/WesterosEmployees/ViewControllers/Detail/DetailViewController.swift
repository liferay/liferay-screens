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

class DetailViewController: CardViewController, AssetDisplayScreenletDelegate, CardDeckDelegate, CardDeckDataSource {

	// MARK: Outlets

	@IBOutlet weak var assetDisplayScreenlet: AssetDisplayScreenlet? {
		didSet {
			self.assetDisplayScreenlet?.delegate = self
		}
	}
	@IBOutlet weak var cardDeck: CardDeckView? {
		didSet {
			cardDeck?.delegate = self
			cardDeck?.dataSource = self
			cardDeck?.layer.zPosition = 0
		}
	}
	@IBOutlet weak var goBackButton: UIButton? {
		didSet {
			goBackButton?.titleEdgeInsets = UIEdgeInsets(top: 0, left: 70, bottom: 0, right: 70)
		}
	}
	@IBOutlet weak var arrowImageView: UIImageView?
	@IBOutlet weak var ratingScreenlet: RatingScreenlet?

	// MARK: Card controllers

	var commentsViewController: CommentsViewController? {
		didSet {
			self.addChildViewController(commentsViewController!)
		}
	}

	// MARK: Actions

	@IBAction func goBackButtonClicked() {
		dismiss(animated: true, completion: nil)
	}

	// MARK: Public methods

	func load(_ assetEntry: Asset?) {

		if let asset = assetEntry {

			var classPK: Int64 = 0

			//Load asset
			assetDisplayScreenlet?.assetEntry = asset

			//Load asset comments
			if let image = asset as? ImageEntry {
				let className = AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!
				classPK = image.imageEntryId

				commentsViewController?.load(className: className, classPK: classPK)
			}
			else {
				let className = AssetClasses.getClassNameFromId(asset.classNameId)!
				classPK = asset.classPK
				
				commentsViewController?.load(className: className, classPK: classPK)
			}

			ratingScreenlet?.className = AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!
			ratingScreenlet?.classPK = classPK
			ratingScreenlet?.loadRatings()
		}
	}

	// MARK: CardViewController

	override func pageWillDisappear() {
		//Remove inner screenlet to avoid infinite media loops
		self.assetDisplayScreenlet?.removeInnerScreenlet()

		//Hide comment card
		self.commentsViewController?.hideAddCommentCard()
		self.cardDeck?.cards[safe: 0]?.changeToState(.minimized)
	}

	// MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		commentsViewController = CommentsViewController()
	}

	override func viewWillAppear(_ animated: Bool) {
		super.viewWillAppear(animated)

		//Hide all views
		self.assetDisplayScreenlet?.alpha = 0
		self.ratingScreenlet?.alpha = 0
	}

	// MARK: Initializers

	convenience init(nibName: String) {
		self.init(nibName: nibName, bundle: nil)
	}

	// MARK: AssetDisplayScreenletDelegate

	func screenlet(_ screenlet: AssetDisplayScreenlet, onAssetResponse asset: Asset) {
		//Change buttons text
		self.cardView?.changeButtonText(asset.title)
		self.goBackButton?.setTitle(asset.title.uppercased(), for: .normal)

		//Show back views
		UIView.animate(withDuration: 1.0, animations: {
			self.assetDisplayScreenlet?.alpha = 1.0
			self.ratingScreenlet?.alpha = 1.0
		})
	}

	// MARK: CardDeckDataSource

	func numberOfCardsIn(_ cardDeck: CardDeckView) -> Int {
		return 1
	}

	func doCreateCard(_ cardDeck: CardDeckView, index: Int) -> CardView? {
		return WesterosCardView.newAutoLayout()
	}

	func cardDeck(_ cardDeck: CardDeckView, controllerForCard position: CardPosition)
		-> CardViewController? {
			return commentsViewController
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

	func cardDeck(_ cardDeck: CardDeckView, titleForCard position: CardPosition) -> String? {
		return "Comments"
	}
}
