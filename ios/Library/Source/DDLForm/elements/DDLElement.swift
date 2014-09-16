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

public enum DDLElementDataType: String {

	case DDLBoolean = "boolean"
	case DDLString = "string"
	case DDLDate = "date"
	case DDLInteger = "integer"
	case DDLNumber = "number"
	case DDLDouble = "double"
	case DDLDocument = "document-library"
	case Unsupported = ""

	public static func from(#xmlElement:SMXMLElement) -> DDLElementDataType {
		return fromRaw(xmlElement.attributeNamed("dataType") ?? "") ?? .Unsupported
	}

	public func createElement(#attributes:[String:String], localized:[String:AnyObject]) -> DDLElement? {
		switch self {
		case .DDLBoolean:
			return DDLElementBoolean(attributes:attributes, localized:localized)
		case .DDLString:
			let type = DDLElementEditor.from(attributes: attributes)

			if type == .Select || type == .Radio {
				return DDLElementStringWithOptions(attributes:attributes, localized:localized)
			}
			else {
				return DDLElementString(attributes:attributes, localized:localized)
			}
		case .DDLDate:
			return DDLElementDate(attributes:attributes, localized:localized)
		case .DDLInteger, .DDLNumber, .DDLDouble:
			return DDLElementNumber(attributes:attributes, localized:localized)
		case .DDLDocument:
			return DDLElementDocument(attributes:attributes, localized:localized)
		default:
			return nil
		}
	}
}


public enum DDLElementEditor: String {

	case Checkbox = "checkbox"
	case Text = "text"
	case Textarea = "textarea"
	case Select = "select"
	case Radio = "radio"
	case Date = "ddm-date"
	case Number = "number"
	case Document = "ddm-documentlibrary"
	case Unsupported = ""

	public static func from(#xmlElement:SMXMLElement) -> DDLElementEditor {
		return from(attributeValue:(xmlElement.attributeNamed("type") ?? ""))
	}

	public static func from(#attributes:[String:String]) -> DDLElementEditor {
		return from(attributeValue:(attributes["type"] ?? ""))
	}

	public static func from(#attributeValue:String) -> DDLElementEditor {
		var result:DDLElementEditor = .Unsupported

		// hack to convert ddm-integer, ddm-number and ddm-decimal to just number
		switch attributeValue {
			case "ddm-integer", "ddm-number", "ddm-decimal":
				result = .Number
			default:
				result = fromRaw(attributeValue) ?? .Unsupported
		}

		return result
	}

	public static func all() -> [DDLElementEditor] {
		return [Checkbox, Text, Textarea, Select, Radio, Date, Number, Document]
	}

	public func toCapitalizedName() -> String {
		var typeName = toRaw()

		// hack for names prefixed with ddm
		if typeName.hasPrefix("ddm-") {
			let wholeRange = Range<String.Index>(
					start: typeName.startIndex,
					end: typeName.endIndex)

			typeName = typeName.stringByReplacingOccurrencesOfString("ddm-", withString: "", options: .CaseInsensitiveSearch, range: wholeRange)
		}

		// Capitalize first char

		let secondCharIndex = typeName.startIndex.successor()

		return typeName.substringToIndex(secondCharIndex).uppercaseString + typeName.substringFromIndex(secondCharIndex)
	}

	public func registerHeight(height:CGFloat) {
		DDLElementEditor.elementEditorHeight[self] = height
	}

	public var registeredHeight: CGFloat {
		get {
			return DDLElementEditor.elementEditorHeight[self] ?? 0
		}
	}

	private static var elementEditorHeight: [DDLElementEditor:CGFloat] = [:]

}


@objc public class DDLElement: Equatable {

	public var currentValue:AnyObject? {
		didSet {
			onChangedCurrentValue()
		}
	}
	public var currentHeight:CGFloat = 0

	public var currentStringValue:String? {
		get {
			return convert(fromCurrentValue: currentValue)
		}
		set {
			currentValue = convert(fromString: newValue)
		}
	}

	public var validatedClosure: ((Bool) -> ())?

	public var lastValidationResult:Bool?

	internal(set) var dataType:DDLElementDataType
	internal(set) var editorType:DDLElementEditor

	internal(set) var name:String

	internal(set) var label:String
	internal(set) var tip:String

	internal(set) var predefinedValue:AnyObject?

	internal(set) var readOnly:Bool
	internal(set) var repeatable:Bool
	internal(set) var required:Bool

	internal(set) var showLabel:Bool


	public init(attributes:[String:String], localized:[String:AnyObject]) {
		dataType = DDLElementDataType.fromRaw(attributes["dataType"] ?? "") ?? .Unsupported
		editorType = DDLElementEditor.from(attributes: attributes)
		name = attributes["name"] ?? ""

		readOnly = Bool.from(string: attributes["readOnly"] ?? "false")
		repeatable = Bool.from(string: attributes["repeatable"] ?? "false")
		required = Bool.from(string: attributes["required"] ?? "true")
		showLabel = Bool.from(string: attributes["showLabel"] ?? "false")

		label = (localized["label"] ?? "") as String
		tip = (localized["tip"] ?? "") as String
		predefinedValue = convert(fromString:(localized["predefinedValue"] ?? nil) as? String)
		currentValue = predefinedValue
	}

	public func validate() -> Bool {
		var valid = !(currentValue == nil && required)

		if valid {
			valid = doValidate()
		}

		validatedClosure?(valid)

		lastValidationResult = valid

		return valid
	}

	public func resetCurrentHeight() {
		currentHeight = editorType.registeredHeight
	}

	internal func doValidate() -> Bool {
		return true
	}

	internal func convert(fromString value:String?) -> AnyObject? {
		return value
	}

	internal func convert(fromCurrentValue value:AnyObject?) -> String? {
		return value?.description
	}

	internal func onChangedCurrentValue() {
	}

}


// MARK Equatable

public func ==(left: DDLElement, right: DDLElement) -> Bool {
	return left.name == right.name
}
