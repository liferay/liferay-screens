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

/*
 * BaseScreenletView is the base class from which all Screenlet's View classes must inherit.
 */
@objc(BaseScreenletView)
@objcMembers
open class BaseScreenletView: UIView {

	open weak var screenlet: BaseScreenlet?

	open weak var presentingViewController: UIViewController?

	open var progressMessages: [String: ProgressMessages] { return [:] }

	open let NoProgressMessage = ""

	open var editable: Bool = true {
		didSet {
			changeEditable(editable)
		}
	}

	open var themeName = "default"

	internal var onPerformAction: ((String, Any?) -> Bool)?

	deinit {
		onDestroy()
	}

	// MARK: UIView

	override open func awakeFromNib() {
		onPreCreate()
		setUpView(self)
		onSetTranslations()
		onCreated()
	}

	override open func becomeFirstResponder() -> Bool {
		var result: Bool

		if let firstView = viewWithTag(1) {
			result = firstView.becomeFirstResponder()
		}
		else {
			result = super.becomeFirstResponder()
		}

		return result
	}

	override open func didMoveToWindow() {
		if window != nil {
			onShow()
		}
		else {
			onHide()
		}
	}

	// MARK: Internal methods

	/*
	 * onCreated is fired after the initialization of the screenlet view. 
	 * Override this method to perform actions such as setting colors, sizes, 
	 * positioning, etc to the component's subviews.
	*/
	open dynamic func onCreated() {
	}

	/*
	 * onDestroy is fired before the destruction of the screenlet view.
	 * Override this method to perform cleanup actions.
	*/
	open dynamic func onDestroy() {
	}

	/*
	 * onPreCreate is fired before the initialization of the screenlet view. 
	 * Override this method to create UI components programatically.
	*/
	open dynamic func onPreCreate() {
	}

	/*
	 * onHide is invoked when the screenlet's view is hidden
	 */
	open dynamic func onHide() {
	}

	/*
	 * onShow is invoked when the screenlet's view is displayed.
	 * Override this method for example to reset values when the screenlet's 
	 * view is shown.
	 */
	open dynamic func onShow() {
	}

	/*
	 * onSetUserActionForControl is invoked just before the user action handler 
	 * is associated to one control.
	 * Override this method to decide whether or not the handler should be 
	 * associated to the control.
	 */
	open dynamic func onSetUserActionForControl(_ control: UIControl) -> Bool {
		return true
	}

	/*
	 * onPreAction is invoked just before any user action is invoked.
	 * Override this method to decide whether or not the user action should be fired.
	 */
	open dynamic func onPreAction(name: String, sender: Any) -> Bool {
		return true
	}

	open dynamic func onSetDefaultDelegate(_ delegate: AnyObject, view: UIView) -> Bool {
		return true
	}

	open dynamic func onSetTranslations() {
	}

	open dynamic func onStartInteraction() {
	}

	open dynamic func onFinishInteraction(_ result: Any?, error: NSError?) {
	}

	open dynamic func createProgressPresenter() -> ProgressPresenter {
		return MBProgressHUDPresenter()
	}

	open dynamic func progressMessageForAction(_ actionName: String,
			messageType: ProgressMessageType) -> String? {

		let messages = progressMessages[actionName] ?? progressMessages[BaseScreenlet.DefaultAction]

		if let messages = messages {
			if let message = messages[messageType] {
				return message
			}
		}

		return nil
	}

	open dynamic func userAction(name: String?) {
		userAction(name: name, sender: nil)
	}

	open dynamic func userAction(name: String?, sender: Any?) {
		let actionName = name ?? BaseScreenlet.DefaultAction

		if onPreAction(name: actionName, sender: sender) {
			endEditing(true)

			_ = onPerformAction?(actionName, sender)
		}
	}

	// MARK: Private methods

	fileprivate func setUpView(_ view: UIView) {
		for subview: UIView in view.subviews {
			setUpView(subview)
		}
	}

	// MARK: Public methods

	open dynamic func changeEditable(_ editable: Bool) {
		isUserInteractionEnabled = editable
	}
}
