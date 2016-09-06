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
import PureLayout

///View used to hold an array of cards. This class will auto arrange them in screen and handle
///its states.
///
///You can create a CardDeckView using interface builder and then create its cards programatically:
///
///    cardDeck.addCards(["Top card", "Middle card", "Bottom card"])
///
public class CardDeckView: UIView, CardDelegate {

	//Default configuration constants
	public static let DefaultBackgroundSpacing: CGFloat = 70
	public static let DefaultZPositionMultiplier: CGFloat = 1000

	//List of cards in this deck
	public var cards: [CardView] {
		return self.subviews.filter{$0 is CardView}.map{$0 as! CardView}
	}

	//MARK: Public methods

	///Creates an array of cards usign the received arguments as titles.
	/// - parameter titles: array of titles for the cards
	public func addCards(titles: [String]) {
		let initialHeight = CardView.DefaultMinimizedHeight

		for index in 0..<titles.count {
			//For each title we will create a card, each card should be on top of the previous ones
			let card = createCard(titles[index], index: index)

			//The normal height for a card will be its parent size with a padding
			card.normalHeight = self.frame.size.height - CardDeckView.DefaultBackgroundSpacing

			//The minimized height for a card will be the initial height plus the height of the
			//previous ones
			card.minimizedHeight = initialHeight +
				CGFloat(titles.count - index - 1) * initialHeight

			card.updateSubviewsConstraints()

			addSubview(card)

			//Set constraints for this card
			setConstraintsForCard(card)
		}
	}

	///Method launched when a button-card is clicked.
	///You can retrieve the card by getting the superview.
	///
	///    let card = sender.superview as? CardView
	///
	/// - parameter sender: button clicked
	public func cardTouchUpInside(sender: UIButton) {
		if let card = sender.superview as? CardView {

			//Split from the clicked card
			let (top, bottom) = cards.splitAtIndex(cards.indexOf(card)!)

			switch (card.currentState) {
			case .Minimized:
				//If the card is minimized all top-cards go to background
				top.forEach {
					if $0.currentState != .Background {
						change($0, toState: .Normal, animateArrow: false)
						change($0, toState: .Background)
					}
				}

				//Actual card should appear in screen
				change(card, toState: .Normal)

				//Make sure bottom cards stay minimized
				bottom.forEach {
					change($0, toState: .Minimized)
				}

			case .Maximized, .Normal:
				//Minimize all cards
				cards.forEach {
					change($0, toState: .Minimized)
				}

			case .Background:
				//Bring the card to foreground
				change(card, toState: .Normal)

				//Make sure bottom cards stay minimized
				bottom.forEach {
					change($0, toState: .Minimized)
				}

			default:
				break
			}
		}
	}

	///Sets the constraints for a card
	/// - parameter card: the card to set the constraints to
	public func setConstraintsForCard(card: CardView) {
		card.autoPinEdgeToSuperviewEdge(.Bottom)
		card.autoPinEdgeToSuperviewEdge(.Left)
		card.autoPinEdgeToSuperviewEdge(.Right)
	}

	///Change a card to a state
	/// - parameters:
	///    - card: card to be changed
	///    - toState: next state for the card
	public func change(card: CardView, toState state: CardView.ShowState, animateArrow: Bool = true,
			time: Double? = nil, delay: Double = 0.0, onComplete: (Bool -> Void)? = nil) {
		card.nextState = state
		card.changeToNextState(animateArrow, time: time, delay: delay, onComplete: onComplete)
	}

	///Creates a card with a certain title, for a certain index
	/// - parameters:
	///    - title: title for the card
	///    - index: index of this card in the deck
	/// - returns: the CardView object
	public func createCard(title: String, index: Int) -> CardView {
		//Create Card
		let card = CardView.newAutoLayoutView()
		card.layer.zPosition = zPositionForIndex(index)
		card.initializeView(backgroundColor: Resources.backgroundColorForIndex(index),
		    buttonTitle: title, buttonFontColor: Resources.textColorForIndex(index),
			arrowImage: Resources.arrowImageForIndex(index))
		card.delegate = self

		card.button.addTarget(self, action: #selector(CardDeckView.cardTouchUpInside(_:)),
			forControlEvents: .TouchUpInside)

		return card
	}

	///Returns the position in the z axis for a given card index
	/// - parameter index: the card index
	/// - returns: z position for this index
	public func zPositionForIndex(index: Int) -> CGFloat {
		return CardDeckView.DefaultZPositionMultiplier * (CGFloat(index) + 1)
	}


	//MARK: CardDelegate

	public func card(card: CardView, titleForPage page: Int) -> String? {
		if let index = cards.indexOf(card) {
		}

		return nil
	}

	public func card(card: CardView, onMissingPage page: Int) -> Bool {
		if let index = cards.indexOf(card) {
		}

		return false
	}
}
