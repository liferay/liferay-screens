//
//  ImageGallerySlideshowCell.swift
//  LiferayScreens
//
//  Created by liferay on 01/08/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import UIKit

public class ImageGallerySlideshowCell: UICollectionViewCell {

	@IBOutlet private var slideshowImage: UIImageView!

	var outputStream: NSOutputStream?

	public var imageUrl: String  {
		get {
			return ""
		}
		set {
			slideshowImage.setImageWithURL(NSURL(string: newValue)!)
		}
	}

	public var image: UIImage {
		get {
			return UIImage()
		}
		set {
			slideshowImage.image = newValue
		}
	}

	public override func prepareForReuse() {
		slideshowImage.image = nil
		slideshowImage.cancelImageRequestOperation()
	}
}
