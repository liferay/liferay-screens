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


@objc public protocol PdfDisplayScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: PdfDisplayScreenlet, onPdfAssetResponse url: NSURL)

	optional func screenlet(screenlet: PdfDisplayScreenlet, onPdfAssetError error: NSError)
}

public class PdfDisplayScreenlet: BaseFileDisplayScreenlet {

	public var pdfDisplayDelegate: PdfDisplayScreenletDelegate? {
		return delegate as? PdfDisplayScreenletDelegate
	}

	public var pdfDisplayViewModel: BaseFileDisplayViewModel? {
		return screenletView as? BaseFileDisplayViewModel
	}

	//MARK: BaseFileDisplayScreenlet

	override public func createLoadAssetInteractor() -> Interactor? {
		let interactor = LoadAssetInteractor(
			screenlet: self, entryId: entryId, className: className, classPK: classPK)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			if let resultAsset = interactor.asset {
				self.fileEntry = FileEntry(attributes: resultAsset.attributes)
				self.load()
			}
			else {
				self.pdfDisplayDelegate?.screenlet?(self, onPdfAssetError:
					NSError.errorWithCause(.InvalidServerResponse))
			}
		}

		interactor.onFailure = {
			self.pdfDisplayDelegate?.screenlet?(self, onPdfAssetError: $0)
		}

		return interactor
	}

	override public func createLoadFileInteractor() -> Interactor? {
		if let fileEntry = fileEntry {
			let url = NSURL(string: LiferayServerContext.server + fileEntry.url)
			let interactor = LoadFileInteractor(screenlet: self, url: url)

			interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

			interactor.onSuccess = {
				if let resultUrl = interactor.resultUrl {
					let title = self.fileEntry!.title

					self.pdfDisplayDelegate?.screenlet?(self, onPdfAssetResponse: resultUrl)

					self.pdfDisplayViewModel?.url = resultUrl
					self.pdfDisplayViewModel?.title = title
				}
				else {
					self.pdfDisplayDelegate?.screenlet?(self, onPdfAssetError: NSError.errorWithCause(.InvalidServerResponse))
				}
			}

			interactor.onFailure = {
				self.pdfDisplayDelegate?.screenlet?(self, onPdfAssetError: $0)
			}

			return interactor
		}
		return nil
	}
}
