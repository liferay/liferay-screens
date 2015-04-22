//
//  OnBoardingCardDeck.swift
//  WesterosBank
//
//  Created by jmWork on 22/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

class IssuesCardDeck: CardDeckView {

	override func topCardTouchUpInside(sender: UIButton) {
		switch topCard!.currentState {
		case .Minimized:
			topCard!.nextState = .Maximized

		case .Maximized:
			topCard!.nextState = (bottomCard!.currentState == .Normal)
					? .Maximized : .Minimized

			bottomCard!.nextState = .Minimized
			bottomCard!.changeToNextState()

		default:
			topCard!.nextState = .Minimized
		}

		topCard!.changeToNextState()
	}

	override func bottomCardTouchUpInside(sender: UIButton) {
		if bottomCard!.currentState == .Minimized {
			bottomCard!.nextState = .Normal
			topCard!.nextState = .Maximized
		}
		else {
			bottomCard!.nextState = .Minimized
			topCard!.nextState = .Maximized
		}

		bottomCard!.changeToNextState()
		topCard!.changeToNextState()
	}

}
