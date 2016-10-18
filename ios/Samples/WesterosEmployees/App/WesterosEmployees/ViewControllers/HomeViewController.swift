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
import MBProgressHUD
import LiferayScreens

public var tourCompleted = false

class HomeViewController: UIViewController, AssetListScreenletDelegate,
	CardDeckDelegate, CardDeckDataSource {
	
	///Flag to control if the home has been initialized
	var homeInitialized = false


	//MARK: Outlets

	@IBOutlet weak var cardDeck: CardDeckView? {
		didSet {
			cardDeck?.delegate = self
			cardDeck?.dataSource = self
			cardDeck?.layer.zPosition = 0
		}
	}
	@IBOutlet weak var userView: UIView? {
		didSet {
			userView?.layer.zPosition = -1000
		}
	}
	@IBOutlet weak var userPortraitScreenlet: UserPortraitScreenlet?
	@IBOutlet weak var userNameLabel: UILabel?
	@IBOutlet weak var assetListScreenlet: AssetListScreenlet? {
		didSet {
			assetListScreenlet?.delegate = self
			assetListScreenlet?.alpha = 0
		}
	}
	@IBOutlet weak var userProfileButton: UIButton?
	@IBOutlet weak var latestChangesLabel: UILabel? {
		didSet {
			latestChangesLabel?.alpha = 0
		}
	}


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

		documentationViewController = DocumentationViewController()
		blogsViewController = BlogsViewController()
		galleryViewController = GalleryViewController()
	}

	override func viewWillAppear(animated: Bool) {
		super.viewWillAppear(animated)

		self.cardDeck?.alpha = SessionContext.isLoggedIn ? 1.0 : 0.0
	}

	override func viewDidAppear(animated: Bool) {
		super.viewDidAppear(animated)

		SessionContext.loadStoredCredentials()
		
		if !SessionContext.isLoggedIn {
			homeInitialized = false
			
			if !tourCompleted {
				dispatch_delayed(0.5) {
					self.performSegueWithIdentifier("tour", sender: nil)
				}
			}
			else {
				self.performSegueWithIdentifier("login", sender: nil)
			}
		}
		else if !homeInitialized {
			tourCompleted = true
			
			//Initialize home only once
			initializeHome()
			homeInitialized = true
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
			case (0, 1), (2, 1):
				return DetailViewController(nibName: "DetailViewController")
			case (1, 1):
				return DetailViewController(nibName: "BlogDetailViewController")
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

		if let vc = cardDeck.cards[position.card].presentingControllers[safe: position.page]
				as? DetailViewController {
			switch (position.card, position.page) {
			case (0, 1):
				vc.load(documentationViewController?.selectedFileEntry)
			case (1, 1):
				vc.load(blogsViewController?.selectedBlogEntry)
			case (2, 1):
				vc.load(galleryViewController?.selectedImageEntry)
			default:
				break
			}
		}
	}


	//MARK: AssetListScreenletDelegate

	func screenlet(screenlet: AssetListScreenlet, onAssetSelected asset: Asset) {
		if let className = AssetClasses.getClassNameFromId(asset.classNameId) {
			var detail: DetailViewController?

			//Change view controller background depending on asset
			if className == AssetClasses.getClassName(AssetClassNameKey_BlogsEntry)! {
				detail = DetailViewController(nibName: "ModalBlogDetailViewController")
			} else {
				detail = DetailViewController(nibName: "ModalDetailViewController")
			}

			//Present view controller and load content
			self.presentViewController(detail!, animated: true) {
				detail?.load(asset)
			}
		}
	}


	//MARK: Private functions

	private func initializeHome() {

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

				SessionContext.currentContext?.cacheManager.countPendingToSync { count in
					if count > 0 {
						self.showSyncAlert(count)
					}
				}
			}
		})
	}

	private func showSyncAlert(syncCount: UInt) {

		let message = syncCount > 1 ? "There are \(syncCount) elements to be synchronized" :
				"There is \(syncCount) element to be synchronized"

		let alert = UIAlertController(
				title: "Pending synchronization",
				message: message,
				preferredStyle: .Alert)

		let syncAction = UIAlertAction(title: "Start syncing", style: .Default) { _ in
			if let cacheManager = SessionContext.currentContext?.cacheManager {
				let sync = SyncManager(cacheManager: cacheManager)
				sync.startSync()
			}
		}

		let cancelAction = UIAlertAction(title: "Cancel", style: .Cancel, handler: nil)

		alert.addAction(syncAction)
		alert.addAction(cancelAction)

		presentViewController(alert, animated: true, completion: nil)
	}
}

