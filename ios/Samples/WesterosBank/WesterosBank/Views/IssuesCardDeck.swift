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
