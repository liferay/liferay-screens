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
import Foundation
import AFNetworking


public class ImageGalleryView_default_list : BaseListTableView, ImageGalleryViewModel {
    
    private let imageCellId = "ImageCellId"

	public var totalEntries: Int {
		return rowCount
	}
	
	public weak var uploadView: UploadProgressView_default?

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
		tableView?.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
	}

	public func onImageUploaded(imageEntry: ImageEntry) {
		uploadView?.uploadComplete()
		if let lastSection = self.sections.last {
			self.addRow(lastSection, element: imageEntry)

			let lastRow = self.rows[lastSection]!.count - 1
			let indexPath = NSIndexPath(forRow: lastRow, inSection: self.sections.count - 1)
			self.tableView?.insertRowsAtIndexPaths([indexPath], withRowAnimation: .Top)
		}
	}

	public func onImageUploadEnqueued(imageEntry: ImageEntryUpload) {
		if uploadView == nil {
			showUploadProgressView()
		}

		uploadView?.addUpload(imageEntry.image)
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

		UIView.animateWithDuration(0.5){
			self.uploadView?.alpha = 1
		}

		uploadView?.cancelClosure = { [weak self] in
			(self?.screenlet as? ImageGalleryScreenlet)?.cancelUploads()
		}
	}

	public func onImageUploadProgress(bytesSent: UInt64, bytesToSend: UInt64, imageEntry: ImageEntryUpload) {
		let progress = Float(bytesSent) / Float(bytesToSend)

		uploadView?.setProgress(progress)
	}


    public override func onCreated() {
        super.onCreated()
        tableView?.rowHeight = 110
    }
    
    override public func createProgressPresenter() -> ProgressPresenter {
        return DefaultProgressPresenter()
    }
    
    override public func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
        guard let imageCell = cell as? ImageGalleryCell, entry = object as? ImageEntry else {
            return
        }

		if let image = entry.image {
			imageCell.imageView?.image = image
		}
		else {
        	imageCell.imageUrl = entry.thumbnailUrl
		}
        imageCell.title = entry.title
    }
    
    override public func doFillInProgressCell(row row: Int, cell: UITableViewCell) {
        cell.textLabel?.text = "..."
        cell.accessoryType = .None
        
        if let image = NSBundle.imageInBundles(
            name: "default-hourglass",
            currentClass: self.dynamicType) {
            
            cell.accessoryView = UIImageView(image: image)
            cell.accessoryView?.frame = CGRectMake(0, 0, image.size.width, image.size.height)
        }
    }
    
    public override func doGetCellId(row row: Int, object: AnyObject?) -> String {
        if let _ = object {
            return imageCellId
        }
        
        return super.doGetCellId(row: row, object: object)
    }
    
    public override func doRegisterCellNibs() {
        if let imageGalleryCellNib = NSBundle.nibInBundles(
            name: "ImageGalleryCell",
            currentClass: self.dynamicType) {
            
            tableView?.registerNib(imageGalleryCellNib, forCellReuseIdentifier: imageCellId)
        }
    }
}