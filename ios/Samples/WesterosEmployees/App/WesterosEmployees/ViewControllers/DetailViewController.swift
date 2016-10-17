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

class DetailViewController: CardViewController, AssetDisplayScreenletDelegate,
	CardDeckDelegate, CardDeckDataSource {
	
	//MARK: Outlets

	@IBOutlet weak var assetDisplayScreenlet: AssetDisplayScreenlet? {
		didSet {
			self.assetDisplayScreenlet?.delegate = self
		}
	}
	@IBOutlet weak var cardDeck: CardDeckView? {
		didSet {
			cardDeck?.delegate = self
			cardDeck?.dataSource = self
		}
	}
	@IBOutlet weak var goBackButton: UIButton? {
		didSet {
			let height = goBackButton?.frame.size.height ?? CardView.DefaultMinimizedHeight
			goBackButton?.titleEdgeInsets = UIEdgeInsetsMake(0, height, 0, height)
		}
	}
	@IBOutlet weak var arrowImageView: UIImageView?
	@IBOutlet weak var ratingScreenlet: RatingScreenlet?


	//MARK: Card controllers

	var commentsViewController: CommentsViewController? {
		didSet {
			self.addChildViewController(commentsViewController!)
		}
	}


	//MARK: View methods

	@IBAction func goBackButtonClicked() {
		dismissViewControllerAnimated(true, completion: nil)
	}


	//MARK: Public methods

	func load(assetEntry: Asset?) {

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
				commentsViewController?.load(
					className: AssetClasses.getClassNameFromId(asset.classNameId)!,
					classPK: asset.classPK)
			}
			
			ratingScreenlet?.className = AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!
			ratingScreenlet?.classPK = classPK
			ratingScreenlet?.loadRatings()
		}
	}

	func closeDetail() {
		//Remove inner screenlet to avoid infinite media loops
		self.assetDisplayScreenlet?.removeInnerScreenlet()

		//Hide comment card
		self.commentsViewController?.hideAddCommentCard()
		self.cardDeck?.cards[safe: 0]?.changeToState(.Minimized)
	}
	

	//MARK: UIViewController
	
	override func viewDidLoad() {
		super.viewDidLoad()

		self.cardDeck?.layer.zPosition = 0
		
		commentsViewController = CommentsViewController()
	}

	override func viewWillAppear(animated: Bool) {
		super.viewWillAppear(animated)
		
		//Hide all views
		self.assetDisplayScreenlet?.alpha = 0
		self.ratingScreenlet?.alpha = 0
	}

	override func viewWillDisappear(animated: Bool) {
		super.viewWillAppear(animated)

		self.closeDetail()
	}


	//MARK: Init methods

	convenience init(nibName: String) {
		self.init(nibName: nibName, bundle: nil)
	}


	//MARK: AssetDisplayScreenletDelegate

	func screenlet(screenlet: AssetDisplayScreenlet, onAssetResponse asset: Asset) {
		//Change buttons text
		self.cardView?.changeButtonText(asset.title)
		self.goBackButton?.setTitle(asset.title.uppercaseString, forState: .Normal)

		//Show back views
		UIView.animateWithDuration(1.0) {
			self.assetDisplayScreenlet?.alpha = 1.0
			self.ratingScreenlet?.alpha = 1.0
		}
	}
	

	//MARK: CardDeckDataSource

	func numberOfCardsIn(cardDeck: CardDeckView) -> Int {
		return 1
	}

	func doCreateCard(cardDeck: CardDeckView, index: Int) -> CardView? {
		return WesterosCardView.newAutoLayoutView()
	}

	func cardDeck(cardDeck: CardDeckView, controllerForCard position: CardPosition)
		-> CardViewController? {
			return commentsViewController
	}


	//MARK: CardDeckDelegate

	func cardDeck(cardDeck: CardDeckView, customizeCard card: CardView, atIndex index: Int) {
		if let cardView = self.cardView {
			card.normalHeight = cardView.frame.height * 0.95
		}
		else {
			card.normalHeight = self.view.frame.height * 0.85
		}
	}

	func cardDeck(cardDeck: CardDeckView, titleForCard position: CardPosition) -> String? {
		return "Comments"
	}
}
