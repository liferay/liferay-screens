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
	case Unsupported = ""

	public static func from(#xmlElement:SMXMLElement) -> DDLElementDataType {
		return fromRaw(xmlElement.attributeNamed("dataType") ?? "") ?? .Unsupported
	}

	public func createElement(#attributes:[String:String], localized:[String:AnyObject]) -> DDLElement? {
		switch self {
		case .DDLBoolean:
			return DDLElementBoolean(attributes:attributes, localized:localized)
		case .DDLString:
			let type = DDLElementType.from(attributes: attributes)

			if type == .Select || type == .Radio {
				return DDLElementStringWithOptions(attributes:attributes, localized:localized)
			}
			else {
				return DDLElementString(attributes:attributes, localized:localized)
			}
		default:
			return nil
		}
	}
}


public enum DDLElementType: String {

	case Checkbox = "checkbox"
	case Text = "text"
	case Textarea = "textarea"
	case Select = "select"
	case Radio = "radio"
	case Unsupported = ""

	public static func from(#xmlElement:SMXMLElement) -> DDLElementType {
		return fromRaw(xmlElement.attributeNamed("type") ?? "") ?? .Unsupported
	}

	public static func from(#attributes:[String:String]) -> DDLElementType {
		return fromRaw(attributes["type"] ?? "") ?? .Unsupported
	}

	public static func all() -> [DDLElementType] {
		return [Checkbox, Text, Textarea, Select, Radio]
	}

	public func toName() -> String {
		let elementName = toRaw()

		let secondCharIndex = elementName.startIndex.successor()

		return elementName.substringToIndex(secondCharIndex).uppercaseString + elementName.substringFromIndex(secondCharIndex)
	}

	public func registerHeight(height:CGFloat) {
		DDLElementType.elementTypeHeight[self] = height
	}

	public var registeredHeight: CGFloat {
		get {
			return DDLElementType.elementTypeHeight[self] ?? 0
		}
	}

	private static var elementTypeHeight: [DDLElementType:CGFloat] = [:]


}


public class DDLElement: Equatable {

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
	}

	public var validatedClosure: ((Bool) -> ())?

	public var lastValidationResult:Bool?

	internal(set) var dataType:DDLElementDataType
	internal(set) var type:DDLElementType

	internal(set) var name:String

	internal(set) var label:String
	internal(set) var tip:String

	internal(set) var predefinedValue:AnyObject?

	internal(set) var readOnly:Bool
	internal(set) var repeatable:Bool
	internal(set) var required:Bool

	internal(set) var showLabel:Bool 	// Makes sense in mobile??
	internal(set) var width:Int? 		// Makes sense in mobile??


	public init(attributes:[String:String], localized:[String:AnyObject]) {
		dataType = DDLElementDataType.fromRaw(attributes["dataType"] ?? "") ?? .Unsupported
		type = DDLElementType.fromRaw(attributes["type"] ?? "") ?? .Unsupported
		name = attributes["name"] ?? ""

		readOnly = Bool.from(string: attributes["readOnly"] ?? "false")
		repeatable = Bool.from(string: attributes["repeatable"] ?? "false")
		required = Bool.from(string: attributes["required"] ?? "true")
		showLabel = Bool.from(string: attributes["showLabel"] ?? "true")

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
		currentHeight = type.registeredHeight
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
