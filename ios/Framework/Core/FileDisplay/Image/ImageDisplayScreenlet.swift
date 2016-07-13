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

	public static let DownloadImageAction = "download-image"
	public static let LoadAssetEntry = "load-asset"

	@IBInspectable public var entryId: Int64 = 0

	@IBInspectable public var autoLoad: Bool = true

	public var assetEntry: Asset?

	public var imageDisplayDelegate: ImageDisplayScreenletDelegate? {
		return delegate as? ImageDisplayScreenletDelegate
	}

	//MARK: Public methods

	override public func onShow() {
		if autoLoad && !loadImageAssetFromAssetEntry() {
			loadImageAssetFromEntryId()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		return nil
	}

	public override func performDefaultAction() -> Bool {
		return performAction(name: ImageDisplayScreenlet.LoadAssetEntry, sender: nil)
	}

	public func loadImageAssetFromEntryId() -> Bool {
		return self.performDefaultAction()
	}

	public func loadImageAssetFromAssetEntry() -> Bool {
		if assetEntry != nil {
			return performAction(name: ImageDisplayScreenlet.DownloadImageAction, sender: nil)
		}

		return false
	}
}
