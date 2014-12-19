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
	@IBOutlet var portraitBorder: UIView?
	@IBOutlet var portraitImage: UIImageView?


	override func onStartOperation() {
		activityIndicator?.startAnimating()
	}

	public func loadPlaceholder() {
		self.portraitImage?.image = UIImage(named: "default-portrait-placeholder")
	}


	public func loadPortrait(portraitURL: NSURL) {
		let request = NSURLRequest(URL: portraitURL)

		activityIndicator?.startAnimating()

		portraitImage?.setImageWithURLRequest(request, placeholderImage: nil, success: {
			(request: NSURLRequest!, response: NSHTTPURLResponse!, image: UIImage!) -> Void in
				self.portraitImage?.image = image
				self.activityIndicator?.stopAnimating()

			},
			failure: {
				(request: NSURLRequest!, response: NSHTTPURLResponse!, error: NSError!) -> Void in
					self.loadPlaceholder()
					self.activityIndicator?.stopAnimating()
			})
	}

	override func onShow() {
		activityIndicator?.hidesWhenStopped = true
		activityIndicator?.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.Gray

		let borderRadius = portraitBorder!.frame.width / 2
		let imageRadius = portraitImage!.frame.width / 2

		portraitBorder!.layer.cornerRadius = borderRadius
		portraitImage!.layer.cornerRadius = imageRadius
	}
}
