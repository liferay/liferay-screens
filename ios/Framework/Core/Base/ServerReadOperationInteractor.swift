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


public class ServerReadOperationInteractor: ServerOperationInteractor {

	override public func getCacheStrategyImpl(strategyType: CacheStrategyType) -> CacheStrategy {
		switch strategyType {
		case .OnlineOnly:
			return createStrategyReadOnlineOnly()

		case .CacheOnly:
			return defaultStrategyReadFromCache

		case .OnlineFirst:
			return createStrategy(
				whenFails: createStrategyReadOnlineOnly(),
				then: defaultStrategyReadFromCache)

		case .CacheFirst:
			return createStrategy(
				whenFails: defaultStrategyReadFromCache,
				then: createStrategyReadOnlineOnly())
		}
	}

	private func createStrategyReadOnlineOnly() -> CacheStrategy {
		return createStrategy(
			whenSucceeds: defaultStrategyOnline,
			then: defaultStrategyWriteToCache)
	}

}