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

	public func pathsForResourcesWithPrefix(prefix:String, suffix:String) -> [String] {

		// Inspired by http://stackoverflow.com/a/5860015

		var filePaths:[String] = []

		if let enumerator = NSFileManager.defaultManager().enumeratorAtPath(self.bundlePath) {
			var filePath: String? = enumerator.nextObject() as? String

			do {
				if let filePathValue = filePath {
					if filePathValue.hasPrefix(prefix) && filePathValue.hasSuffix(suffix) {
						filePaths.append(filePathValue)
					}
				}

				filePath = enumerator.nextObject() as? String
			} while filePath != nil
		}

		return filePaths
	}

}

