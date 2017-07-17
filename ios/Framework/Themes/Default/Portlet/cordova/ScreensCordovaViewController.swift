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
import Cordova
public class ScreensCordovaViewController: CDVViewController, UIWebViewDelegate {

	var cdvDelegate: CDVUIWebViewNavigationDelegate?
	weak var delegate: UIWebViewDelegate?

	public init(webViewDelegate: UIWebViewDelegate) {
		self.delegate = webViewDelegate
		super.init(nibName: nil, bundle: nil)
	}

	required public init?(coder aDecoder: NSCoder) {
		fatalError("you have to use the init(jsCallsHandler: _) initializer")
	}

	public override func viewDidLoad() {
		super.viewDidLoad()
		cdvDelegate = CDVUIWebViewNavigationDelegate(enginePlugin: self.webViewEngine as! CDVPlugin)
	}

	public func inject(script: InjectableScript) {
		self.webViewEngine.evaluateJavaScript(script.content, completionHandler: nil)
	}

	public func webView(_ webView: UIWebView,
	                    shouldStartLoadWith request: URLRequest, navigationType: UIWebViewNavigationType) -> Bool {

		if delegate?.webView?(webView, shouldStartLoadWith: request, navigationType: navigationType) ?? false {
			return true
		}

		return cdvDelegate?.webView(webView, shouldStartLoadWith: request, navigationType: navigationType) ?? false

	}

	public func webViewDidStartLoad(_ webView: UIWebView) {
		delegate?.webViewDidStartLoad?(webView)
		cdvDelegate?.webViewDidStartLoad(webView)
	}

	public func webViewDidFinishLoad(_ webView: UIWebView) {
		delegate?.webViewDidFinishLoad?(webView)
		cdvDelegate?.webViewDidFinishLoad(webView)
	}

	public func webView(_ webView: UIWebView, didFailLoadWithError error: Error) {
		delegate?.webView?(webView, didFailLoadWithError: error)
		cdvDelegate?.webView(webView, didFailLoadWithError: error)
	}
}
