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

	optional func screenlet(screenlet: ImageGalleryScreenlet,
	                        onImageUploadStart image: ImageEntryUpload)
							
	optional func screenlet(screenlet: ImageGalleryScreenlet,
	                        onImageUploadProgress image: ImageEntryUpload, totalBytesSent: UInt64, totalBytesToSend: UInt64)

	optional func screenlet(screenlet: ImageGalleryScreenlet,
	                        onImageUploadError error: NSError)

	optional func screenlet(screenlet: ImageGalleryScreenlet,
	                        onImageUploaded image: ImageEntry)
}


@IBDesignable public class ImageGalleryScreenlet : BaseListScreenlet {

	public static let DeleteImageAction = "delete-image-action"
	public static let UploadImageAction = "upload-image-action"

	public static let DefaultColumns = 4
    
    @IBInspectable public var repositoryId: Int64 = -1
    @IBInspectable public var folderId: Int64 = -1
	@IBInspectable public var mimeTypes: String = ""

    @IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue

	public let DefaultMimeTypes = ["image/png", "image/jpeg", "image/gif"]

	internal var loaded = false

	public override func onShow() {
		if !loaded {
			loaded = true
			super.onShow()
		}
	}

	public var imageGalleryScreenletDelegate: ImageGalleryScreenletDelegate? {
		return delegate as? ImageGalleryScreenletDelegate
	}

	public var viewModel: ImageGalleryViewModel {
		return screenletView as! ImageGalleryViewModel
	}

	public func startMediaSelectorAndUpload() {
		if let viewController = presentingViewController {

			let takeNewPicture = LocalizedString("default", key: "userportrait-take-new-picture", obj: self)
			let chooseExisting = LocalizedString("default", key: "userportrait-choose-existing-picture", obj: self)

			let alert = MediaSelector(
				viewController: viewController,
				types: [.Camera : takeNewPicture, .Image : chooseExisting],
				cancelMessage: "Cancel") { (image, _) in

					if let image = image {
						let imageUpload = ImageEntryUpload(image: image, title: "test\(Int(CFAbsoluteTimeGetCurrent())).png")
						self.performAction(name: ImageGalleryScreenlet.UploadImageAction, sender: imageUpload)
					}
			}
			alert.show()
		}

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
		case ImageGalleryScreenlet.UploadImageAction:
			let imageUpload = sender as! ImageEntryUpload
			return createImageUploadInteractor(imageUpload)
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

	internal func createImageUploadInteractor(imageUpload: ImageEntryUpload) -> ImageGalleryUploadInteractor {
		let interactor = ImageGalleryUploadInteractor(
				screenlet: self,
				imageUpload: imageUpload,
				repositoryId: repositoryId,
				folderId: folderId) { (title, totalBytesSent, totalBytesToSend) in

			self.imageGalleryScreenletDelegate?.screenlet?(self, onImageUploadProgress: imageUpload, totalBytesSent: totalBytesSent, totalBytesToSend: totalBytesToSend)

		}

		interactor.onSuccess = {
			if let result = interactor.result {
				let imageEntry = ImageEntry(attributes: result)
				let thumbnailImage = self.resizeImage(imageUpload.image, toWidth: 300)
				imageEntry.image = thumbnailImage
				
				self.imageGalleryScreenletDelegate?.screenlet?(self, onImageUploaded: imageEntry)
				self.viewModel.onImageUploaded?(imageEntry)
			}
		}

		interactor.onFailure = {
			self.imageGalleryScreenletDelegate?.screenlet?(self, onImageUploadError: $0)
		}

		self.imageGalleryScreenletDelegate?.screenlet?(self, onImageUploadStart: imageUpload)

		return interactor
	}

	internal func parseMimeTypes(mimeTypes: String) -> [String] {
		return mimeTypes.characters.split(",").map(String.init)
	}

	internal func resizeImage(image: UIImage, toWidth width: Int) -> UIImage {
		let oldWidth = image.size.width
		let scaleFactor = CGFloat(width) / oldWidth

		let newHeight = image.size.height * scaleFactor
		let newWidth = CGFloat(width)

		UIGraphicsBeginImageContext(CGSize(width: newWidth, height: newHeight))
		image.drawInRect(CGRect(x: 0, y: 0, width: newWidth, height: newHeight))
		let newImage = UIGraphicsGetImageFromCurrentImageContext()
		UIGraphicsEndImageContext()

		return newImage
	}

}