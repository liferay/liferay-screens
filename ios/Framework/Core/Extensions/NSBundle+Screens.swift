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

	//MARK: bundlesForX methods

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

	public class func bundleForNibName(name: String, currentClass: AnyClass) -> NSBundle? {
		return NSBundle.allBundles(currentClass)
			.filter{
				$0.pathForResource(name, ofType:"nib") != nil
			}
			.first
	}



	//MARK: xInBundles methods

	public class func imageInBundles(name name: String, currentClass: AnyClass) -> UIImage? {
		for bundle in allBundles(currentClass) {
			if let path = bundle.pathForResource(name, ofType: "png") {
				return UIImage(contentsOfFile: path)
			}
		}

		return nil
	}
    
    public class func nibInBundles(name name: String, currentClass: AnyClass) -> UINib? {
        return resourceInBundle(
            	name: name,
            	ofType: "nib",
            	currentClass: currentClass) { _, bundle in
			return UINib(nibName: name, bundle: bundle)
        }
    }


	//MARK: xforTheme methods

	public class func viewForThemeOrDefault(
			name name: String,
			themeName: String,
			currentClass: AnyClass) -> UIView? {

		return rootNibObjectForThemeOrDefault(
			name: name,
			themeName: themeName,
			currentClass: currentClass) as? UIView
	}

	public class func viewForTheme(
			name name: String,
			themeName: String,
			currentClass: AnyClass) -> UIView? {

		return rootNibObjectForTheme(
			name: name,
			themeName: themeName,
			currentClass: currentClass) as? UIView
	}

	public class func rootNibObjectForThemeOrDefault(
			name name: String,
			themeName: String,
			currentClass: AnyClass) -> AnyObject? {

		if let foundObject = NSBundle.rootNibObjectForTheme(
				name: name,
				themeName: themeName,
				currentClass: currentClass) {

			return foundObject
		}

		if themeName == BaseScreenlet.DefaultThemeName {
			return nil
		}

		if let foundObject = NSBundle.rootNibObjectForTheme(
				name: name,
				themeName: BaseScreenlet.DefaultThemeName,
				currentClass: currentClass) {

			return foundObject
		}

		return nil
	}

	public class func rootNibObjectForTheme(
			name name: String,
			themeName: String,
			currentClass: AnyClass) -> AnyObject? {

		let nibName = "\(name)_\(themeName)"
		return resourceInBundle(
				name: nibName,
				ofType: "nib",
				currentClass: currentClass) {_, bundle in

			let objects = bundle.loadNibNamed(nibName, owner: currentClass, options: nil)

			assert(objects == nil || objects!.count > 0, "Malformed xib \(nibName). Without objects")

			return objects![0]
		}
	}
    
    public class func resourceInBundle<T>(
			name name: String,
			ofType type: String,
			currentClass: AnyClass,
			@noescape resourceInit: (String , NSBundle) -> T?) -> T? {

        for bundle in allBundles(currentClass) {
            if let path = bundle.pathForResource(name, ofType: type) {
                return resourceInit(path, bundle)
            }
        }
        
        return nil
    }

}
