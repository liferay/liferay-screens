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

class CardDeckView: UIView {

	var topCard: CardView? {
		didSet {
			if let cardValue = topCard {
				cardValue.currentState = .Minimized
				cardValue.nextState = .Normal

				addButton(cardValue)

				cardValue.layer.zPosition = -layer.bounds.size.width
			}
		}
	}

	var bottomCard: CardView? {
		didSet {
			if let cardValue = bottomCard {
				cardValue.currentState = .Minimized
				cardValue.nextState = .Normal

				topCard?.minimizedHeight += cardValue.minimizedHeight

				addButton(cardValue)
			}
		}
	}

	func addButton(card: CardView) {
		let button = UIButton(frame: CGRectMake(0, 0, card.frame.width, card.minimizedHeight))
		button.setTitle(card.title, forState: UIControlState.Normal)

		let actionName = card === topCard
				? "topCardTouchUpInside:" : "bottomCardTouchUpInside:"
		button.addTarget(self,
				action: Selector(actionName),
				forControlEvents: UIControlEvents.TouchUpInside)

		card.addSubview(button)
	}


	func topCardTouchUpInside(sender: UIButton) {
	}

	func bottomCardTouchUpInside(sender: UIButton) {
	}

}
