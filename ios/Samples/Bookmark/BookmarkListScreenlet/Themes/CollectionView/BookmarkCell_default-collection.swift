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

public class BookmarkCell_default_collection: UICollectionViewCell {

	@IBOutlet weak private var centerLabel: UILabel!

	@IBOutlet weak private var urlLabel: UILabel!


	public var startLetter: String {
		get {
			return centerLabel.text ?? ""
		}
		set {
			centerLabel.text = newValue.capitalizedString
		}
	}

	public var url: String? {
		get {
			return urlLabel.text
		}
		set {
			urlLabel.text = newValue
		}
	}

    override public func awakeFromNib() {
        super.awakeFromNib()

		backgroundColor = UIColor(red: 0.0, green: 184.0/255.0, blue: 224.0/255.0, alpha: 0.87)
		centerLabel.textColor = .whiteColor()
		urlLabel.textColor = .whiteColor()
    }

	override public func prepareForReuse() {
		super.prepareForReuse()

		centerLabel.text = ""
		urlLabel.text = ""
	}
}
