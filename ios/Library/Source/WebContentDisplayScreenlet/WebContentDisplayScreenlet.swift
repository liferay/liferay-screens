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


@objc public protocol WebContentDisplayScreenletDelegate {

	optional func onWebContentResponse(html:String) -> String?
	optional func onWebContentError(error: NSError)

}


@IBDesignable public class WebContentDisplayScreenlet: BaseScreenlet {

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var articleId: String = ""

	@IBInspectable public var autoload: Bool = true

	@IBOutlet public var delegate: WebContentDisplayScreenletDelegate?

	internal var webContentDisplayData: WebContentDisplayData {
		return screenletView as WebContentDisplayData
	}


	//MARK: Public methods

	override func onShow() {
		if autoload && articleId != "" {
			loadWebContent()
		}
	}

	public func loadWebContent() -> Bool {
		let webContentOperation = LiferayWebContentLoadOperation(screenlet: self)

		webContentOperation.groupId =
				(self.groupId != 0) ?
						self.groupId : LiferayServerContext.groupId

		webContentOperation.articleId = self.articleId

		return webContentOperation.validateAndEnqueue() {
			if let error = $0.lastError {
				self.delegate?.onWebContentError?(error)
			}
			else {
				let modifiedHtml =
						self.delegate?.onWebContentResponse?(webContentOperation.resultHTML!)

				self.webContentDisplayData.htmlContent =
						(modifiedHtml != nil) ?
							modifiedHtml! : webContentOperation.resultHTML!
			}
		}
	}

}
