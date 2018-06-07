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

struct Geolocation {
	var latitude: Double
	var longitude: Double
}

@objc(DDMFieldGeolocation)
open class DDMFieldGeolocation: DDMField {

	var geolocation: Geolocation? {
		return currentValue as? Geolocation
	}

	override func convert(fromString value: String?) -> Any? {
		var result: Geolocation?

		guard let valueString = value, value != "" else {
			return nil
		}

		let data = valueString.data(using: String.Encoding.utf8, allowLossyConversion: false)

		let jsonObject = try? JSONSerialization.jsonObject(with: data!, options: [])

		if let jsonDict = jsonObject as? [String: AnyObject] {
			let latitude = jsonDict["latitude"] as! Double
			let longitude = jsonDict["longitude"] as! Double
			result = Geolocation(latitude: latitude, longitude: longitude)
		}

		return result
	}

	override func convert(fromCurrentValue value: Any?) -> String? {
		guard let geolocation = geolocation else {
			return nil
		}

		return """
			{"latitude": \(geolocation.latitude),
			"longitude": \(geolocation.longitude)}
		"""
	}

	override func doValidate() -> Bool {
		guard let geolocation = geolocation else {
			return !required
		}

		if geolocation.latitude > 90.0 || geolocation.latitude < -90.0 {
			return false
		}

		if geolocation.longitude > 180.0 || geolocation.longitude < -180.0 {
			return false
		}

		return true
	}
}
