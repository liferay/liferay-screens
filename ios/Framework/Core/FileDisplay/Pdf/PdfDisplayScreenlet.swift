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

	optional func screenlet(screenlet: PdfDisplayScreenlet, onPdfAssetResponse fileEntry: FileEntry)

	optional func screenlet(screenlet: PdfDisplayScreenlet, onPdfAssetError error: NSError)
}

public class PdfDisplayScreenlet: BaseScreenlet {

	@IBInspectable public var entryId: Int64 = 0

	@IBInspectable public var autoLoad: Bool = true

	public var fileEntry: FileEntry?
	
	public var pdfDisplayDelegate: PdfDisplayScreenletDelegate? {
		return delegate as? PdfDisplayScreenletDelegate
	}

	//MARK: Public methods

	override public func onShow() {
		if autoLoad && entryId != 0 {
			loadPdfAssetFromEntryId()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		let interactor = AssetDisplayInteractor(
			screenlet: self,
			entryId: self.entryId)

		interactor.onSuccess = {
			if let resultAsset = interactor.assetEntry {
				self.fileEntry = FileEntry(attributes: resultAsset.attributes)
				self.pdfDisplayDelegate?.screenlet?(self, onPdfAssetResponse: self.fileEntry!)

				(self.screenletView as! PdfDisplayViewModel).fileEntry = self.fileEntry!
			}
		}

		interactor.onFailure = {
			self.pdfDisplayDelegate?.screenlet?(self, onPdfAssetError: $0)
		}

		return interactor
	}

	public func loadPdfAssetFromEntryId() -> Bool {
		return self.performDefaultAction()
	}
}
