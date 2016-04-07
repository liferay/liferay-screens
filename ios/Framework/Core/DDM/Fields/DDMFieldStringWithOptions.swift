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


public class DDMFieldStringWithOptions : DDMField {

	public class Option: NSObject, NSCoding {

		public var label: String
		public var name: String?
		public var value: String

		public init(label: String, name: String?, value: String) {
			self.label = label
			self.name = name
			self.value = value
		}

		public required convenience init?(coder aDecoder: NSCoder) {
			let label = aDecoder.decodeObjectForKey("label") as! String
			let name = aDecoder.decodeObjectForKey("name") as? String
			let value = aDecoder.decodeObjectForKey("value") as! String

			self.init(label: label, name: name, value: value)
		}

		public func encodeWithCoder(aCoder: NSCoder) {
			aCoder.encodeObject(label, forKey: "label")
			if let name = name {
				aCoder.encodeObject(name, forKey: "name")
			}
			aCoder.encodeObject(value, forKey: "value")
		}

		override public var description: String {
			return self.value
		}

	}


	//FIXME: Multiple selection not supported yet
	private(set) var multiple: Bool

	private(set) var options: [Option] = []

	override public init(attributes: [String:AnyObject], locale: NSLocale) {
		multiple = Bool.from(any: attributes["multiple"] ?? "false")

		if let optionsArray = (attributes["options"] ?? nil) as? [[String:AnyObject]] {
			for optionDict in optionsArray {
				let label = (optionDict["label"] ?? "") as! String
				let name = optionDict["name"] as? String
				let value = (optionDict["value"] ?? "") as! String

				let option = Option(label: label, name: name, value: value)

				self.options.append(option)
			}
		}

		super.init(attributes: attributes, locale: locale)
	}

	public required init?(coder aDecoder: NSCoder) {
		multiple = aDecoder.decodeBoolForKey("multiple")
		options = aDecoder.decodeObjectForKey("options") as! [Option]

		super.init(coder: aDecoder)
	}

	public override func encodeWithCoder(aCoder: NSCoder) {
		super.encodeWithCoder(aCoder)

		aCoder.encodeBool(multiple, forKey: "multiple")
		aCoder.encodeObject(options, forKey: "options")
	}


	//MARK: DDMField

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		var result = "["

		if let currentOptions = value as? [NSObject] {
			var first = true
			for option in currentOptions {
				if first {
					first = false
				}
				else {
					result += ", "
				}

				result += "\"\(option.description)\""
			}
		}

		return result + "]"
	}

	override internal func convert(fromString value: String?) -> AnyObject? {
		var result = [Option]()

		if let firstOptionValue = extractOption(value) {
			if let foundOption = findOptionByLabel(firstOptionValue) {
				result = [foundOption]
			}
			else if let foundOption = findOptionByValue(firstOptionValue) {
				result = [foundOption]
			}
		}

		return result
	}

	override func convert(fromLabel labels: String?) -> AnyObject? {
		if let label = extractOption(labels) {
			if let foundOption = findOptionByLabel(label) {
				return [foundOption]
			}
		}

		return [Option]()
	}


	override func convertToLabel(fromCurrentValue value: AnyObject?) -> String? {
		if let currentOptions = currentValue as? [Option] {
			if let firstOption = currentOptions.first {
				return firstOption.label
			}
		}

		return ""
	}

	override internal func doValidate() -> Bool {
		let current = (currentValue as! [Option]?) ?? []

		return !(required && current.count == 0)
	}

	override internal func onChangedCurrentValue() {
		if !(currentValue is [Option]) {
			if let currentValueAsString = currentValue as? String {
				currentValue = convert(fromString: currentValueAsString)
			}
		}
	}


	//MARK: Private methods

	private func extractOption(options: String?) -> String? {
		if let optionsValue = options {
			if optionsValue.hasPrefix("[") {
				return extractFirstOption(optionsValue)
			}

			return optionsValue
		}

		return nil
	}

	private func extractFirstOption(options: String) -> String? {

		func removeFirstAndLastChars(value: String) -> String {
			if value.characters.count >= 2 {
				let range = value.startIndex.successor()..<value.endIndex.predecessor()
				return value.substringWithRange(range)
			}

			return value
		}

		let optionsArray = removeFirstAndLastChars(options).componentsSeparatedByString(",")

		if let firstOption = optionsArray.first {
			return firstOption.hasPrefix("\"")
					? removeFirstAndLastChars(firstOption)
					: firstOption
		}

		return nil
	}

	private func findOptionByValue(value: String) -> Option? {
		return options.filter {
				$0.value == value
			}.first
	}

	private func findOptionByLabel(label: String) -> Option? {
		return options.filter {
				$0.label == label
			}.first
	}

}
