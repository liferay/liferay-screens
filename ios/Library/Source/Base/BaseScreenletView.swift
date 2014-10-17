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
 * BaseScreenletView is the base class from which all Screenlet's View classes must inherit.
 */
public class BaseScreenletView: UIView, UITextFieldDelegate {

	internal var onUserAction: ((String?, AnyObject?) -> Void)?

	internal var themeName: String? {
		var className = NSStringFromClass(self.dynamicType)

		let components = className.componentsSeparatedByString("_")

		return (components.count > 1) ? components.last : nil
	}

	deinit {
		onDestroy()
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

	override public func didMoveToWindow() {
		if (window != nil) {
			onShow();
		}
		else {
			onHide();
		}
	}


	//MARK: UITextFieldDelegate

	public func textFieldShouldReturn(textField: UITextField!) -> Bool {
		let nextResponder = nextResponderForView(textField)

		if nextResponder != textField {

			switch textField.returnKeyType {
				case .Next
				where nextResponder is UITextInputTraits:
					if textField.canResignFirstResponder() {
						textField.resignFirstResponder()

						if nextResponder.canBecomeFirstResponder() {
							nextResponder.becomeFirstResponder()
						}
					}

				case _
				where nextResponder is UIControl:
					(nextResponder as UIControl).sendActionsForControlEvents(
							UIControlEvents.TouchUpInside)

				default: ()
			}
		}

		return true
	}


	//MARK: Internal methods

	/*
	 * onCreated is fired after the initialization of the screenlet view. 
	 * Override this method to perform actions such as setting colors, sizes, 
	 * positioning, etc to the component's subviews.
	*/
	internal func onCreated() {
	}

	/*
	 * onDestroy is fired before the destruction of the screenlet view.
	 * Override this method to perform cleanup actions.
	*/
	internal func onDestroy() {
	}

	/*
	 * onPreCreate is fired before the initialization of the screenlet view. 
	 * Override this method to create UI components programatically.
	*/
	internal func onPreCreate() {
	}

	/*
	 * onHide is invoked when the screenlet's view is hidden
	 */
	internal func onHide() {
	}

	/*
	 * onShow is invoked when the screenlet's view is displayed.
	 * Override this method for example to reset values when the screenlet's 
	 * view is shown.
	 */
	internal func onShow() {
	}

	/*
	 * onSetUserActionForControl is invoked just before the user action handler 
	 * is associated to one control.
	 * Override this method to decide whether or not the handler should be 
	 * associated to the control.
	 */
	internal func onSetUserActionForControl(control: UIControl) -> Bool {
		return true
	}

	/*
	 * onPreUserAction is invoked just before any user action is invoked.
	 * Override this method to decide whether or not the user action should be fired.
	 */
	internal func onPreUserAction(actionName: String?, sender: AnyObject?) -> Bool {
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

	internal func userActionWithSender(sender: AnyObject?) {
		if let controlSender = sender as? UIControl {
			userActionWithName(actionName: controlSender.restorationIdentifier, sender: sender)
		}
		else {
			userActionWithName(actionName: nil, sender: sender)
		}
	}

	internal func userActionWithName(actionName: String?) {
		userActionWithName(actionName: actionName, sender: nil)
	}
	
	internal func userActionWithName(#actionName: String?, sender: AnyObject?) {
		if onPreUserAction(actionName, sender: sender) {
			endEditing(true)
		
			onUserAction?(actionName, sender)
		}
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
					action: "userActionWithSender:",
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
