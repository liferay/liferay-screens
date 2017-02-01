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


open class WebContentDisplayView_default: BaseScreenletView, WebContentDisplayViewModel {


	//MARK: Outlets

	@IBOutlet open var webView: UIWebView?

	override open var progressMessages: [String:ProgressMessages] {
		return [
			BaseScreenlet.DefaultAction :
				[.working : LocalizedString("default", key: "webcontentdisplay-loading-message", obj: self),
				.failure : LocalizedString("default", key: "webcontentdisplay-loading-error", obj: self)]]
	}

	fileprivate let styles =
		".MobileCSS {padding: 4%; width: 92%;} " +
		".MobileCSS, .MobileCSS span, .MobileCSS p, .MobileCSS h1, .MobileCSS h2, .MobileCSS h3 { " +
			"font-size: 110%; font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif; font-weight: 200; } " +
		".MobileCSS img { width: 100% !important; } " +
		".span2, .span3, .span4, .span6, .span8, .span10 { width: 100%; }"


	override open func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}

	//MARK: WebContentDisplayViewModel

	open var htmlContent: String? {
		get {
			return ""
		}
		set {
			let styledHtml = "<style>\(styles)</style><div class=\"MobileCSS\">\(newValue ?? "")</div>"

			webView!.loadHTMLString(styledHtml, baseURL: URL(string:LiferayServerContext.server))
		}
	}

	open var recordContent: DDLRecord?

}
