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

class HomeViewController: UIViewController, AssetListScreenletDelegate, CardDeckDelegate, CardDeckDataSource {

	///Flag to control if the home has been initialized
	var homeInitialized = false

	// MARK: Outlets

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

	// MARK: Card controllers

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

	// MARK: Actions

	@IBAction func userButtonClicked() {
		performSegue(withIdentifier: "user_profile", sender: self)
	}

	// MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		documentationViewController = DocumentationViewController()
		blogsViewController = BlogsViewController()
		galleryViewController = GalleryViewController()
	}

	override func viewWillAppear(_ animated: Bool) {
		super.viewWillAppear(animated)

		self.cardDeck?.alpha = SessionContext.isLoggedIn ? 1.0 : 0.0
	}

	override func viewDidAppear(_ animated: Bool) {
		super.viewDidAppear(animated)

        if SessionContext.currentContext?.session == nil {
            SessionContext.loadStoredCredentials()
        }

		if !SessionContext.isLoggedIn {
			homeInitialized = false

			if !tourCompleted {
				dispatch_delayed(0.5) {
					self.performSegue(withIdentifier: "tour", sender: nil)
				}
			}
			else {
				self.performSegue(withIdentifier: "login", sender: nil)
			}
		}
		else if !homeInitialized {
			tourCompleted = true

			//Initialize home only once
			initializeHome()
			homeInitialized = true
		}
	}

	// MARK: CardDeckDataSource

	func numberOfCardsIn(_ cardDeck: CardDeckView) -> Int {
		return 3
	}

	func doCreateCard(_ cardDeck: CardDeckView, index: Int) -> CardView? {
		return WesterosCardView.newAutoLayout()
	}

	func cardDeck(_ cardDeck: CardDeckView, controllerForCard position: CardPosition)
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

	// MARK: CardDeckDelegate

	func cardDeck(_ cardDeck: CardDeckView, customizeCard card: CardView, atIndex index: Int) {
		if index % 2 == 0 {
			(card as? WesterosCardView)?.activityIndicator.color = WesterosThemeBasicRed
		}
	}

	func cardDeck(_ cardDeck: CardDeckView, titleForCard position: CardPosition) -> String? {
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

	func cardDeck(_ cardDeck: CardDeckView, onPageChange position: CardPosition) {

		dispatch_delayed(1.0) {
			self.userProfileButton?.isEnabled = position.page == 0
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

	// MARK: AssetListScreenletDelegate

	func screenlet(_ screenlet: AssetListScreenlet, onAssetSelected asset: Asset) {
		if let className = AssetClasses.getClassNameFromId(asset.classNameId) {
			var detail: DetailViewController?

			//Change view controller background depending on asset
			if className == AssetClasses.getClassName(AssetClassNameKey_BlogsEntry)! {
				detail = DetailViewController(nibName: "ModalBlogDetailViewController")
			} else {
				detail = DetailViewController(nibName: "ModalDetailViewController")
			}

			//Present view controller and load content
			self.present(detail!, animated: true) {
				detail?.load(asset)
			}
		}
	}

	// MARK: Private functions

	fileprivate func initializeHome() {

		//Load user profile
		let userId = SessionContext.currentContext!.user.userId
		
		self.userPortraitScreenlet?.load(userId: userId)
			
		let firstName = SessionContext.currentContext!.user.firstName
		let lastName = SessionContext.currentContext!.user.lastName

		self.userNameLabel?.text = "\(firstName) \(lastName)".trimmingCharacters(in: .whitespacesAndNewlines)

		self.assetListScreenlet?.loadList()

		self.cardDeck?.alpha = 1.0

		//Show second card with a small delay
		self.cardDeck?.cards[1].currentState = .hidden
		self.cardDeck?.cards[1].resetToCurrentState()
		self.cardDeck?.cards[1].changeToState(.minimized)

		//Show first card with a big delay
		self.cardDeck?.cards[0].currentState = .hidden
		self.cardDeck?.cards[0].resetToCurrentState()
		self.cardDeck?.cards[0].nextState = .minimized
		self.cardDeck?.cards[0].changeToNextState(delay: 0.5, onComplete: { _ in
			UIView.animate(withDuration: 0.5, animations: {
				self.assetListScreenlet?.alpha = 1.0
				self.latestChangesLabel?.alpha = 1.0

				SessionContext.currentContext?.cacheManager.countPendingToSync { count in
					if count > 0 {
						self.showSyncAlert(count)
					}
				}
			})
		})
	}

	fileprivate func showSyncAlert(_ syncCount: UInt) {

		let message = syncCount > 1 ? "There are \(syncCount) elements to be synchronized" :
				"There is \(syncCount) element to be synchronized"

		let alert = UIAlertController(
				title: "Pending synchronization",
				message: message,
				preferredStyle: .alert)

		let syncAction = UIAlertAction(title: "Start syncing", style: .default) { _ in
			if let cacheManager = SessionContext.currentContext?.cacheManager {
				let sync = SyncManager(cacheManager: cacheManager)
				sync.startSync()
			}
		}

		let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: nil)

		alert.addAction(syncAction)
		alert.addAction(cancelAction)

		present(alert, animated: true, completion: nil)
	}
}
