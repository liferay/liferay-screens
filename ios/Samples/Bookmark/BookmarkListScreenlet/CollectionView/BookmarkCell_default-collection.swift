//
//  BookmarkCell_default-collection.swift
//  BookmarksTestApp
//
//  Created by Victor Galán on 28/09/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

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
