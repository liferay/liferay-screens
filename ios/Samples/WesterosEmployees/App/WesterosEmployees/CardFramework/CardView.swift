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

///Custom view used to show one card in screen, this class will usually be used with a CardDeckView.
///
///To fully create a card you can do something like this:
///
///    let card = CardView.newAutoLayoutView()
///    card.backgroundColor = .redColor()
///    card.createTitleView("My card", buttonFontColor: .blackColor(), arrowImage: image)
///    card.button.addTarget(self, action: selector, forControlEvents: .TouchUpInside)
///
///CardView uses [PureLayout](https://github.com/PureLayout/PureLayout) to set its constraints.
///To get more information about how to tweak this constraints go to the 
///[API Cheat Sheet](https://github.com/PureLayout/PureLayout#api-cheat-sheet)
public class CardView: UIView {

	///Different states that a card can have
	public enum ShowState {
		case Minimized, Normal, Maximized, Background, Hidden

		var isVisible: Bool {
			return (self == .Normal || self == .Maximized)
		}
	}

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

	//Card properties
	public var title: String = "Card" {
		didSet {
			self.button.setTitle(title.uppercaseString, forState: .Normal)
		}
	}

	//Subviews
	public var arrow: UIImageView = UIImageView.newAutoLayoutView()
	public var button: UIButton = UIButton.newAutoLayoutView()
	public var scrollView: UIScrollView = UIScrollView.newAutoLayoutView()
	public var cardContentView = UIView.newAutoLayoutView()
	public var scrollContentView: UIView = UIView.newAutoLayoutView()

	//Constraints
	private var didSetupConstraints = false
	private var heightConstraint: NSLayoutConstraint?
	private var scrollContentWidthConstraint: NSLayoutConstraint?

	//States
	var currentState: ShowState = .Minimized
	var nextState: ShowState = .Normal
	var beforeBackgroundState: ShowState = .Normal

	///This controller will be notified when the card appears/dissapears
	weak var presentingController: CardViewController?

	private var onChangeCompleted: (Bool -> Void)?

	private var backgroundTransform: CGAffineTransform?


	//MARK: Public methods

	///Adds a subview to the contentview of this card. This should be the entry point for all
	///subviews of a card.
	/// - parameter view: view to be added
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
	}


	//MARK: View methods

	///Adds the button and arrow to the view. Sets the title view (button and arrow) properties
	///of this card
	/// - parameters:
	///    - title: title for the card
	///    - buttonFontColor: color for the button text
	///    - arrowImage: image to be used as "indicator" arrow
	public func initializeView(title: String, buttonFontColor fontColor: UIColor,
	                         arrowImage image: UIImage) {
		//Add button and arrow
		addSubview(button)
		button.addSubview(arrow)

		//Add content view and scrollview
		addSubview(cardContentView)
		cardContentView.addSubview(scrollView)
		scrollView.addSubview(scrollContentView)

		//Initialize scroll view
		scrollView.bounces = true
		scrollView.scrollEnabled = true
		scrollView.pagingEnabled = true

		self.layer.cornerRadius = CardView.DefaultCornerRadius

		setButton(title, fontColor: fontColor)
		setArrowImage(image)
	}

	///Sets the constant value for the height constraint for a given state.
	/// - parameter state: next state of the card
	public func setHeightConstraintForState(state: ShowState) {
		switch state {
		case .Minimized:
			self.heightConstraint?.constant = self.minimizedHeight
		case .Normal:
			self.heightConstraint?.constant = self.normalHeight
		case .Maximized:
			if let superView = self.superview {
				self.heightConstraint?.constant = superView.frame.height - self.maximizedMargin
			}
		case .Hidden:
			self.heightConstraint?.constant = 0
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
	public func setButton(title: String, fontColor: UIColor) {
		self.button.setTitle(title.uppercaseString, forState: .Normal)
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
		self.cardContentView.autoPinEdgeToSuperviewEdge(.Bottom, withInset: self.minimizedHeight -
			CardView.DefaultMinimizedHeight, relation: .LessThanOrEqual)

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
	        delay: Double = 0.0, onComplete: (Bool -> Void)? = nil) {

		//Exit if we are asked to change to our current state
		if nextState == currentState {
			return
		}

		//Notify the view controller, if any
		if nextState.isVisible != currentState.isVisible {
			if nextState.isVisible {
				presentingController?.cardWillAppear()
			}
			else {
				presentingController?.cardWillDisappear()
			}
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

			//Animate the constraint change
			UIView.animateWithDuration((time ?? CardView.DefaultAnimationTime) * 1.30,
				delay: delay,
				usingSpringWithDamping: 1.0,
				initialSpringVelocity: 0.0,
				options: [.BeginFromCurrentState, .CurveEaseIn],
				animations: {
					self.layoutIfNeeded()
				}, completion: onComplete)
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
				self.arrow.alpha = self.nextState == .Normal || self.nextState == .Maximized ? 1.0 : 0.0
			}, completion: nil)
	}
	

	//MARK: Animation group delegate


	//Called when background animation finish
	override public func animationDidStop(theAnimation: CAAnimation, finished flag: Bool) {
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

	public override func updateConstraints() {
		super.updateConstraints()

		if !self.didSetupConstraints {
			heightConstraint = autoSetDimension(.Height, toSize: minimizedHeight)

			self.didSetupConstraints = true
		}
	}
}
