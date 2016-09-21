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
	@IBOutlet weak var latestChangesLabel: UILabel?

	//MARK: Card controllers

	var documentationViewController: DocumentationViewController? {
		didSet {
			addChildViewController(documentationViewController!)
		}
	}

	var blogsViewController: BlogsViewController? {
		didSet {
			addChildViewController(blogsViewController!)
		}
	}

	var galleryViewController: GalleryViewController? {
		didSet {
			addChildViewController(galleryViewController!)
		}
	}


	//MARK: View actions

	@IBAction func userButtonClicked() {
		performSegueWithIdentifier("user_profile", sender: self)
	}


	//MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		self.userView?.layer.zPosition = -1000
		self.cardDeck?.layer.zPosition = 0

		self.assetListScreenlet?.alpha = 0
		self.latestChangesLabel?.alpha = 0

		documentationViewController = DocumentationViewController()
		blogsViewController = BlogsViewController()
		galleryViewController = GalleryViewController()

		cardDeck?.delegate = self
		cardDeck?.dataSource = self
	}

	override func viewWillAppear(animated: Bool) {
		super.viewWillAppear(animated)

		self.cardDeck?.alpha = SessionContext.isLoggedIn ? 1.0 : 0.0
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

	override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
		if segue.identifier == "login" {
			if let authController = segue.destinationViewController as? AuthViewController {
				authController.onAuthDone = {
					//Load user profile
					let userId = SessionContext.currentContext!.userId!
					if self.userPortraitScreenlet?.userId != userId {
						self.userPortraitScreenlet?.load(userId: userId)
						let firstName =
							SessionContext.currentContext!.userAttribute("firstName") as! String
						let lastName =
							SessionContext.currentContext!.userAttribute("lastName") as! String
						self.userNameLabel?.text = "\(firstName) \(lastName)"
							.stringByTrimmingCharactersInSet(
								NSCharacterSet.whitespaceAndNewlineCharacterSet())
					}

					self.assetListScreenlet?.loadList()

					self.cardDeck?.alpha = 1.0

					//Show second card with a small delay
					self.cardDeck?.cards[1].currentState = .Hidden
					self.cardDeck?.cards[1].resetToCurrentState()
					self.cardDeck?.cards[1].changeToState(.Minimized)

					//Show first card with a big delay
					self.cardDeck?.cards[0].currentState = .Hidden
					self.cardDeck?.cards[0].resetToCurrentState()
					self.cardDeck?.cards[0].nextState = .Minimized
					self.cardDeck?.cards[0].changeToNextState(delay: 0.5, onComplete: { _ in
						UIView.animateWithDuration(0.5) {
							self.assetListScreenlet?.alpha = 1.0
							self.latestChangesLabel?.alpha = 1.0
						}
					})
				}
			}
		}
	}

	//MARK: CardDeckDataSource

	func numberOfCardsIn(cardDeck: CardDeckView) -> Int {
		return 3
	}

	func doCreateCard(cardDeck: CardDeckView, index: Int) -> CardView? {
		return WesterosCardView.newAutoLayoutView()
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
			case (0, 1), (1, 1), (2, 1):
				return DetailViewController()
			default:
				return nil
			}
	}


	//MARK: CardDeckDelegate

	func cardDeck(cardDeck: CardDeckView, customizeCard card: CardView, atIndex index: Int) {
		if index % 2 == 0 {
			(card as? WesterosCardView)?.activityIndicator.color = WesterosThemeBasicRed
		}
	}

	func cardDeck(cardDeck: CardDeckView, titleForCard position: CardPosition) -> String? {
		switch (position.card, position.page) {
		case (0, 0):
			return "Docs"
		case (1, 0):
			return "Company news"
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
			case (0, 1):
				vc.load(
					className: AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!,
					classPK: (documentationViewController?.selectedFileEntry?.classPK)!)
			case (1, 1):
				vc.load(
					className: AssetClasses.getClassName(AssetClassNameKey_BlogsEntry)!,
					classPK: (blogsViewController?.selectedBlogEntry?.classPK)!)
			case (2, 1):
				vc.load(
					className: AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!,
					classPK: (galleryViewController?.selectedImageEntry?.imageEntryId)!)
			case (_, 0):
				vc.hideCommentsCard()
			default:
				break
			}
		}
	}
}

