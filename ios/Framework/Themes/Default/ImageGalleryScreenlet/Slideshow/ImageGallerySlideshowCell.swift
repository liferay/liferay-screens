//
//  ImageGallerySlideshowCell.swift
//  LiferayScreens
//
//  Created by liferay on 01/08/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import UIKit

public class ImageGallerySlideshowCell: UICollectionViewCell {

	@IBOutlet private weak var slideshowImage: UIImageView!

	private var placeholderImage: UIImage?

	public var imageUrl: String  {
		get {
			return ""
		}
		set {
			slideshowImage.lr_setImageWithURL(NSURL(string: newValue)!,
					placeholderImage: placeholderImage,
					optionsInfo: [.BackgroundDecode])
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

	public override func awakeFromNib() {
		super.awakeFromNib()
		slideshowImage.kf_showIndicatorWhenLoading = true
		
		placeholderImage = NSBundle.imageInBundles(
			name: "default-placeholder-image",
			currentClass: self.dynamicType)
	}

	public override func prepareForReuse() {
		super.prepareForReuse()
		slideshowImage.image = placeholderImage
	}
}
