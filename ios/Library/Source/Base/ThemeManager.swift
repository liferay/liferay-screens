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

	//MARK: Singleton

	class func instance() -> ThemeManager {
		struct Static {
			static var instance: ThemeManager? = nil
			static var onceToken: dispatch_once_t = 0
		}

		dispatch_once(&Static.onceToken) {
			Static.instance = self()
		}

		return Static.instance!
	}

	required override public init() {
		super.init()
		loadThemes()
	}

	public func installedThemes() -> [String] {
		return _installedThemes
	}

	internal func loadThemes() {
		_installedThemes.removeAll(keepCapacity: true)

		let bundle = NSBundle(forClass:self.dynamicType)

		let pngFileNames = bundle.pathsForResourcesWithPrefix("theme-", suffix: ".png")

		let widgetNames = pngFileNames.map {(var fileName) -> String in
			let prefixRange = NSMakeRange(0, 6)

			var name = fileName.bridgeToObjectiveC().stringByReplacingCharactersInRange(prefixRange, withString:"").stringByDeletingPathExtension

			return name.lowercaseString
		}

		_installedThemes += widgetNames
	}

	private var _installedThemes:[String] = []

}
