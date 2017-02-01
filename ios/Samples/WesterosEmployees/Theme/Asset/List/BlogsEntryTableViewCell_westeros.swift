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

open class BlogsEntryTableViewCell_westeros: UITableViewCell {

	@IBOutlet open weak var imageDisplayScreenlet: ImageDisplayScreenlet? {
		didSet {
			imageDisplayScreenlet?.imageMode = .scaleAspectFill
		}
	}
	@IBOutlet open weak var titleLabel: UILabel?
	@IBOutlet open weak var subtitleLabel: UILabel?

	open var title: String? {
		get {
			return titleLabel?.text
		}
		set {
			titleLabel?.text = newValue
		}
	}

	open var subtitle: String? {
		get {
			return subtitleLabel?.text
		}
		set {
			subtitleLabel?.text = newValue
		}
	}

	open var imageEntryId: Int64 {
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
