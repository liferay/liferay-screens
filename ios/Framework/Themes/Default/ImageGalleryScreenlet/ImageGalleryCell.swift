//
//  ImageGalleryCell.swift
//  Pods
//
//  Created by liferay on 06/07/16.
//
//

import UIKit
import AFNetworking

class ImageGalleryCell: UITableViewCell {
    
    @IBOutlet weak private var imagePreview: UIImageView!
    
    @IBOutlet weak private var imageTitle: UILabel!
    
    public var title: String? {
        get {
            return title
        }
        set {
            imageTitle.text = newValue
        }
    }
    
    public var imageUrl: String? {
        get {
            return title
        }
        set {
            imagePreview.setImageWithURL(NSURL(string: newValue!)!)
        }
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        
        imagePreview.backgroundColor = .redColor()
        imagePreview.clipsToBounds = true
    }
    
}
