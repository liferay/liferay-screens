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


@objc public protocol BlogsEntryDisplayScreenletDelegate : BaseScreenletDelegate {

	/// Called when the Screenlet receives the BlogsEntry object.
	///
	/// - Parameters:
	///   - screenlet
	///   - blogEntry: blog entry object.
	@objc optional func screenlet(_ screenlet: BlogsEntryDisplayScreenlet,
			onBlogEntryResponse blogEntry: BlogsEntry)

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error while retrieving blog entry.
	@objc optional func screenlet(_ screenlet: BlogsEntryDisplayScreenlet,
			onBlogEntryError error: NSError)
}


open class BlogsEntryDisplayScreenlet: BaseScreenlet {


	//MARK: Inspectables

	@IBInspectable open var assetEntryId: Int64 = 0

	@IBInspectable open var classPK: Int64 = 0

	@IBInspectable open var autoLoad: Bool = true

	@IBInspectable open var offlinePolicy: String? = CacheStrategyType.remoteFirst.rawValue

	open var blogsEntry: BlogsEntry? {
		didSet {
			blogsEntryViewModel?.blogsEntry = self.blogsEntry
		}
	}
	
	open var blogsEntryDisplayDelegate: BlogsEntryDisplayScreenletDelegate? {
		return delegate as? BlogsEntryDisplayScreenletDelegate
	}

	open var blogsEntryViewModel: BlogsDisplayViewModel? {
		return screenletView as? BlogsDisplayViewModel
	}

	//MARK: BaseScreenlet

	override open func onShow() {
		if autoLoad {
			load()
		}
	}

	override open func createInteractor(name: String, sender: AnyObject?) -> Interactor? {
		if isActionRunning(name) {
			cancelInteractorsForAction(name)
		}

		let interactor: LoadAssetInteractor

		if assetEntryId != 0 {
			interactor = LoadAssetInteractor(screenlet: self, assetEntryId: assetEntryId)
		}
		else {
			interactor = LoadAssetInteractor(
				screenlet: self,
				className: AssetClasses.getClassName(AssetClassNameKey_BlogsEntry)!,
				classPK: self.classPK)
		}

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .remoteFirst

		interactor.onSuccess = {
			if let resultAsset = interactor.asset {
				self.blogsEntry = BlogsEntry(attributes: resultAsset.attributes)
				self.blogsEntryViewModel?.blogsEntry = self.blogsEntry
				self.blogsEntryDisplayDelegate?.screenlet?(self, onBlogEntryResponse: self.blogsEntry!)
			}
			else {
				self.blogsEntryDisplayDelegate?.screenlet?(self, onBlogEntryError:
					NSError.errorWithCause(.invalidServerResponse, message: "No blog entry found."))
			}
		}

		interactor.onFailure = {
			self.blogsEntryDisplayDelegate?.screenlet?(self, onBlogEntryError: $0)
		}

		return interactor
	}


	//MARK: Public methods

	/// Loads a blog entry in the screenlet.
	///
	/// - Returns: true if default use case has been perform, false otherwise.
	@discardableResult
	open func load() -> Bool {
		return self.performDefaultAction()
	}
}
