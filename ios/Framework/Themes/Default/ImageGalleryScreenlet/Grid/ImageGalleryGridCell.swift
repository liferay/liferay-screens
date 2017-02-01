//
//  ImageGalleryGridCell.swift
//  LiferayScreens
//
//  Created by liferay on 20/07/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import UIKit

open class ImageGalleryGridCell: UICollectionViewCell {

	@IBOutlet fileprivate weak var previewImage: UIImageView?

	fileprivate var placeholderImage: UIImage?

	open var imageUrl: String  {
		get {
			return ""
		}
		set {
			previewImage?.lr_setImageWithURL(
					URL(string: newValue)!,
					placeholderImage:  placeholderImage,
					optionsInfo: [.backgroundDecode])
		}
	}

	open var image: UIImage {
		get {
			return UIImage()
		}
		set {
			previewImage?.image = newValue
		}
	}

	open override func awakeFromNib() {
		super.awakeFromNib()
		
		previewImage?.clipsToBounds = true
		previewImage?.kf.indicatorType = .activity

		placeholderImage = Bundle.imageInBundles(
			name: "default-placeholder-image",
			currentClass: type(of: self))
	}

	open override func prepareForReuse() {
		super.prepareForReuse()

		previewImage?.image = placeholderImage
	}
}
