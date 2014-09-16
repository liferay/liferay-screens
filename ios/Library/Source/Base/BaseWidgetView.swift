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

	typealias CustomActionType = (String?, UIControl?) -> (Void)

	var customAction: CustomActionType?


	//MARK: UIView

	override public func awakeFromNib() {
		setUpView(self)
		onSetTranslations()
		onCreate();
	}

	/*
	* becomeFirstResponder is invoked to make the widget view the first responder. Override this method to set one
	* child component as first responder.
	*/
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
					(nextResponder as UIControl).sendActionsForControlEvents(UIControlEvents.TouchUpInside)

				default: ()
			}
		}

		return true
	}
	
	//MARK: BaseWidgetView Methods

	/*
	* onCreate is fired after the initialization of the widget view. Override this method to perform actions such as
	* setting colors, sizes, positioning, etc to the component's subviews.
	*/
	public func onCreate() {
	}

	public func onSetCustomActionForControl(control: UIControl) -> Bool {
		return true
	}

	public func onSetDefaultDelegate(delegate:AnyObject, view:UIView) -> Bool {
		return true
	}

	public func onSetTranslations() {
	}

	public func onStartOperation() {
	}

	public func onFinishOperation() {
	}
	
	internal func customActionHandler(sender: UIControl!) {
		customActionHandler(sender.restorationIdentifier)
	}
	
	internal func customActionHandler(actionName: String?) {
		endEditing(true)

		customAction?(actionName, nil)
	}

	internal func nextResponderForView(view:UIView) -> UIResponder {
		if view.tag > 0 {
			if let nextView = viewWithTag(view.tag + 1) {
				return nextView
			}
		}
		return view
	}

	internal func themeName() -> String? {
		var className = NSStringFromClass(self.dynamicType)

		let components = className.componentsSeparatedByString("_")

		return (components.count > 1) ? components.last : nil
	}

	private func addCustomActionForControl(control: UIControl) {
		if onSetCustomActionForControl(control) {
			control.addTarget(self, action: "customActionHandler:", forControlEvents: UIControlEvents.TouchUpInside)
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
			addCustomActionForControl(control)
		}

		addDefaultDelegatesForView(view)

		for subview:UIView in view.subviews as [UIView] {
			setUpView(subview)
		}
	}

}