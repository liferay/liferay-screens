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


@objc public protocol AssetDisplayScreenletDelegate : BaseScreenletDelegate {
	
	optional func screenlet(screenlet: AssetDisplayScreenlet,
	                        onAssetResponse asset: Asset)
	
	optional func screenlet(screenlet: AssetDisplayScreenlet,
	                        onAssetError error: NSError)

	optional func screenlet(screenlet: AssetDisplayScreenlet,
	                        onConfigureScreenlet childScreenlet: BaseScreenlet?,
							onAsset asset: Asset)

	optional func screenlet(screenlet: AssetDisplayScreenlet, onAsset asset: Asset) -> UIView?
}

@IBDesignable public class AssetDisplayScreenlet: BaseScreenlet {
	
	@IBInspectable public var assetEntryId: Int64 = 0

	@IBInspectable public var className: String = ""
	@IBInspectable public var classPK: Int64 = 0

	@IBInspectable public var autoLoad: Bool = true

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.CacheFirst.rawValue


	public var assetDisplayDelegate: AssetDisplayScreenletDelegate? {
		return delegate as? AssetDisplayScreenletDelegate
	}

	public var assetDisplayViewModel: AssetDisplayViewModel? {
		return screenletView as? AssetDisplayViewModel
	}
	
	//MARK: Public methods
	
	override public func onShow() {
		if autoLoad {
			load()
		}
	}
	
	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		let interactor: LoadAssetInteractor

		if assetEntryId != 0 {
			interactor = LoadAssetInteractor(screenlet: self, assetEntryId: assetEntryId)
		}
		else {
			interactor = LoadAssetInteractor(
				screenlet: self, className: self.className, classPK: self.classPK)
		}

		interactor.onSuccess = {
			if let resultAsset = interactor.asset {
				self.assetDisplayDelegate?.screenlet?(self, onAssetResponse: resultAsset)

				if let innerScreenlet = self.createInnerScreenlet(resultAsset) {
					self.assetDisplayViewModel?.innerScreenlet = innerScreenlet
					self.assetDisplayViewModel?.asset = resultAsset
				}
				else {
					self.assetDisplayViewModel?.asset = nil
				}
			}
		}
		
		interactor.onFailure = {
			self.assetDisplayDelegate?.screenlet?(self, onAssetError: $0)
		}
		
		return interactor
	}
	
	public func load() -> Bool {
		return self.performDefaultAction()
	}

	public func createInnerScreenlet(asset: Asset) -> UIView? {
		guard let view = screenletView else {
			return nil
		}

		let frame = CGRect(origin: CGPointZero, size: view.frame.size)

		let factory = AssetDisplayFactory()

		guard let innerScreenlet = factory.createScreenlet(frame, asset: asset) else {
			return assetDisplayDelegate?.screenlet?(self, onAsset: asset)
		}

		configureInnerScreenlet(innerScreenlet, asset: asset)

		return innerScreenlet
	}

	public func configureInnerScreenlet(innerScreenlet: BaseScreenlet, asset: Asset) {
		//Load same theme as parent screenlet
		innerScreenlet.themeName = self.themeName

		if let screenlet = innerScreenlet as? FileDisplayScreenlet {
			screenlet.fileEntry = FileEntry(attributes: asset.attributes)
			screenlet.autoLoad = false
			screenlet.presentingViewController = self.presentingViewController
			screenlet.load()
		}
		else if let screenlet = innerScreenlet as? BlogsEntryDisplayScreenlet {
			screenlet.blogsEntry = BlogsEntry(attributes: asset.attributes)
			screenlet.autoLoad = false
		}

		assetDisplayDelegate?.screenlet?(self, onConfigureScreenlet: innerScreenlet, onAsset: asset)
	}

}
