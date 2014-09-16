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

	public var rows: [DDLElement] = [] {
		didSet {
			onChangedRows()
		}
	}

	public var values: [String:AnyObject] {
		get {
			var result:[String:AnyObject] = [:]

			for element in rows {
				if let value = element.currentStringValue {
					// FIXME - LPS-49460
					// Server rejects the request if the value is empty string.
					// This way we workaround the problem but a field can't be
					// emptied when you're editing an existing row.
					if value != "" {
						result[element.name] = value
					}
				}
			}

			return result
		}
	}

	public var showSubmitButton: Bool = true


	public func validateForm(#autoscroll:Bool) -> Bool {
		var result = true
		var firstFailedElement:DDLElement?

		for element in rows {
			if !element.validate() {
				if firstFailedElement == nil {
					firstFailedElement = element
				}
				result = false
			}
		}

		if autoscroll && firstFailedElement != nil {
			showElement(firstFailedElement!)
		}

		return result
	}

	internal func changeDocumentUploadStatus(element:DDLElementDocument) {
	}

	internal func showElement(element:DDLElement) {
	}

	internal func onChangedRows() {
	}

}