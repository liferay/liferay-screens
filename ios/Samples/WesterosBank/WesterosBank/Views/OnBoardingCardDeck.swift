//
//  OnBoardingCardDeck.swift
//  WesterosBank
//
//  Created by jmWork on 22/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

class OnBoardingCardDeck: CardDeckView {

	override func topCardAction(touchedCard: CardView) {
		switch touchedCard.currentState {
		case .Minimized:
			touchedCard.nextState = .Normal

		case .Maximized:
			touchedCard.nextState = .Normal

			cards.map { card -> Void in
				if card !== touchedCard {
					card.nextState = .Minimized
					card.changeToNextState()
				}
			}

		default:
			touchedCard.nextState = .Minimized
		}

		touchedCard.changeToNextState()
	}

	override func cardAction(touchedCard: CardView) {
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
