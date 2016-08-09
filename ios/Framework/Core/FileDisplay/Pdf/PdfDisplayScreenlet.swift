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


public class PdfDisplayScreenlet: FileDisplayScreenlet {

	//MARK: FileDisplayScreenlet

	override public func createLoadFileInteractor() -> Interactor? {
		if let fileEntry = fileEntry {
			let url = NSURL(string: LiferayServerContext.server + fileEntry.url)
			let interactor = LoadFileInteractor(screenlet: self, url: url)

			interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

			interactor.onSuccess = {
				if let resultUrl = interactor.resultUrl {
					let title = self.fileEntry!.title

					self.fileDisplayDelegate?.screenlet?(self, onFileAssetResponse: resultUrl)

					self.fileDisplayViewModel?.url = resultUrl
					self.fileDisplayViewModel?.title = title
				}
				else {
					self.fileDisplayDelegate?.screenlet?(self,
							onFileAssetError: NSError.errorWithCause(.InvalidServerResponse))
				}
			}

			interactor.onFailure = {
				self.fileDisplayDelegate?.screenlet?(self, onFileAssetError: $0)
			}

			return interactor
		}
		return nil
	}
}
