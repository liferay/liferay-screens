//
//  ImageGalleryGridCell.swift
//  LiferayScreens
//
//  Created by liferay on 20/07/16.
//  Copyright © 2016 Liferay. All rights reserved.
//

import UIKit

public class ImageGalleryGridCell: UICollectionViewCell {

	@IBOutlet private weak var previewImage: UIImageView!

	private var placeholderImage: UIImage?

	public var imageUrl: String  {
		get {
			return ""
		}
		set {
			previewImage.lr_setImageWithURL(
					NSURL(string: newValue)!,
					placeholderImage:  placeholderImage,
					optionsInfo: [.BackgroundDecode])
		}
	}

	public var image: UIImage {
		get {
			return UIImage()
		}
		set {
			previewImage.image = newValue
		}
	}

	public override func awakeFromNib() {
		super.awakeFromNib()
		
		previewImage.clipsToBounds = true
		previewImage.kf_showIndicatorWhenLoading = true

		placeholderImage = NSBundle.imageInBundles(
			name: "default-placeholder-image",
			currentClass: self.dynamicType)
	}

	public override func prepareForReuse() {
		super.prepareForReuse()

		previewImage.image = placeholderImage
	}
}
