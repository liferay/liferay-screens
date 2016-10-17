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
@objc public protocol CardDelegate : NSObjectProtocol {

	///Get the title for a page of the card
	/// - parameter titleForPage page: index of the page
	/// - returns: title for the page
	optional func card(card: CardView,
					   titleForPage page: Int) -> String?

	///Called when trying to move to a page
	/// - parameter onWillMoveToPage page: index of the page
	/// - returns: true, if a view for the page has been added, false otherwise
	optional func card(card: CardView,
	                   onWillMoveToPage page: Int,
	                   fromPage previousPage: Int) -> Bool

	///Called when a card scroll content has move to another page
	/// - parameters:
	///    - onDidMoveToPage page: number of the page the card has moved to
	///    - moveToRight right: true if content move to the right
	optional func card(card: CardView,
	                   onDidMoveToPage page: Int,
	                   moveToRight right: Bool)
	
}

///Different states that a card can have
public enum ShowState {
	case Minimized, Normal, Maximized, Background, Hidden

	///True if in this state, card is in bottom (minimized or hidden)
	var isInBottom: Bool {
		return (self == .Minimized || self == .Hidden)
	}

	///True if state is visible (normal or maximized)
	var isVisible: Bool {
		return (self == .Normal || self == .Maximized)
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
public class CardView: UIView, CAAnimationDelegate {

	//Default configuration constants
	public static let DefaultMinimizedHeight: CGFloat = 70
	public static let DefaultAnimationTime: Double = 0.7
	public static let DefaultFontName = "AvenirNext-DemiBold"
	public static let DefaultFontSize: CGFloat = 18
	public static let DefaultCornerRadius: CGFloat = 4.0

	//Initialization variables
	public var minimizedHeight: CGFloat = CardView.DefaultMinimizedHeight
	public var normalHeight: CGFloat = 0
	public var maximizedMargin: CGFloat = 30

	//Subviews
	public var arrow: UIImageView = UIImageView.newAutoLayoutView()
	public var button: UIButton = UIButton.newAutoLayoutView()
	public var accesoryView: UIView? {
		didSet {
			if let accesory = accesoryView {
				self.button.addSubview(accesory)
				self.accesoryView?.autoAlignAxisToSuperviewAxis(.Horizontal)
				self.accesoryView?.autoPinEdgeToSuperviewEdge(
					.Right, withInset: CardView.DefaultMinimizedHeight / 2)
			}
		}
	}
	public var scrollView: UIScrollView = UIScrollView.newAutoLayoutView()
	public var cardContentView = UIView.newAutoLayoutView()
	public var scrollContentView: UIView = UIView.newAutoLayoutView()

	//Constraints
	private var didSetupConstraints = false
	private var heightConstraint: NSLayoutConstraint?
	private var contentBottomConstraint: NSLayoutConstraint?
	private var scrollContentWidthConstraint: NSLayoutConstraint?

	//States
	var currentState: ShowState = .Minimized
	var nextState: ShowState = .Normal
	var beforeBackgroundState: ShowState = .Normal

	///Delegate for customizing cards
	var delegate: CardDelegate?

	///Current page of the scroll view
	var currentPage: Int {
		let width = scrollView.frame.size.width
		return lround(Double(scrollView.contentOffset.x) / Double(width))
	}

	///Number of pages
	var pageCount: Int {
		return scrollContentView.subviews.count
	}

	//Controls
	var maximizeOnMove = true

	///This controller will be notified when the card appears/dissapears
	var presentingControllers = [CardViewController]()

	private var onChangeCompleted: (Bool -> Void)?

	private var backgroundTransform: CGAffineTransform?


	//MARK: Lifecycle methods

	///Called when card view is going to be initialized, this should be override.
	public func onPreCreate() {
	}

	///Called when card view is initialized, this should be override.
	public func onCreated() {
	}

	//MARK: Public methods

	///Adds a controller's subview to the contentview of this card. This should be the entry point
	///for all subviews of a card.
	/// - parameter controller: controller which view is going to be added as page
	public func addPageFromController(controller: CardViewController) {
		
		presentingControllers.append(controller)
		
		addPage(controller.view)
	}
	
	///Adds a view to the contentview of this card. This should be the entry point for all subviews
	///of a card.
	/// - parameter view: view that is going to be added as page
	public func addPage(view: UIView) {
		view.translatesAutoresizingMaskIntoConstraints = false

		let lastView = scrollContentView.subviews.last

		scrollContentView.addSubview(view)

		let viewCount = scrollContentView.subviews.count

		view.autoPinEdgeToSuperviewEdge(.Top)

		if (viewCount == 1) {
			//Set dimensions constraints
			view.autoMatchDimension(.Height, toDimension: .Height, ofView: cardContentView)
			view.autoMatchDimension(.Width, toDimension: .Width, ofView: cardContentView)

			//Pin left edge to left edge of scroll content view
			view.autoPinEdgeToSuperviewEdge(.Left)
		}
		else {
			//Update scroll content view width
			cardContentView.removeConstraint(scrollContentWidthConstraint!)

			scrollContentWidthConstraint = scrollContentView.autoMatchDimension(.Width,
				toDimension: .Width, ofView: cardContentView, withMultiplier: CGFloat(viewCount))

			if let last = lastView {
				//Set dimensions constraints
				view.autoMatchDimension(.Height, toDimension: .Height, ofView: last)
				view.autoMatchDimension(.Width, toDimension: .Width, ofView: last)

				//Pin left edge to right edge of previous view
				view.autoPinEdge(.Left, toEdge: .Right, ofView: last)
			}
		}
		
		scrollView.layoutIfNeeded()
	}

	///Remove a page view from the card
	/// - parameter index: index of the page
	public func removePageAtIndex(index: Int) {
		if scrollContentView.subviews.count > 1 {
			scrollContentView.subviews[safe: index]?.removeFromSuperview()
		}
	}

	///Moves the content inside the scrollview to the right. If no content found, it will try
	///to add it via delegate
	public func moveRight() {
		let nextPage = currentPage + 1
		moveToPage(nextPage, fromPage: currentPage)
	}

	///Moves the content inside the scrollview to the left.
	public func moveLeft() {
		if currentPage != 0 {
			let nextPage = currentPage - 1
			moveToPage(nextPage, fromPage: currentPage)
		}
	}

	///Moves the content inside the scrollview to a page
	/// - parameter page: index of the page to move to
	public func moveToPage(page: Int, fromPage previousPage: Int) {
		if delegate?.card?(self, onWillMoveToPage: page, fromPage: previousPage) ?? false {
			let rect = CGRectMake(scrollView.frame.size.width * CGFloat(page),
			                      y: 0, size: scrollView.frame.size)
			
			scrollView.scrollRectToVisible(rect, animated: true)
			
			//If it's one of the first views, rotate arrow accordingly
			if page < 2 {
				UIView.animateWithDuration(0.3, animations: {
					self.arrow.transform = page == 0 ?
						CGAffineTransformIdentity :
						CGAffineTransformMakeRotation(CGFloat(M_PI_2))
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
	public func changeButtonText(text: String?) {
		if let text = text {
			button.setTitle(text.uppercaseString, forState: .Normal)
		}
	}


	//MARK: View methods

	///Adds the button and arrow to the view. Sets the title view (button and arrow) properties
	///of this card
	/// - parameters:
	///    - title: title for the card
	///    - buttonFontColor: color for the button text
	///    - arrowImage: image to be used as "indicator" arrow
	public func initializeView(backgroundColor backgroundColor: UIColor,
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
		cardContentView.addSubview(scrollView)
		scrollView.addSubview(scrollContentView)

		self.backgroundColor = backgroundColor

		//Initialize scroll view
		scrollView.bounces = true
		scrollView.scrollEnabled = false
		scrollView.pagingEnabled = false

		setButton(buttonTitle, fontColor: fontColor)
		setArrowImage(image)

		onCreated()
	}

	///Sets the constant value for the height constraint for a given state.
	/// - parameter state: next state of the card
	public func setHeightConstraintForState(state: ShowState) {
		switch state {
		case .Minimized:
			self.heightConstraint?.constant = self.minimizedHeight
			break
		case .Normal:
			self.heightConstraint?.constant = self.normalHeight
			break
		case .Maximized:
			if let superView = self.superview {
				self.heightConstraint?.constant = superView.frame.height - self.maximizedMargin
				self.contentBottomConstraint?.constant = 0
			}
			break
		case .Hidden:
			self.heightConstraint?.constant = 0
			break
		default:
			break
		}
	}

	///Sets the arrow image
	/// - parameter image: UIImage for the arrow UIImageView
	public func setArrowImage(image: UIImage) {
		//Set arrow properties
		self.arrow.image = image
		self.arrow.alpha = 0.0
	}

	///Sets the button properties
	/// - parameters:
	///    - title: title for the button
	///    - fontColor: color for the title
	public func setButton(title: String?, fontColor: UIColor) {
		changeButtonText(title)
		self.button.titleLabel?.font = UIFont(name: CardView.DefaultFontName,
		                                      size: CardView.DefaultFontSize)
		self.button.setTitleColor(fontColor, forState: .Normal)
	}

	///Sets the constraints for all subviews. This method must be called from outside the card,
	///when all properties have been set.
	public func updateSubviewsConstraints() {
		//Arrow constraints
		if let image = self.arrow.image {
			self.arrow.autoSetDimension(.Height, toSize: image.size.height)
			self.arrow.autoSetDimension(.Width, toSize: image.size.width)
		}
		self.arrow.autoAlignAxisToSuperviewAxis(.Horizontal)
		self.arrow.autoPinEdgeToSuperviewEdge(.Left, withInset: CardView.DefaultMinimizedHeight / 2)

		//Button constraints
		self.button.autoPinEdgeToSuperviewEdge(.Left)
		self.button.autoPinEdgeToSuperviewEdge(.Right)
		self.button.autoPinEdgeToSuperviewEdge(.Top)
		self.button.autoSetDimension(.Height, toSize: CardView.DefaultMinimizedHeight)

		//Card content view constraints
		self.cardContentView.autoPinEdge(.Top, toEdge: .Bottom, ofView: button)
		self.cardContentView.autoPinEdgeToSuperviewEdge(.Left)
		self.cardContentView.autoPinEdgeToSuperviewEdge(.Right)
		contentBottomConstraint = self.cardContentView.autoPinEdgeToSuperviewEdge(
			.Bottom, withInset: self.minimizedHeight - CardView.DefaultMinimizedHeight,
			relation: .LessThanOrEqual)

		//Scrollview constraints
		self.scrollView.autoPinEdgesToSuperviewEdges()

		//ScrollView content view constraints
		self.scrollContentView.autoPinEdgesToSuperviewEdges()

		self.scrollContentView.autoMatchDimension(.Height, toDimension: .Height,
			ofView: cardContentView)
		self.scrollContentWidthConstraint =
			self.scrollContentView.autoMatchDimension(.Width, toDimension: .Width,
				ofView: cardContentView)
	}


	//MARK: State methods

	///Changes the card to a certain state
	public func changeToState(state: ShowState) {
		self.nextState = state
		self.changeToNextState()
	}

	///Resets the card to the current state
	public func resetToCurrentState() {
		setHeightConstraintForState(currentState)
		self.layoutIfNeeded()
	}

	///Changes the card to the following state
	/// - parameters:
	///    - time: how much time will take up the animation
	///    - delay: delay for the animation start
	///    - onComplete: closure to be executed when the animation finishes
	public func changeToNextState(animateArrow: Bool = true, time: Double? = nil,
	    	delay: Double = 0.0, animateContent: Bool = true, onComplete: (Bool -> Void)? = nil) {

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

		if currentState == .Maximized {
			self.contentBottomConstraint?.constant = (self.minimizedHeight -
				CardView.DefaultMinimizedHeight) * -1
		}

		//Sets the height for the next state
		setHeightConstraintForState(nextState)

		//If the state is background, change using CATransform3D
		if nextState == .Background {
			beforeBackgroundState = currentState
			onChangeCompleted = onComplete
			self.layer.addAnimation(backgroundAnimation(
				time ?? CardView.DefaultAnimationTime), forKey: "pushBackAnimation")
		}
		else {
			//If we are in background right now, first restore the matrix
			if currentState == .Background {
				transform = CGAffineTransformIdentity
				self.layer.addAnimation(resetBackgroundAnimation(
					time ?? CardView.DefaultAnimationTime), forKey: "popBackAnimation")
			}

			onChangeCompleted = nil

			let completion: (Bool -> ()) = { flag in
				if self.currentState.isInBottom || self.currentState == .Maximized {
					self.setNeedsDisplay()
				}
				onComplete?(flag)
			}

			let animateCardContent = animateContent && self.currentState.isInBottom

			if animateCardContent {
				self.cardContentView.alpha = 0
			} else if animateContent && nextState.isInBottom {
				UIView.animateWithDuration(0.3, animations: {self.cardContentView.alpha = 0})
			}

			let time = (time ?? CardView.DefaultAnimationTime) * 1.30

			//Animate the constraint change
			UIView.animateWithDuration(time,
				delay: delay,
				usingSpringWithDamping: 1.0,
				initialSpringVelocity: 0.0,
				options: [.BeginFromCurrentState, .CurveEaseIn],
				animations: {
					self.layoutIfNeeded()

					if self.currentState.isInBottom || self.nextState == .Maximized {
						self.setNeedsDisplay()
					}
				}, completion: completion)

			if animateCardContent {
				UIView.animateWithDuration(0.5,
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
	public func toggleArrow(time: Double? = nil, delay: Double = 0.0) {
		UIView.animateWithDuration(time ?? CardView.DefaultAnimationTime, delay: delay,
			options: UIViewAnimationOptions.CurveEaseIn,
		    animations: {
				self.arrow.alpha =
					self.nextState.isVisible ? 1.0 : 0.0
			}, completion: nil)
	}
	

	//MARK: Animation group delegate


	//Called when background animation finish
	public func animationDidStop(anim: CAAnimation, finished flag: Bool) {
		onChangeCompleted?(flag)
		onChangeCompleted = nil

		if let transform = backgroundTransform {
			self.transform = transform
			backgroundTransform = nil
		}
	}


	//MARK: Background animation methods

	///Returns the animated change from background to previous state
	/// - parameter animationTime: duration for the reset animation
	/// - returns: reset state animation
	public func resetBackgroundAnimation(animationTime: Double) -> CAAnimation {
		let animation = CABasicAnimation(keyPath: "transform")
		animation.toValue = NSValue(CATransform3D: CATransform3DIdentity)
		animation.duration = animationTime
		animation.fillMode = kCAFillModeForwards
		animation.removedOnCompletion = false
		animation.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseInEaseOut)

		return animation;
	}

	///Returns the animated change from previous state to background
	/// - parameter animationTime: duration for the background animation
	/// - returns: background state animation
	public func backgroundAnimation(animationTime: Double) -> CAAnimation {
		var t0 = CATransform3DIdentity
		let animation0 = CABasicAnimation(keyPath: "transform")

		t0.m34 = CGFloat(1.0)/CGFloat(-900)
		t0 = CATransform3DTranslate(t0, 0, maximizedMargin - self.frame.origin.y, 0)

		animation0.toValue = NSValue(CATransform3D: t0)
		animation0.duration = animationTime
		animation0.fillMode = kCAFillModeForwards
		animation0.removedOnCompletion = false
		animation0.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseIn)

		var t1 = t0
		t1.m34 = t0.m34
		t1 = CATransform3DScale(t1, 0.95, 0.95, 1)
		t1 = CATransform3DRotate(t1, CGFloat(10.0 * M_PI/180.0), 1, 0, 0)

		let animation1 = CABasicAnimation(keyPath: "transform")
		animation1.toValue = NSValue(CATransform3D: t1)
		animation1.duration = animationTime*3.0/4.0
		animation1.fillMode = kCAFillModeForwards
		animation1.removedOnCompletion = false
		animation1.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseOut)

		var t2 = t0
		t2.m34 = t1.m34
		t2 = CATransform3DTranslate(t2, 0, self.frame.size.height * CGFloat(-0.04), 0)
		t2 = CATransform3DScale(t2, 0.95, 0.90, 1)

		let animation2 = CABasicAnimation(keyPath: "transform")
		animation2.toValue = NSValue(CATransform3D: t2)
		animation2.beginTime = animation1.duration
		animation2.duration = animationTime*1.0/4.0
		animation2.fillMode = kCAFillModeForwards
		animation2.removedOnCompletion = false
		animation2.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseIn/*EaseOut*/)

		let group = CAAnimationGroup()
		group.fillMode = kCAFillModeForwards
		group.removedOnCompletion = false

		group.duration = animationTime

		group.animations = [animation0, animation1, animation2]

		group.delegate = self

		backgroundTransform = CATransform3DGetAffineTransform(t2)

		return group;
	}


	//MARK: UIView

	override public func drawRect(rect: CGRect) {
		super.drawRect(rect)

		//Create mask path with top rounding corners
		let maskPath = UIBezierPath(
				roundedRect: self.bounds,
				byRoundingCorners: [.TopLeft, .TopRight],
				cornerRadii: CGSize(width: CardView.DefaultCornerRadius,
					height: CardView.DefaultCornerRadius))

		//Create mask layer, with card bounds and previously mask
		let maskLayer = CAShapeLayer()
		maskLayer.frame = self.bounds
		maskLayer.path  = maskPath.CGPath

		//Apply layer to card mask
		self.layer.mask = maskLayer
	}

	public override func updateConstraints() {
		super.updateConstraints()

		if !self.didSetupConstraints {
			heightConstraint = autoSetDimension(.Height, toSize: minimizedHeight)

			self.didSetupConstraints = true
		}
	}
}
