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


public class DDMFieldDate: DDMField {

	static let GMTTimeZone = NSTimeZone(abbreviation: "GMT")

	public var clientDateFormatter: NSDateFormatter {
		let result = NSDateFormatter()
		result.dateStyle = .LongStyle
		result.timeStyle = .NoStyle
		result.locale = currentLocale
		return result
	}

	public var serverDateFormat: String {
		return "MM/dd/yyyy"
	}

	public func formatterWithFormat(format: String) -> NSDateFormatter {
		let formatter = NSDateFormatter()

		formatter.timeZone = DDMFieldDate.GMTTimeZone
		formatter.dateFormat = format

		return formatter
	}

	//MARK: DDMField

	override private init(attributes: [String:AnyObject], locale: NSLocale) {
		super.init(attributes: attributes, locale: locale)
	}

	public required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

	override internal func convert(fromString value: String?) -> AnyObject? {
		func convertFromDateStr(str: String) -> NSDate? {
			let separator = str[str.endIndex.advancedBy(-3)]
			let format = separator == "/" ? "MM/dd/yy" : serverDateFormat

			return formatterWithFormat(format).dateFromString(str)
		}

		func convertFromJavaEpoch(str: String) -> NSDate? {
			guard let epoch = Double(str) else {
				return nil
			}

			return NSDate(timeIntervalSince1970: epoch/1000)
		}

		guard let stringValue = value else {
			return nil
		}
		guard !stringValue.isEmpty else {
			return nil
		}

		return convertFromDateStr(stringValue) ?? convertFromJavaEpoch(stringValue)
	}

	override func convert(fromLabel label: String?) -> AnyObject? {
		guard let label = label else {
			return nil
		}
		guard !label.isEmpty else {
			return nil
		}

		return clientDateFormatter.dateFromString(label)
	}

	override func convertToLabel(fromCurrentValue value: AnyObject?) -> String? {
		guard let date = currentValue as? NSDate else {
			return nil
		}

		return clientDateFormatter.stringFromDate(date)
	}

}



public class DDMFieldDate_v62: DDMFieldDate {

	override public init(attributes: [String:AnyObject], locale: NSLocale) {
		super.init(attributes: attributes, locale: locale)
	}

	public required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		guard let date = value as? NSDate else {
			return nil
		}

		// Java uses milliseconds instead of seconds
		let epoch = Int64(date.timeIntervalSince1970 * 1000)

		return "\(epoch)"
	}

}


public class DDMFieldDate_v70: DDMFieldDate {

	override public init(attributes: [String:AnyObject], locale: NSLocale) {
		super.init(attributes: attributes, locale: locale)
	}

	public required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

	override public var serverDateFormat: String {
		return "yyyy'-'MM'-'dd"
	}


	override internal func convert(fromString value: String?) -> AnyObject? {
		guard let stringValue = value else {
			return nil
		}

		// Liferay 7 is not consistent in date format.
		// It uses MM/dd/YYYY in predefinedValue field.

		return super.convert(fromString: value)
			?? formatterWithFormat("M'/'d'/'yyyy").dateFromString(stringValue)
	}


	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		guard let date = value as? NSDate else {
			return nil
		}

		return formatterWithFormat(serverDateFormat).stringFromDate(date)
	}


}
