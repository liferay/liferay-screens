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

#if LIFERAY_SCREENS_FRAMEWORK
	import AFNetworking
#endif


public class UserPortraitView_default: BaseScreenletView, UserPortraitViewModel {

	@IBOutlet public var activityIndicator: UIActivityIndicatorView?
	@IBOutlet public var portraitImage: UIImageView?

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
			else {
				loadPlaceholder()
			}
		}
	}

	public var portraitLoaded: ((UIImage?, NSError?) -> (UIImage?))?


	private(set) var loadedURL: NSURL?


	//MARK: BaseScreenletView

	override public func onStartOperation() {
		objc_sync_enter(self)

		// use tag to track the start count
		if activityIndicator?.tag == 0 {
			activityIndicator?.startAnimating()
		}

		activityIndicator?.tag++

		objc_sync_exit(self)
	}

	override public func onFinishOperation() {
		if activityIndicator?.tag > 0 {
			objc_sync_enter(self)

			activityIndicator?.tag--

			if activityIndicator?.tag == 0 {
				activityIndicator?.stopAnimating()
			}

			objc_sync_exit(self)
		}
	}

	override public func onShow() {
		portraitImage?.layer.borderWidth = borderWidth
		portraitImage?.layer.borderColor = (borderColor ?? DefaultThemeBasicBlue).CGColor
		portraitImage?.layer.cornerRadius = DefaultThemeButtonCornerRadius
	}

	public func loadPlaceholder() {
		self.portraitImage?.image = imageInAnyBundle(
				name: "default-portrait-placeholder",
				currentClass: self.dynamicType,
				currentTheme: "default")
	}


	//MARK: Internal methods

	internal func loadPortrait(URL url: NSURL) {
		// ignore AFNetworking's cache by now
		// TODO contribute to UIImageView+AFNetworking to support "If-Modified-Since" header
		let request = NSURLRequest(
				URL: url,
				cachePolicy: .ReloadIgnoringLocalCacheData,
				timeoutInterval: 60.0)

		onStartOperation()

		portraitImage?.setImageWithURLRequest(request, placeholderImage: nil, success: {
			(request: NSURLRequest!, response: NSHTTPURLResponse!, image: UIImage!) -> Void in
				self.loadedURL = url

				if self.portraitLoaded == nil {
					self.portraitImage?.image = image
				}
				else {
					if let finalImageValue = self.portraitLoaded!(image, nil) {
						self.portraitImage?.image = finalImageValue
					}
					else {
						self.portraitImage?.image = image
					}
				}

				self.onFinishOperation()

			},
			failure: {
				(request: NSURLRequest!, response: NSHTTPURLResponse!, error: NSError!) -> Void in
					self.loadPlaceholder()
					self.loadedURL = nil
					self.portraitLoaded?(nil, error)
					self.onFinishOperation()
			})
	}

}
