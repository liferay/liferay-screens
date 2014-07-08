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
class BaseWidgetView: UIView {

	typealias CustomActionType = (String?, UIControl) -> (Void)

	var customAction: CustomActionType?

	override func awakeFromNib() {
		addCustomActionsForViews(self)
		onCreate();
	}

    func addCustomActionForControl(control: UIControl) {
        let currentActions = control.actionsForTarget(self, forControlEvent: UIControlEvents.TouchUpInside)
        
        if !currentActions || currentActions?.count == 0 {
            control.addTarget(self, action: "customActionHandler:", forControlEvents: UIControlEvents.TouchUpInside)
        }
    }
    
	func addCustomActionsForViews(parentView: UIView!) {
		for subview:AnyObject in parentView.subviews {
			if subview is UIControl {
				addCustomActionForControl(subview as UIControl)
			}
		}
	}

    /*
     * becomeFirstResponder is invoked to make the widget view the first responder. Override this method to set one 
     * child component as first responder.
     */
    override func becomeFirstResponder() -> Bool {
        return super.becomeFirstResponder()
    }
    
    func customActionHandler(sender: UIControl!) {
		self.endEditing(true)

		// WORKAROUND
		// In theory, an implicit optional (with `type!`) behaves the same as ObjC pointer.
		// But it's not true. If you access optional's value when it's empty, it crashes
		customAction?(sender.restorationIdentifier, sender)
	}

    /*
     * onCreate is fired after the initialization of the widget view. Override this method to perform actions such as
     * setting colors, sizes, positioning, etc to the component's subviews.
     */
	func onCreate() {
	}

}