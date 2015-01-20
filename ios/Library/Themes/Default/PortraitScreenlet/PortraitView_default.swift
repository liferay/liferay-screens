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


public class PortraitView_default: BaseScreenletView, PortraitData {

	@IBOutlet var activityIndicator: UIActivityIndicatorView?
	@IBOutlet var portraitImage: UIImageView?

	public var borderWidth: CGFloat = 1.0 {
		didSet {
			portraitImage?.layer.borderWidth = borderWidth
		}
	}
	public var borderColor: UIColor? {
		didSet {
			portraitImage?.layer.borderColor = (borderColor ?? DefaultThemeBasicBlue).CGColor
		}
	}

	public var portraitURL: NSURL? {
		get {
			return loadedURL
		}
		set {
			if let urlValue = newValue {
				loadPortrait(URL: urlValue)
			}
		}
	}

	public var portraitLoaded: ((NSError?) -> (Void))?


	private(set) var loadedURL: NSURL?


	//MARK: BaseScreenletView

	override func onStartOperation() {
		activityIndicator?.startAnimating()
	}

	override func onFinishOperation() {
		activityIndicator?.stopAnimating()
	}

	override func onShow() {
		portraitImage?.layer.borderWidth = borderWidth
		portraitImage?.layer.borderColor = (borderColor ?? DefaultThemeBasicBlue).CGColor
		portraitImage?.layer.cornerRadius = DefaultThemeButtonCornerRadius
	}


	//MARK: Internal methods

	internal func loadPortrait(URL url: NSURL) {
		let request = NSURLRequest(URL: url)

		activityIndicator?.startAnimating()

		portraitImage?.setImageWithURLRequest(request, placeholderImage: nil, success: {
			(request: NSURLRequest!, response: NSHTTPURLResponse!, image: UIImage!) -> Void in
				self.portraitImage?.image = image
				self.loadedURL = url
				self.portraitLoaded?(nil)
				self.onFinishOperation()

			},
			failure: {
				(request: NSURLRequest!, response: NSHTTPURLResponse!, error: NSError!) -> Void in
					self.portraitImage?.image = UIImage(named: "default-portrait-placeholder")
					self.loadedURL = nil
					self.portraitLoaded?(error)
					self.onFinishOperation()
			})
	}

}
