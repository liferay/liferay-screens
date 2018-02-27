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
@objc open class CardPosition: NSObject {
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
	@objc optional func cardDeck(_ cardDeck: CardDeckView,
	                       titleForCard position: CardPosition) -> String?

	///Get the background color for a card
	/// - parameter colorForCardIndex index: position of the card
	/// - returns: background color for the card
	@objc optional func cardDeck(_ cardDeck: CardDeckView,
	                       colorForCardIndex index: Int) -> UIColor?

	///Get the button text color for a card
	/// - parameter colorForButtonIndex index: position of the card
	/// - returns: color for the button text of the card
	@objc optional func cardDeck(_ cardDeck: CardDeckView,
	                       colorForButtonIndex index: Int) -> UIColor?

	///Get the button image for a card
	/// - parameter buttonImageForCardIndex index: position of the card
	/// - returns: image for the button of the card
	@objc optional func cardDeck(_ cardDeck: CardDeckView,
	                       buttonImageForCardIndex index: Int) -> UIImage?
	
	///Customize visual aspects of the card
	/// - parameters:
	///    - customizeCard card: card to be customized
	///    - atIndex index: index of the card
	@objc optional func cardDeck(_ cardDeck: CardDeckView,
	                       customizeCard card: CardView, atIndex index: Int)

	///Notify that a card page have changed
	/// - parameter onPageChange position: position of the change
	@objc optional func cardDeck(_ cardDeck: CardDeckView,
	                       onPageChange position: CardPosition)
}

///Data source for card decks
@objc public protocol CardDeckDataSource : NSObjectProtocol {

	///Get the number of cards in this deck
	func numberOfCardsIn(_ cardDeck: CardDeckView) -> Int

	///Create a card object for a certain index
	@objc optional func doCreateCard(_ cardDeck: CardDeckView, index: Int) -> CardView?

	///Get the CardViewController for a position
	/// - parameter controllerForCard position: position for the controller
	/// - returns: controller for given position
	func cardDeck(_ cardDeck: CardDeckView,
	              controllerForCard position: CardPosition) -> CardViewController?
}

///View used to hold an array of cards. This class will auto arrange them in screen and handle
///its states.
///
///You can create a CardDeckView using interface builder and then create its cards programatically:
///
///    cardDeck.addCards(["Top card", "Middle card", "Bottom card"])
///
@objcMembers
open class CardDeckView: UIView, CardDelegate {

	//Default configuration constants
	open static let DefaultBackgroundSpacing: CGFloat = 70
	open static let DefaultZPositionMultiplier: CGFloat = 1000

	//Delegate for customizing cards for this deck
	open var delegate: CardDeckDelegate?

	//Data source for providing card content
	open var dataSource: CardDeckDataSource?

	//List of cards in this deck
	open var cards: [CardView] {
		return self.subviews.filter{$0 is CardView}.map{$0 as! CardView}
	}


	//MARK: UIView

	open override func willMove(toWindow newWindow: UIWindow?) {
		super.willMove(toWindow: newWindow)
		
		self.layoutIfNeeded()
		
		if cards.isEmpty {
			guard let source = dataSource else { return }
			let count = source.numberOfCardsIn(self)

			let initialHeight = CardView.DefaultMinimizedHeight

			for index in 0...(count - 1) {
				//For each title we will create a card, each card should be on top of the previous ones
				let card = createCardForIndex(index)

				//The normal height for a card will be its parent size with a padding
				card.normalHeight = self.frame.size.height - CardDeckView.DefaultBackgroundSpacing

				//The minimized height for a card will be the initial height plus the height of the
				//previous ones
				card.minimizedHeight = initialHeight +
					CGFloat(count - index - 1) * initialHeight

				addSubview(card)
				
				//Set constraints for this card
				card.updateSubviewsConstraints()
				setConstraintsForCard(card)

				self.delegate?.cardDeck?(self, customizeCard: card, atIndex: index)
			}
		}
	}

	//MARK: Public methods

