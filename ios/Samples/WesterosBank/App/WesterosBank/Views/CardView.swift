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


func CGRectMake(_ x: CGFloat, y: CGFloat, size: CGSize) -> CGRect {
	return CGRect(x: x, y: y, width: size.width, height: size.height)
}


class CardView: UIView, CAAnimationDelegate {

	enum ShowState: CustomDebugStringConvertible {
		case hidden
		case minimized
		case normal
		case maximized
		case background

		var isVisible: Bool {
			return (self == .normal || self == .maximized)
		}

	    var debugDescription: String {
			switch self {
			case .hidden:
				return "Hidden"
			case .minimized:
				return "Minimized"
			case .normal:
				return "Normal"
			case .maximized:
				return "Maximized"
			case .background:
				return "Background"
			}
		}

	}


	@IBInspectable var minimizedHeight: CGFloat = 50
	@IBInspectable var normalHeight: CGFloat = 200
	@IBInspectable var maximizedMargin: CGFloat = 20
	@IBInspectable var title: String = "CARD"

	var arrow: UIImageView?
	var button: UIButton?

	let animationTime = 0.5

	var currentState: ShowState = .hidden
	var nextState: ShowState = .normal
	var beforeBackgroundState: ShowState = .normal

	weak var presentingController: CardViewController?

	fileprivate var onChangeCompleted: ((Bool) -> Void)?

	func createArrow(_ color: UIColor) {
		let arrowName = (color == UIColor.white) ? "icon_DOWN_W" : "icon_DOWN"
		let arrowImage = UIImage(named: arrowName)!

		self.arrow = UIImageView(image: arrowImage)

		let x = self.frame.size.width - arrowImage.size.width - 25
		let y = (self.minimizedHeight/2) - arrowImage.size.height + 6

		self.arrow!.frame = CGRectMake(x, y: y, size: arrowImage.size)
		self.arrow!.alpha = 0.0

		self.addSubview(self.arrow!)
	}

	func createButton(_ fontColor: UIColor) -> UIButton {
		button = UIButton(frame: CGRect(x: 0, y: 0, width: self.frame.width, height: self.minimizedHeight))
		button!.setTitle(self.title, for: UIControlState())
		button!.setTitleColor(fontColor, for: UIControlState())
		button!.titleLabel?.font = UIFont(name: "AvenirNext-DemiBold", size: 18)

		self.addSubview(button!)

		return button!
	}

	func resetToCurrentState() {
		let pos = positionForState(currentState)

		self.frame = CGRectMake(0, y: pos, size: self.frame.size)
	}

	func changeToNextState(_ time: Double? = nil, delay: Double = 0.0, onComplete: ((Bool) -> Void)? = nil) {
		func showArrow() {
			UIView.animate(withDuration: time ?? animationTime,
				delay: delay,
				options: UIViewAnimationOptions.curveEaseIn,
				animations: {
					if self.nextState == .normal || self.nextState == .maximized {
						self.arrow?.alpha = 1.0
					}
					else {
						self.arrow?.alpha = 0.0
					}
				}, completion: nil)
		}

		if nextState == currentState {
			return
		}

		if currentState == .background {
			// go to previous
			nextState = beforeBackgroundState
		}

		if nextState.isVisible != currentState.isVisible {
			if nextState.isVisible {
				presentingController?.cardWillAppear()
			}
			else {
				presentingController?.cardWillDisappear()
			}
		}

		let nextPosition = positionForState(nextState)

		if nextState == .background {
			beforeBackgroundState = currentState
			onChangeCompleted = onComplete
			self.layer.add(backgroundAnimation(time ?? animationTime), forKey: "pushBackAnimation")
		}
		else if currentState == .background {
			onChangeCompleted = onComplete
			self.layer.add(resetBackgroundAnimation(time ?? animationTime), forKey: "popBackAnimation")
		}
		else {
			onChangeCompleted = nil
			UIView.animate(withDuration: (time ?? animationTime)*1.30,
				delay: delay,
				usingSpringWithDamping: 1.0,
				initialSpringVelocity: 0.0,
				options: [.beginFromCurrentState, .curveEaseIn],
				animations: {
					self.frame = CGRectMake(0, y: nextPosition, size: self.frame.size)
				}, completion: onComplete)
		}

		showArrow()

		self.currentState = self.nextState
	}

	func enabledButton(_ enable: Bool) {
		button?.isEnabled = enable

		UIView.animate(withDuration: animationTime,
				delay: 0,
				options: UIViewAnimationOptions.curveEaseIn,
				animations: {
					if enable {
						self.arrow?.alpha = 1.0
					}
					else {
						self.arrow?.alpha = 0.0
					}
				}, completion: nil)

	}

	fileprivate func resetBackgroundAnimation(_ animationTime: Double) -> CAAnimation {
		let animation = CABasicAnimation(keyPath: "transform")
		animation.toValue = NSValue(caTransform3D: CATransform3DIdentity)
		animation.duration = animationTime
		animation.fillMode = kCAFillModeForwards
		animation.isRemovedOnCompletion = false
		animation.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseInEaseOut)

		return animation;
	}

	fileprivate func backgroundAnimation(_ animationTime: Double) -> CAAnimation {
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
		t1 = CATransform3DRotate(t1, CGFloat(10.0 * Double.pi/180.0), 1, 0, 0)

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

		return group;
	}

	func animationDidStop(_ theAnimation: CAAnimation, finished flag: Bool) {
		onChangeCompleted?(flag)
		onChangeCompleted = nil
	}

	fileprivate func positionForState(_ state: ShowState) -> CGFloat {
		let result: CGFloat

		switch state {
		case .hidden:
			result = self.superview!.frame.size.height
		case .minimized:
			result = self.superview!.frame.size.height - minimizedHeight
		case .normal:
			result = self.superview!.frame.size.height - normalHeight
		case .maximized, .background:
			result = maximizedMargin
		}

		return result
	}

}
