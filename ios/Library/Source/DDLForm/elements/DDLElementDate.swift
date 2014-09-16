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


public class DDLElementDate : DDLElement {

	public var currentDateLabel: String? {
		var result: String?

		if let date = currentValue as? NSDate {
			result = clientDateFormatter.stringFromDate(date)
		}

		return result
	}

	private let serverYYYYDateFormat = "MM/dd/yyyy"
	private let serverYYDateFormat = "MM/dd/yy"

	private let serverYYYYDateFormatter = NSDateFormatter()
	private let serverYYDateFormatter = NSDateFormatter()
	private let clientDateFormatter = NSDateFormatter()

	override init(attributes: [String:String], localized: [String:AnyObject]) {
		serverYYYYDateFormatter.dateFormat = serverYYYYDateFormat
		serverYYDateFormatter.dateFormat = serverYYDateFormat

		clientDateFormatter.dateStyle = .MediumStyle
		clientDateFormatter.timeStyle = .NoStyle

		super.init(attributes: attributes, localized: localized)
	}


	override internal func convert(fromString value:String?) -> AnyObject? {
		if let stringValue = value {
			// minimum date length in mm/dd/yy is 6 characters
			if countElements(stringValue) >= 6 {
				let formatter = stringValue[stringValue.endIndex.predecessor().predecessor()] == "/" ?
					serverYYDateFormatter : serverYYDateFormatter
				return formatter.dateFromString(stringValue)
			}
		}

		return nil
	}

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		var result: String?

		if let date = value as? NSDate {
			// Java uses milliseconds instead of seconds
			let epoch = UInt64(date.timeIntervalSince1970 * 1000)

			result = "\(epoch)"
		}

		return result
	}

}
