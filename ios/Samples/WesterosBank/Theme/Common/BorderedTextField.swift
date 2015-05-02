//
//  BorderedTextField.swift
//  WesterosBank
//
//  Created by jmWork on 26/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

class BorderedTextField: UITextField, UITextFieldDelegate {

	@IBInspectable var borderColor: UIColor? = UIColor.clearColor()
	@IBInspectable var focusedColor: UIColor? = UIColor.clearColor()
	@IBInspectable var unfocusedColor: UIColor? = UIColor.clearColor()

	required init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)

		self.delegate = self
	}

	func textFieldDidBeginEditing(textField: UITextField) {
		superview?.layer.borderWidth = 1.0
		superview?.layer.borderColor = self.borderColor?.CGColor
		superview?.backgroundColor = self.focusedColor!
	}

	func textFieldDidEndEditing(textField: UITextField) {
		superview?.layer.borderWidth = 0.0
		superview?.layer.borderColor = UIColor.clearColor().CGColor
		superview?.backgroundColor = self.unfocusedColor!
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
