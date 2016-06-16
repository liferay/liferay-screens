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


public class DDMFieldNumber : DDMField {

	public var maximumDecimalDigits = 2
	public var minimumDecimalDigits = 2

	public var isDecimal: Bool {
		return dataType != DataType.DDMInteger
	}


	//MARK: DDMField

	override internal func convert(fromString value: String?) -> AnyObject? {
		if let value = value {
			// server may return the number is one format that uses , as decimal separator
			let number = value.stringByReplacingOccurrencesOfString(",", withString: ".")

			let enFormatter = NSNumberFormatter()
			enFormatter.locale = NSLocale(localeIdentifier: "en_US")

			if let result = enFormatter.numberFromString(number) {
				switch CFNumberGetType(result as CFNumberRef) {
				case .Float32Type, .Float64Type, .FloatType, .CGFloatType:
					return NSDecimalNumber(float: result.floatValue)
				case .DoubleType:
					return NSDecimalNumber(double: result.doubleValue)
				default:
					return NSInteger(result.integerValue)
				}
			}
		}

		return nil
	}

	override internal func convert(fromLabel label: String?) -> AnyObject? {
		if label != nil {
			let formatter = NSNumberFormatter()

			formatter.locale = self.currentLocale

			if isDecimal {
				formatter.numberStyle = .DecimalStyle
				formatter.roundingMode = .RoundHalfUp
				formatter.maximumFractionDigits = maximumDecimalDigits
				formatter.minimumFractionDigits = minimumDecimalDigits
			}

			return formatter.numberFromString(label!)
		}

		return nil
	}

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		return formatNumber(value as? NSNumber,
				locale: NSLocale(localeIdentifier: "en_US"))
	}

	override internal func convertToLabel(fromCurrentValue value: AnyObject?) -> String? {
		return formatNumber(value as? NSNumber, locale: self.currentLocale)
	}

	override internal func onChangedCurrentValue() {
		if !isDecimal && currentValue is NSDecimalNumber {
			let decimal = (currentValue as! NSDecimalNumber).doubleValue
			currentValue = NSNumber(double: decimal + 0.5).integerValue
		}
	}


	//MARK: Private methods

	private func formatNumber(number: NSNumber?, locale: NSLocale) -> String? {
		if number == nil {
			return nil
		}

		let formatter = NSNumberFormatter()

		formatter.locale = locale

		if isDecimal {
			formatter.numberStyle = .DecimalStyle
			formatter.roundingMode = .RoundHalfUp
			formatter.maximumFractionDigits = maximumDecimalDigits
			formatter.minimumFractionDigits = minimumDecimalDigits
		}

		return formatter.stringFromNumber(number!)
	}

}
