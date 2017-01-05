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
	optional func screenlet(screenlet: FileDisplayScreenlet, onFileAssetResponse url: NSURL)

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error while retrieving the file.
	optional func screenlet(screenlet: FileDisplayScreenlet, onFileAssetError error: NSError)
}


public class FileDisplayScreenlet: BaseScreenlet {

	public static let LoadFileAction = "LoadFileAction"


	//MARK: Inspectables

	@IBInspectable public var assetEntryId: Int64 = 0

	@IBInspectable public var className: String =
		AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!
	
	@IBInspectable public var classPK: Int64 = 0

	@IBInspectable public var autoLoad: Bool = true

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue

	public class var supportedMimeTypes: [String] {
		return []
	}

	public var fileEntry: FileEntry?

	public var fileDisplayViewModel: FileDisplayViewModel? {
		return screenletView as? FileDisplayViewModel
	}

	public var fileDisplayDelegate: FileDisplayScreenletDelegate? {
		return delegate as? FileDisplayScreenletDelegate
	}


	//MARK: BaseScreenlet

	override public func onShow() {
		if autoLoad {
			load()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
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
	public func load() -> Bool {
		if fileEntry == nil {
			return performDefaultAction()
		}
		else {
			return self.performAction(name: FileDisplayScreenlet.LoadFileAction)
		}
	}


	//MARK: Private methods

	private func createLoadAssetInteractor() -> Interactor? {
		let interactor: LoadAssetInteractor

		if assetEntryId != 0 {
			interactor = LoadAssetInteractor(screenlet: self, assetEntryId: assetEntryId)
		}
		else {
			interactor = LoadAssetInteractor(
				screenlet: self, className: self.className, classPK: self.classPK)
		}

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			if let resultAsset = interactor.asset {
				self.fileEntry = FileEntry(attributes: resultAsset.attributes)
				self.load()
			}
			else {
				self.fileDisplayDelegate?.screenlet?(self,
						onFileAssetError: NSError.errorWithCause(.InvalidServerResponse,
								message: "No file entry found."))
			}
		}

		interactor.onFailure = {
			self.fileDisplayDelegate?.screenlet?(self, onFileAssetError: $0)
		}

		return interactor
	}

	private func createLoadFileInteractor() -> Interactor? {
		guard let fileEntry = fileEntry else {
			return nil
		}

		let interactor = LoadFileEntryInteractor(screenlet: self, fileEntry: fileEntry)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			if let resultUrl = interactor.resultUrl {
				let title = fileEntry.title

				self.fileDisplayDelegate?.screenlet?(self, onFileAssetResponse: resultUrl)

				self.fileDisplayViewModel?.url = resultUrl
				self.fileDisplayViewModel?.title = title
			}
			else {
				self.fileDisplayDelegate?.screenlet?(self,
						onFileAssetError: NSError.errorWithCause(.InvalidServerResponse,
								message: "No file entry found."))
			}
		}

		interactor.onFailure = {
			self.fileDisplayDelegate?.screenlet?(self, onFileAssetError: $0)
		}

		return interactor
	}

}
