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


public enum ScreenletsErrorCause: Int {

	case AbortedDueToPreconditions = -2
	case InvalidServerResponse = -3

}


internal func createError(#cause: ScreenletsErrorCause, userInfo: NSDictionary? = nil) -> NSError {
	return NSError(domain: "LiferayScreenlets", code: cause.rawValue, userInfo: userInfo)
}

internal func createError(#cause: ScreenletsErrorCause, #message: String) -> NSError {
	let userInfo = [NSLocalizedDescriptionKey: message]

	return NSError(domain: "LiferayScreenlets", code: cause.rawValue, userInfo: userInfo)
}


public func nullIfEmpty(string: String?) -> String? {
	if string == nil {
		return nil
	}
	else if string! == "" {
		return nil
	}

	return string
}

func synchronized(lock:AnyObject, closure: Void -> Void) {
	objc_sync_enter(lock)
	closure()
	objc_sync_exit(lock)
}


func delayed(delay: NSTimeInterval, block: dispatch_block_t) {
    let time = dispatch_time(DISPATCH_TIME_NOW, Int64(delay * Double(NSEC_PER_SEC)))
    dispatch_after(time, dispatch_get_main_queue(), block)
}


func allBundles(#currentClass: AnyClass, #currentTheme: String?) -> [NSBundle?] {
	let frameworkBundle = NSBundle(forClass: currentClass)

	let themeBundlePath = (currentTheme == nil)
			? nil
			: frameworkBundle.pathForResource("LiferayScreens-\(currentTheme!)", ofType: "bundle")

	let coreBundlePath = frameworkBundle.pathForResource("LiferayScreens-core", ofType: "bundle")

	return [
		(themeBundlePath == nil) ? nil : NSBundle(path: themeBundlePath!),
		(coreBundlePath == nil) ? nil : NSBundle(path: coreBundlePath!),
		frameworkBundle,
		NSBundle.mainBundle()]
}


func imageInAnyBundle(#name: String, #currentClass: AnyClass, #currentTheme: String?) -> UIImage? {
	let bundles = allBundles(currentClass: currentClass, currentTheme: currentTheme)

	for bundle in bundles {
		if let path = bundle?.pathForResource(name, ofType: "png") {
			return UIImage(contentsOfFile: path)
		}
	}

	return nil
}


func LocalizedString(tableName: String, var key: String, obj: AnyObject) -> String {
	key = "\(tableName)-\(key)"

	let bundles = allBundles(currentClass: obj.dynamicType, currentTheme: tableName)

	for bundle in bundles {
		if let bundleValue = bundle {
			let res = NSLocalizedString(key,
						tableName: tableName,
						bundle: bundleValue,
						value: key,
						comment: "");

			if res.lowercaseString != key {
				return res
			}
		}
	}

	return key
}


func isOSAtLeastVersion(version: String) -> Bool {
	let currentVersion = UIDevice.currentDevice().systemVersion

	if currentVersion.compare(version,
			options: .NumericSearch,
			range: nil,
			locale: nil) != NSComparisonResult.OrderedAscending {

		return true
	}

	return false
}


func isOSEarlierThanVersion(version: String) -> Bool {
	return !isOSAtLeastVersion(version)
}


func adjustRectForCurrentOrientation(rect: CGRect) -> CGRect {
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

func centeredRectInView(view: UIView, #size: CGSize) -> CGRect {
	return CGRectMake(
			(view.frame.size.width - size.width) / 2,
			(view.frame.size.height - size.height) / 2,
			size.width,
			size.height)
}
