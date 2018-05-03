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

///Delegate for cards, usually it will be adopted by a CardDeckView
@objc public protocol CardDelegate: NSObjectProtocol {

	///Get the title for a page of the card
	/// - parameter titleForPage page: index of the page
	/// - returns: title for the page
	@objc optional func card(_ card: CardView,
					   titleForPage page: Int) -> String?

	///Called when trying to move to a page
	/// - parameter onWillMoveToPage page: index of the page
	/// - returns: true, if a view for the page has been added, false otherwise
	@objc optional func card(_ card: CardView,
	                   onWillMoveToPage page: Int,
	                   fromPage previousPage: Int) -> Bool

	///Called when a card scroll content has move to another page
	/// - parameters:
	///    - onDidMoveToPage page: number of the page the card has moved to
	///    - moveToRight right: true if content move to the right
	@objc optional func card(_ card: CardView,
	                   onDidMoveToPage page: Int,
	                   moveToRight right: Bool)

}

///Different states that a card can have
public enum ShowState {
	case minimized, normal, maximized, background, hidden

	///True if in this state, card is in bottom (minimized or hidden)
	var isInBottom: Bool {
		return (self == .minimized || self == .hidden)
	}

	///True if state is visible (normal or maximized)
	var isVisible: Bool {
		return (self == .normal || self == .maximized)
	}
}

///Custom view used to show one card in screen, this class will usually be used with a CardDeckView.
///
///To fully create a card you can do something like this:
///
///    let card = CardView.newAutoLayoutView()
///    card.backgroundColor = .redColor()
///    card.createTitleView("My card", buttonFontColor: .blackColor(), buttonImage: image)
///    card.button.addTarget(self, action: selector, forControlEvents: .TouchUpInside)
///
///CardView uses [PureLayout](https://github.com/PureLayout/PureLayout) to set its constraints.
///To get more information about how to tweak this constraints go to the 
///[API Cheat Sheet](https://github.com/PureLayout/PureLayout#api-cheat-sheet)
open class CardView: UIView, CAAnimationDelegate {

	//Default configuration constants
	open static let DefaultMinimizedHeight: CGFloat = 70
	open static let DefaultAnimationTime: Double = 0.7
	open static let DefaultFontName = "AvenirNext-DemiBold"
	open static let DefaultFontSize: CGFloat = 18
	open static let DefaultCornerRadius: CGFloat = 4.0

	//Initialization variables
	open var minimizedHeight: CGFloat = CardView.DefaultMinimizedHeight
	open var normalHeight: CGFloat = 0
	open var maximizedMargin: CGFloat = 30

	//Subviews
	open var arrow: UIImageView = UIImageView.newAutoLayout()
	open var button: UIButton = UIButton.newAutoLayout()
	open var accesoryView: UIView? {
		didSet {
			if let accesory = accesoryView {
				self.button.addSubview(accesory)
				self.accesoryView?.autoAlignAxis(toSuperviewAxis: .horizontal)
				self.accesoryView?.autoPinEdge(
					toSuperviewEdge: .right, withInset: CardView.DefaultMinimizedHeight / 2)
			}
		}
	}
	open var scroll: UIScrollView = UIScrollView.newAutoLayout()
	open var cardContentView = UIView.newAutoLayout()
	open var scrollContentView: UIView = UIView.newAutoLayout()

	//Constraints
	fileprivate var didSetupConstraints = false
	fileprivate var heightConstraint: NSLayoutConstraint?
	fileprivate var contentBottomConstraint: NSLayoutConstraint?
	fileprivate var scrollContentWidthConstraint: NSLayoutConstraint?

	//States
	var currentState: ShowState = .minimized
	var nextState: ShowState = .normal
	var beforeBackgroundState: ShowState = .normal

	///Delegate for customizing cards
	var delegate: CardDelegate?

	///Current page of the scroll view
	var currentPage: Int {
		let width = scroll.frame.size.width
		return lround(Double(scroll.contentOffset.x) / Double(width))
	}

	///Number of pages
	var pageCount: Int {
		return scrollContentView.subviews.count
	}

