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


func CGRectMake(x: CGFloat, y: CGFloat, size: CGSize) -> CGRect {
	return CGRectMake(x, y, size.width, size.height)
}


class CardView: UIView {

	enum ShowState {
		case Hidden
		case Minimized
		case Normal
		case Maximized
		case Background
	}


	@IBInspectable var minimizedHeight: CGFloat = 50
	@IBInspectable var normalHeight: CGFloat = 200
	@IBInspectable var maximizedMargin: CGFloat = 20
	@IBInspectable var title: String = "CARD"

	var arrow: UIImageView?
	var button: UIButton?

	let animationTime = 0.5

	var currentState: ShowState = .Hidden
	var nextState: ShowState = .Normal

	var onChangeCompleted: (Bool -> Void)?

	func createArrow(color: UIColor) {
		let arrowName = (color == UIColor.whiteColor()) ? "icon_DOWN_W" : "icon_DOWN"
		let arrowImage = UIImage(named: arrowName)!

		self.arrow = UIImageView(image: arrowImage)

		let x = self.frame.size.width - arrowImage.size.width - 25
		let y = (self.minimizedHeight/2) - arrowImage.size.height + 6

		self.arrow!.frame = CGRectMake(x, y, arrowImage.size)
		self.arrow!.alpha = 0.0

		self.addSubview(self.arrow!)
	}

	func createButton(fontColor: UIColor) -> UIButton {
		button = UIButton(frame: CGRectMake(0, 0, self.frame.width, self.minimizedHeight))
		button!.setTitle(self.title, forState: .Normal)
		button!.setTitleColor(fontColor, forState: .Normal)
		self.addSubview(button!)

		return button!
	}

	func resetToCurrentState() {
		let pos = positionForState(currentState)

		self.frame = CGRectMake(0, pos, self.frame.size)
	}

	func changeToNextState(time: Double? = nil, onComplete: (Bool -> Void)? = nil) {
		func showArrow() {
			UIView.animateWithDuration(time ?? animationTime) {
				if self.nextState == .Normal || self.nextState == .Maximized {
					self.arrow!.alpha = 1.0
				}
				else {
					self.arrow!.alpha = 0.0
				}
			}
		}




		if nextState == currentState {
			return
		}

		let nextPosition = positionForState(nextState)

		if nextState == .Background {
			onChangeCompleted = onComplete
			self.layer.addAnimation(backgroundAnimation(time ?? animationTime), forKey: "pushBackAnimation")
		}
		else if currentState == .Background {
			onChangeCompleted = onComplete
			self.layer.addAnimation(resetBackgroundAnimation(time ?? animationTime), forKey: "popBackAnimation")
		}
		else {
			onChangeCompleted = nil
			UIView.animateWithDuration((time ?? animationTime)*1.30,
					delay: 0.0,
					usingSpringWithDamping: 1.0,
					initialSpringVelocity: 0.0,
					options: .BeginFromCurrentState | .CurveEaseIn,
					animations: {
						self.frame = CGRectMake(0, nextPosition, self.frame.size)
					}, completion: onComplete)
		}

		showArrow()

		self.currentState = self.nextState
	}

	private func resetBackgroundAnimation(animationTime: Double) -> CAAnimation {
		var animation = CABasicAnimation(keyPath: "transform")
		animation.toValue = NSValue(CATransform3D: CATransform3DIdentity)
		animation.duration = animationTime
		animation.fillMode = kCAFillModeForwards
		animation.removedOnCompletion = false
		animation.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseInEaseOut)

		return animation;
	}

	private func backgroundAnimation(animationTime: Double) -> CAAnimation {
		var t0 = CATransform3DIdentity
		var animation0 = CABasicAnimation(keyPath: "transform")

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

		var animation1 = CABasicAnimation(keyPath: "transform")
		animation1.toValue = NSValue(CATransform3D: t1)
		animation1.duration = animationTime*3.0/4.0
		animation1.fillMode = kCAFillModeForwards
		animation1.removedOnCompletion = false
		animation1.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseOut)

		var t2 = t0
		t2.m34 = t1.m34
		t2 = CATransform3DTranslate(t2, 0, self.frame.size.height * CGFloat(-0.04), 0)
		t2 = CATransform3DScale(t2, 0.95, 0.90, 1)

		var animation2 = CABasicAnimation(keyPath: "transform")
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

		return group;
	}

	override func animationDidStop(theAnimation: CAAnimation!, finished flag: Bool) {
		onChangeCompleted?(flag)
		onChangeCompleted = nil
	}

	private func positionForState(state: ShowState) -> CGFloat {
		let result: CGFloat

		switch state {
		case .Hidden:
			result = self.superview!.frame.size.height
		case .Minimized:
			result = self.superview!.frame.size.height - minimizedHeight
		case .Normal:
			result = self.superview!.frame.size.height - normalHeight
		case .Maximized, .Background:
			result = maximizedMargin
		}

		return result
	}

}
