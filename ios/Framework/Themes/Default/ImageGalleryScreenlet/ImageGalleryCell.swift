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
import AFNetworking

public class ImageGalleryCell: UITableViewCell {

    
	@IBOutlet weak var imagePreview: UIImageView!
	@IBOutlet weak var imageTitle: UILabel!

	
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
            imagePreview.setImageWithURL(NSURL(string: newValue ?? "")!)
        }
    }

    override public func awakeFromNib() {
        super.awakeFromNib()
        
        imagePreview.clipsToBounds = true
        
        if let image = NSBundle.imageInBundles(
            name: "default-hourglass",
            currentClass: self.dynamicType) {
            
            imagePreview.image = image
        }
    }

	public override func prepareForReuse() {
		imagePreview.image = nil
	}
}
