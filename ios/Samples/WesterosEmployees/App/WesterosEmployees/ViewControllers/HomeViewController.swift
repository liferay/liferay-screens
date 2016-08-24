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

class HomeViewController: UIViewController {

	@IBOutlet weak var cardDeck: CardDeckView!
	@IBOutlet weak var userProfileView: UIView!

	override func viewDidLoad() {
		super.viewDidLoad()

		cardDeck.addCards(["Documentation", "Blogs", "Gallery"])

		self.userProfileView.layer.zPosition = -1000
		self.cardDeck.layer.zPosition = 0
	}

	override func viewWillAppear(animated: Bool) {
		let isLoggedIn = SessionContext.isLoggedIn
		if isLoggedIn {
			UIView.animateWithDuration(1.5) {
				self.userProfileView.alpha = 1.0
			}
		} else {
			self.userProfileView.alpha = 0.0
		}

		cardDeck.cards.forEach {
			$0.hidden = !isLoggedIn
		}
	}

	override func viewDidAppear(animated: Bool) {
		super.viewDidAppear(animated)

		if !SessionContext.isLoggedIn {
			if !tourCompleted {
				performSegueWithIdentifier("tour", sender: nil)
			}
			else {
				performSegueWithIdentifier("login", sender: nil)
			}
		}
		
	}

}

