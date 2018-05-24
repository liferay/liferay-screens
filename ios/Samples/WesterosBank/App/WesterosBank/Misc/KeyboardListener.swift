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
