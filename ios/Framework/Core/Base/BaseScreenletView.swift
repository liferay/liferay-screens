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

	public weak var screenlet: BaseScreenlet?

	public weak var presentingViewController: UIViewController?

	public var progressMessages: [String:ProgressMessages] { return [:] }


	public var editable: Bool = true {
		didSet {
			changeEditable(editable, fromView:self)
		}
	}

	public var themeName = "default"

	internal var onPerformAction: ((String, AnyObject?) -> Bool)?


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

	public func textFieldShouldReturn(textField: UITextField) -> Bool {
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
					userActionWithSender(nextResponder)

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
	public func onCreated() {
	}

	/*
	 * onDestroy is fired before the destruction of the screenlet view.
	 * Override this method to perform cleanup actions.
	*/
	public func onDestroy() {
	}

	/*
	 * onPreCreate is fired before the initialization of the screenlet view. 
	 * Override this method to create UI components programatically.
	*/
	public func onPreCreate() {
	}

	/*
	 * onHide is invoked when the screenlet's view is hidden
	 */
	public func onHide() {
	}

	/*
	 * onShow is invoked when the screenlet's view is displayed.
	 * Override this method for example to reset values when the screenlet's 
	 * view is shown.
	 */
	public func onShow() {
	}

	/*
	 * onSetUserActionForControl is invoked just before the user action handler 
	 * is associated to one control.
	 * Override this method to decide whether or not the handler should be 
	 * associated to the control.
	 */
	public func onSetUserActionForControl(control: UIControl) -> Bool {
		return true
	}

	/*
	 * onPreAction is invoked just before any user action is invoked.
	 * Override this method to decide whether or not the user action should be fired.
	 */
	public func onPreAction(name name: String, sender: AnyObject?) -> Bool {
		return true
	}

	public func onSetDefaultDelegate(delegate: AnyObject, view: UIView) -> Bool {
		return true
	}

	public func onSetTranslations() {
	}

	public func onStartInteraction() {
	}

	public func onFinishInteraction(result: AnyObject?, error: NSError?) {
	}

	public func createProgressPresenter() -> ProgressPresenter {
		return MBProgressHUDPresenter()
	}

	public func progressMessageForAction(actionName: String,
			messageType: ProgressMessageType) -> String? {

		let messages = progressMessages[actionName] ?? progressMessages[BaseScreenlet.DefaultAction]

		if let messages = messages {
			if let message = messages[messageType] {
				return message
			}
		}

		return nil
	}

	public func userActionWithSender(sender: AnyObject?) {
		if let controlSender = sender as? UIControl {
			userAction(name: controlSender.restorationIdentifier, sender: sender)
		}
		else {
			userAction(name: nil, sender: sender)
		}
	}

	public func userAction(name name: String?) {
		userAction(name: name, sender: nil)
	}
	
	public func userAction(name name: String?, sender: AnyObject?) {
		let actionName = name ?? BaseScreenlet.DefaultAction

		if onPreAction(name: actionName, sender: sender) {
			endEditing(true)
		
			onPerformAction?(actionName, sender)
		}
	}


	//MARK: Private methods

	private func nextResponderForView(view:UIView) -> UIResponder {
		if view.tag > 0 {
			if let nextView = viewWithTag(view.tag + 1) {
				return nextView
			}
		}
		return view
	}

	private func addUserActionForControl(control: UIControl) {
		let hasIdentifier = (control.restorationIdentifier != nil)

		let userDefinedActions = control.actionsForTarget(self,
			forControlEvent: .TouchUpInside)
		let hasUserDefinedActions = (userDefinedActions?.count ?? 0) > 0

		if hasIdentifier && !hasUserDefinedActions
				&& onSetUserActionForControl(control) {
			control.addTarget(self,
					action: "userActionWithSender:",
					forControlEvents: .TouchUpInside)
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

		for subview:UIView in view.subviews {
			setUpView(subview)
		}
	}

	private func changeEditable(editable: Bool, fromView view: UIView) {
		view.userInteractionEnabled = editable
		for v in view.subviews {
			changeEditable(editable, fromView: v)
		}
	}

}