	//Controls
	var maximizeOnMove = true

	///This controller will be notified when the card appears/dissapears
	var presentingControllers = [CardViewController]()

	fileprivate var onChangeCompleted: ((Bool) -> Void)?

	fileprivate var backgroundTransform: CGAffineTransform?

	// MARK: Lifecycle methods

	///Called when card view is going to be initialized, this should be override.
	open func onPreCreate() {
	}

	///Called when card view is initialized, this should be override.
	open func onCreated() {
	}

	// MARK: Public methods

	///Adds a controller's subview to the contentview of this card. This should be the entry point
	///for all subviews of a card.
	/// - parameter controller: controller which view is going to be added as page
	open func addPageFromController(_ controller: CardViewController) {

		presentingControllers.append(controller)

		addPage(controller.view)
	}

	///Adds a view to the contentview of this card. This should be the entry point for all subviews
	///of a card.
	/// - parameter view: view that is going to be added as page
	open func addPage(_ view: UIView) {
		view.translatesAutoresizingMaskIntoConstraints = false

		let lastView = scrollContentView.subviews.last

		scrollContentView.addSubview(view)

		let viewCount = scrollContentView.subviews.count

		view.autoPinEdge(toSuperviewEdge: .top)

		if (viewCount == 1) {
			//Set dimensions constraints
			view.autoMatch(.height, to: .height, of: cardContentView)
			view.autoMatch(.width, to: .width, of: cardContentView)

			//Pin left edge to left edge of scroll content view
			view.autoPinEdge(toSuperviewEdge: .left)
		}
		else {
			//Update scroll content view width
			cardContentView.removeConstraint(scrollContentWidthConstraint!)

			scrollContentWidthConstraint = scrollContentView.autoMatch(.width,
				to: .width, of: cardContentView, withMultiplier: CGFloat(viewCount))

			if let last = lastView {
				//Set dimensions constraints
				view.autoMatch(.height, to: .height, of: last)
				view.autoMatch(.width, to: .width, of: last)

				//Pin left edge to right edge of previous view
				view.autoPinEdge(.left, to: .right, of: last)
			}
		}

		scroll.layoutIfNeeded()
	}

	///Remove a page view from the card
	/// - parameter index: index of the page
	open func removePageAtIndex(_ index: Int) {
		if scrollContentView.subviews.count > 1 {
			scrollContentView.subviews[safe: index]?.removeFromSuperview()
		}
	}

	///Moves the content inside the scrollview to the right. If no content found, it will try
	///to add it via delegate
	open func moveRight() {
		let nextPage = currentPage + 1
		moveToPage(nextPage, fromPage: currentPage)
	}

	///Moves the content inside the scrollview to the left.
	open func moveLeft() {
		if currentPage != 0 {
			let nextPage = currentPage - 1
			moveToPage(nextPage, fromPage: currentPage)
		}
	}

	///Moves the content inside the scrollview to a page
	/// - parameter page: index of the page to move to
	open func moveToPage(_ page: Int, fromPage previousPage: Int) {
		if delegate?.card?(self, onWillMoveToPage: page, fromPage: previousPage) ?? false {
			let rect = CGRect(x: scroll.frame.size.width * CGFloat(page),
			                      y: 0, size: scroll.frame.size)

			scroll.scrollRectToVisible(rect, animated: true)

			//If it's one of the first views, rotate arrow accordingly
			if page < 2 {
				UIView.animate(withDuration: 0.3, animations: {
					self.arrow.transform = page == 0 ?
						CGAffineTransform.identity :
						CGAffineTransform(rotationAngle: CGFloat(M_PI_2))
					}, completion: { _ in
						self.changeButtonText(self.delegate?.card?(self, titleForPage: page))
				})
			}
			//Notify the delegate that the scroll has changed
			self.delegate?.card?(self, onDidMoveToPage: page, moveToRight: false)
		}
	}

	///Change card button text title
	/// - parameter text: new text for the button
	open func changeButtonText(_ text: String?) {
		if let text = text {
			button.setTitle(text.uppercased(), for: .normal)
		}
	}

