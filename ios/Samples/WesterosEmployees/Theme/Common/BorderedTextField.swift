//
//  BorderedTextField.swift
//  WesterosBank
//
//  Created by jmWork on 26/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

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
				attributes: [.foregroundColor : self.unfocusedColor!])

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
				attributes: [.foregroundColor : self.focusedColor!])

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
