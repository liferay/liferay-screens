
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

public class ImageGalleryView_default: ImageGalleryCollectionViewBase {

	var spacing: CGFloat = 1.0
	
	public var columnNumber = ImageGalleryScreenlet.DefaultColumns {
		didSet {
			changeLayout()
		}
	}

	internal var lastOffset: CGPoint?
	internal var currentOrientation: UIInterfaceOrientation?

	// MARK: UIView

	public override func layoutSubviews() {
		super.layoutSubviews()

		if let orientation = currentOrientation {
			let actualOrientation = UIApplication.sharedApplication().statusBarOrientation

			if orientation != actualOrientation {
				changeLayout()
				currentOrientation = actualOrientation
			}
		}
	}

	// MARK: BaseScreenletView
	
	public override func onShow() {
		super.onShow()

		currentOrientation = UIApplication.sharedApplication().statusBarOrientation

		if let lastOffset = lastOffset {
			collectionView?.contentOffset = lastOffset
		}
	}

	public override func onHide() {
		lastOffset = collectionView?.contentOffset
	}

	// MARK: BaseListCollectionView

	public override func doConfigureCollectionView(collectionView: UICollectionView) {
		collectionView.backgroundColor = .whiteColor()
	}

	public override func doRegisterCellNibs() {
		if let imageGalleryGridCellNib = NSBundle.nibInBundles(
			name: "ImageGalleryGridCell",
			currentClass: self.dynamicType) {

			collectionView?.registerNib(
					imageGalleryGridCellNib,
					forCellWithReuseIdentifier: imageCellId)
		}
	}

	public override func doCreateLayout() -> UICollectionViewLayout {

		return createCustomLayout()
	}

	override public func doFillLoadedCell(
			indexPath indexPath: NSIndexPath,
			cell: UICollectionViewCell,
			object:AnyObject) {

		guard let imageCell = cell as? ImageGalleryGridCell, entry = object as? ImageEntry else {
			return
		}

		if let image = entry.image {
			imageCell.image = image
		}
		else {
			imageCell.imageUrl = entry.thumbnailUrl
		}
	}

	public override func doFillInProgressCell(indexPath indexPath: NSIndexPath, cell: UICollectionViewCell) {

		cell.backgroundColor = .grayColor()
	}

	public override func doGetCellId(indexPath indexPath: NSIndexPath, object: AnyObject?) -> String {
		return imageCellId
	}


	// MARK: Private methods

	public func changeLayout() {
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

	internal func cellWidthForNumberOfColumns(numCols: Int) -> CGFloat {

		let horizontalMargins: CGFloat = 40
		let viewWidth = collectionView!.bounds.width

		let cellWidth =  (viewWidth - horizontalMargins) / CGFloat(numCols) - CGFloat(spacing)

		return cellWidth
	}

}