	// MARK: View methods

	///Adds the button and arrow to the view. Sets the title view (button and arrow) properties
	///of this card
	/// - parameters:
	///    - title: title for the card
	///    - buttonFontColor: color for the button text
	///    - arrowImage: image to be used as "indicator" arrow
	open func initializeView(backgroundColor: UIColor,
			buttonTitle: String?, buttonFontColor fontColor: UIColor, buttonImage image: UIImage) {
		onPreCreate()

		//Add button and arrow
		addSubview(button)
		button.addSubview(arrow)

		//Set button insets
		button.titleEdgeInsets = UIEdgeInsetsMake(
			0, CardView.DefaultMinimizedHeight, 0, CardView.DefaultMinimizedHeight)

		//Add content view and scrollview
		addSubview(cardContentView)
		cardContentView.addSubview(scroll)
		scroll.addSubview(scrollContentView)

		self.backgroundColor = backgroundColor

		//Initialize scroll view
		scroll.bounces = true
		scroll.isScrollEnabled = false
		scroll.isPagingEnabled = false

		setButton(buttonTitle, fontColor: fontColor)
		setArrowImage(image)

		onCreated()
	}

	///Sets the constant value for the height constraint for a given state.
	/// - parameter state: next state of the card
	open func setHeightConstraintForState(_ state: ShowState) {
		switch state {
		case .minimized:
			self.heightConstraint?.constant = self.minimizedHeight
			break
		case .normal:
			self.heightConstraint?.constant = self.normalHeight
			break
		case .maximized:
			if let superView = self.superview {
				self.heightConstraint?.constant = superView.frame.height - self.maximizedMargin
				self.contentBottomConstraint?.constant = 0
			}
			break
		case .hidden:
			self.heightConstraint?.constant = 0
			break
		default:
			break
		}
	}

	///Sets the arrow image
	/// - parameter image: UIImage for the arrow UIImageView
	open func setArrowImage(_ image: UIImage) {
		//Set arrow properties
		self.arrow.image = image
		self.arrow.alpha = 0.0
	}

	///Sets the button properties
	/// - parameters:
	///    - title: title for the button
	///    - fontColor: color for the title
	open func setButton(_ title: String?, fontColor: UIColor) {
		changeButtonText(title)
		self.button.titleLabel?.font = UIFont(name: CardView.DefaultFontName,
		                                      size: CardView.DefaultFontSize)
		self.button.setTitleColor(fontColor, for: .normal)
	}

	///Sets the constraints for all subviews. This method must be called from outside the card,
	///when all properties have been set.
	open func updateSubviewsConstraints() {
		//Arrow constraints
		if let image = self.arrow.image {
			self.arrow.autoSetDimension(.height, toSize: image.size.height)
			self.arrow.autoSetDimension(.width, toSize: image.size.width)
		}
		self.arrow.autoAlignAxis(toSuperviewAxis: .horizontal)
		self.arrow.autoPinEdge(toSuperviewEdge: .left, withInset: CardView.DefaultMinimizedHeight / 2)

		//Button constraints
		self.button.autoPinEdge(toSuperviewEdge: .left)
		self.button.autoPinEdge(toSuperviewEdge: .right)
		self.button.autoPinEdge(toSuperviewEdge: .top)
		self.button.autoSetDimension(.height, toSize: CardView.DefaultMinimizedHeight)

		//Card content view constraints
		self.cardContentView.autoPinEdge(.top, to: .bottom, of: button)
		self.cardContentView.autoPinEdge(toSuperviewEdge: .left)
		self.cardContentView.autoPinEdge(toSuperviewEdge: .right)
		contentBottomConstraint = self.cardContentView.autoPinEdge(
			toSuperviewEdge: .bottom, withInset: self.minimizedHeight - CardView.DefaultMinimizedHeight,
			relation: .lessThanOrEqual)

		//Scrollview constraints
		self.scroll.autoPinEdgesToSuperviewEdges()

		//ScrollView content view constraints
		self.scrollContentView.autoPinEdgesToSuperviewEdges()

		self.scrollContentView.autoMatch(.height, to: .height,
			of: cardContentView)
		self.scrollContentWidthConstraint =
			self.scrollContentView.autoMatch(.width, to: .width,
				of: cardContentView)
	}

