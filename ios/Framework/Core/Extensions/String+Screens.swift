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

	public func toHtmlTextWithAttributes(attributes: [String: NSObject]) -> NSAttributedString? {

		//Init text with default paragraph style
		var text = "<style>p:last-of-type{ margin-bottom: 0px; padding-bottom: 0px; }</style>"
			+ "<div style=\""

		if let font = attributes[NSFontAttributeName] as? UIFont {
			text.appendContentsOf("font-family: \(font.fontName);font-size: \(font.pointSize);")
		}

		if let color = attributes[NSForegroundColorAttributeName] as? UIColor {
			text.appendContentsOf("color: \(self.toHexString(color));")
		}

		text.appendContentsOf("\">\(self)</div>")

		let encodedData = text.dataUsingEncoding(NSUTF8StringEncoding)

		if let data = encodedData {

			let attributes = attributes.copyAndMerge([
				NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType,
				NSCharacterEncodingDocumentAttribute: NSUTF8StringEncoding,
			])

			return try! NSAttributedString(data: data, options: attributes, documentAttributes: nil)
		}

		return nil
	}

	func toHexString(color: UIColor) -> String {
		var r:CGFloat = 0
		var g:CGFloat = 0
		var b:CGFloat = 0
		var a:CGFloat = 0

		color.getRed(&r, green: &g, blue: &b, alpha: &a)

		let rgb:Int = (Int)(r*255)<<16 | (Int)(g*255)<<8 | (Int)(b*255)<<0

		return NSString(format:"#%06x", rgb) as String
	}

	func removeFirstAndLastChars() -> String {
		if characters.count >= 2 {
			let range = startIndex.successor()..<endIndex.predecessor()
			return substringWithRange(range)
		}

		return self
	}

	func trim() -> String {
		return stringByTrimmingCharactersInSet(
			NSCharacterSet.whitespaceAndNewlineCharacterSet())
	}

}
