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

	override public var currentLocale: NSLocale {
		didSet {
			clientDateFormatter.locale = self.currentLocale
		}
	}

	private let serverYYYYDateFormat = "MM/dd/yyyy"
	private let serverYYDateFormat = "MM/dd/yy"

	private let serverYYYYDateFormatter = NSDateFormatter()
	private let serverYYDateFormatter = NSDateFormatter()
	private let clientDateFormatter = NSDateFormatter()

	private let gmtTimeZone = NSTimeZone(abbreviation: "GMT")


	override public init(attributes: [String:AnyObject], locale: NSLocale) {
		super.init(attributes: attributes, locale: locale)

		initFormatters(locale)
	}

	public required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)

		initFormatters(self.currentLocale)
	}

	private func initFormatters(locale: NSLocale) {
		serverYYYYDateFormatter.dateFormat = serverYYYYDateFormat
		serverYYDateFormatter.dateFormat = serverYYDateFormat

		clientDateFormatter.dateStyle = .LongStyle
		clientDateFormatter.timeStyle = .NoStyle
		clientDateFormatter.locale = locale

		serverYYYYDateFormatter.timeZone = gmtTimeZone
		serverYYDateFormatter.timeZone = gmtTimeZone
	}


	//MARK: DDLField

	override internal func convert(fromString value:String?) -> AnyObject? {
		if let stringValue = value {
			// minimum date length in mm/dd/yy is 6 characters
			if stringValue.characters.count >= 6 {
				let formatter = stringValue[stringValue.endIndex.predecessor().predecessor()] == "/"
					? serverYYDateFormatter : serverYYYYDateFormatter
				return formatter.dateFromString(stringValue)
			}
		}

		return nil
	}

	override func convert(fromLabel label: String?) -> AnyObject? {
		var result: AnyObject?

		if label != nil {
			result = clientDateFormatter.dateFromString(label!)
		}

		return result
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

	override func convertToLabel(fromCurrentValue value: AnyObject?) -> String? {
		var result: String?

		if let date = currentValue as? NSDate {
			result = clientDateFormatter.stringFromDate(date)
		}

		return result
	}

}
