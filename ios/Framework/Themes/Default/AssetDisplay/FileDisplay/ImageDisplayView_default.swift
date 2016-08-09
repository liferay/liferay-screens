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


public class ImageDisplayView_default: BaseScreenletView, BaseFileDisplayViewModel {

	@IBOutlet weak var imageView: UIImageView?

	public var url: NSURL? {
		didSet {
			if let url = url {
				let imageData = NSData(contentsOfURL: url)
				if let imageData = imageData {
					imageView?.image = UIImage(data: imageData)
				}
			}
		}
	}

	public var title: String? {
		didSet{
		}
	}
}
