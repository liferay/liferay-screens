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


open class FileDisplayView_default: BaseScreenletView, FileDisplayViewModel {


	//MARK: Outlets

	@IBOutlet weak var webView: UIWebView?


	//MARK: FileDisplayViewModel

	open var url: URL? {
		didSet {
			if let url = url {
				webView?.loadRequest(URLRequest(url: url))
			}
		}
	}

	open var title: String? {
		didSet {
			self.presentingViewController?.title = title
		}
	}
}
