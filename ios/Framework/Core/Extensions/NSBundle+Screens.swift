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
import Foundation


extension NSBundle {

	public class func allBundles(currentClass: AnyClass) -> [NSBundle] {
		let bundles =
			[discoverBundles(),
				[bundleForDefaultTheme(),
					bundleForCore(),
					NSBundle(forClass: currentClass), NSBundle(forClass: currentClass), NSBundle(forClass: currentClass)],
				bundlesForApp()]
				.flatMap { $0 }
		return reduce(bundles, []) { ac, x in
			contains(ac, x) ? ac : ac + [x]
		}
	}

	public class func discoverBundles() -> [NSBundle] {
		let allBundles = NSBundle.allFrameworks() as! [NSBundle]

		return allBundles.filter {
			let screensPrefix = "LiferayScreens"
			let bundleName = $0.bundleIdentifier?.pathExtension ?? ""

			return count(bundleName) > count(screensPrefix)
					&& bundleName.hasPrefix(screensPrefix)
		}
	}

	public class func bundleForDefaultTheme() -> NSBundle {
		let frameworkBundle = NSBundle(forClass: BaseScreenlet.self)

		let defaultBundlePath = frameworkBundle.pathForResource("LiferayScreens-default",
				ofType: "bundle")!

		return NSBundle(path: defaultBundlePath)!
	}

	public class func bundleForCore() -> NSBundle {
		let frameworkBundle = NSBundle(forClass: BaseScreenlet.self)

		let coreBundlePath = frameworkBundle.pathForResource("LiferayScreens-core",
				ofType: "bundle")!

		return NSBundle(path: coreBundlePath)!
	}

	public class func bundlesForApp() -> [NSBundle] {

		func appFile(path: String) -> String? {
			var outError: NSError? = nil
			let files = NSFileManager.defaultManager().contentsOfDirectoryAtPath(path, error: &outError)
			return (files as? [String] ?? []).filter { $0.pathExtension == "app" }.first
		}

		let components = NSBundle.mainBundle().resourcePath?.pathComponents ?? []

		if components.last == "Overlays" {
			// running into IB
			let coreBundle = bundleForCore()

			if let range = coreBundle.resourcePath?.rangeOfString("Debug-iphonesimulator"),
					path = coreBundle.resourcePath?.substringToIndex(range.endIndex),
					appName = appFile(path),
					appBundle = NSBundle(path: path.stringByAppendingPathComponent(appName)) {
				return [NSBundle.mainBundle(), appBundle]
			}
		}

		return [NSBundle.mainBundle()]
	}


	public class func imageInBundles(#name: String, currentClass: AnyClass) -> UIImage? {
		for bundle in allBundles(currentClass) {
			if let path = bundle.pathForResource(name, ofType: "png") {
				return UIImage(contentsOfFile: path)
			}
		}

		return nil
	}

}