	// MARK: State methods

	///Changes the card to a certain state
	open func changeToState(_ state: ShowState) {
		self.nextState = state
		self.changeToNextState()
	}

	///Resets the card to the current state
	open func resetToCurrentState() {
		setHeightConstraintForState(currentState)
		self.superview?.layoutIfNeeded()
	}

	///Changes the card to the following state
	/// - parameters:
	///    - time: how much time will take up the animation
	///    - delay: delay for the animation start
	///    - onComplete: closure to be executed when the animation finishes
	open func changeToNextState(_ animateArrow: Bool = true, time: Double? = nil,
	    	delay: Double = 0.0, animateContent: Bool = true, onComplete: ((Bool) -> Void)? = nil) {

		//Exit if we are asked to change to our current state
		if nextState == currentState {
			return
		}

		//Notify the view controller, if any
		if nextState.isVisible != currentState.isVisible {
			if nextState.isVisible {
				presentingControllers[safe: currentPage]?.pageWillAppear()
			}
			else {
				presentingControllers[safe: currentPage]?.pageWillDisappear()
			}
		}

		if currentState == .maximized {
			self.contentBottomConstraint?.constant = (self.minimizedHeight -
				CardView.DefaultMinimizedHeight) * -1
		}

		//Sets the height for the next state
		setHeightConstraintForState(nextState)

		//If the state is background, change using CATransform3D
		if nextState == .background {
			beforeBackgroundState = currentState
			onChangeCompleted = onComplete
			self.layer.add(backgroundAnimation(
				time ?? CardView.DefaultAnimationTime), forKey: "pushBackAnimation")
		}
		else {
			//If we are in background right now, first restore the matrix
			if currentState == .background {
				transform = CGAffineTransform.identity
				self.layer.add(resetBackgroundAnimation(
					time ?? CardView.DefaultAnimationTime), forKey: "popBackAnimation")
			}

			onChangeCompleted = nil

			let completion: ((Bool) -> Void) = { flag in
				if self.currentState.isInBottom || self.currentState == .maximized {
					self.setNeedsDisplay()
				}
				onComplete?(flag)
			}

			let animateCardContent = animateContent && self.currentState.isInBottom

			if animateCardContent {
				self.cardContentView.alpha = 0
			}
			else if animateContent && nextState.isInBottom {
				UIView.animate(withDuration: 0.3, animations: {self.cardContentView.alpha = 0})
			}

			let time = (time ?? CardView.DefaultAnimationTime) * 1.30

			//Animate the constraint change
			UIView.animate(withDuration: time,
				delay: delay,
				usingSpringWithDamping: 1.0,
				initialSpringVelocity: 0.0,
				options: [.beginFromCurrentState, .curveEaseIn],
				animations: {
					self.superview?.layoutIfNeeded()

					if self.currentState.isInBottom || self.nextState == .maximized {
						self.setNeedsDisplay()
					}
				}, completion: completion)

			if animateCardContent {
				UIView.animate(withDuration: 0.5,
					delay: (delay + time) * 0.3,
					options: [],
					animations: {
						self.cardContentView.alpha = 1
					}, completion: nil)
			}
		}

		//Show/hide the arrow
		if animateArrow {
			toggleArrow()
		}

		self.currentState = self.nextState
	}

	///Toggles the arrow visibility, depending on the next state.
	/// - parameters:
	///    - time: how much time will take up the animation
	///    - delay: delay for the animation start
	open func toggleArrow(_ time: Double? = nil, delay: Double = 0.0) {
		UIView.animate(withDuration: time ?? CardView.DefaultAnimationTime, delay: delay,
			options: UIViewAnimationOptions.curveEaseIn,
		    animations: {
				self.arrow.alpha =
					self.nextState.isVisible ? 1.0 : 0.0
			}, completion: nil)
	}

