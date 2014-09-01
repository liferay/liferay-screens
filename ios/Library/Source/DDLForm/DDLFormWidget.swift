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

@objc protocol DDLFormWidgetDelegate {

	optional func onFormLoaded(elements: [DDLElement])
	optional func onFormLoadError(error: NSError)

}

@IBDesignable public class DDLFormWidget: BaseWidget {

	@IBInspectable var structureId: Int?

	@IBOutlet var delegate: DDLFormWidgetDelegate?


    // MARK: BaseWidget METHODS

	override public func onCreate() {
	}

	override public func onCustomAction(actionName: String?, sender: UIControl) {
	}

	override public func onServerError(error: NSError) {
		delegate?.onFormLoadError?(error)
		finishOperationWithMessage("An error happened loading form", details: nil)
	}

	override public func onServerResult(result: [String:AnyObject]) {
		if let xml = result["xsd"]! as? String {
			let parser = DDLParser(locale:NSLocale.currentLocale())

			parser.xml = xml

			if let elements = parser.parse() {
				formView().rows = elements

				delegate?.onFormLoaded?(elements)

				finishOperationWithMessage("Form loaded", details: nil)
			}
			else {
				//TODO error
			}
		}
		else {
			//TODO error
		}
    }

	public func loadForm() {
		startOperationWithMessage("Loading form...", details: "Wait a second...")

	}


	private func formView() -> DDLFormView {
		return widgetView as DDLFormView
	}

}
