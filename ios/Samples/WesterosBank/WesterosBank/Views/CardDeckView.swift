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
				setUpCard(cardValue)

				addButton(cardValue, fontColor: Colors.mainRed)

				cardValue.layer.zPosition = -layer.bounds.size.width
			}
		}
	}

	var bottomCard: CardView? {
		didSet {
			if let cardValue = bottomCard {
				setUpCard(cardValue)

				topCard?.minimizedHeight += cardValue.minimizedHeight

				addButton(cardValue, fontColor: UIColor.whiteColor())
			}
		}
	}

	func setUpCard(card: CardView) {
		card.currentState = .Minimized
		card.nextState = .Normal

		card.layer.cornerRadius = 4.0
	}

	func addButton(card: CardView, fontColor: UIColor) {
		let button = UIButton(frame: CGRectMake(0, 0, card.frame.width, card.minimizedHeight))
		button.setTitle(card.title, forState: .Normal)
		button.setTitleColor(fontColor, forState: .Normal)

		let actionName = card === topCard
				? "topCardTouchUpInside:" : "bottomCardTouchUpInside:"
		button.addTarget(self,
				action: Selector(actionName),
				forControlEvents: UIControlEvents.TouchUpInside)

		card.addSubview(button)

		card.createArrow(fontColor)
	}


	func topCardTouchUpInside(sender: UIButton) {
	}

	func bottomCardTouchUpInside(sender: UIButton) {
	}

}
