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


public class ThemeManager: NSObject {

	private(set) var installedThemes:[String] = []


	//MARK: Singleton

	class func instance() -> ThemeManager {
		struct Singleton {
			static var instance: ThemeManager? = nil
			static var onceToken: dispatch_once_t = 0
		}

		dispatch_once(&Singleton.onceToken) {
			Singleton.instance = self()
		}

		return Singleton.instance!
	}

	required override public init() {
		super.init()
		loadThemes()
	}


	//MARK: Internal methods

	internal func loadThemes() {
		installedThemes.removeAll(keepCapacity: true)

		let bundle = NSBundle(forClass:self.dynamicType)

		let pngFileNames = bundle.pathsForResourcesWithPrefix("theme-", suffix: ".png")

		let widgetNames = pngFileNames.map {(var fileName) -> String in
			let prefixRange = NSMakeRange(0, 6)

			var name = (fileName as NSString).stringByReplacingCharactersInRange(prefixRange,
					withString:"").stringByDeletingPathExtension

			return name.lowercaseString
		}

		installedThemes += widgetNames
	}

}
