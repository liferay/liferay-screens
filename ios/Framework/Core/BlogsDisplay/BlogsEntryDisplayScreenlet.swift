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

	optional func screenlet(screenlet: BlogsEntryDisplayScreenlet, onBlogAssetResponse blogEntry: BlogsEntry)

	optional func screenlet(screenlet: BlogsEntryDisplayScreenlet, onBlogAssetError error: NSError)
}

public class BlogsEntryDisplayScreenlet: BaseScreenlet {

	@IBInspectable public var entryId: Int64 = 0

	@IBInspectable public var classPK: Int64 = 0
	@IBInspectable public var className: String = ""

	@IBInspectable public var autoLoad: Bool = true

	public var blogsEntry: BlogsEntry? {
		didSet {
			blogsEntryViewModel?.blogsEntry = self.blogsEntry
		}
	}
	
	public var blogsEntryDisplayDelegate: BlogsEntryDisplayScreenletDelegate? {
		return delegate as? BlogsEntryDisplayScreenletDelegate
	}

	public var blogsEntryViewModel: BlogsDisplayViewModel? {
		return screenletView as? BlogsDisplayViewModel
	}

	//MARK: Public methods

	override public func onShow() {
		if autoLoad {
			load()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		if isActionRunning(name) {
			cancelInteractorsForAction(name)
		}

		let interactor = AssetDisplayInteractor(
			screenlet: self, entryId: entryId, classPK: classPK, className: className)

		interactor.onSuccess = {
			if let resultAsset = interactor.assetEntry {
				self.blogsEntry = BlogsEntry(attributes: resultAsset.attributes)
				self.blogsEntryViewModel?.blogsEntry = self.blogsEntry
			}
			else {
				self.blogsEntryDisplayDelegate?.screenlet?(self, onBlogAssetError: NSError.errorWithCause(.InvalidServerResponse))
			}
		}

		interactor.onFailure = {
			self.blogsEntryDisplayDelegate?.screenlet?(self, onBlogAssetError: $0)
		}

		return interactor
	}

	public func load() -> Bool {
		return self.performDefaultAction()
	}
}
