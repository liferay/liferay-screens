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


///Indicates the position for a page inside a card
public class CardPosition: NSObject {
	let card: Int
	let page: Int

	init (card: Int, page: Int) {
		self.card = card
		self.page = page
	}
}


///Delegate for customizing cards inside a CardDeck
@objc public protocol CardDeckDelegate : NSObjectProtocol {

	///Get the title for a page in a card
	/// - parameter titleForCard position: position for the title
	/// - returns: title for the page
	optional func cardDeck(cardDeck: CardDeckView,
	                       titleForCard position: CardPosition) -> String?

	///Get the background color for a card
	/// - parameter colorForCardIndex index: position of the card
	/// - returns: background color for the card
	optional func cardDeck(cardDeck: CardDeckView,
	                       colorForCardIndex index: Int) -> UIColor?

	///Get the button text color for a card
	/// - parameter colorForButtonIndex index: position of the card
	/// - returns: color for the button text of the card
	optional func cardDeck(cardDeck: CardDeckView,
	                       colorForButtonIndex index: Int) -> UIColor?

	///Get the button image for a card
	/// - parameter buttonImageForCardIndex index: position of the card
	/// - returns: image for the button of the card
	optional func cardDeck(cardDeck: CardDeckView,
	                       buttonImageForCardIndex index: Int) -> UIImage?
	
}

///Data source for card decks
@objc public protocol CardDeckDataSource : NSObjectProtocol {

	///Get the number of cards in this deck
	func numberOfCardsIn(cardDeck: CardDeckView) -> Int

	///Get the CardViewController for a position
	/// - parameter controllerForCard position: position for the controller
	/// - returns: controller for given position
	func cardDeck(cardDeck: CardDeckView,
	              controllerForCard position: CardPosition) -> CardViewController?

	///Customize visual aspects of the card
	/// - parameters:
	///    - customizeCard card: card to be customized
	///    - atIndex index: index of the card
	optional func cardDeck(cardDeck: CardDeckView,
	              customizeCard card: CardView, atIndex index: Int)
	
}

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

	//Delegate for customizing cards for this deck
	public var delegate: CardDeckDelegate?

	//Data source for providing card content
	public var dataSource: CardDeckDataSource? {
		didSet {
			if let source = dataSource {
				let count = source.numberOfCardsIn(self)

				let initialHeight = CardView.DefaultMinimizedHeight

				for index in 0...count {
					//For each title we will create a card, each card should be on top of the previous ones
					let card = createCardForIndex(index)

					//The normal height for a card will be its parent size with a padding
					card.normalHeight = self.frame.size.height - CardDeckView.DefaultBackgroundSpacing

					//The minimized height for a card will be the initial height plus the height of the
					//previous ones
					card.minimizedHeight = initialHeight +
						CGFloat(count - index - 1) * initialHeight

					self.dataSource?.cardDeck?(self, customizeCard: card, atIndex: index)

					card.updateSubviewsConstraints()

					addSubview(card)

					//Set constraints for this card
					setConstraintsForCard(card)
				}
			}
		}
	}

	//List of cards in this deck
	public var cards: [CardView] {
		return self.subviews.filter{$0 is CardView}.map{$0 as! CardView}
	}

	//MARK: Public methods

	///Method launched when a button-card is clicked.
	///You can retrieve the card by getting the superview.
	///
	///    let card = sender.superview as? CardView
	///
	/// - parameter sender: button clicked
	public func cardTouchUpInside(sender: UIButton) {
		if let card = sender.superview as? CardView {

			if card.currentPage != 0 && card.currentState.isVisible {
				card.moveLeft()
			} else {
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
	public func createCardForIndex(index: Int) -> CardView {
		//Create Card
		let card = CardView.newAutoLayoutView()
		card.layer.zPosition = zPositionForIndex(index)

		let cardBackgroundColor = self.delegate?.cardDeck?(self, colorForCardIndex: index)
			?? DefaultResources.backgroundColorForIndex(index)

		let buttonFontColor = self.delegate?.cardDeck?(self, colorForButtonIndex: index)
			?? DefaultResources.textColorForIndex(index)

		let arrowImage = self.delegate?.cardDeck?(self, buttonImageForCardIndex: index)
			?? DefaultResources.arrowImageForIndex(index)

		let cardPosition = CardPosition(card: index, page: 0)

		let title = self.delegate?.cardDeck?(self, titleForCard: cardPosition)

		card.initializeView(backgroundColor: cardBackgroundColor,
		    buttonTitle: title, buttonFontColor: buttonFontColor,
			buttonImage: arrowImage)

		card.delegate = self

		card.button.addTarget(self, action: #selector(CardDeckView.cardTouchUpInside(_:)),
			forControlEvents: .TouchUpInside)

		let controller = dataSource?.cardDeck(self, controllerForCard: cardPosition)
		controller?.cardView = card 

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
			let cardPosition = CardPosition(card: index, page: page)
			return self.delegate?.cardDeck?(self, titleForCard: cardPosition)
		}

		return nil
	}

	public func card(card: CardView, onMissingPage page: Int) -> Bool {
		if let index = cards.indexOf(card) {
			let cardPosition = CardPosition(card: index, page: page)
			let controller = dataSource?.cardDeck(self, controllerForCard: cardPosition)
			controller?.cardView = card

			return controller != nil
		}

		return false
	}

	public func card(card: CardView, onMovingToPage page: Int, moveToRight right: Bool) {
		if card.maximizeOnMove {
			if page == 0 {
				let (top, bottom) = cards.splitAtIndex(cards.indexOf(card)!)

				//Send top cards to background
				top.forEach {
					change($0, toState: .Normal, animateArrow: false)
					change($0, toState: .Background)
				}

				//Minimize bottom cards
				bottom.forEach {
					change($0, toState: .Minimized)
				}

				change(card, toState: .Normal)

			} else if card.currentState != .Maximized {

				//Hide all cards, and maximize current card
				cards.filter({ $0 != card }).forEach {
					change($0, toState: .Hidden)
				}

				change(card, toState: .Maximized)
			}
		}
	}
}
