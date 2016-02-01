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


public class DDLFieldDate : DDLField {

	private let serverDateFormat = "yyyy-MM-dd"
	private let gmtTimeZone = NSTimeZone(abbreviation: "GMT")

	public var serverDateFormatter: NSDateFormatter {
		let result = NSDateFormatter()
		result.dateFormat = serverDateFormat
		result.timeZone = gmtTimeZone
		return result
	}

	public var clientDateFormatter: NSDateFormatter {
		let result = NSDateFormatter()
		result.dateStyle = .LongStyle
		result.timeStyle = .NoStyle
		result.locale = currentLocale
		return result
	}


	override public init(attributes: [String:AnyObject], locale: NSLocale) {
		super.init(attributes: attributes, locale: locale)
	}

	public required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}


	//MARK: DDLField

	override internal func convert(fromString value: String?) -> AnyObject? {
		guard let stringValue = value else {
			return nil
		}
		guard !stringValue.isEmpty else {
			return nil
		}

		return serverDateFormatter.dateFromString(stringValue)
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

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		guard let date = value as? NSDate else {
			return nil
		}

		return serverDateFormatter.stringFromDate(date)
	}

	override func convertToLabel(fromCurrentValue value: AnyObject?) -> String? {
		guard let date = currentValue as? NSDate else {
			return nil
		}

		return clientDateFormatter.stringFromDate(date)
	}

}
