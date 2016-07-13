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

	public func createScreenlet(autoLoad autoLoad: Bool, frame: CGRect) -> BaseScreenlet? {

		let classAssetName = AssetClassNameIds.get(assetEntry.classNameId)

		if let className = classAssetName {
			switch className {
			case "DLFileEntry":

				let mimeType = assetEntry.mimeType

				if isImage(mimeType) {
					return ImageDisplayScreenlet(frame: frame, themeName: nil) {
						if let s = $0 as? ImageDisplayScreenlet {
							s.assetEntry = self.assetEntry
							s.autoLoad = autoLoad
						}
					}
				}

			default:
				return nil
			}

		}

		return nil
	}

	func isImage(mimeType: String) -> Bool {
		return imageMimeTypes.contains(mimeType)
	}
}
