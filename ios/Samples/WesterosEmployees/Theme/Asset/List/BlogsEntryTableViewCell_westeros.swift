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
import LiferayScreens

public class BlogsEntryTableViewCell_westeros: UITableViewCell {

	@IBOutlet public weak var imageDisplayScreenlet: ImageDisplayScreenlet? {
		didSet {
			imageDisplayScreenlet?.imageMode = .ScaleAspectFill
		}
	}
	@IBOutlet public weak var titleLabel: UILabel?
	@IBOutlet public weak var subtitleLabel: UILabel?

	public var title: String? {
		get {
			return titleLabel?.text
		}
		set {
			titleLabel?.text = newValue
		}
	}

	public var subtitle: String? {
		get {
			return subtitleLabel?.text
		}
		set {
			subtitleLabel?.text = newValue
		}
	}

	public var imageEntryId: Int64 {
		get { return 0 }
		set {
			if newValue != 0 {
				imageDisplayScreenlet?.className = AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!
				imageDisplayScreenlet?.classPK = newValue
				imageDisplayScreenlet?.load()
			}
		}
	}
}
