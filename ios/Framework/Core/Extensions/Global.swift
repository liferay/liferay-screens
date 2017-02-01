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

public func nullIfEmpty(_ string: String?) -> String? {
	if string == nil {
		return nil
	}
	else if string! == "" {
		return nil
	}

	return string
}

public func synchronized(_ lock: AnyObject, closure: (Void) -> Void) {
	objc_sync_enter(lock)
	closure()
	objc_sync_exit(lock)
}


public func dispatch_delayed(_ delay: TimeInterval, block: @escaping ()->()) {
    let time = DispatchTime.now() + Double(Int64(delay * Double(NSEC_PER_SEC))) / Double(NSEC_PER_SEC)
    DispatchQueue.main.asyncAfter(deadline: time, execute: block)
}

public func dispatch_async(_ block: @escaping ()->()) {
	let queue = DispatchQueue.global(qos: .background)
	queue.async {
		block()
	}
}


public func dispatch_async(_ block: @escaping ()->(), thenMain mainBlock: @escaping ()->()) {
	let queue = DispatchQueue.global(qos: .background)
	queue.async {
		block()

		DispatchQueue.main.async {
			mainBlock()
		}
	}
}


public typealias Signal = (() -> ())

public func dispatch_sync(_ block: (@escaping Signal) -> ()) {
	let waitGroup = DispatchGroup()
	waitGroup.enter()
	block {
		waitGroup.leave()
	}
	_ = waitGroup.wait(timeout: DispatchTime.distantFuture)
}

public func to_sync(_ function: @escaping (@escaping Signal) -> ()) -> () -> () {
	return {
		dispatch_sync(function)
	}
}

public func dispatch_main(_ block: @escaping ()->()) {
	if Thread.isMainThread {
		block()
	}
	else {
		DispatchQueue.main.async {
			block()
		}
	}
}

public func dispatch_main(_ forceDispatch: Bool, block: @escaping ()->()) {
	if !forceDispatch && Thread.isMainThread {
		block()
	}
	else {
		DispatchQueue.main.async {
			block()
		}
	}
}



public func ScreenletName(_ klass: AnyClass) -> String {
	var className = NSStringFromClass(klass)

	if className.characters.index(of: ".") != nil {
		className = className.components(separatedBy: ".")[1]
	}

	return className.components(separatedBy: "Screenlet")[0]
}

public func dynamicInit(_ className: String) -> NSObject? {
	guard let klass = NSClassFromString(className) else {
		return nil
	}
	guard let type = klass as? NSObject.Type else {
		return nil
	}
	return type.init()
}

public func LocalizedString(_ tableName: String, key: String, obj: AnyObject) -> String {
	return LocalizedString(tableName, key: key, obj: obj, lang: NSLocale.currentLanguageString)
}

public func LocalizedPlural(_ tableName: String, keySingular key1: String, keyPlural key2: String,
		obj: AnyObject, count: NSNumber) -> String {
	if count == 1 {
		return LocalizedString(tableName, key: key1, obj: obj)
	}

	return NSString.localizedStringWithFormat(
		LocalizedString(tableName, key: key2, obj: obj) as NSString, count.int32Value) as String
}

public func LocalizedString(_ tableName: String, key: String, obj: AnyObject, lang: String) -> String {
	let namespacedKey = "\(tableName)-\(key)"

	func getString(_ bundle: Bundle) -> String? {
		let res = NSLocalizedString(namespacedKey,
			tableName: tableName,
			bundle: bundle,
			value: namespacedKey,
			comment: "");

		return (res.lowercased() != namespacedKey.lowercased()) ? res : nil
	}

	let bundles = Bundle.allBundles(type(of: obj))

	for bundle in bundles {
		// use forced language bundle
		if let languageBundle = NSLocale.bundleForLanguage(lang, bundle: bundle),
				let res = getString(languageBundle) {
			return res
		}

		// try with outer bundle
		if let res = getString(bundle) {
			return res
		}
		
		// by default fallback to english
		if let languageBundle = NSLocale.bundleForLanguage("en", bundle: bundle),
			let res = getString(languageBundle) {
			return res
		}
	}

	return namespacedKey
}


public func isOSAtLeastVersion(_ version: String) -> Bool {
	let currentVersion = UIDevice.current.systemVersion

	if currentVersion.compare(version,
			options: .numeric,
			range: nil,
			locale: nil) != .orderedAscending {

		return true
	}

	return false
}


public func isOSEarlierThanVersion(_ version: String) -> Bool {
	return !isOSAtLeastVersion(version)
}


public func adjustRectForCurrentOrientation(_ rect: CGRect) -> CGRect {
	var adjustedRect = rect

	if isOSEarlierThanVersion("8.0") {
		// For 7.x and earlier, the width and height are reversed when
		// the device is landscaped
		switch UIDevice.current.orientation {
			case .landscapeLeft, .landscapeRight:
				adjustedRect = CGRect(
						x: rect.origin.y, y: rect.origin.x,
						width: rect.size.height, height: rect.size.width)
			default: ()
		}
	}

	return adjustedRect
}

public func centeredRectInView(_ view: UIView, size: CGSize) -> CGRect {
	return CGRect(
			x: (view.frame.size.width - size.width) / 2,
			y: (view.frame.size.height - size.height) / 2,
			width: size.width,
			height: size.height)
}

public func cacheFilePath() -> String {
	let cache = NSSearchPathForDirectoriesInDomains(.cachesDirectory, .allDomainsMask, true)[0]

	var cachePath = ""
	repeat {
		cachePath = "\(cache)/\(UUID().uuidString)"
	} while (FileManager.default.fileExists(atPath: cachePath))

	return cachePath
}

public func isCacheFilePath(_ path: String) -> Bool {
	return path.contains("/Library/Caches/")
}

// FIXME: comparison operators with optionals were removed from the Swift Standard Libary.
// Consider refactoring the code to use the non-optional operators.
func < <T : Comparable>(lhs: T?, rhs: T?) -> Bool {
	switch (lhs, rhs) {
	case let (l?, r?):
		return l < r
	case (nil, _?):
		return true
	default:
		return false
	}
}
