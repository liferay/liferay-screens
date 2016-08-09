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


public class AssetDisplayScreenletFactory {

	private var assetEntry: Asset

	public init(assetEntry: Asset) {
		self.assetEntry = assetEntry
	}

	let imageMimeTypes = ["image/png", "image/jpg", "image/jpeg", "image/gif"]
	let videoMimeTypes = ["video/mp4", "video/x-flv", "video/3gp", "video/quicktime", "video/x-msvideo", "video/x-ms-wmv"]
	let audioMimeTypes = ["audio/mpeg", "audio/mpeg3", "audio/wav"]

	public func createScreenlet(frame: CGRect, screenlet: AssetDisplayScreenlet?) -> BaseScreenlet? {

		let classAssetName = AssetClassNameIds.get(assetEntry.classNameId)

		var childScreenlet: BaseScreenlet?

		if let className = classAssetName {
			switch className {
			case "DLFileEntry":
				let mimeType = assetEntry.mimeType

				if isImage(mimeType) {
					childScreenlet = ImageDisplayScreenlet(frame: frame, themeName: nil, initalizer: nil)
				} else if isVideo(mimeType) {
					childScreenlet = VideoDisplayScreenlet(frame: frame, themeName: nil, initalizer: nil)
				} else if isAudio(mimeType) {
					childScreenlet = AudioDisplayScreenlet(frame: frame, themeName: nil, initalizer: nil)
				} else if mimeType == "application/pdf" {
					childScreenlet = PdfDisplayScreenlet(frame: frame, themeName: nil, initalizer: nil)
				}
			case "BlogsEntry":
				childScreenlet = BlogsEntryDisplayScreenlet(frame: frame, themeName: nil, initalizer: nil)
			default:
				break;
			}
		}

		return configureScreenlet(screenlet, childScreenlet: childScreenlet)
	}

	func isImage(mimeType: String) -> Bool {
		return imageMimeTypes.contains(mimeType)
	}

	func isVideo(mimeType: String) -> Bool {
		return videoMimeTypes.contains(mimeType)
	}

	func isAudio(mimeType: String) -> Bool {
		return audioMimeTypes.contains(mimeType)
	}

	func configureScreenlet(screenlet: AssetDisplayScreenlet?, childScreenlet: BaseScreenlet?) -> BaseScreenlet? {

		if childScreenlet is BaseFileDisplayScreenlet {
			if let s = childScreenlet as? BaseFileDisplayScreenlet {
				s.fileEntry = FileEntry(attributes: self.assetEntry.attributes)
				s.autoLoad = false
				s.loadFile()
			}
		}
		else if childScreenlet is BlogsEntryDisplayScreenlet {
			if let s = childScreenlet as? BlogsEntryDisplayScreenlet {
				s.blogsEntry = BlogsEntry(attributes: self.assetEntry.attributes)
				s.autoLoad = false
			}
		}

		screenlet?.assetDisplayDelegate?.screenlet?(screenlet!, onConfigureScreenlet: childScreenlet, onAssetEntry: self.assetEntry)

		return childScreenlet
	}
}
