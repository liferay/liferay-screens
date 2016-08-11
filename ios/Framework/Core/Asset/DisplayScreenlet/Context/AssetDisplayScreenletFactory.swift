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

	public func createScreenlet(frame: CGRect, asset: Asset) -> BaseScreenlet? {
		// TODO don't use AssetClassNameIds here
		// Using this class makes the programmer to set the correct ids inside it
		// and this is a new configuration step.
		// Try to retrieve the className from the portal somehow.
		let classAssetName = AssetClassNameIds.get(asset.classNameId)

		var childScreenlet: BaseScreenlet?

		if let className = classAssetName {
			switch className {
			case "DLFileEntry":

				if asset.isAnyMimeType(ImageDisplayScreenlet.supportedMimeTypes) {
					childScreenlet = ImageDisplayScreenlet(frame: frame, themeName: nil)
				}
				else if asset.isAnyMimeType(VideoDisplayScreenlet.supportedMimeTypes) {
					childScreenlet = VideoDisplayScreenlet(frame: frame, themeName: nil)
				}
				else if asset.isAnyMimeType(AudioDisplayScreenlet.supportedMimeTypes) {
					childScreenlet = AudioDisplayScreenlet(frame: frame, themeName: nil)
				}
				else if asset.isAnyMimeType(PdfDisplayScreenlet.supportedMimeTypes) {
					childScreenlet = PdfDisplayScreenlet(frame: frame, themeName: nil)
				}
			case "BlogsEntry":
				childScreenlet = BlogsEntryDisplayScreenlet(frame: frame, themeName: nil)
			default:
				break;
			}
		}

		return childScreenlet
	}

}
