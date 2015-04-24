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
			topCard!.nextState = .Background
		}
		else {
			bottomCard!.nextState = .Minimized
			topCard!.nextState = .Minimized
		}

		bottomCard!.changeToNextState()
		topCard!.changeToNextState()
	}

}
