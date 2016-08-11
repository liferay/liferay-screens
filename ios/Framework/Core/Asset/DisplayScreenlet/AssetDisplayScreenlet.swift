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

				if let childScreenlet = self.createChildScreenlet(resultAsset) {
					self.assetDisplayViewModel?.childScreenlet = childScreenlet
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

	private func createChildScreenlet(asset: Asset) -> BaseScreenlet? {
		guard let view = screenletView else {
			return nil
		}

		let frame = CGRect(origin: CGPointZero, size: view.frame.size)

		let factory = AssetDisplayFactory()

		guard let childScreenlet = factory.createScreenlet(frame, asset: asset) else {
			return nil
		}

		configureChildScreenlet(childScreenlet, asset: asset)

		return childScreenlet
	}

	func configureChildScreenlet(childScreenlet: BaseScreenlet, asset: Asset) {
		if let screenlet = childScreenlet as? FileDisplayScreenlet {
			screenlet.fileEntry = FileEntry(attributes: asset.attributes)
			screenlet.autoLoad = false
			screenlet.load()
		}
		else if let screenlet = childScreenlet as? BlogsEntryDisplayScreenlet {
			screenlet.blogsEntry = BlogsEntry(attributes: asset.attributes)
			screenlet.autoLoad = false
			screenlet.load()
		}

		assetDisplayDelegate?.screenlet?(self, onConfigureScreenlet: childScreenlet, onAsset: asset)
	}

}
