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
import UIKit
import Kingfisher

private var lr_lastURLKey: Void?


extension UIImageView {

	public var lr_webURL: NSURL? {
		return objc_getAssociatedObject(self, &lr_lastURLKey) as? NSURL
	}

	private func lr_setWebURL(URL: NSURL) {
		objc_setAssociatedObject(self, &lr_lastURLKey, URL, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
	}

	public func lr_setImageWithURL(
			URL: NSURL,
			placeholderImage: Image? = nil,
			optionsInfo: KingfisherOptionsInfo? = nil) {

		switch(ImageCache.screensOfflinePolicy) {

		case CacheStrategyType.RemoteOnly.rawValue:
			var optionsInfoFinal = optionsInfo ?? []
			optionsInfoFinal.append(.ForceRefresh)
			optionsInfoFinal.append(.Transition(.Fade(0.2)))

			self.kf_setImageWithURL(URL, placeholderImage: placeholderImage, optionsInfo: optionsInfoFinal)

		case CacheStrategyType.RemoteFirst.rawValue:
			var optionsInfoFinal = optionsInfo ?? []
			optionsInfoFinal.append(.ForceRefresh)

			self.kf_setImageWithURL(
					URL,
					placeholderImage: placeholderImage,
					optionsInfo: optionsInfoFinal,
					completionHandler: { (image, error, cacheType, imageURL) in

						if (error != nil) {
							var optionsInfo = optionsInfo ?? []
							optionsInfo.append(.OnlyFromCache)

							self.kf_setImageWithURL(
								URL,
								placeholderImage: placeholderImage,
								optionsInfo: optionsInfo)
						}
					})

		case CacheStrategyType.CacheFirst.rawValue:
			self.kf_setImageWithURL(URL, placeholderImage: placeholderImage, optionsInfo: optionsInfo)

		case CacheStrategyType.CacheOnly.rawValue:
			var optionsInfoFinal = optionsInfo ?? []
			optionsInfoFinal.append(.OnlyFromCache)

			self.kf_setImageWithURL(
				URL,
				placeholderImage: placeholderImage,
				optionsInfo: optionsInfoFinal)

		default: break
		}
	}
}