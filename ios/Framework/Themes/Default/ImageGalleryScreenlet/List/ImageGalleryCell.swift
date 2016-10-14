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

public class ImageGalleryCell: UITableViewCell {

	@IBOutlet private weak var imagePreview: UIImageView!
	@IBOutlet private weak var imageTitle: UILabel!

	private var placeholderImage: UIImage?
	
    public var title: String? {
        get {
            return imageTitle.text
        }
        set {
            imageTitle.text = newValue
        }
    }
    
    public var imageUrl: String? {
        get {
            return ""
        }
        set {
            imagePreview.lr_setImageWithURL(NSURL(string: newValue ?? "")!)
        }
    }

	public var img: UIImage? {
		get {
			return imagePreview.image
		}
		set {
			imagePreview.image = newValue
		}
	}

    override public func awakeFromNib() {
        super.awakeFromNib()
        
		imagePreview.kf_showIndicatorWhenLoading = true

		placeholderImage = NSBundle.imageInBundles(
			name: "default-placeholder-image",
			currentClass: self.dynamicType)

    }

	public override func prepareForReuse() {
		super.prepareForReuse()
		
		imagePreview.image = placeholderImage
	}
}
