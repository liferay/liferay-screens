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


extension DDLField {

	public enum Editor: String {

		case Checkbox = "checkbox"
		case Text = "text"
		case Textarea = "textarea"
		case Select = "select"
		case Radio = "radio"
		case Date = "ddm-date"
		case Number = "number"
		case Document = "ddm-documentlibrary"
		case Unsupported = ""

		public static func from(xmlElement xmlElement:SMXMLElement) -> Editor {
			return from(attributeValue:(xmlElement.attributeNamed("type") ?? ""))
		}

		public static func from(attributes attributes:[String:AnyObject]) -> Editor {
			return from(attributeValue:((attributes["type"] ?? "") as! String))
		}

		public static func from(attributeValue attributeValue:String) -> Editor {
			var result = Editor.Unsupported

			// hack to convert ddm-integer, ddm-number and ddm-decimal to just number
			switch attributeValue {
				case "ddm-integer", "ddm-number", "ddm-decimal":
					result = .Number
				default:
					result = Editor(rawValue: attributeValue) ?? .Unsupported
			}

			return result
		}

		public static func all() -> [Editor] {
			return [Checkbox, Text, Textarea, Select, Radio, Date, Number, Document]
		}

		public func toCapitalizedName() -> String {
			var typeName = rawValue

			// hack for names prefixed with ddm
			if typeName.hasPrefix("ddm-") {
				let wholeRange = Range<String.Index>(
						start: typeName.startIndex,
						end: typeName.endIndex)

				typeName = typeName.stringByReplacingOccurrencesOfString("ddm-",
						withString: "",
						options: .CaseInsensitiveSearch,
						range: wholeRange)
			}

			// Capitalize first char

			let secondCharIndex = typeName.startIndex.successor()

			return typeName.substringToIndex(secondCharIndex).uppercaseString +
					typeName.substringFromIndex(secondCharIndex)
		}

	}

}
