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

@objc(DDLFieldGeolocationTableCell_default)
open class DDLFieldGeolocationTableCell_default: DDMFieldTableCell, UITextFieldDelegate {

	// MARK: Outlets

	@IBOutlet open var latitudeTextField: DefaultTextField?
	@IBOutlet open var longitudeTextField: DefaultTextField?

	@IBOutlet open var label: UILabel?

	// MARK: Static properties

	open static let InvalidLatitude = Double(90 + 1)
	open static let InvalidLongitude = Double(180 + 1)

	// MARK: DDMFieldTableCell

	open override func onChangedField() {
		guard let field = field as? DDMFieldGeolocation else {
			return
		}

		if field.showLabel {
			label?.text = field.label
		}

		if let geolocation = field.currentValue as? Geolocation {
			latitudeTextField?.text = "\(geolocation.latitude)"
			longitudeTextField?.text = "\(geolocation.longitude)"
		}
		else {
			latitudeTextField?.text = ""
			longitudeTextField?.text = ""
		}
	}

	open override func onPostValidation(_ valid: Bool) {
		super.onPostValidation(valid)

		if valid {
			latitudeTextField?.setDefaultState()
			longitudeTextField?.setDefaultState()
		}
		else {
			latitudeTextField?.setErrorState()
			longitudeTextField?.setErrorState()
		}
	}

	// MARK: UITextViewDelegate

	public func textFieldDidEndEditing(_ textField: UITextField) {
		let latitudeText = latitudeTextField?.text ?? ""
		let longitudeText = longitudeTextField?.text ?? ""

		let latitude = Double(latitudeText) ?? DDLFieldGeolocationTableCell_default.InvalidLatitude
		let longitude = Double(longitudeText) ?? DDLFieldGeolocationTableCell_default.InvalidLongitude

		if field != nil {
			let geolocation = Geolocation(latitude: latitude, longitude: longitude)
			field?.currentValue = geolocation
		}
	}
}
