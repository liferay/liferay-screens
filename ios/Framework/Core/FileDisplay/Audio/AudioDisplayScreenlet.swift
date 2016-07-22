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
import AVFoundation


@objc public protocol AudioDisplayScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: AudioDisplayScreenlet, onAudioAssetResponse fileEntry: FileEntry)

	optional func screenlet(screenlet: AudioDisplayScreenlet, onAudioAssetError error: NSError)
}

public class AudioDisplayScreenlet: BaseScreenlet {

	@IBInspectable public var entryId: Int64 = 0

	@IBInspectable public var autoLoad: Bool = true

	public var fileEntry: FileEntry?

	public var audioDisplayDelegate: AudioDisplayScreenletDelegate? {
		return delegate as? AudioDisplayScreenletDelegate
	}

	//MARK: Public methods

	override public func onShow() {
		if autoLoad && entryId != 0 {
			loadAudioAssetFromEntryId()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		let interactor = AssetDisplayInteractor(
			screenlet: self,
			entryId: self.entryId)


		interactor.onSuccess = {
			if let resultAsset = interactor.assetEntry {
				self.fileEntry = FileEntry(attributes: resultAsset.attributes)
				self.audioDisplayDelegate?.screenlet?(self, onAudioAssetResponse: self.fileEntry!)

				(self.screenletView as! AudioDisplayViewModel).fileEntry = self.fileEntry!
				(self.screenletView as! AudioDisplayViewModel).audioTitle = self.fileEntry!.title
			}
		}

		interactor.onFailure = {
			self.audioDisplayDelegate?.screenlet?(self, onAudioAssetError: $0)
		}

		return interactor
	}

	public func loadAudioAssetFromEntryId() -> Bool {
		return self.performDefaultAction()
	}
}