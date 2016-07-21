
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

public class ImageGalleryView_default: BaseListCollectionView, ImageGalleryViewModel {

	// ImageGalleryViewModel

	public var columnNumber = ImageGalleryScreenlet.DefaultColumns {
		didSet {
			changeLayout()
		}
	}

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

		return createCustomLayout()
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

	internal func cellWidthForNumberOfColumns(numCols: Int) -> CGFloat {

		let horizontalMargins: CGFloat = 40
		let viewWidth = collectionView!.bounds.width

		let cellWidth =  (viewWidth - horizontalMargins) / CGFloat(numCols) - CGFloat(spacing)

		return cellWidth
	}

	internal func changeLayout() {
		if let collectionView = collectionView {
			let newLayout = createCustomLayout()
			collectionView.setCollectionViewLayout(newLayout, animated: true)
		}
	}

	internal func createCustomLayout() -> UICollectionViewLayout {
		let layout = UICollectionViewFlowLayout()

		layout.minimumLineSpacing = spacing
		layout.minimumInteritemSpacing = spacing
		layout.sectionInset = UIEdgeInsets(top: 10, left: 20, bottom: 10, right: 20)

		let cellWidth = cellWidthForNumberOfColumns(columnNumber)
		layout.itemSize = CGSize(width: cellWidth, height: cellWidth)

		return layout
	}
}