	// MARK: Animation group delegate

	//Called when background animation finish
	open func animationDidStop(_ anim: CAAnimation, finished flag: Bool) {
		onChangeCompleted?(flag)
		onChangeCompleted = nil

		if let transform = backgroundTransform {
			self.transform = transform
			backgroundTransform = nil
		}
	}

	// MARK: Background animation methods

	///Returns the animated change from background to previous state
	/// - parameter animationTime: duration for the reset animation
	/// - returns: reset state animation
	open func resetBackgroundAnimation(_ animationTime: Double) -> CAAnimation {
		let animation = CABasicAnimation(keyPath: "transform")
		animation.toValue = NSValue(caTransform3D: CATransform3DIdentity)
		animation.duration = animationTime
		animation.fillMode = kCAFillModeForwards
		animation.isRemovedOnCompletion = false
		animation.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseInEaseOut)

		return animation
	}

	///Returns the animated change from previous state to background
	/// - parameter animationTime: duration for the background animation
	/// - returns: background state animation
	open func backgroundAnimation(_ animationTime: Double) -> CAAnimation {
		var t0 = CATransform3DIdentity
		let animation0 = CABasicAnimation(keyPath: "transform")

		t0.m34 = CGFloat(1.0)/CGFloat(-900)
		t0 = CATransform3DTranslate(t0, 0, maximizedMargin - self.frame.origin.y, 0)

		animation0.toValue = NSValue(caTransform3D: t0)
		animation0.duration = animationTime
		animation0.fillMode = kCAFillModeForwards
		animation0.isRemovedOnCompletion = false
		animation0.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseIn)

		var t1 = t0
		t1.m34 = t0.m34
		t1 = CATransform3DScale(t1, 0.95, 0.95, 1)
		t1 = CATransform3DRotate(t1, CGFloat(10.0 * M_PI/180.0), 1, 0, 0)

		let animation1 = CABasicAnimation(keyPath: "transform")
		animation1.toValue = NSValue(caTransform3D: t1)
		animation1.duration = animationTime*3.0/4.0
		animation1.fillMode = kCAFillModeForwards
		animation1.isRemovedOnCompletion = false
		animation1.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseOut)

		var t2 = t0
		t2.m34 = t1.m34
		t2 = CATransform3DTranslate(t2, 0, self.frame.size.height * CGFloat(-0.04), 0)
		t2 = CATransform3DScale(t2, 0.95, 0.90, 1)

		let animation2 = CABasicAnimation(keyPath: "transform")
		animation2.toValue = NSValue(caTransform3D: t2)
		animation2.beginTime = animation1.duration
		animation2.duration = animationTime*1.0/4.0
		animation2.fillMode = kCAFillModeForwards
		animation2.isRemovedOnCompletion = false
		animation2.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseIn/*EaseOut*/)

		let group = CAAnimationGroup()
		group.fillMode = kCAFillModeForwards
		group.isRemovedOnCompletion = false

		group.duration = animationTime

		group.animations = [animation0, animation1, animation2]

		group.delegate = self

		backgroundTransform = CATransform3DGetAffineTransform(t2)

		return group
	}

	// MARK: UIView

	override open func draw(_ rect: CGRect) {
		super.draw(rect)

		//Create mask path with top rounding corners
		let maskPath = UIBezierPath(
				roundedRect: self.bounds,
				byRoundingCorners: [.topLeft, .topRight],
				cornerRadii: CGSize(width: CardView.DefaultCornerRadius,
					height: CardView.DefaultCornerRadius))

		//Create mask layer, with card bounds and previously mask
		let maskLayer = CAShapeLayer()
		maskLayer.frame = self.bounds
		maskLayer.path  = maskPath.cgPath

		//Apply layer to card mask
		self.layer.mask = maskLayer
	}

	open override func updateConstraints() {
		super.updateConstraints()

		if !self.didSetupConstraints {
			heightConstraint = autoSetDimension(.height, toSize: minimizedHeight)

			self.didSetupConstraints = true
		}
	}
}
