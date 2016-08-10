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


@objc public class AssetDisplayScreenletFactory: NSObject {

	private var asset: Asset

	public init(asset: Asset) {
		self.asset = asset

		super.init()
	}

	let imageMimeTypes = ["image/png", "image/jpg", "image/jpeg", "image/gif"]
	let videoMimeTypes = ["video/mp4", "video/3gp", "video/quicktime"]
	let audioMimeTypes = ["audio/mpeg", "audio/mpeg3", "audio/wav"]

	public func createScreenlet(frame: CGRect, screenlet: AssetDisplayScreenlet?) -> BaseScreenlet? {

		let classAssetName = AssetClassNameIds.get(asset.classNameId)

		var childScreenlet: BaseScreenlet?

		if let className = classAssetName {
			switch className {
			case "DLFileEntry":
				let mimeType = asset.mimeType

				if isImage(mimeType) {
					childScreenlet = ImageDisplayScreenlet(frame: frame, themeName: nil)
				} else if isVideo(mimeType) {
					childScreenlet = VideoDisplayScreenlet(frame: frame, themeName: nil)
				} else if isAudio(mimeType) {
					childScreenlet = AudioDisplayScreenlet(frame: frame, themeName: nil)
				} else if mimeType == "application/pdf" {
					childScreenlet = PdfDisplayScreenlet(frame: frame, themeName: nil)
				}
			case "BlogsEntry":
				childScreenlet = BlogsEntryDisplayScreenlet(frame: frame, themeName: nil)
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

		if childScreenlet is FileDisplayScreenlet {
			if let s = childScreenlet as? FileDisplayScreenlet {
				s.fileEntry = FileEntry(attributes: self.asset.attributes)
				s.autoLoad = false
				s.load()
			}
		}
		else if childScreenlet is BlogsEntryDisplayScreenlet {
			if let s = childScreenlet as? BlogsEntryDisplayScreenlet {
				s.blogsEntry = BlogsEntry(attributes: self.asset.attributes)
				s.autoLoad = false
			}
		}

		screenlet?.assetDisplayDelegate?.screenlet?(screenlet!, onConfigureScreenlet: childScreenlet, onAsset: self.asset)

		return childScreenlet
	}
}
