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
import WebKit


public class WebContentView_default: WebContentView {

	private var webView:WKWebView?


	//MARK: WebContentView

	override public func setHtmlContent(html:String) {
		webView!.loadHTMLString(html, baseURL: NSURL(string:LiferayServerContext.server))
	}

	override internal func onPreCreate() {
		webView = WKWebView(frame: bounds, configuration: WKWebViewConfiguration())

		webView!.autoresizingMask =
				UIViewAutoresizing.FlexibleWidth | UIViewAutoresizing.FlexibleHeight

		addSubview(webView!)
	}

}
