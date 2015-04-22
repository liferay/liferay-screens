//
//  OnBoardingCardDeck.swift
//  WesterosBank
//
//  Created by jmWork on 22/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

class OnBoardingCardDeck: CardDeckView {

	override func topCardTouchUpInside(sender: UIButton) {
		switch topCard!.currentState {
		case .Minimized:
			topCard!.nextState = .Normal

		case .Maximized:
			topCard!.nextState = .Normal

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
			topCard!.nextState = .Normal
		}

		bottomCard!.changeToNextState()
		topCard!.changeToNextState()
	}

}
