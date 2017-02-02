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

	override func topCardTouchUpInside(_ sender: UIButton) {
		super.topCardTouchUpInside(sender)

		switch topCard!.currentState {
		case .minimized:
			topCard!.nextState = .normal

		case .maximized:
			topCard!.nextState = .normal

			bottomCard!.nextState = .minimized
			bottomCard!.changeToNextState()

		default:
			topCard!.nextState = .minimized
		}

		topCard!.changeToNextState()
	}

	override func bottomCardTouchUpInside(_ sender: UIButton) {
		super.bottomCardTouchUpInside(sender)

		if bottomCard!.currentState == .minimized {
			bottomCard!.nextState = .normal
			topCard!.nextState = .background
		}
		else {
			bottomCard!.nextState = .minimized
			topCard!.nextState = .minimized
		}

		bottomCard!.changeToNextState()
		topCard!.changeToNextState()
	}

}
