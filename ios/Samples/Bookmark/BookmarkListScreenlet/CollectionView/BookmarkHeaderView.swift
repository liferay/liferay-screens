//
//  BookmarkHeaderView.swift
//  BookmarksTestApp
//
//  Created by Victor Galán on 28/09/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import UIKit

public class BookmarkHeaderView: UICollectionReusableView {

	@IBOutlet private weak var sectionLabel: UILabel!

	public var sectionTitle: String {
		set {
			sectionLabel.text = newValue
		}
		get {
			return sectionLabel.text ?? ""
		}
	}

    public override func awakeFromNib() {
        super.awakeFromNib()
        
    }
    
}
