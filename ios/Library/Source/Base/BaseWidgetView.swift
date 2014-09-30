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


/*!
 * BaseWidgetView is the base class from which all Widget's View classes must inherit.
 */
public class BaseWidgetView: UIView, UITextFieldDelegate {

	internal var onUserAction: ((String?, AnyObject?) -> Void)?

	internal var themeName: String? {
		var className = NSStringFromClass(self.dynamicType)

		let components = className.componentsSeparatedByString("_")

		return (components.count > 1) ? components.last : nil
	}


	//MARK: UIView

	override public func awakeFromNib() {
		onPreCreate()
		setUpView(self)
		onSetTranslations()
		onCreated()
	}

	override public func becomeFirstResponder() -> Bool {
		var result:Bool

		if let firstView = viewWithTag(1) {
			result = firstView.becomeFirstResponder()
		}
		else {
			result = super.becomeFirstResponder()
		}

		return result
	}


	//MARK: UITextFieldDelegate

	public func textFieldShouldReturn(textField: UITextField!) -> Bool {
		let nextResponder = nextResponderForView(textField)

		if nextResponder != textField {

			switch textField.returnKeyType {
				case .Next where nextResponder is UITextInputTraits:
					if textField.canResignFirstResponder() {
						textField.resignFirstResponder()

						if nextResponder.canBecomeFirstResponder() {
							nextResponder.becomeFirstResponder()
						}
					}

				case _ where nextResponder is UIControl:
					(nextResponder as UIControl).sendActionsForControlEvents(
							UIControlEvents.TouchUpInside)

				default: ()
			}
		}

		return true
	}


	//MARK: Internal methods

	/*
	 * onCreated is fired after the initialization of the widget view. 
	 * Override this method to perform actions such as setting colors, sizes, 
	 * positioning, etc to the component's subviews.
	*/
	internal func onCreated() {
	}

	/*
	 * onPreCreate is fired before the initialization of the widget view. 
	 * Override this method to create UI components programatically.
	*/
	internal func onPreCreate() {
	}

	internal func onSetUserActionForControl(control: UIControl) -> Bool {
		return true
	}

	internal func onSetDefaultDelegate(delegate:AnyObject, view:UIView) -> Bool {
		return true
	}

	internal func onSetTranslations() {
	}

	internal func onStartOperation() {
	}

	internal func onFinishOperation() {
	}	

	internal func userActionHandler(sender: AnyObject?) {
		if let controlSender = sender as? UIControl {
			userActionHandler(actionName: controlSender.restorationIdentifier, sender: sender)
		}
		else {
			userActionHandler(actionName: nil, sender: sender)
		}
	}

	internal func userActionHandler(actionName: String?) {
		userActionHandler(actionName: actionName, sender: nil)
	}
	
	internal func userActionHandler(#actionName: String?, sender: AnyObject?) {
		endEditing(true)
		
		onUserAction?(actionName, sender)
	}

	internal func nextResponderForView(view:UIView) -> UIResponder {
		if view.tag > 0 {
			if let nextView = viewWithTag(view.tag + 1) {
				return nextView
			}
		}
		return view
	}


	//MARK: Private methods

	private func addUserActionForControl(control: UIControl) {
		if onSetUserActionForControl(control) {
			control.addTarget(self,
					action: "userActionHandler:",
					forControlEvents: UIControlEvents.TouchUpInside)
		}
	}

	private func addDefaultDelegatesForView(view:UIView) {
		if let textField = view as? UITextField {
			if onSetDefaultDelegate(self, view:textField) {
				textField.delegate = self
			}
		}
	}

	private func setUpView(view: UIView) {
		if let control = view as? UIControl {
			addUserActionForControl(control)
		}

		addDefaultDelegatesForView(view)

		for subview:UIView in view.subviews as [UIView] {
			setUpView(subview)
		}
	}

}
