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


class SignInViewController: CardViewController {

	@IBOutlet weak var scroll: UIScrollView!
	@IBOutlet weak var forgotTitle: UIButton!
	@IBOutlet weak var backArrow: UIImageView!

	override init(card: CardView, nibName: String) {
		let save = card.minimizedHeight
		card.minimizedHeight = 0
		super.init(card: card, nibName: nibName)
		card.minimizedHeight = save
	}

	convenience init(card: CardView) {
		self.init(card: card, nibName:"SignInViewController")
	}

	required init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

	override func viewDidLoad() {
		scroll.contentSize = CGSizeMake(scroll.frame.size.width * 2, scroll.frame.size.height)
	}

	override func viewWillAppear(animated: Bool) {
		if cardView!.button!.superview !== scroll {
			cardView!.button!.removeFromSuperview()
			scroll.addSubview(cardView!.button!)
		}
	}

	@IBAction func backAction(sender: AnyObject) {
		UIView.animateWithDuration(0.3,
				animations: {
					self.forgotTitle.alpha = 0.0
					self.backArrow.alpha = 0.0
				},
				completion: nil)

		let newRect = CGRectMake(0, 0, scroll.frame.size)
		scroll.scrollRectToVisible(newRect, animated: true)
	}

	@IBAction func forgotPasswordAction(sender: AnyObject) {
		self.forgotTitle.alpha = 0.0
		self.backArrow.alpha = 0.0

		UIView.animateWithDuration(1.0,
				animations: {
					self.forgotTitle.alpha = 1.0
					self.backArrow.alpha = 1.0
				},
				completion: nil)

		let newRect = CGRectMake(scroll.frame.size.width, 0, scroll.frame.size)
		scroll.scrollRectToVisible(newRect, animated: true)
	}

}
