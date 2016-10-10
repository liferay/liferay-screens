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
import LiferayScreens

extension LiferayServerContext {
	
	public class func property(key: String) -> AnyObject {
		guard let value = propertyForKey(key) else {
			fatalError("Missing key \(key) on liferay-server-context.plist file")
		}
		
		return value
	}
	
	public class func numberProperty(key: String) -> NSNumber {
		guard let value = property(key) as? NSNumber else {
			fatalError("Key \(key) is not a NSNumber")
		}
		
		return value
	}
	
	public class func longProperty(key: String) -> Int64 {
		return numberProperty(key).longLongValue
	}
	
	public class func stringProperty(key: String) -> String {
		guard let value = property(key) as? String else {
			fatalError("Key \(key) is not a String")
		}
		
		return value
	}
}
