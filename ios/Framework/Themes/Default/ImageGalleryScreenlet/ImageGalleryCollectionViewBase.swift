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

public class ImageGalleryCollectionViewBase: BaseListCollectionView, ImageGalleryViewModel {

	// ImageGalleryViewModel

	public var columnNumber = ImageGalleryScreenlet.DefaultColumns {
		didSet {
			changeLayout()
		}
	}

	internal let imageCellId = "ImageCellId"

	public func onImageEntryDeleted(imageEntry: ImageEntry) {

		var section: Int?
		var sectionKey: String?
		var rowIndex: Int?

		for (keyIndex, key) in rows.keys.enumerate() {
			for (index, row) in rows[key]!.enumerate() {
				if let row = row as? ImageEntry {
					if imageEntry == row {
						section = keyIndex
						sectionKey = key
						rowIndex = index
					}
				}
			}
		}

		guard let finalSection = section, finalRowIndex = rowIndex, finalSectionKey = sectionKey
			else {
				return
		}

		deleteRow(finalSectionKey, row: finalRowIndex)

		let indexPath = NSIndexPath(forRow: finalRowIndex, inSection: finalSection)
		collectionView?.deleteItemsAtIndexPaths([indexPath])
	}

	public func showImageSelector() {
		if let viewController = presentingViewController {

			let takeNewPicture = LocalizedString("default", key: "userportrait-take-new-picture", obj: self)
			let chooseExisting = LocalizedString("default", key: "userportrait-choose-existing-picture", obj: self)

			let alert = MediaSelector(
					viewController: viewController,
					types: [.Camera : takeNewPicture, .Image : chooseExisting],
					cancelMessage: "Cancel") { (image, _) in
					
				if let image = image {
					let imageUpload = ImageEntryUpload(image: image, title: "test\(Int(CFAbsoluteTimeGetCurrent())).png")
					self.screenlet?.performAction(name: ImageGalleryScreenlet.UploadImageAction, sender: imageUpload)
				}
			}
			alert.show()
		}
	}

	public func onImageUploaded(imageEntry: ImageEntry) {
		if let lastSection = self.sections.last {
			self.addRow(lastSection, element: imageEntry)

			let lastRow = self.rows[lastSection]!.count - 1
			let indexPath = NSIndexPath(forRow: lastRow, inSection: self.sections.count - 1)
			self.collectionView?.insertItemsAtIndexPaths([indexPath])
		}
	}

	// BaseScreenletView

	public override func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}

	public func changeLayout() {
		
	}
	
}