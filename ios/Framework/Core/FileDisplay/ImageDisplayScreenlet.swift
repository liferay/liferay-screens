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


public class ImageDisplayScreenlet: FileDisplayScreenlet {

	@IBInspectable public var placeholder: UIImage?  {
		didSet {
			imageDisplayViewModel?.placeholder = placeholder
		}
	}

	public var imageDisplayViewModel: ImageDisplayViewModel? {
		return screenletView as? ImageDisplayViewModel
	}

	public var imageMode: UIViewContentMode = .ScaleAspectFit {
		didSet {
			imageDisplayViewModel?.imageMode = imageMode
		}
	}

	public var placeholderImageMode: UIViewContentMode = .Center {
		didSet {
			imageDisplayViewModel?.placeholderImageMode = placeholderImageMode
		}
	}


	//MARK: FileDisplayScreenlet

	override public class var supportedMimeTypes: [String] {
		return ["image/png", "image/jpg", "image/jpeg", "image/gif"]
	}


	//MARK: BaseScreenlet

	public override func onCreated() {
		super.onCreated()
		
		imageDisplayViewModel?.imageMode = imageMode
		imageDisplayViewModel?.placeholder = placeholder
		imageDisplayViewModel?.placeholderImageMode = placeholderImageMode
	}

}
