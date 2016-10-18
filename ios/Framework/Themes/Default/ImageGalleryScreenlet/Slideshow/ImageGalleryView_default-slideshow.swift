
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

public class ImageGalleryView_default_slideshow: ImageGalleryCollectionViewBase {


	// MARK: BaseListCollectionView

	public override func doConfigureCollectionView(collectionView: UICollectionView) {
		collectionView.backgroundColor = .blackColor()
	}

	public override func doRegisterCellNibs() {
		if let imageGalleryGridCellNib = NSBundle.nibInBundles(
			name: "ImageGallerySlideshowCell",
			currentClass: self.dynamicType) {

			collectionView?.registerNib(imageGalleryGridCellNib, forCellWithReuseIdentifier: imageCellId)
		}
	}

	public override func doCreateLayout() -> UICollectionViewLayout {

		return SlideShowLayout()
	}

	override public func doFillLoadedCell(
			indexPath indexPath: NSIndexPath,
			cell: UICollectionViewCell, object:AnyObject) {

		guard let imageCell = cell as? ImageGallerySlideshowCell, entry = object as? ImageEntry else {
			return
		}
		if let image = entry.image {
			imageCell.image = image
		}
		else {
			imageCell.imageUrl = entry.imageUrl
		}
	}

	public override func doFillInProgressCell(
			indexPath indexPath: NSIndexPath,
			cell: UICollectionViewCell) {

		cell.backgroundColor = .grayColor()
	}

	public override func doGetCellId(indexPath indexPath: NSIndexPath, object: AnyObject?) -> String {
		if let _ = object {
			return imageCellId
		}

		return super.doGetCellId(indexPath: indexPath, object: object)
	}

	public func collectionView(collectionView: UICollectionView,
	                           layout collectionViewLayout: UICollectionViewLayout,
	                                  insetForSectionAtIndex section: Int) -> UIEdgeInsets {

		if let layout = collectionViewLayout as? UICollectionViewFlowLayout {
			let margin = collectionView.bounds.width / 2 - layout.itemSize.width / 2

			return UIEdgeInsets(top: 10, left: margin, bottom: 10, right: margin)
		}

		return UIEdgeInsetsZero
	}

}
