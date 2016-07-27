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
	                        onAssetEntryResponse assetEntry: Asset)
	
	optional func screenlet(screenlet: AssetDisplayScreenlet,
	                        onAssetEntryError error: NSError)
}

@IBDesignable public class AssetDisplayScreenlet: BaseScreenlet {
	
	@IBInspectable public var entryId: Int64 = 0
	
	@IBInspectable public var autoLoad: Bool = true
	
	var assetEntry: Asset?
	
	public var assetDisplayDelegate: AssetDisplayScreenletDelegate? {
		return delegate as? AssetDisplayScreenletDelegate
	}
	
	//MARK: Public methods
	
	override public func onShow() {
		if autoLoad && entryId != 0 {
			loadAsset()
		}
	}
	
	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		let interactor = AssetDisplayInteractor(
			screenlet: self,
			entryId: self.entryId)
		
		
		interactor.onSuccess = {
			if let resultAsset = interactor.assetEntry {
				self.assetDisplayDelegate?.screenlet?(self, onAssetEntryResponse: resultAsset)
				
				(self.screenletView as! AssetDisplayViewModel).assetEntry = resultAsset
			}
		}
		
		interactor.onFailure = {
			self.assetDisplayDelegate?.screenlet?(self, onAssetEntryError: $0)
		}
		
		return interactor
	}
	
	public func loadAsset() -> Bool {
		return self.performDefaultAction()
	}
}
