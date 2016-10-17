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


public class ImageDisplayView_default: BaseScreenletView, ImageDisplayViewModel {

	@IBOutlet public weak var imageView: UIImageView?

	public var imageMode: UIViewContentMode = .ScaleAspectFit

	public var placeholderImageMode: UIViewContentMode = .Center
	public var placeholder: UIImage?

	public var url: NSURL? {
		didSet {
			if let url = url, imageData = NSData(contentsOfURL: url) {
				imageView?.contentMode = self.imageMode
				imageView?.image = UIImage(data: imageData)
			}
		}
	}

	public var title: String? {
		didSet {
			self.presentingViewController?.title = title
		}
	}

	public override func onStartInteraction() {
		imageView?.contentMode = placeholderImageMode
		imageView?.image = placeholder
	}
}
