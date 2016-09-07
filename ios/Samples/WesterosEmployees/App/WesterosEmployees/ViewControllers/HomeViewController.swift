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
	@IBOutlet weak var userProfileView: AssetDisplayScreenlet?


	//MARK: Card ViewControllers

	var documentationViewController: DocumentationViewController?
	var blogsViewController: BlogsViewController?
	var galleryViewController: GalleryViewController?


	//MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		self.userProfileView?.layer.zPosition = -1000
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
			self.userProfileView?.delegate = self

			//Load user profile
			if userProfileView?.classPK != SessionContext.currentContext!.userId! {
				userProfileView?.className = AssetClasses.getClassName(AssetClassNameKey_User)!
				userProfileView?.classPK = SessionContext.currentContext!.userId!
				userProfileView?.load()
			}

			//self.documentationViewController?.reload()
			self.galleryViewController?.reload()

			self.cardDeck?.alpha = 1.0
			self.userProfileView?.alpha = 1.0

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
			self.userProfileView?.alpha = 0.0
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


	//MARK: AssetDisplayScreenletDelegate

	func screenlet(screenlet: AssetDisplayScreenlet, onAsset asset: Asset) -> UIView? {
		if let type = asset.attributes["object"]?.allKeys.first as? String {
			if type == "user" {
				let vc = UserDisplayViewController(nibName: "UserDisplayViewController", bundle: nil)
				self.addChildViewController(vc)
				screenlet.addSubview(vc.view)
				vc.view.frame = screenlet.bounds
				vc.user = User(attributes: asset.attributes)
			}
		}
		return nil
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
			default:
				return nil
			}
	}


	//MARK: CardDeckDelegate

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
}

