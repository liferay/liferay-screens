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
import Foundation


internal protocol KeyboardLayoutable {

	func layoutWhenKeyboardShown(keyboardHeight: CGFloat, animation:(time: NSNumber, curve: NSNumber))

	func layoutWhenKeyboardHidden()
}


public class KeyboardManager {

	private struct StaticData {
		static var currentHeight: CGFloat?
		static var visible = false
	}


	public class var currentHeight: CGFloat? {
		get {
			return StaticData.currentHeight
		}
		set {
			StaticData.currentHeight = newValue
		}
	}

	public class var isVisible: Bool {
		return StaticData.visible
	}

	//FIXME
	internal class var defaultHeight: CGFloat { return 253 }
	internal class var defaultAutocorrectionBarHeight: CGFloat { return 38 }


	private var layoutable: KeyboardLayoutable?


	internal init() {
	}

	deinit {
		unregisterObserver()
	}

	internal func registerObserver(layoutable: KeyboardLayoutable) {
		self.layoutable = layoutable

		NSNotificationCenter.defaultCenter().addObserver(self,
				selector: "keyboardShown:",
				name: UIKeyboardWillShowNotification,
				object: nil)

		NSNotificationCenter.defaultCenter().addObserver(self,
				selector: "keyboardHidden:",
				name: UIKeyboardWillHideNotification,
				object: nil)
	}

	internal func unregisterObserver() {
		self.layoutable = nil

		NSNotificationCenter.defaultCenter().removeObserver(self,
				name: UIKeyboardDidShowNotification,
				object: nil)

		NSNotificationCenter.defaultCenter().removeObserver(self,
				name: UIKeyboardDidHideNotification,
				object: nil)
	}


	//MARK: Private methods

	private dynamic func keyboardShown(notification: NSNotification?) {
		let value = notification!.userInfo![UIKeyboardFrameEndUserInfoKey] as NSValue
		let frame = adjustRectForCurrentOrientation(value.CGRectValue())

		StaticData.currentHeight = frame.size.height
		StaticData.visible = true

		let animationDuration =
				notification!.userInfo![UIKeyboardAnimationDurationUserInfoKey] as NSNumber
		let animationCurve =
				notification!.userInfo![UIKeyboardAnimationCurveUserInfoKey] as NSNumber

		layoutable?.layoutWhenKeyboardShown(frame.size.height,
				animation: (time: animationDuration, curve: animationCurve))
	}

	private dynamic func keyboardHidden(notification: NSNotification?) {
		StaticData.visible = false

		layoutable?.layoutWhenKeyboardHidden()
	}

}
