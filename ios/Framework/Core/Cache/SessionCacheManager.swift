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


@objc public class SessionCacheManager: NSObject {

	private var session: LRSession?

	public init(session: LRSession?) {
		self.session = session

		super.init()
	}

	public func getString(#key: String, result: String? -> Void) {
		CacheManager.sharedManager.getString(
			collection: collectionName,
			key: key,
			result: result)
	}

	public func getAny(#key: String, result: AnyObject? -> Void) {
		CacheManager.sharedManager.getAny(
			collection: collectionName,
			key: key,
			result: result)
	}

	public func set(#key: String, string value: String) {
		CacheManager.sharedManager.set(
			collection: collectionName,
			key: key,
			string: value)
	}

	public func set(#key: String, value: NSCopying) {
		CacheManager.sharedManager.set(
			collection: collectionName,
			key: key,
			value: value)
	}

	public func remove(#key: String) {
		CacheManager.sharedManager.remove(
			collection: collectionName,
			key: key)
	}

	public func removeAll() {
		CacheManager.sharedManager.remove(
			collection: collectionName)
	}

	private var collectionName: String {
		return session?.server ?? "default"
	}

}
