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


@objc public protocol WebContentWidgetDelegate {

	optional func onWebContentResponse(html:String)
	optional func onWebContentError(error: NSError)

}


@IBDesignable public class WebContentWidget: BaseWidget {

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var articleId = ""

	@IBOutlet public var delegate: WebContentWidgetDelegate?

	internal var webContentView: WebContentView {
		return widgetView as WebContentView
	}


	//MARK: BaseWidget

	override internal func onServerError(error: NSError) {
		delegate?.onWebContentError?(error)

		finishOperationWithError(error, message:"Error requesting password!")
	}

	override internal func onServerResult(result: [String:AnyObject]) {
		if let responseValue:AnyObject = result["result"] {
			let htmlContent = responseValue as String

			delegate?.onWebContentResponse?(htmlContent)

			webContentView.setHtmlContent(htmlContent)

			finishOperation()
		}
		else {
			finishOperationWithMessage("An error happened", details: "Can't load the content")
		}
	}


	//MARK: Public methods

	public func loadWebContent() -> Bool {
		if !SessionContext.hasSession {
			println("ERROR: No session initialized. Can't load the web content without session")

			return false
		}

		if articleId == "" {
			println("ERROR: ArticleId is empty. Can't load the web content without it.")

			return false
		}

		startOperationWithMessage("Loading content...", details:"Wait few seconds...")

		let session = SessionContext.createSessionFromCurrentSession()!
		session.callback = self

		let groupId = (self.groupId != 0) ? self.groupId : LiferayServerContext.groupId

		let service = LRJournalArticleService_v62(session: session)

		var outError: NSError?

		service.getArticleContentWithGroupId(groupId,
				articleId: articleId, languageId: NSLocale.currentLocaleString(),
				themeDisplay: nil, error: &outError)

		if let error = outError {
			onFailure(error)
			return false
		}

		return true
	}

}
