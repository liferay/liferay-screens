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

class BorderedTextField: UITextField, UITextFieldDelegate {

	@IBOutlet var chainedDelegate: UITextFieldDelegate?

	@IBInspectable var borderWidth: CGFloat = 1.0
	@IBInspectable var unfocusedBorderWidth: CGFloat = 0.0

	@IBInspectable var borderColor: UIColor? = UIColor.clearColor()
	@IBInspectable var unfocusedBorderColor: UIColor? = UIColor.clearColor()
	@IBInspectable var focusedColor: UIColor? = UIColor.clearColor()
	@IBInspectable var unfocusedColor: UIColor? = UIColor.clearColor()

	private var revertTextColor = false


	required init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)

		self.delegate = self
	}

	override func awakeFromNib() {
		textFieldDidEndEditing(self)
	}

	func textFieldDidBeginEditing(textField: UITextField) {
		superview?.layer.borderWidth = 1.0
		superview?.layer.borderColor = self.borderColor?.CGColor
		superview?.backgroundColor = self.focusedColor!

		self.attributedPlaceholder = NSAttributedString(
				string: self.placeholder!,
				attributes: [NSForegroundColorAttributeName : self.unfocusedColor!])

		if self.textColor == self.focusedColor! {
			self.textColor = UIColor.blackColor()
			revertTextColor = true
		}
	}

	func textFieldDidEndEditing(textField: UITextField) {
		superview?.layer.borderWidth = 0.0
		superview?.layer.borderColor = UIColor.clearColor().CGColor
		superview?.backgroundColor = self.unfocusedColor!

		self.attributedPlaceholder = NSAttributedString(
				string: self.placeholder!,
				attributes: [NSForegroundColorAttributeName : self.focusedColor!])

		if revertTextColor {
			self.textColor = self.focusedColor!
			revertTextColor = false
		}
	}

	func textFieldShouldReturn(textField: UITextField) -> Bool {
		return parentDelegate(self.superview)?.textFieldShouldReturn?(self) ?? true
	}

	private func parentDelegate(view:UIView?) -> UITextFieldDelegate? {
		if view == nil {
			return nil
		}
		if view is UITextFieldDelegate {
			return view as? UITextFieldDelegate
		}
		return parentDelegate(view?.superview)
	}

}
