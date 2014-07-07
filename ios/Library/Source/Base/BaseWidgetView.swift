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

class BaseWidgetView: UIView {

	typealias CustomActionType = (String, UIControl) -> (Void)

	var customAction: CustomActionType?

	override func awakeFromNib() {
		self.addCustomActionsForViews(self)
		self.onCreate();
	}

	func addCustomActionsForViews(parentView:UIView!) {
		for subview:AnyObject in parentView.subviews {
			if subview is UIControl {
				self.addCustomActionForControl(subview as UIControl)
			}
		}
	}

	func addCustomActionForControl(control:UIControl) {
		let currentActions = control.actionsForTarget(self, forControlEvent: UIControlEvents.TouchUpInside)

		if !currentActions || currentActions?.count == 0 {
			control.addTarget(self, action: "customActionHandler:", forControlEvents: UIControlEvents.TouchUpInside)
		}
	}

	func customActionHandler(sender:UIControl!) {
		self.endEditing(true)

		// WTF!
		// In theory, an implicit optional (with `type!`) behaves the same as 
		// ObjC pointer.
		// But it's not true. If you access optional's value when it's empty, it crashes
		customAction?(sender.restorationIdentifier, sender)
	}

	func onCreate() {

	}

	override func becomeFirstResponder() -> Bool {
		// to be overwritten
		return super.becomeFirstResponder()
	}



}
