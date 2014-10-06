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


@objc public protocol WebContentDisplayWidgetDelegate {

	optional func onWebContentResponse(html:String)
	optional func onWebContentError(error: NSError)

}


@IBDesignable public class WebContentDisplayWidget: BaseWidget {

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var articleId = ""

	@IBOutlet public var delegate: WebContentDisplayWidgetDelegate?

	internal var webContentDisplayData: WebContentDisplayData {
		return widgetView as WebContentDisplayData
	}

	internal var webContentOperation: LiferayWebContentLoadOperation {
		return serverOperation as LiferayWebContentLoadOperation
	}


	//MARK: BaseWidget

	override func onCreated() {
		super.onCreated()

		serverOperation = LiferayWebContentLoadOperation(widget: self)
	}


	//MARK: Public methods

	public func loadWebContent() -> Bool {
		webContentOperation.groupId = (self.groupId != 0) ? self.groupId : LiferayServerContext.groupId
		webContentOperation.articleId = self.articleId

		return webContentOperation.validateAndEnqueue() {
			if let error = $0.lastError {
				self.delegate?.onWebContentError?(error)
			}
			else {
				self.delegate?.onWebContentResponse?(self.webContentOperation.loadedHTML!)

				self.webContentDisplayData.htmlContent = self.webContentOperation.loadedHTML!
			}
		}
	}

}
