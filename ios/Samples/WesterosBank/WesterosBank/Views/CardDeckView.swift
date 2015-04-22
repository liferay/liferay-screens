//
//  CardDeckView.swift
//  WesterosBank
//
//  Created by jmWork on 21/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

class CardDeckView: UIView {

	var topCard: CardView? {
		didSet {
			if let cardValue = topCard {
				cardValue.currentState = .Minimized
				cardValue.nextState = .Normal

				addButton(cardValue)
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
