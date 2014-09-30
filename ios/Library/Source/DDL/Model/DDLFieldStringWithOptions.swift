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


public class DDLFieldStringWithOptions : DDLField {

	public class Option {

		public var label:String
		public var name:String
		public var value:String

		public init(label:String, name:String, value:String) {
			self.label = label
			self.name = name
			self.value = value
		}

	}


	public var currentOptionLabel:String {
		var result = ""

		if let currentOptions = currentValue as? [Option] {
			if let firstOption = currentOptions.first {
				result = firstOption.label
			}
		}

		return result
	}

	//FIXME: Multiple selection not supported yet
	private(set) var multiple:Bool

	private(set) var options:[Option] = []

	override public init(attributes: [String:AnyObject]) {
		multiple = Bool.from(any: attributes["multiple"] ?? "false")

		if let optionsArray = (attributes["options"] ?? nil) as? [[String:AnyObject]] {
			for optionDict in optionsArray {
				let label = (optionDict["label"] ?? "") as String
				let name = (optionDict["name"] ?? "") as String
				let value = (optionDict["value"] ?? "") as String

				let option = Option(label:label, name:name, value:value)

				self.options.append(option)
			}
		}

		super.init(attributes: attributes)
	}


	//MARK: DDLField

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		var result:String = "["

		if let currentOptions = value as? [Option] {
			var first = true
			for option in currentOptions {
				if first {
					first = false
				}
				else {
					result += ", "
				}

				result += "\"\(option.value)\""
			}
		}

		return result + "]"
	}

	override internal func convert(fromString value:String?) -> AnyObject? {
		var result:[Option] = []

		func findOptionByValue(value:String) -> Option? {
			return options.filter { $0.value == value }.first
		}

		func findOptionByLabel(label:String) -> Option? {
			return options.filter { $0.label == label }.first
		}

		func extractFirstOption(options:String) -> String? {

			func removeFirstAndLastChars(value:String) -> String {
				var result: String = value

				if countElements(value) >= 2 {
					let range = Range<String.Index>(
									start: value.startIndex.successor(),
									end: value.endIndex.predecessor())
					result = value.substringWithRange(range)
				}

				return result
			}

			let optionsArray = removeFirstAndLastChars(options).componentsSeparatedByString(",")

			var result:String?

			if let firstOptionValue = optionsArray.first {
				result = firstOptionValue.hasPrefix("\"")
						? removeFirstAndLastChars(firstOptionValue) : firstOptionValue
			}

			return result
		}

		var firstOption:String?

		if let valueString = value {
			if valueString.hasPrefix("[") {
				firstOption = extractFirstOption(valueString)
			}
			else {
				firstOption = valueString
			}
		}

		if let firstOptionValue = firstOption {
			if let foundOption = findOptionByLabel(firstOptionValue) {
				result = [foundOption]
			}
			else if let foundOption = findOptionByValue(firstOptionValue) {
				result = [foundOption]
			}
		}

		return result
	}

	override internal func doValidate() -> Bool {
		let current = (currentValue as [Option]?) ?? []

		return !(required && current.count == 0)
	}

	override internal func onChangedCurrentValue() {
		if !(currentValue is [Option]) {
			if let currentValueAsString = currentValue as? String {
				currentValue = convert(fromString: currentValueAsString)
			}
		}
	}

}
