//
//  KeyboardListener.swift
//  WesterosBank
//
//  Created by jmWork on 12/05/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit


@objc protocol KeyboardListener {

	func showKeyboard(notif: NSNotification)

	func hideKeyboard(notif: NSNotification)

}

func registerKeyboardListener(listener: KeyboardListener) {
	NSNotificationCenter.defaultCenter().addObserver(listener,
			selector: "showKeyboard:",
			name: UIKeyboardWillChangeFrameNotification,
			object: nil)

	NSNotificationCenter.defaultCenter().addObserver(listener,
			selector: "hideKeyboard:",
			name: UIKeyboardWillHideNotification,
			object: nil)
}

func unregisterKeyboardListener(listener: KeyboardListener) {
	NSNotificationCenter.defaultCenter().removeObserver(listener,
			name: UIKeyboardWillHideNotification,
			object: nil)

	NSNotificationCenter.defaultCenter().removeObserver(listener,
			name: UIKeyboardWillChangeFrameNotification,
			object: nil)
}
