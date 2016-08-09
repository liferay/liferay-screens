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


@objc public protocol VideoDisplayScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: VideoDisplayScreenlet, onVideoAssetResponse url: NSURL)

	optional func screenlet(screenlet: VideoDisplayScreenlet, onVideoAssetError error: NSError)
}

public class VideoDisplayScreenlet: BaseFileDisplayScreenlet {

	public var videoDisplayDelegate: VideoDisplayScreenletDelegate? {
		return delegate as? VideoDisplayScreenletDelegate
	}

	public var videoDisplayViewModel: BaseFileDisplayViewModel? {
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
				self.videoDisplayDelegate?.screenlet?(self, onVideoAssetError:
					NSError.errorWithCause(.InvalidServerResponse))
			}
		}

		interactor.onFailure = {
			self.videoDisplayDelegate?.screenlet?(self, onVideoAssetError: $0)
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

					self.videoDisplayDelegate?.screenlet?(self, onVideoAssetResponse: resultUrl)

					self.videoDisplayViewModel?.url = resultUrl
					self.videoDisplayViewModel?.title = title
				}
				else {
					self.videoDisplayDelegate?.screenlet?(self, onVideoAssetError:
						NSError.errorWithCause(.InvalidServerResponse))
				}
			}

			interactor.onFailure = {
				self.videoDisplayDelegate?.screenlet?(self, onVideoAssetError: $0)
			}

			return interactor
		}
		return nil
	}
}
