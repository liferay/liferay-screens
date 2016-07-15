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


@objc public protocol ImageDisplayScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: ImageDisplayScreenlet, onImageAssetResponse image: UIImage) -> UIImage?

	optional func screenlet(screenlet: ImageDisplayScreenlet, onImageAssetError error: NSError)
}

public class ImageDisplayScreenlet: BaseScreenlet {

	@IBInspectable public var entryId: Int64 = 0

	@IBInspectable public var autoLoad: Bool = true

	public var assetEntry: FileEntry?

	public var imageDisplayDelegate: ImageDisplayScreenletDelegate? {
		return delegate as? ImageDisplayScreenletDelegate
	}

	public var imageDisplayViewModel: ImageDisplayViewModel? {
		return screenletView as? ImageDisplayViewModel
	}

	//MARK: Public methods

	override public func onShow() {
		if autoLoad && assetEntry?.entryId != 0 {
			loadImageAssetFromEntryId()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		if let assetEntry = assetEntry, url = NSURL(string: LiferayServerContext.server + assetEntry.url) {
			let interactor = DownloadImageAssetInteractor(screenlet: self, url: url)

			interactor.onSuccess = {
				if let resultImage = interactor.resultImage {
					let modifiedImage = self.imageDisplayDelegate?.screenlet?(self, onImageAssetResponse: resultImage)

					(self.screenletView as! ImageDisplayViewModel).image = modifiedImage ?? resultImage
				}
			}

			interactor.onFailure = {
				self.imageDisplayDelegate?.screenlet?(self, onImageAssetError: $0)
			}

			return interactor
		}

		return nil
	}

	public func loadImageAssetFromEntryId() -> Bool {
		return self.performDefaultAction()
	}
}
