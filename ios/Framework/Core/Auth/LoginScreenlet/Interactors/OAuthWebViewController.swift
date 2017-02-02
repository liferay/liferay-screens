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
import LROAuth

class OAuthWebViewController: UIViewController, UIWebViewDelegate {

	@IBOutlet weak var webView: UIWebView?
	@IBOutlet weak var activityIndicator: UIActivityIndicatorView?
	@IBOutlet weak var closeButton: UIButton?

	var onAuthorized: (String -> Void)?

	private let URL: NSURL

	init(URL: NSURL, themeName: String) {
		var nibName = "OAuthWebViewController_\(themeName)"
		var bundle = NSBundle.bundleForNibName(nibName, currentClass: self.dynamicType)
		if bundle == nil {
			nibName = "OAuthWebViewController_default"
			bundle = NSBundle.bundleForNibName(nibName, currentClass: self.dynamicType)
		}

		self.URL = URL

		super.init(
				nibName: nibName,
				bundle: bundle)
	}

	required init?(coder aDecoder: NSCoder) {
		URL = NSURL()

		super.init(coder: aDecoder)
	}

	override func viewWillAppear(animated: Bool) {
		activityIndicator?.startAnimating()
		webView?.delegate = self
		webView?.loadRequest(NSURLRequest(URL: URL))
		closeButton?.titleLabel?.text = NSLocalizedString("default-oauth-close", comment: "Close")
	}

	@IBAction func closeAction(sender: AnyObject) {
		activityIndicator?.stopAnimating()

		self.dismissViewControllerAnimated(true, completion: nil)
	}

	func webView(webView: UIWebView,
			shouldStartLoadWithRequest request: NSURLRequest,
			navigationType: UIWebViewNavigationType) -> Bool {

		let URL = request.URL?.absoluteString

		if request.URL?.scheme == "screens"
				&& URL?.rangeOfString("oauth_verifier=") != nil {

			let params = LROAuth.extractRequestParams(URL!) as [NSObject:AnyObject]
			onAuthorized?(params["oauth_verifier"] as! String)
			return false
		}

		return true
	}

	func webViewDidFinishLoad(webView: UIWebView) {
		activityIndicator?.stopAnimating()
	}


}
