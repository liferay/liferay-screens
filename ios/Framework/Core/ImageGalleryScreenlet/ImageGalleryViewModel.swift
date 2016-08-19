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

@objc public protocol ImageGalleryViewModel {

	var totalEntries: Int { get }

	optional func onImageEntryDeleted(imageEntry: ImageEntry)

	optional func onImageUploaded(imageEntry: ImageEntry)

	optional func onImageUploadEnqueued(imageEntryUpload: ImageEntryUpload)

	optional func onImageUploadProgress(
			bytesSent: UInt64,
			bytesToSend: UInt64,
			imageEntryUpload: ImageEntryUpload)

	optional func onImageUploadError(imageEntryUpload: ImageEntryUpload, error: NSError)

	optional func indexOf(imageEntry imageEntry: ImageEntry) -> Int
			
}