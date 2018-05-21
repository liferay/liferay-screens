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

	@IBInspectable var borderColor: UIColor? = .clear
	@IBInspectable var focusedColor: UIColor? = .clear
	@IBInspectable var unfocusedColor: UIColor? = .clear

	fileprivate var revertTextColor = false


	required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)

		self.delegate = self
	}

	override func awakeFromNib() {
		textFieldDidEndEditing(self)
	}

	func textFieldDidBeginEditing(_ textField: UITextField) {
		superview?.layer.borderWidth = 1.0
		superview?.layer.borderColor = self.borderColor?.cgColor
		superview?.backgroundColor = self.focusedColor!

		self.attributedPlaceholder = NSAttributedString(
				string: self.placeholder!,
				attributes: [NSAttributedStringKey.foregroundColor : self.unfocusedColor!])

		if self.textColor == self.focusedColor! {
			self.textColor = .black
			revertTextColor = true
		}
	}

	func textFieldDidEndEditing(_ textField: UITextField) {
		superview?.layer.borderWidth = 0.0
		superview?.layer.borderColor = UIColor.clear.cgColor
		superview?.backgroundColor = self.unfocusedColor!

		self.attributedPlaceholder = NSAttributedString(
				string: self.placeholder!,
				attributes: [NSAttributedStringKey.foregroundColor : self.focusedColor!])

		if revertTextColor {
			self.textColor = self.focusedColor!
			revertTextColor = false
		}
	}

	func textFieldShouldReturn(_ textField: UITextField) -> Bool {
		return parentDelegate(self.superview)?.textFieldShouldReturn?(self) ?? true
	}

	fileprivate func parentDelegate(_ view:UIView?) -> UITextFieldDelegate? {
		if view == nil {
			return nil
		}
		if view is UITextFieldDelegate {
			return view as? UITextFieldDelegate
		}
		return parentDelegate(view?.superview)
	}

}
