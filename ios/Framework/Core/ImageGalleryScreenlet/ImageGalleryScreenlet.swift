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

@objc public protocol ImageGalleryScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: ImageGalleryScreenlet,
	                        onImageEntriesResponse imageEntries: [ImageEntry])

	optional func screenlet(screenlet: ImageGalleryScreenlet,
	                        onImageEntriesError error: NSError)

	optional func screenlet(screenlet: ImageGalleryScreenlet,
	                        onImageEntrySelected imageEntry: ImageEntry)

	optional func screenlet(screenlet: ImageGalleryScreenlet,
	                        onImageEntryDeleted: ImageEntry)

	optional func screenlet(screenlet: ImageGalleryScreenlet,
	                        onImageEntryDeleteError: NSError)

}


@IBDesignable public class ImageGalleryScreenlet : BaseListScreenlet {

	public static let DeleteImageAction = "deleteImage"

	public static let DefaultColumns = 4
    
    @IBInspectable public var repositoryId: Int64 = -1
    @IBInspectable public var folderId: Int64 = -1
	@IBInspectable public var mimeTypes: String = ""

    @IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue

	public let DefaultMimeTypes = ["image/png", "image/jpeg", "image/gif"]

	public var columNumber =  ImageGalleryScreenlet.DefaultColumns {
		didSet {
			if columNumber > 0 {
				viewModel.columnNumber = columNumber
			}
		}
	}

	public var imageGalleryScreenletDelegate: ImageGalleryScreenletDelegate? {
		return delegate as? ImageGalleryScreenletDelegate
	}

	public var viewModel: ImageGalleryViewModel {
		return screenletView as! ImageGalleryViewModel
	}

	public func deleteImageEntry(imageEntry: ImageEntry) {
		performAction(name: ImageGalleryScreenlet.DeleteImageAction, sender: imageEntry)
	}
    
    public override func createPageLoadInteractor(page page: Int, computeRowCount: Bool) -> BaseListPageLoadInteractor {

		let finalMimeTypes = mimeTypes.isEmpty ? DefaultMimeTypes : parseMimeTypes(mimeTypes)
		
        return ImageGalleryLoadInteractor(screenlet: self,
                                      page: page,
                                      computeRowCount: computeRowCount,
                                      repositoryId: repositoryId,
                                      folderId: folderId,
									  mimeTypes: finalMimeTypes)
    }

	public override func onLoadPageError(page page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)
		imageGalleryScreenletDelegate?.screenlet?(self, onImageEntriesError: error)
	}

	public override func onLoadPageResult(page page: Int, rows: [AnyObject], rowCount: Int) {
		super.onLoadPageResult(page: page, rows: rows, rowCount: rowCount)
		imageGalleryScreenletDelegate?.screenlet?(self, onImageEntriesResponse: rows as! [ImageEntry])
	}

	public override func onSelectedRow(row: AnyObject) {
		super.onSelectedRow(row)
		imageGalleryScreenletDelegate?.screenlet?(self, onImageEntrySelected: row as! ImageEntry)
	}

	public override func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		switch name {
		case BaseListScreenlet.LoadInitialPageAction, BaseListScreenlet.LoadPageAction:
			return super.createInteractor(name: name, sender: sender)
		case ImageGalleryScreenlet.DeleteImageAction:
			let imageEntry = sender as! ImageEntry
			return createImageGalleryDeleteInteractor(imageEntry)
		default:
			return nil
		}
	}

	internal func createImageGalleryDeleteInteractor(imageEntry: ImageEntry) -> ImageGalleryDeleteInteractor {
		let interactor = ImageGalleryDeleteInteractor(screenlet: self, imageEntryId: imageEntry.imageEntryId)

		interactor.onSuccess = {
			self.imageGalleryScreenletDelegate?.screenlet?(self, onImageEntryDeleted: imageEntry)
			self.viewModel.onImageEntryDeleted?(imageEntry)
		}

		interactor.onFailure = {
			self.imageGalleryScreenletDelegate?.screenlet?(self, onImageEntryDeleteError: $0)
		}
		
		return interactor
	}

	internal func parseMimeTypes(mimeTypes: String) -> [String] {
		return mimeTypes.characters.split(",").map(String.init)
	}

}