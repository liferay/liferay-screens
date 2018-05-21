//
//  KeyboardListener.swift
//  WesterosBank
//
//  Created by jmWork on 12/05/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

@objc protocol KeyboardListener {

	func showKeyboard(_ notif: Notification)

	func hideKeyboard(_ notif: Notification)

}

func registerKeyboardListener(_ listener: KeyboardListener) {
	NotificationCenter.default.addObserver(listener,
			selector: #selector(KeyboardListener.showKeyboard(_:)),
			name: .UIKeyboardWillChangeFrame,
			object: nil)

	NotificationCenter.default.addObserver(listener,
			selector: #selector(KeyboardListener.hideKeyboard(_:)),
			name: .UIKeyboardWillHide,
			object: nil)
}

func unregisterKeyboardListener(_ listener: KeyboardListener) {
	NotificationCenter.default.removeObserver(listener,
			name: .UIKeyboardWillHide,
			object: nil)

	NotificationCenter.default.removeObserver(listener,
			name: .UIKeyboardWillChangeFrame,
			object: nil)
}
