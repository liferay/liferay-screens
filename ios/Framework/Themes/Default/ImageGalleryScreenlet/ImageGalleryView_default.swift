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

public class ImageGalleryView_default: BaseListCollectionView {

	private let imageCellId = "ImageCellId"

	var spacing: CGFloat = 1.0

	public override func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}

	public override func doRegisterCellNibs() {
		if let imageGalleryGridCellNib = NSBundle.nibInBundles(
			name: "ImageGalleryGridCell",
			currentClass: self.dynamicType) {

			collectionView?.registerNib(imageGalleryGridCellNib, forCellWithReuseIdentifier: imageCellId)
		}
	}

	public override func doCreateLayout() -> UICollectionViewLayout {
		let layout = super.doCreateLayout() as! UICollectionViewFlowLayout

		layout.minimumLineSpacing = spacing
		layout.minimumInteritemSpacing = spacing

		return layout
	}

	override public func doFillLoadedCell(indexPath indexPath: NSIndexPath, cell: UICollectionViewCell, object:AnyObject) {
		guard let imageCell = cell as? ImageGalleryGridCell, entry = object as? ImageEntry else {
			return
		}

		imageCell.imageUrl = entry.thumbnailUrl
	}

	public override func doFillInProgressCell(indexPath indexPath: NSIndexPath, cell: UICollectionViewCell) {

		cell.backgroundColor = .grayColor()
	}

	public override func doGetCellId(indexPath indexPath: NSIndexPath, object: AnyObject?) -> String {
		if let _ = object {
			return imageCellId
		}

		return super.doGetCellId(indexPath: indexPath, object: object)
	}

	public override func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {

		let screenRect = UIScreen.mainScreen().bounds

		let cellWidth = screenRect.width / 4 - spacing

		return CGSize(width: cellWidth, height: cellWidth)
	}
}
