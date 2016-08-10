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

	public weak var uploadView: UploadProgressView_default?

	internal let imageCellId = "ImageCellId"


	// MARK: ImageGalleryViewModel

	public var totalEntries: Int {
		return rowCount
	}

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

	public func onImageUploaded(imageEntry: ImageEntry) {
		uploadView?.uploadComplete()
		if let lastSection = self.sections.last {
			self.addRow(lastSection, element: imageEntry)

			let lastRow = self.rows[lastSection]!.count - 1
			let indexPath = NSIndexPath(forRow: lastRow, inSection: self.sections.count - 1)
			self.collectionView?.insertItemsAtIndexPaths([indexPath])
		}
	}

	public func onImageUploadEnqueued(imageEntry: ImageEntryUpload) {
		if uploadView == nil {
			showUploadProgressView()
		}

		uploadView?.addUpload(imageEntry.image)
	}

	public func onImageUploadProgress(
		bytesSent: UInt64,
		bytesToSend: UInt64,
		imageEntry: ImageEntryUpload) {

		let progress = Float(bytesSent) / Float(bytesToSend)

		uploadView?.setProgress(progress)
	}


	// MARK: BaseScreenletView

	public override func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}

	public func showUploadProgressView() {
		uploadView = NSBundle.viewForTheme(
				name: "UploadProgressView",
				themeName: "default",
				currentClass: self.dynamicType) as? UploadProgressView_default

		uploadView?.translatesAutoresizingMaskIntoConstraints = false
		uploadView?.alpha = 0

		addSubview(uploadView!)

		let views = ["view" : self, "uploadView" : uploadView!]

		let constraintH = NSLayoutConstraint.constraintsWithVisualFormat(
				"H:|-(5)-[uploadView]-(5)-|",
				options: [],
				metrics: nil,
				views: views)

		let constraintV = NSLayoutConstraint.constraintsWithVisualFormat(
				"V:[uploadView(55)]-(3)-|",
				options: [],
				metrics: nil,
				views: views)

		addConstraints(constraintH)
		addConstraints(constraintV)

		UIView.animateWithDuration(0.2){
			self.uploadView?.alpha = 1
		}

		uploadView?.cancelClosure = { [weak self] in
			(self?.screenlet as? ImageGalleryScreenlet)?.cancelUploads()
		}
	}

}