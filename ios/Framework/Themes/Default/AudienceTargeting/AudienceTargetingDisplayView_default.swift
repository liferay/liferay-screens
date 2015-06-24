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


public class AudienceTargetingDisplayView_default: BaseScreenletView, AudienceTargetingDisplayViewModel {

	@IBOutlet weak var imageView: UIImageView?
	@IBOutlet weak var activityView: UIActivityIndicatorView!

	override public func onCreated() {
		BaseScreenlet.setHUDCustomColor(DefaultThemeBasicBlue)
	}

	override public func onStartOperation() {
		activityView?.startAnimatingConcurrent()
	}

	override public func onFinishOperation() {
		activityView?.stopAnimatingConcurrent()
	}


	//MARK: AudienceTargetingDisplayViewModel

	public func setContent(content: AnyObject, mimeType: String?) {
		if let mimeType = mimeType {
			if mimeType.hasPrefix("image/") {
				if let image = content as? UIImage {
					imageView?.image = image
					imageView?.hidden = false
				}
				else if content is String && (content as! String).hasPrefix("http") {
					loadImage(content as! String)
				}
			}
			else {
				println("Unknown MimeType: \(mimeType)")
			}
		}
		else {
			println("Can't render Audience Targeting content without MimeType: \(content)")
		}
	}

	func loadImage(url: String) {
		let request = NSURLRequest(
				URL: NSURL(string: url)!,
				cachePolicy: .ReloadIgnoringLocalCacheData,
				timeoutInterval: 30.0)

		imageView?.setImageWithURLRequest(request,
				placeholderImage: nil,
				success: { (request, response, image) -> Void in
					self.imageView?.image = image
					self.imageView?.hidden = false
				},
				failure: { (request, response, error) -> Void in
					println("Can't load image from URL \(url)")
				})
	}



}