	///Method launched when a button-card is clicked.
	///You can retrieve the card by getting the superview.
	///
	///    let card = sender.superview as? CardView
	///
	/// - parameter sender: button clicked
	open func cardTouchUpInside(_ sender: UIButton) {
		if let card = sender.superview as? CardView {

			if card.currentPage != 0 && card.currentState.isVisible {
				card.moveLeft()
			} else {
				//Split from the clicked card
				let (top, bottom) = cards.splitAtIndex(cards.index(of: card)!)

				switch (card.currentState) {
				case .minimized:
					//If the card is minimized all top-cards go to background
					top.forEach {
						if $0.currentState != .background {
							change($0, toState: .normal, animateArrow: false)
							change($0, toState: .background)
						}
					}

					//Actual card should appear in screen
					change(card, toState: .normal)

					//Make sure bottom cards stay minimized
					bottom.forEach {
						change($0, toState: .minimized)
					}

				case .maximized, .normal:
					//Minimize all cards
					cards.forEach {
						change($0, toState: .minimized)
					}

				case .background:
					//Bring the card to foreground
					change(card, toState: .normal)

					//Make sure bottom cards stay minimized
					bottom.forEach {
						change($0, toState: .minimized)
					}
					
				default:
					break
				}
			}
		}
	}

	///Sets the constraints for a card
	/// - parameter card: the card to set the constraints to
	open func setConstraintsForCard(_ card: CardView) {
		card.autoPinEdge(toSuperviewEdge: .bottom)
		card.autoPinEdge(toSuperviewEdge: .left)
		card.autoPinEdge(toSuperviewEdge: .right)
	}

	///Change a card to a state
	/// - parameters:
	///    - card: card to be changed
	///    - toState: next state for the card
	open func change(_ card: CardView, toState state: ShowState, animateArrow: Bool = true,
			time: Double? = nil, delay: Double = 0.0, onComplete: ((Bool) -> Void)? = nil) {
		card.nextState = state
		card.changeToNextState(animateArrow, time: time, delay: delay, onComplete: onComplete)
	}

	///Creates a card with a certain title, for a certain index
	/// - parameters:
	///    - title: title for the card
	///    - index: index of this card in the deck
	/// - returns: the CardView object
	open func createCardForIndex(_ index: Int) -> CardView {
		//Create Card
		let card = self.dataSource?.doCreateCard?(self, index: index) ??
			CardView.newAutoLayout()
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
			for: .touchUpInside)

		let controller = dataSource?.cardDeck(self, controllerForCard: cardPosition)
		controller?.cardView = card 

		return card
	}

	///Returns the position in the z axis for a given card index
	/// - parameter index: the card index
	/// - returns: z position for this index
	open func zPositionForIndex(_ index: Int) -> CGFloat {
		return CardDeckView.DefaultZPositionMultiplier * (CGFloat(index) + 1)
	}


	//MARK: CardDelegate

	open func card(_ card: CardView, titleForPage page: Int) -> String? {
		if let index = cards.index(of: card) {
			let cardPosition = CardPosition(card: index, page: page)
			return self.delegate?.cardDeck?(self, titleForCard: cardPosition)
		}

		return nil
	}

	open func card(_ card: CardView, onWillMoveToPage page: Int, fromPage previousPage: Int) -> Bool {
		if let index = cards.index(of: card) {
			
			//Notify the previous controller that its page it's going to disappear
			card.presentingControllers[safe: previousPage]?.pageWillDisappear()
			
			if card.pageCount <= page {
				let cardPosition = CardPosition(card: index, page: page)
				let controller = dataSource?.cardDeck(self, controllerForCard: cardPosition)
				
				controller?.cardView = card
				
				return controller != nil
			}
			
			//Notify the new presenting controller that its page it's appearing
			card.presentingControllers[safe: page]?.pageWillAppear()

			return true
		}

		return false
	}

	open func card(_ card: CardView, onDidMoveToPage page: Int, moveToRight right: Bool) {
		if card.maximizeOnMove {
			if page == 0 {
				let (top, bottom) = cards.splitAtIndex(cards.index(of: card)!)

				//Send top cards to background
				top.forEach {
					change($0, toState: .normal, animateArrow: false)
					change($0, toState: .background)
				}

				//Minimize bottom cards
				bottom.forEach {
					change($0, toState: .minimized)
				}

				change(card, toState: .normal)

			} else if card.currentState != .maximized {

				//Hide all cards, and maximize current card
				cards.filter({ $0 != card }).forEach {
					change($0, toState: .hidden)
				}

				change(card, toState: .maximized)
			}
		}

		if let index = cards.index(of: card) {
			let cardPosition = CardPosition(card: index, page: page)
			self.delegate?.cardDeck?(self, onPageChange: cardPosition)
		}
	}
}
