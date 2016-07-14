//
//  ImageGalleryCell.swift
//  Pods
//
//  Created by liferay on 06/07/16.
//
//

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
}
