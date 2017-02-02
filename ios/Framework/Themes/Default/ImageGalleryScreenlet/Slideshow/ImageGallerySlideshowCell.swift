//
//  ImageGallerySlideshowCell.swift
//  LiferayScreens
//
//  Created by liferay on 01/08/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import UIKit

open class ImageGallerySlideshowCell: UICollectionViewCell {

	@IBOutlet fileprivate weak var slideshowImage: UIImageView?

	fileprivate var placeholderImage: UIImage?

	open var imageUrl: String  {
		get {
			return ""
		}
		set {
			slideshowImage?.lr_setImageWithURL(URL(string: newValue)!,
					placeholderImage: placeholderImage,
					optionsInfo: [.backgroundDecode])
		}
	}

	open var image: UIImage {
		get {
			return UIImage()
		}
		set {
			slideshowImage?.image = newValue
		}
	}

	open override func awakeFromNib() {
		super.awakeFromNib()
		slideshowImage?.kf.indicatorType = .activity
		
		placeholderImage = Bundle.imageInBundles(
			name: "default-placeholder-image",
			currentClass: type(of: self))
	}

	open override func prepareForReuse() {
		super.prepareForReuse()
		slideshowImage?.image = placeholderImage
	}
}
