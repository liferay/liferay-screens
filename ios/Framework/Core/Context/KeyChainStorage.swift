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

#if LIFERAY_SCREENS_FRAMEWORK
	import UICKeyChainStore
#endif


protocol KeyChainStorage {

	func setData(data: NSData, forKey: String) -> Bool
	func dataForKey(key: String) -> NSData!
	func removeItemForKey(key: String) -> Bool

}


class KeyChainStorageImpl : KeyChainStorage {

	func setData(data: NSData, forKey key: String) -> Bool {
		return UICKeyChainStore.setData(data, forKey:key)
	}

	func dataForKey(key: String) -> NSData! {
		return UICKeyChainStore.dataForKey(key)
	}

	func removeItemForKey(key: String) -> Bool {
		return UICKeyChainStore.removeItemForKey(key)
	}

}
