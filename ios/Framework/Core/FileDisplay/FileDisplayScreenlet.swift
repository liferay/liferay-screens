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


@objc public protocol FileDisplayScreenletDelegate : BaseScreenletDelegate {

	/// Called when the screenlet receives the file.
	///
	/// - Parameters:
	///   - screenlet
	///   - url: file URL.
	@objc optional func screenlet(_ screenlet: FileDisplayScreenlet, onFileAssetResponse url: URL)

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error while retrieving the file.
	@objc optional func screenlet(_ screenlet: FileDisplayScreenlet, onFileAssetError error: NSError)
}


open class FileDisplayScreenlet: BaseScreenlet {

	open static let LoadFileAction = "LoadFileAction"


	//MARK: Inspectables

	@IBInspectable open var assetEntryId: Int64 = 0

	@IBInspectable open var className: String =
		AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!
	
	@IBInspectable open var classPK: Int64 = 0

	@IBInspectable open var autoLoad: Bool = true

	@IBInspectable open var offlinePolicy: String? = CacheStrategyType.remoteFirst.rawValue

	open var supportedMimeTypes: [String] {
		return []
	}

	open var fileEntry: FileEntry?

	open var fileDisplayViewModel: FileDisplayViewModel? {
		return screenletView as? FileDisplayViewModel
	}

	open var fileDisplayDelegate: FileDisplayScreenletDelegate? {
		return delegate as? FileDisplayScreenletDelegate
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

		switch name {
			case BaseScreenlet.DefaultAction:
				return createLoadAssetInteractor()
			case FileDisplayScreenlet.LoadFileAction:
				return createLoadFileInteractor()
			default:
				return nil
		}
	}

	//MARK: Public methods

	/// Call this method to load the file.
	///
	/// - Returns: true if default use case has been perform, false otherwise.
	@discardableResult
	open func load() -> Bool {
		if fileEntry == nil {
			return performDefaultAction()
		}
		else {
			return self.performAction(name: FileDisplayScreenlet.LoadFileAction)
		}
	}


	//MARK: Private methods

	fileprivate func createLoadAssetInteractor() -> Interactor? {
		let interactor: LoadAssetInteractor

		if assetEntryId != 0 {
			interactor = LoadAssetInteractor(screenlet: self, assetEntryId: assetEntryId)
		}
		else {
			interactor = LoadAssetInteractor(
				screenlet: self, className: self.className, classPK: self.classPK)
		}

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .remoteFirst

		interactor.onSuccess = {
			if let resultAsset = interactor.asset {

				guard let assetMimeType = resultAsset.mimeType,
						self.supportedMimeTypes.contains(assetMimeType) else {

						self.fileDisplayDelegate?.screenlet?(self,
						        onFileAssetError: NSError.errorWithCause(.invalidServerResponse,
						                message: "Asset mimeType is not supported."))
						return
				}

				self.fileEntry = FileEntry(attributes: resultAsset.attributes)
				self.load()
			}
			else {
				self.fileDisplayDelegate?.screenlet?(self,
						onFileAssetError: NSError.errorWithCause(.invalidServerResponse,
								message: "No file entry found."))
			}
		}

		interactor.onFailure = {
			self.fileDisplayDelegate?.screenlet?(self, onFileAssetError: $0)
		}

		return interactor
	}

	fileprivate func createLoadFileInteractor() -> Interactor? {
		guard let fileEntry = fileEntry else {
			return nil
		}

		let interactor = LoadFileEntryInteractor(screenlet: self, fileEntry: fileEntry)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .remoteFirst

		interactor.onSuccess = {
			if let resultUrl = interactor.resultUrl {
				let title = fileEntry.title

				self.fileDisplayDelegate?.screenlet?(self, onFileAssetResponse: resultUrl as URL)

				self.fileDisplayViewModel?.url = resultUrl
				self.fileDisplayViewModel?.title = title
			}
			else {
				self.fileDisplayDelegate?.screenlet?(self,
						onFileAssetError: NSError.errorWithCause(.invalidServerResponse,
								message: "No file entry found."))
			}
		}

		interactor.onFailure = {
			self.fileDisplayDelegate?.screenlet?(self, onFileAssetError: $0)
		}

		return interactor
	}

}
