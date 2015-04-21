//
//  CardDeckView.swift
//  WesterosBank
//
//  Created by jmWork on 21/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

class CardDeckView: UIView {

	private var cards = [CardView]()

	func addCard(card: CardView, withController controller: UIViewController) {
		func addButton() {
			let button = UIButton(frame: CGRectMake(0, 0, card.frame.width, card.minimizedHeight))
			button.setTitle(card.title, forState: UIControlState.Normal)

			let actionName = cards.isEmpty
					? "topCardTouchUpInside:" : "cardTouchUpInside:"
			button.addTarget(self,
					action: Selector(actionName),
					forControlEvents: UIControlEvents.TouchUpInside)

			button.tag = cards.count
			card.addSubview(button)
		}

		card.currentState = .Minimized
		card.nextState = .Normal

		controller.view.frame = CGRectMake(
				0, card.minimizedHeight,
				card.frame.width, controller.view.frame.height)

		card.controller = controller

		cards.map {
			$0.minimizedHeight += card.minimizedHeight
		}

		addButton()

		cards.append(card)
	}

	func topCardTouchUpInside(sender: UIButton) {
		let card = cards[sender.tag]

		switch card.currentState {
		case .Minimized:
			card.nextState = .Normal
		case .Maximized:
			card.nextState = .Normal

			for i in sender.tag + 1..<cards.count {
				let c = cards[i]
				c.nextState = .Minimized
				c.changeToNextState()
			}

		default:
			card.nextState = .Minimized
		}

		card.changeToNextState()
	}

	func cardTouchUpInside(sender: UIButton) {
		let touchedCard = cards[sender.tag]

		cards.map { card -> Void in
			switch card.currentState {
				case .Minimized:
					card.nextState = (card === touchedCard) ? .Normal : .Maximized
				case .Normal:
					card.nextState = (card === touchedCard) ? .Minimized : .Maximized
				default:
					card.nextState = (card === touchedCard) ? .Minimized : .Normal
			}
			card.changeToNextState()
		}
	}

}
