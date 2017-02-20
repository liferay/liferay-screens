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
	
	///  Called when the screenlet receives the asset.
	///
	/// - Parameters:
	///   - screenlet
	///   - asset: asset object.
	@objc optional func screenlet(_ screenlet: AssetDisplayScreenlet,
	                        onAssetResponse asset: Asset)
	
	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error while retrieving the asset.
	@objc optional func screenlet(_ screenlet: AssetDisplayScreenlet,
	                        onAssetError error: NSError)

	/// Called when the child Screenlet (the Screenlet rendered inside Asset Display Screenlet) 
	/// has been successfully initialized.
	/// Use this method to configure or customize it.
	///
	/// - Parameters:
	///   - screenlet
	///   - childScreenlet: AssetDisplayScreenlet inner screenlet.
	///   - asset: asset object.
	@objc optional func screenlet(_ screenlet: AssetDisplayScreenlet,
	                        onConfigureScreenlet childScreenlet: BaseScreenlet?,
							onAsset asset: Asset)

	/// Called to render a custom asset.
	///
	/// - Parameters:
	///   - screenlet
	///   - asset: custom asset.
	/// - Returns: custom view.
	@objc optional func screenlet(_ screenlet: AssetDisplayScreenlet, onAsset asset: Asset) -> UIView?
}


open class AssetDisplayScreenlet: BaseScreenlet {


	//MARK: Inspectables
	
	@IBInspectable open var assetEntryId: Int64 = 0

	@IBInspectable open var className: String = ""

	@IBInspectable open var classPK: Int64 = 0

	@IBInspectable public var portletItemName: String?

	@IBInspectable public var autoLoad: Bool = true

	@IBInspectable open var offlinePolicy: String? = CacheStrategyType.remoteFirst.rawValue

	open var assetEntry: Asset? {
		didSet {
			if let asset = assetEntry {
				if let innerScreenlet = self.createInnerScreenlet(asset) {
					self.assetDisplayViewModel?.innerScreenlet = innerScreenlet
					self.assetDisplayViewModel?.asset = asset

					self.assetDisplayDelegate?.screenlet?(self, onAssetResponse: asset)
				}
				else {
					self.assetDisplayViewModel?.asset = nil

					self.assetDisplayDelegate?.screenlet?(self,
							onAssetError: NSError.errorWithCause(.invalidServerResponse,
									message: "Could not create inner screenlet."))
				}
			}
		}
	}

	open var assetDisplayDelegate: AssetDisplayScreenletDelegate? {
		return delegate as? AssetDisplayScreenletDelegate
	}

	open var assetDisplayViewModel: AssetDisplayViewModel? {
		return screenletView as? AssetDisplayViewModel
	}
	
	//MARK: BaseScreenlet
	
	override open func onShow() {
		if autoLoad {
			load()
		}
	}
	
	override open func createInteractor(name: String, sender: AnyObject?) -> Interactor? {
		let interactor: LoadAssetInteractor

		if assetEntryId != 0 {
			interactor = LoadAssetInteractor(screenlet: self, assetEntryId: assetEntryId)
		}
		else if className != "" && classPK != 0 {
			interactor = LoadAssetInteractor(
				screenlet: self, className: self.className, classPK: self.classPK)
		}
		else {
			interactor = LoadAssetInteractor(screenlet: self, portletItemName: self.portletItemName!)
		}

		interactor.cacheStrategy = CacheStrategyType(rawValue: offlinePolicy ?? "") ?? .remoteFirst

		interactor.onSuccess = {
			self.assetEntry = interactor.asset
		}
		
		interactor.onFailure = {
			self.assetDisplayDelegate?.screenlet?(self, onAssetError: $0)
		}
		
		return interactor
	}


	//MARK: Public methods

	/// Call this method to load the asset.
	///
	/// - Returns: true if default use case has been perform, false otherwise.
	@discardableResult
	open func load() -> Bool {
		return self.performDefaultAction()
	}

	/// Creates AssetDisplayScreenlet inner screenlet.
	///
	/// - Parameter asset: asset object.
	/// - Returns: inner screenlet (UIView)
	open func createInnerScreenlet(_ asset: Asset) -> UIView? {
		guard let view = screenletView else {
			return nil
		}

		let frame = CGRect(origin: CGPoint.zero, size: view.frame.size)

		guard let innerScreenlet = AssetDisplayBuilder.createScreenlet(frame,
				asset: asset, themeName: themeName) else {

			return assetDisplayDelegate?.screenlet?(self, onAsset: asset)
		}

		configureInnerScreenlet(innerScreenlet, asset: asset)

		return innerScreenlet
	}

	/// Configures AssetDisplayScreenlet inner screenlet.
	///
	/// - Parameters:
	///   - innerScreenlet: AssetDisplayScreenlet inner screenlet (UIView).
	///   - asset: asset object.
	open func configureInnerScreenlet(_ innerScreenlet: BaseScreenlet, asset: Asset) {
		if let screenlet = innerScreenlet as? FileDisplayScreenlet {
			screenlet.autoLoad = false
			screenlet.offlinePolicy = self.offlinePolicy
			screenlet.presentingViewController = self.presentingViewController

			if let image = asset as? ImageEntry {
				screenlet.classPK = image.imageEntryId
			}
			else {
				screenlet.fileEntry = FileEntry(attributes: asset.attributes)
			}
			
			screenlet.load()
		}
		else if let screenlet = innerScreenlet as? BlogsEntryDisplayScreenlet {
			screenlet.blogsEntry = BlogsEntry(attributes: asset.attributes)
			screenlet.autoLoad = false
			screenlet.offlinePolicy = self.offlinePolicy
		}
		else if let screenlet = innerScreenlet as? WebContentDisplayScreenlet {
			let webContent = WebContent(attributes: asset.attributes)
			screenlet.articleId = (webContent.attributes["articleId"] as? String) ?? ""
			screenlet.autoLoad = false
			screenlet.loadWebContent()
		}

		assetDisplayDelegate?.screenlet?(self, onConfigureScreenlet: innerScreenlet, onAsset: asset)
	}

	/// Removes AssetDisplayScreenlet inner screenlet.
	open func removeInnerScreenlet() {
		assetDisplayViewModel?.innerScreenlet = nil
	}
}
