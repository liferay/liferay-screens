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


public class WebContentDisplayView_default: BaseScreenletView, WebContentDisplayViewModel {

	@IBOutlet public var webView: UIWebView?

	override public var progressMessages: [String:ProgressMessages] {
		return [
			BaseScreenlet.DefaultAction :
				[.Working : LocalizedString("default", key: "webcontentdisplay-loading-message", obj: self),
				.Failure : LocalizedString("default", key: "webcontentdisplay-loading-error", obj: self)]]
	}


	private let styles =
		".MobileCSS {padding: 4%; width: 92%;} " +
		".MobileCSS, .MobileCSS span, .MobileCSS p, .MobileCSS h1, .MobileCSS h2, .MobileCSS h3 { " +
			"font-size: 110%; font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif; font-weight: 200; } " +
		".MobileCSS img { width: 100% !important; } " +
		".span2, .span3, .span4, .span6, .span8, .span10 { width: 100%; }"


	override public func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}

	//MARK: WebContentDisplayViewModel

	public var htmlContent: String? {
		get {
			return ""
		}
		set {
			let styledHtml = "<style>\(styles)</style><div class=\"MobileCSS\">\(newValue ?? "")</div>"

			webView!.loadHTMLString(styledHtml, baseURL: NSURL(string:LiferayServerContext.server))
		}
	}

	public var recordContent: DDLRecord?

}
