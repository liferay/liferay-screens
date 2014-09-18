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
import UIKit


public class DDLFormView: BaseWidgetView, UITextFieldDelegate {

	public var showSubmitButton = true

	public var fields: [DDLField] = [] {
		didSet {
			onChangedFields()
		}
	}

	public var values: [String:AnyObject] {
		var result:[String:AnyObject] = [:]

		forEachField() {
			if let value = $0.currentStringValue {
				//FIXME - LPS-49460
				// Server rejects the request if the value is empty string.
				// This way we workaround the problem but a field can't be
				// emptied when you're editing an existing row.
				if value != "" {
					result[$0.name] = value
				}
			}
		}

		return result
	}


	//MARK: Public methods

	public func validateForm(#autoscroll:Bool) -> Bool {
		var result = true
		var firstFailedField:DDLField?

		forEachField() {
			if !$0.validate() {
				if firstFailedField == nil {
					firstFailedField = $0
				}
				result = false
			}
		}

		if autoscroll && firstFailedField != nil {
			showField(firstFailedField!)
		}

		return result
	}


	//MARK: Internal methods

	internal func changeDocumentUploadStatus(field:DDLFieldDocument) {
	}

	internal func forEachField(body:DDLField -> Void) {
		for field in fields {
			body(field)
		}
	}

	internal func showField(field:DDLField) {
	}

	internal func onChangedFields() {
	}

}
