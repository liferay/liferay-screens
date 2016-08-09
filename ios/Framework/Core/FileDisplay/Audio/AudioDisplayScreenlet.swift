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

	optional func screenlet(screenlet: AudioDisplayScreenlet, onAudioAssetResponse url: NSURL)

	optional func screenlet(screenlet: AudioDisplayScreenlet, onAudioAssetError error: NSError)
}

public class AudioDisplayScreenlet: FileDisplayScreenlet {

	public var audioDisplayDelegate: AudioDisplayScreenletDelegate? {
		return delegate as? AudioDisplayScreenletDelegate
	}

	//MARK: FileDisplayScreenlet

	override public func createLoadAssetInteractor() -> Interactor? {
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
				self.audioDisplayDelegate?.screenlet?(self, onAudioAssetError:
					NSError.errorWithCause(.InvalidServerResponse))
			}
		}

		interactor.onFailure = {
			self.audioDisplayDelegate?.screenlet?(self, onAudioAssetError: $0)
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

					self.audioDisplayDelegate?.screenlet?(self, onAudioAssetResponse: resultUrl)

					self.fileDisplayViewModel?.url = resultUrl
					self.fileDisplayViewModel?.title = title
				}
				else {
					self.audioDisplayDelegate?.screenlet?(self, onAudioAssetError:
						NSError.errorWithCause(.InvalidServerResponse))
				}
			}

			interactor.onFailure = {
				self.audioDisplayDelegate?.screenlet?(self, onAudioAssetError: $0)
			}

			return interactor
		}
		return nil
	}
}
