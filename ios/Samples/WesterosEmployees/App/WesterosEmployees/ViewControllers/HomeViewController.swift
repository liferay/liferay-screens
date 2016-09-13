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

public var tourCompleted = false

class HomeViewController: UIViewController, AssetDisplayScreenletDelegate,
	CardDeckDelegate, CardDeckDataSource {


	//MARK: Outlets

	@IBOutlet weak var cardDeck: CardDeckView?
	@IBOutlet weak var userView: UIView?
	@IBOutlet weak var userPortraitScreenlet: UserPortraitScreenlet?
	@IBOutlet weak var userNameLabel: UILabel?
	@IBOutlet weak var assetListScreenlet: AssetListScreenlet?
	@IBOutlet weak var userProfileButton: UIButton?

	//MARK: Card ViewControllers

	var documentationViewController: DocumentationViewController?
	var blogsViewController: BlogsViewController?
	var galleryViewController: GalleryViewController?


	//MARK: View actions

	@IBAction func userButtonClicked() {
		performSegueWithIdentifier("user_profile", sender: self)
	}


	//MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		self.userView?.layer.zPosition = -1000
		self.cardDeck?.layer.zPosition = 0

		documentationViewController = DocumentationViewController()
		blogsViewController = BlogsViewController()
		galleryViewController = GalleryViewController()

		cardDeck?.delegate = self
		cardDeck?.dataSource = self
	}

	override func viewWillAppear(animated: Bool) {
		super.viewWillAppear(animated)
		let isLoggedIn = SessionContext.isLoggedIn

		if isLoggedIn {

			//Load user profile
			if userPortraitScreenlet?.userId != SessionContext.currentContext!.userId! {
				userPortraitScreenlet?.load(userId: SessionContext.currentContext!.userId!)
				let firstName = SessionContext.currentContext!.userAttribute("firstName") as! String
				let lastName = SessionContext.currentContext!.userAttribute("lastName") as! String
				userNameLabel?.text = "\(firstName) \(lastName)".stringByTrimmingCharactersInSet(
					NSCharacterSet.whitespaceAndNewlineCharacterSet())
			}

			assetListScreenlet?.loadList()

			self.cardDeck?.alpha = 1.0

			//Load lists accross the app
			self.documentationViewController?.load()
			self.blogsViewController?.load()
			self.galleryViewController?.load()

			//Show second card with a small delay
			self.cardDeck?.cards[1].currentState = .Hidden
			self.cardDeck?.cards[1].resetToCurrentState()
			self.cardDeck?.cards[1].nextState = .Minimized
			self.cardDeck?.cards[1].changeToNextState(delay: 0.5)

			//Show first card with a big delay
			self.cardDeck?.cards[0].currentState = .Hidden
			self.cardDeck?.cards[0].resetToCurrentState()
			self.cardDeck?.cards[0].nextState = .Minimized
			self.cardDeck?.cards[0].changeToNextState(delay: 1.0)
		} else {
			self.cardDeck?.alpha = 0.0
		}
	}

	override func viewDidAppear(animated: Bool) {
		super.viewDidAppear(animated)

		if !SessionContext.isLoggedIn {
			if !tourCompleted {
				dispatch_delayed(0.5) {
					self.performSegueWithIdentifier("tour", sender: nil)
				}
			}
			else {
				self.performSegueWithIdentifier("login", sender: nil)
			}
		}
	}

	//MARK: CardDeckDataSource

	func numberOfCardsIn(cardDeck: CardDeckView) -> Int {
		return 3
	}

	func cardDeck(cardDeck: CardDeckView, controllerForCard position: CardPosition)
		-> CardViewController? {
			switch (position.card, position.page) {
			case (0, 0):
				return documentationViewController
			case (1, 0):
				return blogsViewController
			case (2, 0):
				return galleryViewController
			case (1, 1), (2, 1):
				return DetailViewController()
			default:
				return nil
			}
	}


	//MARK: CardDeckDelegate

	func cardDeck(cardDeck: CardDeckView, customizeCard card: CardView, atIndex index: Int) {
		if index == 2 {
			let image = UIImage(named: "icon_ADD")
			if let image = image {
				card.setSecondaryButtonImage(image)
			}
		}
	}

	func cardDeck(cardDeck: CardDeckView, titleForCard position: CardPosition) -> String? {
		switch (position.card, position.page) {
		case (0, 0):
			return "Documentation"
		case (1, 0):
			return "Blogs"
		case (2, 0):
			return "Gallery"
		default:
			return nil
		}
	}

	func cardDeck(cardDeck: CardDeckView, onPageChange position: CardPosition) {

		dispatch_delayed(1.0) {
			self.userProfileButton?.enabled = position.page == 0
		}

		if let vc = cardDeck.cards[position.card].presentingController as? DetailViewController {
			switch (position.card, position.page) {
			case (1, 1):
				vc.className = AssetClasses.getClassName(AssetClassNameKey_BlogsEntry)!
				vc.classPK = blogsViewController?.selectedBlogEntry?.classPK
				vc.load()
			case (2, 1):
				vc.className = AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!
				vc.classPK = galleryViewController?.selectedImageEntry?.imageEntryId
				cardDeck.cards[2].secondaryButton.alpha = 0.0
				vc.load()
			default:
				break
			}
		}
	}
}

