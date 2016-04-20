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


public func nullIfEmpty(string: String?) -> String? {
	if string == nil {
		return nil
	}
	else if string! == "" {
		return nil
	}

	return string
}

public func synchronized(lock: AnyObject, closure: Void -> Void) {
	objc_sync_enter(lock)
	closure()
	objc_sync_exit(lock)
}


public func dispatch_delayed(delay: NSTimeInterval, block: dispatch_block_t) {
    let time = dispatch_time(DISPATCH_TIME_NOW, Int64(delay * Double(NSEC_PER_SEC)))
    dispatch_after(time, dispatch_get_main_queue(), block)
}

public func dispatch_async(block: dispatch_block_t) {
	let queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_BACKGROUND, 0)
	dispatch_async(queue) {
		block()
	}
}


public func dispatch_async(block: dispatch_block_t, thenMain mainBlock: dispatch_block_t) {
	let queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_BACKGROUND, 0)

	dispatch_async(queue) {
		block()

		dispatch_async(dispatch_get_main_queue()) {
			mainBlock()
		}
	}
}


public typealias Signal = () -> ()

public func dispatch_sync(block: Signal -> ()) {
	let waitGroup = dispatch_group_create()
	dispatch_group_enter(waitGroup)
	block {
		dispatch_group_leave(waitGroup)
	}
	dispatch_group_wait(waitGroup, DISPATCH_TIME_FOREVER)
}

public func to_sync(function: Signal -> ()) -> () -> () {
	return {
		dispatch_sync(function)
	}
}

public func dispatch_main(block: dispatch_block_t) {
	if NSThread.isMainThread() {
		block()
	}
	else {
		dispatch_async(dispatch_get_main_queue()) {
			block()
		}
	}
}

public func dispatch_main(forceDispatch: Bool, block: dispatch_block_t) {
	if !forceDispatch && NSThread.isMainThread() {
		block()
	}
	else {
		dispatch_async(dispatch_get_main_queue()) {
			block()
		}
	}
}



public func ScreenletName(klass: AnyClass) -> String {
	var className = NSStringFromClass(klass)

	if className.characters.indexOf(".") != nil {
		className = className.componentsSeparatedByString(".")[1]
	}

	return className.componentsSeparatedByString("Screenlet")[0]
}

public func dynamicInit(className: String) -> NSObject? {
	guard let klass = NSClassFromString(className) else {
		return nil
	}
	guard let type = klass as? NSObject.Type else {
		return nil
	}
	return type.init()
}

public func LocalizedString(tableName: String, key: String, obj: AnyObject) -> String {
	return LocalizedString(tableName, key: key, obj: obj, lang: NSLocale.currentLanguageString)
}

public func LocalizedString(tableName: String, key: String, obj: AnyObject, lang: String) -> String {
	let namespacedKey = "\(tableName)-\(key)"

	func getString(bundle: NSBundle) -> String? {
		let res = NSLocalizedString(namespacedKey,
			tableName: tableName,
			bundle: bundle,
			value: namespacedKey,
			comment: "");

		return (res.lowercaseString != namespacedKey.lowercaseString) ? res : nil
	}

	let bundles = NSBundle.allBundles(obj.dynamicType)

	for bundle in bundles {
		// use forced language bundle
		if let languageBundle = NSLocale.bundleForLanguage(lang, bundle: bundle),
				res = getString(languageBundle) {
			return res
		}

		// try with outer bundle
		if let res = getString(bundle) {
			return res
		}
	}

	return namespacedKey
}


public func isOSAtLeastVersion(version: String) -> Bool {
	let currentVersion = UIDevice.currentDevice().systemVersion

	if currentVersion.compare(version,
			options: .NumericSearch,
			range: nil,
			locale: nil) != NSComparisonResult.OrderedAscending {

		return true
	}

	return false
}


public func isOSEarlierThanVersion(version: String) -> Bool {
	return !isOSAtLeastVersion(version)
}


public func adjustRectForCurrentOrientation(rect: CGRect) -> CGRect {
	var adjustedRect = rect

	if isOSEarlierThanVersion("8.0") {
		// For 7.x and earlier, the width and height are reversed when
		// the device is landscaped
		switch UIDevice.currentDevice().orientation {
			case .LandscapeLeft, .LandscapeRight:
				adjustedRect = CGRectMake(
						rect.origin.y, rect.origin.x,
						rect.size.height, rect.size.width)
			default: ()
		}
	}

	return adjustedRect
}

public func centeredRectInView(view: UIView, size: CGSize) -> CGRect {
	return CGRectMake(
			(view.frame.size.width - size.width) / 2,
			(view.frame.size.height - size.height) / 2,
			size.width,
			size.height)
}
