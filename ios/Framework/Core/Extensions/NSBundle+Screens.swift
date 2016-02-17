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
			[
				discoverBundles(),
				bundlesForDefaultTheme(),
				bundlesForCore(),
				bundlesForApp(),
				[NSBundle(forClass: currentClass)]
			]
			.flatMap { $0 }

		return bundles.reduce([]) { ac, x in
			ac.contains(x) ? ac : ac + [x]
		}
	}

	public class func discoverBundles() -> [NSBundle] {
		let allBundles = NSBundle.allFrameworks() 

		return allBundles.filter {
			let screensPrefix = "LiferayScreens"
			let bundleName = (($0.bundleIdentifier ?? "") as NSString).pathExtension

			return bundleName.characters.count > screensPrefix.characters.count
					&& bundleName.hasPrefix(screensPrefix)
		}
	}

	public class func bundlesForDefaultTheme() -> [NSBundle] {
		return [bundleForName("LiferayScreens-default"), bundleForName("LiferayScreens-ee-default")]
	}

	public class func bundlesForCore() -> [NSBundle] {
		return [bundleForName("LiferayScreens-core"), bundleForName("LiferayScreens-ee-core")]
	}

	public class func bundleForName(name: String) -> NSBundle {
		let frameworkBundle = NSBundle(forClass: BaseScreenlet.self)

		let bundlePath = frameworkBundle.pathForResource(name, ofType: "bundle")

		// In test environment, separated bundles don't exist.
		// In such case, the frameworkBundle is used
		return (bundlePath != nil) ? NSBundle(path: bundlePath!)! : frameworkBundle
	}

	public class func bundlesForApp() -> [NSBundle] {

		func appFile(path: String) -> String? {
			let files = try? NSFileManager.defaultManager().contentsOfDirectoryAtPath(path)
			return (files ?? []).filter {
					($0 as NSString).pathExtension == "app"
				}
				.first
		}

		let components = ((NSBundle.mainBundle().resourcePath ?? "") as NSString).pathComponents ?? []

		if components.last == "Overlays" {
			// running into IB
			let coreBundle = bundlesForCore()[0]

			if let range = coreBundle.resourcePath?.rangeOfString("Debug-iphonesimulator"),
					path = coreBundle.resourcePath?.substringToIndex(range.endIndex),
					appName = appFile(path),
					appBundle = NSBundle(path: (path as NSString).stringByAppendingPathComponent(appName)) {
				return [NSBundle.mainBundle(), appBundle]
			}
		}

		return [NSBundle.mainBundle()]
	}


	public class func imageInBundles(name name: String, currentClass: AnyClass) -> UIImage? {
		for bundle in allBundles(currentClass) {
			if let path = bundle.pathForResource(name, ofType: "png") {
				return UIImage(contentsOfFile: path)
			}
		}

		return nil
	}

}
