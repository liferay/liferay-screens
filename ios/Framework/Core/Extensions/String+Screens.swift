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

#if LIFERAY_SCREENS_FRAMEWORK
	import SMXMLDocument
#endif


extension String {

	public func toSafeFilename() -> String {
		let regex = try! NSRegularExpression(
			pattern: "[^a-zA-Z0-9_]+",
			options: [])

		return regex.stringByReplacingMatchesInString(self,
			options: [],
			range: NSMakeRange(0, self.characters.count),
			withTemplate: "-")
	}

	public var asNumber: NSNumber? {
		guard let number = Int64(self) else {
			return nil
		}

		return NSNumber(longLong: number)
	}

	public var asLong: Int64? {
		return Int64(self)
	}

	public var isXml: Bool {
		return self.hasPrefix("<?xml")
	}

	public func asLocalized(locale: NSLocale) -> String {
		guard self.isXml else {
			return self
		}

		let data = self.dataUsingEncoding(NSUTF8StringEncoding)

		guard let document = try? SMXMLDocument(data: data) else {
			return self
		}

		let defaultLocale = document.attributeNamed("default-locale") ?? "en_US"

		let found =
			document.deepChildWithAttribute("language-id", value: locale.localeIdentifier)
			??
			document.deepChildWithAttribute("language-id", value: defaultLocale)

		return found?.value ?? self
	}

}
