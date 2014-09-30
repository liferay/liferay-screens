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


public struct ThemeManager {

	private static var installedThemes: [String]?

	public static func exists(#themeName: String) -> Bool {
		if installedThemes == nil {
			loadThemes()
		}

		return !(installedThemes!.filter() { return $0 == themeName }).isEmpty
	}


	//MARK: Internal methods

	private static func loadThemes() {
		let allThemes = themedXibs().map { $0.stringByDeletingPathExtension.lowercaseString }

		let notDuplicatedThemes = allThemes.reduce([String]()) { (acum, value) -> [String] in
			if (acum.filter() { return $0 == value }).isEmpty {
				return acum + [value]
			}

			return acum
		}

		installedThemes = notDuplicatedThemes
	}

	private static func themedXibs() -> [String] {
		var filePaths:[String] = []

		// create a dummy object in order to get the bundle where it lives

		let bundle = NSBundle(forClass: DummyClass().dynamicType)

		if let enumerator = NSFileManager.defaultManager().enumeratorAtPath(bundle.bundlePath) {
			var filePath: String? = enumerator.nextObject() as? String

			do {
				if filePath != nil && filePath!.hasSuffix(".nib") {
					let parts = filePath!.componentsSeparatedByString("_")
					if parts.count > 1 {
						filePaths.append(parts[1])
//						filePaths.append((parts[1] as NSString).stringByDeletingPathExtension)
					}
				}

				filePath = enumerator.nextObject() as? String
			} while filePath != nil
		}

		return filePaths
	}

}

private class DummyClass {
}
